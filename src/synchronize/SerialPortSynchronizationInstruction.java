/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package synchronize;

import sesameDoorsApp.*;


import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LamineBA
 */
public class SerialPortSynchronizationInstruction extends SerialPortGPIO{
    
    private String id_sesame_sharer = null;
    private DeviceLinkingData device = null;
    private String id_sesame_accredited = null;
    private String key_accredited = null;
    
    public SerialPortSynchronizationInstruction(int baudrate, String identifiant, DeviceLinkingData device){
        super(baudrate);
        id_sesame_sharer = identifiant;
        this.device = device;
        
        try {
            Thread.sleep(1000);
            super.sendData(BONJOUR);
        } catch (InterruptedException ex) {}
    }
    
    /**
     * Methode : analyzeDataReceived => Traitement des données recu
     * @param received_data
     * @throws java.lang.InterruptedException
     */
    @Override
    public void analyzeDataReceived(String received_data) throws InterruptedException{
        super.setLastReceivedData(received_data);
        switch (received_data) {
            
            case BONJOUR:
                Thread.sleep(100);
                System.out.println("|BONJOUR| recu dans la classe herité");
                super.sendData(BONJOUR);
                break;
                
            case DEMANDE_SYNCHRONIZATION_APRES_PARTAGE_ACCES:
                Thread.sleep(100);
                System.out.println("'DEMANDE_SYNCHRONIZATION_APRES_PARTAGE_ACCES' recu dans la classe herité");
                super.sendData(DEMANDE_SYNCHRONIZATION_APRES_PARTAGE_ACCES_AUTORISEE);
                break;
                        
            case PREPARATION_ENREGISTREMENT_CLE_ACCES_ACCREDITEE:
                Thread.sleep(1000);
                System.out.println("'PREPARATION_ENREGISTREMENT_CLE_ACCES_ACCREDITEE' recu dans la classe herité");
                super.sendData(SESAME_PRET_ENREGISTRE_CLE_ACCES);
                break;
                
            case BEGIN:
                System.out.println("|BEGIN| dans la classe hérité");
                Thread.sleep(100);
                // reset the buffer
                super.resetBufferReception();
                super.setSavingFlag(true);
                break;
                
            case END:
                Thread.sleep(100);
                System.out.println("|END| dans la classe hérité");
                super.setSavingFlag(false);
                this.checkBufferData();
                break;

            default:
                break;
        }
    }
    
    /**
     * Methode : saveAccreditedInformation() allows you to save all the information about the accredited user
     * in a ser file. 
     * @param data : contains the data received in the serial port
     * @return true if the owner information is correct
     * @throws java.lang.InterruptedException
     */
    private boolean saveAccreditedInformation(String [] data) throws InterruptedException {
        boolean flag = false;

        if (super.isChecksumCorrect(data)){
            OwnerInformation user = new OwnerInformation(data);
            // Make the Serialization before closing the windows
            File file = new File("accredited_information.ser");
            try{
                try (FileOutputStream fileOut = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                    out.writeObject(user);
                    System.out.println(user);
                    flag = true;
                    System.out.println("accredited_information.ser file is created correcly");
                }
            }catch(IOException i){
                System.out.println("Exception de Serialisation " + i.getMessage());
                flag = false;
            }
        }
        else{
            System.out.println("The data about the accredited user is not saved");
            flag = false;
        }

        return flag;
    }
    
    
    /**
     * Methode checkBufferData() allows you to verify all the data saved in the buffer
     * @throws java.lang.InterruptedException
     */
    public void checkBufferData() throws InterruptedException{
        System.out.println("<--- BEGIN OF CALLING checkBufferData() methode --->");
        
        // Get the data saved in the buffer
        System.out.println("Contents of the buffer : " + super.getBufferReception());
        String [] data_in = SerialPortGPIO.extractBufferData(super.getBufferReception());
        
        //reset the reception buffer
        super.resetBufferReception();
        
        // Extract the first and last data to check the kind of request
        String first_data = data_in[0];
        String last_data  = data_in[data_in.length - 1];
        
        System.out.println("First = " + first_data);
        System.out.println("Last  = " + last_data);
        
        // Extract only the data about the request
        int size_data = data_in.length -2;
        String [] data_temp = new String[size_data];
        System.arraycopy(data_in, 1, data_temp, 0, size_data);
        for (int i=0; i<data_temp.length; i++){
            //data_temp[i] = data_in[i+1];
            System.out.println("Classe fille : valide data["+i+"] = " + data_temp[i]);
        }
        
        // Call the checkSavedData() methode to identify the kind of request
        boolean flag = checkSavedData(first_data, last_data, data_temp);
        
        System.out.println("<--- END OF CALLING checkBufferData() methode --->");
    }
    
    /**
     * Methode : checkSavedData : Traitement des données recu et sauvegarder dans le buffer de reception
     * @param first_data
     * @param last_data
     * @param data
     * @return flag : if the data is saved correctly
     * @throws java.lang.InterruptedException
     */
    private boolean checkSavedData(String first_data, String last_data, String [] data) throws InterruptedException{
        System.out.println("<--- BEGIN OF THE checkSavedData() methode --->");
        boolean flag = false;
        
        if ((first_data != null && first_data.equals(DEBUT_ENVOIE_INFORMATION_ACCREDITEE)) && 
            (last_data  != null  && last_data.equals(FIN_ENVOIE_INFORMATION_ACCREDITEE))){
            System.out.println("First if");
            if(data != null){
                
                System.out.println("Before calling saveAccreditedInformation()");
                flag = saveAccreditedInformation(data);
                System.out.println("After calling saveAccreditedInformation()");
                
                if(flag){
                    System.out.println("The data is saved correctly");
                    Thread.sleep(500);
                    super.sendData(INFORMATION_ACCREDITEE_ENREGISTRE_CORRECTEMENT);
                }
                else{
                    System.out.println("The data is not saved because it contains some invalid data");
                    Thread.sleep(500);
                    super.sendData(INFORMATION_ACCREDITEE_DONNEES_ERONEES);
                }
            }
            else{
                System.out.println("The data is not saved because it contains some invalid data");
                Thread.sleep(500);
                super.sendData(INFORMATION_ACCREDITEE_DONNEES_ERONEES);
                System.out.println("Le buffer est invalide");
                flag = false;
            }   
        }
        if ((first_data != null && first_data.equals(DEBUT_ENVOIE_CLE_ACCES_ACCREDITEE)) && 
            (last_data  != null  && last_data.equals(FIN_ENVOIE_CLE_ACCES_ACCREDITEE))){
            System.out.println("First if");
            if(data != null){
                
                System.out.println("Before calling checkSharingConfirmation()");
                flag = addDeviceLink(data);
                System.out.println("After calling checkSharingConfirmation()");
                
                if(flag){
                    System.out.println("The data is saved correctly");
                    Thread.sleep(500);
                    super.sendData(CLE_ACCES_ACCREDITEE_ENREGISTREE_CORRECTEMENT);
                    this.getSerial().shutdown();
                    super.openUartPort();
                }
                else{
                    System.out.println("The data is not saved because it contains some invalid data");
                    Thread.sleep(500);
                    super.sendData(CLE_ACCES_ACCREDITEE_DONNEES_ERONNEES);
                }
            }
            else{
                System.out.println("The data is not saved because it contains some invalid data");
                Thread.sleep(500);
                super.sendData(CLE_ACCES_ACCREDITEE_DONNEES_ERONNEES);
                System.out.println("Le buffer est invalide");
                flag = false;
            }   
        }
        // ===>
        else{
            flag = false;
            System.out.println("First Else ");
        }
        
        System.out.println("<--- END OF THE checkSavedData() methode -->");
        return flag; 
    }
    
    
    /**
     * Methode : addDeviceLink() allows you to add the device in the table which containing 
     * the id and the key information. 
     * @param data
     * @return true if the owner information is correct
     * @throws java.lang.InterruptedException
     */
    private boolean addDeviceLink(String [] data) throws InterruptedException {
        
        boolean flag = false;
        boolean flag_checksum = false;
        boolean flag_extract_user = false;
        boolean flag_extract_device = false;
        boolean flag_creating_device = false;
        boolean flag_serialization = false;
        boolean flag_end = false;
        
        int checksum_received = 0; 
        int checksum_calcule = 0;
        
        int key_size = 0;
        
        String id_device = "";
        OwnerInformation accredited_user = null;
        DeviceLinkingData device_linking = null;
        
        DeviceLinkedData device_linked = null;
        
        IdentifiantAndKeyTable table_temp = null;
        
        if (data != null && data.length >= 2){
            key_accredited = data[0];
            key_size = key_accredited.length();
            
            // Extract the checksul and convert it
            try{
                checksum_received = Integer.parseInt(data[1]);
            }catch(NumberFormatException ex){
                checksum_received = 0;
                System.out.println("Impossible de convert String to Intger : " + ex.getMessage());
            }
            
            // Calcul du checksul
            checksum_calcule  = (key_size*128) + (key_size/2)*64 + (key_size/4)*32; 
            checksum_calcule += (key_size/8)*16 + (key_size/16)*8 + (key_size/32)*4;
            checksum_calcule += (key_size/64)*2;
            
        }
        else{
            System.out.println("L'argument passé en paramètre est invalide");
            flag= false;
        }
        
        flag_checksum = checksum_calcule == checksum_received;
        
        // Test if the checksum is correct or not 
        if (flag_checksum){
            System.out.println("CRC Correct");

            File file = new File("accredited_information.ser");
            // Deserialization otf the OwnerInformation
            try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                accredited_user = (OwnerInformation) in.readObject();
                flag_extract_user = true;
                
            }catch(IOException i){
                flag_extract_user = false;
                System.out.println("IOException : " + i.getMessage());
            }catch(ClassNotFoundException c){
                flag_extract_user = false;
                System.out.println("OwnerInformation class not found " + c.getMessage());
            }
                    
            file = new File("linking_test.ser");
            // Deserialization of the DeviceLinkingInfo
            try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                device_linking = (DeviceLinkingData) in.readObject();
                System.out.println("DEVICE LINKING DEBUG = \n" + device_linking);
                flag_extract_device = true;
                
            }catch(IOException i){
                flag_extract_device = false;
                System.out.println("IOException : " + i.getMessage());
            }catch(ClassNotFoundException c){
                flag_extract_device = false;
                System.out.println("DeviceLinkingData class not found " + c.getMessage());
            }
        }
        else{
            flag_extract_user = false;
            flag_extract_device = false;
            System.out.println("CRC inCorrect");
        }

        // Check if the extraction of the owner is done succesfully, create the class DeviceLinkedData
        if (flag_extract_user && flag_extract_device){
            id_device = device_linking.getDeviceIdentifiant();
            
            if (id_device != null){
                System.out.println("Identifiant du Device partagé = " + id_device);
            }
            else{
                System.out.println("Identifiant du Device partagé est null");
                id_device = "TOTO";
            }
            
            // Create the DeviceLinkedData class
            //device_linked = new DeviceLinkedData (user,device_linking, id_device, key);
            //device_linked = new DeviceLinkedData (accredited_user,device_linking, id_device, key_accredited);
            device_linked = new DeviceLinkedData (accredited_user,device, id_sesame_sharer, key_accredited);
            flag_creating_device = true;
            System.out.println("DEVICE LINKED DEBUG = \n" + device_linked);
            System.out.println("device_linked.getDeviceId() = " + device_linked.getDeviceId() );
        }
        else{
            flag_creating_device = false;
        }
        
        
        // Check if the class "DeviceLinkedData" is created correctly
        if (flag_creating_device){
            // Make the deserialization of the table file which is the database of the device
            File file = new File("identifiant_and_key_table.ser");
            try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                table_temp = (IdentifiantAndKeyTable) in.readObject();

                // Add the device link information on the table
                table_temp.addDeviceForLink(device_linked);
                System.out.println("Content of the table after");
                System.out.println(table_temp);

                flag_serialization = true;

            }catch(IOException i){
                flag_serialization = false;
                System.out.println("IOException : " + i.getMessage());
            }catch(ClassNotFoundException c){
                flag_serialization =false;
                System.out.println("IdentifiantAndKeyTable class not found");
            }
        }
        else{
            System.out.println("'identifiant_and_key_table.ser' file is not found in the current directory");
            flag_serialization = false;
        }

        // Check if the flag serialization is correct then make the serialization in the file   
        if (flag_serialization){
            // Make the serialization to save the new added device on the table
            File file = new File("identifiant_and_key_table.ser");
            try(FileOutputStream fileOut = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(table_temp);
                // End of the Serialization for IdentifiantAndKeyTable
                System.out.println("Serialized of the new IdentifiantAndKeyTable is saved in identifiant_and_key_table.ser");
                flag_end = true;
            }catch(IOException io){
                flag_end = false;
                System.out.println("Exception for IdentifiantAndKeyTable : " + io.getMessage());
                System.out.println("Serialized of the new IdentifiantAndKeyTable is not done : check error");
            }
        }
        else{
            flag_end = false;
            System.out.println("impossible to read the 'identifiant_and_key_table.ser' file or the file is not found");
        }
        
        return flag_end;
    }
    
    
    public static void main(String [] args){
        /*SerialPortSynchronizationInstruction test = new SerialPortSynchronizationInstruction(115200);
        int count = 0;
        while (true){
            if (count<5){
                System.out.println("Count SESAME DOORS = " + count);
            }
            count ++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
        }*/
    }
}
