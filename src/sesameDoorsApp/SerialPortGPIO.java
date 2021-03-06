// SESAME DOORS SHARER

package sesameDoorsApp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import synchronize.*;
import com.pi4j.io.serial.*;
import java.io.*;

/**
 * This example code demonstrates how to perform serial communications using the Raspberry Pi.
 * @author Robert Savage
 */
public class SerialPortGPIO implements ConstantsConfiguration{
    
    private String reception_buffer;
    private boolean flag_saving = false;
    private int baudrate = 0;
    private Serial serial;
    UARTListener uart_listener = null;
    private StringBuffer buffer;
    private IdentifiantAndKeyTable table_id_key;
    private String identifiant = "";
    private String key = "";
    private DeviceLinkingData device = null;
    private SerialPortSynchronizationInstruction port_synchro = null;

    /**
     * Constructor
     * @param baudrate is the speed of the uart communication
     */
    public SerialPortGPIO (int baudrate) {
        this.baudrate = baudrate;
        System.out.println("<-------------------------------------------------------------->");
        System.out.println("<--- Pi4J Serial Communication Class ... started            --->");
        System.out.println("<--- connect using settings: "+ this.baudrate + ", N, 8, 1                --->");
        System.out.println("<--- data received on serial port should be displayed below --->");
        System.out.println("<-------------------------------------------------------------->");              
        System.out.println("<--------------- SESAME DOORS STARTED ------------------------->");      
        System.out.println("<-------------------------------------------------------------->");
        this.initialize();
    }
    
    /**
     * Methode : initialize() allows you to initialize the uart port. 
     */
    private void initialize() {
        System.out.println("Initialisation du port de communication serie");
        // create an instance of the serial communications class
        serial = SerialFactory.createInstance();
        
        // open the default serial port provided on the GPIO header
        serial.open(Serial.DEFAULT_COM_PORT, this.baudrate);
        serial.flush();
        
        // create and register the serial data listener
        uart_listener = new UARTListener(this);
        serial.addListener(uart_listener);
        
        /*// Create the table which contains all identifiant and key with the linking information. 
        table_id_key = new IdentifiantAndKeyTable();
        System.out.println(table_id_key);

        // Make the Serialization for IdentifiantAndKeyTable 
        File file = new File("identifiant_and_key_table.ser");
        try(FileOutputStream fileOut = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(table_id_key);
        }catch(IOException i){
            System.out.println("Exception for IdentifiantAndKeyTable");
        }
        // End of the Serialization for IdentifiantAndKeyTable
        System.out.println("Serialized of IdentifiantAndKeyTable is saved in identifiant_and_key_table.ser");*/

        buffer = new StringBuffer("");
    }

    /**
     * Methode : setDataBufferReception(String data)
     * @param data
     */
    public void setLastReceivedData (String data){
        this.reception_buffer = data;
    }
    
    
    /**
     * Methode getNewDataReceived allow you to get the contains of the uart reception buffer
     * @return reception_buffer 
     */
    public String getLastReceivedData (){
        return reception_buffer;
    }

    /**
     * Methode getSerial()
     * @return 
     */
    public Serial getSerial(){
        return this.serial;
    }
    
    /**
     * Methode getSavingFlag()
     * @return flag_saving
     */
    public boolean getSavingFlag(){
        return this.flag_saving;
    }
    
    /**
     * Methode setSavingFlag()
     * @param flag
     */
    public void setSavingFlag(boolean flag){
        this.flag_saving = flag;
    }

    /**
     * Methode setBufferReception() allows you to write the received data in the buffer which you want
     * @param data : is the data which received by the serial port
     */
    public void setBufferReception (String data){
        buffer.append(data);
    }
    
    /**
     * Methode getBufferReception allow you to get the contains of the uart buffer
     * @return buffer 
     */
    public String getBufferReception (){
        return buffer.toString();
    }

    /**
     * Methode : resetBufferReception(String data)
     */
    public void resetBufferReception (){
        int len = buffer.length();
        buffer.delete(0, len);        
    }
    /**
     * Methode sendData allow you to send data via the uart
     * @param data_to_send
     * @throws java.lang.InterruptedException
     */
    public void sendData (String data_to_send) throws InterruptedException{  
        try {
            // Call methode sampleDataToSend() to arrange the data to send by package of 10 char maxi.  
            String [] data_temp = sampleDataToSend(data_to_send);
            
            for(int j=0;j<data_temp.length;j++){
                Thread.sleep(250);
                System.out.println("Data sent = [" + data_temp[j] + "]");
                serial.writeln(data_temp[j]);
            }
        }
        catch(IllegalStateException ex){
        }
        catch(SerialPortException ex) {
            System.out.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
        }	
    }
    
    /**
     * Methode : sendData() allows you to send all the data about the Owner of the SESAME
     * @param data
     * @throws java.lang.InterruptedException
     */
    public void sendData(String [] data) throws InterruptedException{
        String [] data_to_send = formatData(data);
        int i=0;
        while (i<data_to_send.length){
            sendData(data_to_send[i]);
            i++;
        }        
    }
    
    /**
     * Methode : formatData() allows you to arrange data to the correct format for sending
     * @param data : is the data arranged on the String Array
     * @return data_out : data arranged on the correct format for sending
     */
    private String [] formatData(String [] data) {
        String [] buffer_temp = new String[100];
        String [] data_out;
        // Send the first char to prevent the receiver "B" = Begin
        buffer_temp[0] = BEGIN;
        int k = 0;
        // Put the linking information 
        for (int i=0; i<data.length; i=i+1){
            k=2*i+1;
            buffer_temp[k] = String.format("%02d", data[i].length());
            buffer_temp[k+1] = data[i];
        }
        // Send the last char to end the transmission : "E" = End
        buffer_temp[k+2] = END;
        // Count the size of the String array
        int size=0;
        int count=0;
        while(buffer_temp[count]!= null){
            count ++;
        }
        size = count;
        // Make a table which size is the size of the data available on the buffer data_temp
        data_out = new String[size];
        
        // Copy the data on the buffer which will returned by the medthode
        System.arraycopy(buffer_temp, 0, data_out, 0, size);
        //System.out.println("Data formatted = " + Arrays.toString(data_out));
        return data_out;
    }
    
    /**
     * Methode : sampleDataToSend allow you to sample the data that will sent by the uart
     * @param data_to_send
     * @return String[] containing all the sampled data
     */
    private static String [] sampleDataToSend(String data_to_send){
        String data_to_send_in = data_to_send;
        String [] data_sampled_temp = new String[20];
        String [] data_sampled_out;
        
        boolean flag_data = false; 
        String data_to_send_temp = "";
        
        int size_original = data_to_send_in.length();
        char[] charArray = data_to_send_in.toCharArray();
        //System.out.println("Char = " + Arrays.toString(charArray));
        int size_rest = 0; 
        int number_char_to_delete = 10;
        int count = 0;
        
        if (size_original >10){
            //System.out.println("Size of the buffer is superior to 10");
            flag_data = true;
            
            while(flag_data){
               
                if (size_original - number_char_to_delete>0){
                    size_rest = number_char_to_delete;
                }
                else{
                    size_rest = size_original;
                }
                data_to_send_temp = "";
                // Extract the character
                for (int i=0; i<size_rest; i++){
                    data_to_send_temp += String.valueOf(charArray[i]); 
                }
                //System.out.println("Data extracted = " + data_to_send_temp);
                data_sampled_temp[count] = data_to_send_temp;
                count ++;
                        
                // delete the character that is extracted
                StringBuilder sb = new StringBuilder();
                sb.append(data_to_send_in);
                //System.out.println("StringBuilder = " + sb.toString());
                sb.delete(0, size_rest);
                data_to_send_in = sb.toString();
                //System.out.println("Buffer after = " + data_to_send);
                charArray = data_to_send_in.toCharArray();

                if (size_original == size_rest){
                    flag_data = false;
                }
                else{
                    size_original = data_to_send_in.length();
                    flag_data = true;
                }
            }
        }
        else{
            count = 0;
            data_sampled_temp[0] = data_to_send;
        }
        // Extract the valid data on the data_sampled_temp
        int count_data = 0;
        while(data_sampled_temp[count_data]!= null){
            count_data ++;
        }
        
        data_sampled_out = new String[count_data];
        
        for(int i=0;i<count_data;i++){
            data_sampled_out[i] = data_sampled_temp[i];
            //System.out.println("Data extraction methode["+i+"] = " + data_sampled_out[i]);
        }
        
        return data_sampled_out; 
    }
    
    /**
     * Methode extractBufferData() allows you to extract all the data saved in the serial buffer
     * @param string_buffer is the input of the methode which contains the received data
     * @return data_out[] is the data arranged on the array String
     */
    public static String [] extractBufferData(String string_buffer){
        System.out.println("<--- BEGIN OF THE extractBufferData() methode --->");
        
        String str_arg_clean = string_buffer.replace("\n", "").replace("\r", "");
        
        StringBuffer str_arg = new StringBuffer(str_arg_clean);
        
        String [] data_in = new String[100];
        String [] data_out;
        
        System.out.println("Buffer = " + str_arg);
        
        boolean flag_buffer_content = str_arg.length() > 0;
        
        //Check the data saved in the buffer
        int buffer_size = 0;       
        
        // Put the buffer contents in the char Array
        char[] charArray = null;
        StringBuffer temp = new StringBuffer("");
        int len_temp = 0;
        String str_default = null;
        
        if (flag_buffer_content){
            buffer_size = str_arg.length(); 
            str_default = str_arg.toString();
            charArray = str_default.toCharArray();
        }
        else{
            System.out.println("Le buffer est vide");
            str_default = "02AB02CD06EFGHIJ";
            buffer_size = str_default.length();
            charArray = str_default.toCharArray();
        }
        
        // Extract the first data which contains 7 charactere
        int count = 0;
        int size = 0;

        // Extract the first size of the first frame
        temp.insert(0, String.valueOf(charArray[count]));
        len_temp = temp.length();
        temp.insert(len_temp, String.valueOf(charArray[count+1])); //temp = String.valueOf(charArray[count]) + String.valueOf(charArray[count+1]);
        count = count + 2;
        
        int size_charactere = 0;
        
        try{
            size_charactere = Integer.parseInt(temp.toString());
        }catch(NumberFormatException ex){
            size_charactere = 0;
            System.out.println("Error while trying to convert String to Integer. The data is : " + temp);
        }
        
        boolean flag_extraction = true;
        while ((count <= buffer_size-1) && flag_extraction){
            len_temp = temp.length();
            temp.delete(0, len_temp);
            //temp = "";
            
            for (int j=count; j<(count + size_charactere); j++){
                char char_ = charArray[j];
                len_temp = temp.length();
                temp.insert(len_temp, String.valueOf(char_));
                //temp = temp + String.valueOf(char_);
            }
            data_in[size] = temp.toString();
            size ++;
            
            // Check if you have got the end of the buffer to stop extracting
            if (buffer_size - (count+size_charactere) <=1){
                flag_extraction = false;
            }
            else{
                len_temp = temp.length();
                temp.delete(0, len_temp);
                
                temp.insert(0, String.valueOf(charArray[count + size_charactere]));
                len_temp = temp.length();
                temp.insert(len_temp, String.valueOf(charArray[count + size_charactere+1]));
                //temp = String.valueOf(charArray[count + size_charactere]) + String.valueOf(charArray[count + size_charactere+1]);                
                
                count = count + size_charactere + 2;
                try{
                    size_charactere = Integer.parseInt(temp.toString());
                }catch(NumberFormatException ex){
                    size_charactere = 0;
                    System.out.println("Error while trying to convert String to Integer " + ex.getMessage());
                }
            }
            
        }
        
        int count_data = 0;
        while(data_in[count_data]!= null){
            count_data ++;
        }
        data_out = new String[count_data];
        System.arraycopy(data_in, 0, data_out, 0, count_data);
        
        System.out.println("<--- END OF THE extractBufferData() methode --->");
        
        return data_out; 
    }


    /**
     * Methode : openUartPort() allow you to open the uart port if this one is closed
     */
    public void openUartPort (){
        if (this.serial.isOpen()){
            System.out.println("Le port est déjà ouvert");
            serial.addListener(uart_listener);
        }
        else{
            try {
                // Re-open the UART port
                serial.open(Serial.DEFAULT_COM_PORT, this.baudrate);
                serial.addListener(uart_listener);
            }
            catch(IllegalStateException ex){
                System.err.println("Impossible to re-open the uart port");
            }
            
            if (this.serial.isOpen()){
                System.out.println("Le port est déjà ouvert");
            }
            else{
                System.err.println("Impossible d'ouvrir le port");
            }
        }
    }
    
    /**
     * Methode : closeUartPort() allow you to close the uart port is this one is already open
     */
    public void closeUartPort (){
        if (this.serial.isOpen()){
            // Remove first the listener
            this.serial.removeListener(uart_listener);
            this.serial.close();
            System.out.println("Fermeture immédiat du port UART");
        }
        else{
            System.out.println("Le port UART est déjà fermé");
        }
    }
    
    /**
     * Methode : waitUartPort()
     * @param time_to_wait : is the time wich the Uart will wait
     * @throws java.lang.InterruptedException
     */
    public void waitUartPort (int time_to_wait) throws InterruptedException{
        synchronized(this){
            try {
                this.wait(time_to_wait);
            } catch(InterruptedException e) {
            }
        }
        synchronized(this) {
            this.notify();
        }
    } 
    
    /**
     * Methode : analyzeDataReceived => Traitement des données recu
     * @param received_data
     * @throws java.lang.InterruptedException
     */
    public void analyzeDataReceived(String received_data) throws InterruptedException{
        
        switch (received_data) {
            case BONJOUR:
                Thread.sleep(100);
                System.out.println("|BONJOUR| envoyé avec succès");
                sendData(BONJOUR);
                break;
                
            case DEMANDE_ENREGISTREMENT_PROPRIETAIRE:
                Thread.sleep(100);
                System.out.println("|ENREGISTREMENT_PROPRIETAIRE_AUTORISEE| envoyé avec succès");
                sendData(ENREGISTREMENT_PROPRIETAIRE_AUTORISEE);
                break;

            case DEMANDE_RATTACHEMENT_SESAME:
                Thread.sleep(100);
                System.out.println("|RATTACHEMENT_AUTORISEE_PERIPHERIQUE| envoyé avec succès");
                sendData(RATTACHEMENT_AUTORISEE_PERIPHERIQUE);
                break;
                
            case DEMANDE_CONFIRMATION_RATTACHEMENT:
                Thread.sleep(100);
                System.out.println("DEMANDE_CONFIRMATION_RATTACHEMENT recu");
                boolean flag_l = sendLinkingConfirmation();
                System.out.println("Confirmation de rattachement => Status = " + flag_l);
                break;
                
            case DEMANDE_ETABLISSEMENT_CONNEXION_ACCES:
                Thread.sleep(100);
                System.out.println("|ETABLISSEMENT_CONNEXION_ACCES_ETABLIE| envoyé avec succès");
                sendData(ETABLISSEMENT_CONNEXION_ACCES_ETABLIE);
                break;
                
            case SCAN_SESAME_ENVIRONNANT:
                System.out.println("SCAN_SESAME_ENVIRONNANT frame is received");
                Thread.sleep(100);
                String device_name = "RPi_M1";
                sendData(device_name);
                break;
                
            case DEMANDE_SYNCHRONIZATION_APRES_PARTAGE_ACCES:
                System.out.println("'DEMANDE_SYNCHRONIZATION_APRES_PARTAGE_ACCES' frame is received");
                this.closeUartPort();
                serial.shutdown();
                Thread.sleep(100);
                port_synchro = new SerialPortSynchronizationInstruction(baudrate, identifiant, device);
                break;
                
                
            case BEGIN:
                Thread.sleep(100);
                System.out.println("Reception de BEGIN");
                // reset the buffer
                resetBufferReception();
                flag_saving = true;
                break;
                
            case END:
                Thread.sleep(100);
                System.out.println("Reception de END");
                flag_saving = false;
                this.checkBufferData();
                break;
                
            case MERCI:
                System.out.println("Information envoyée avec succès et rajout de l'écouteur");
                sendData(FIN_DE_LA_COMMUNICATION);
                break;
                
            default:
                break;
        }
    }
    
    /**
     * Methode checkReceivedData()
     * @param data : is the array String which contains the received data by the serial port
     * @return status=true if the owner information is received correctly. 
     */
    private boolean checkReceivedData (String [] data){
        boolean status = true;
        if (data.length>0){
            for (int i=0; i<data.length; i++) {
                status = status && data[i] != null && !data[i].isEmpty();
            }
        } 
        else{
            status = false;
        }
        
        return status;
    }
    
    /**
     * Methode isChecksumCorrect()
     * @param data
     * @return status=true if the checksum is correct. 
     * @throws java.lang.InterruptedException 
     */
    public boolean isChecksumCorrect (String [] data) throws InterruptedException{
        boolean status = false;
        int formule = 0;
        int CRC = 0;
                
        if (checkReceivedData(data) && data.length>0){
            int number_element = data.length-1;
            System.out.println("Taille de data = " + number_element);
            for (int i=0; i<number_element; i++){
                formule = formule + (number_element -i)*data[i].length();
            }
            
            int checksum = formule - number_element*128;
            System.out.println("Resultat du checksum = " + checksum);
            
            try{
                CRC = Integer.parseInt(data[number_element]);
                System.out.println("Checksum conversion to integer done successfully");
            }
            catch(NumberFormatException ex){
                System.out.println("Le checksum recu n'est un pas un entier" + ex.getMessage());
                CRC = 0;
            }
            
            if ((checksum - CRC)==0){
                System.out.println("CRC Correct");
                status = true;
            }
            else{
                System.out.println("CRC wrong");
                status = false;
            }
        }
        else{
            status = false;
        }
        return status;
    }

    /**
     * Methode : saveOwnerInformation() allows you to save all the information about the user
     * in a ser file. 
     * @param data : contains the data received in the serial port
     * @return true if the owner information is correct
     * @throws java.lang.InterruptedException
     */
    private boolean saveOwnerInformation(String [] data) throws InterruptedException {
        
        boolean flag_checksum = false;
        boolean flag_creating_owner = false;
        boolean flag_serialization = false;
        boolean flag_end = false;
        
        OwnerInformation owner = null;

        if (isChecksumCorrect(data)){
            flag_checksum = true;
        }
        else{
            System.out.println("The data about the owner of the SESAME is not saved");
            flag_checksum = false;
        }
        
        // Check if the checksum is correct and the data is valide
        if (flag_checksum){
            owner = new OwnerInformation(data);
            flag_creating_owner = true;
            System.out.println("Object OwnerInformation is created and all received data is added succesfully");
        }
        else{
            flag_creating_owner = false;
            System.out.println("Object is not created in the memory");
        }

        // if the OwnerInformation object is create, then make the serialization
        if(flag_creating_owner){
            // Make the Serialization before closing the windows
            File file = new File("owner_information.ser");
            try{
                try (FileOutputStream fileOut = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                    out.writeObject(owner);
                    flag_serialization = true;
                    System.out.println("owner_information.ser file is created correcly");
                }
            }catch(IOException i){
                flag_serialization = false;
                System.out.println("Exception de Serialisation " + i.getMessage());
            }
        }
        else{
            flag_serialization = false; 
            
        }
        
        flag_end = flag_checksum && flag_creating_owner && flag_serialization;
        
        return flag_end;
    }

    /**
     * Methode : saveLinkingData() allows you to save all the information about the user
     * in a ser file. 
     * @param data
     * @return true if the owner information is correct
     * @throws java.lang.InterruptedException
     */
    private boolean saveLinkingData(String [] data) throws InterruptedException {
        boolean flag = false;

        if (isChecksumCorrect(data)){
            DeviceLinkingData dev = new DeviceLinkingData (data);
            System.out.println(dev);

            // Make the Serialization before closing the windows
            File file = new File("linking_data.ser");
            try{
                try (FileOutputStream fileOut = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                    out.writeObject(dev);
                    System.out.println("linking_data.ser file is read correctly");
                }
            }catch(IOException i)
            {
                System.out.println("Exception " + i.getMessage());
            }

            flag = true;
        }
        else{
            System.out.println("The data about the connection between SESAME and DEVICE is not saved");
            flag = false;
        }
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
        
        boolean flag_file = false;
        File file = new File("identifiant_and_key_table.ser");
        if (file.exists()){
            flag_file = true;
            System.out.println("'" + file + "' existe dans le dossier courant");
        }
        else{
            flag_file = false;
            System.out.println("'" + file + "' n'existe pas dans le dossier courant");
        }
        
        if (flag_file){
            // On fait rien
        }
        else{
            // Create the table which contains all identifiant and key with the linking information. 
            table_id_key = new IdentifiantAndKeyTable();
            System.out.println(table_id_key);

            // Make the Serialization for IdentifiantAndKeyTable 
            try(FileOutputStream fileOut = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(table_id_key);
            }catch(IOException i){
                System.out.println("Exception for IdentifiantAndKeyTable");
            }
            // End of the Serialization for IdentifiantAndKeyTable
            System.out.println("Serialized of IdentifiantAndKeyTable is saved in identifiant_and_key_table.ser");
        }
        
        
        
        String key = "";
        String id_device = identifiant;
        OwnerInformation user = null;
        DeviceLinkingData device_linking = null;
        
        DeviceLinkedData device_linked = null;
        
        IdentifiantAndKeyTable table_temp = null;
        
        if (data != null && data.length>=2){
            key = data[0];
            key_size = key.length();
            
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

            file = new File("owner_information.ser");
            // Deserialization otf the OwnerInformation
            try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                user = (OwnerInformation) in.readObject();
                flag_extract_user = true;
                
            }catch(IOException i){
                flag_extract_user = false;
                System.out.println("IOException : " + i.getMessage());
            }catch(ClassNotFoundException c){
                flag_extract_user = false;
                System.out.println("OwnerInformation class not found " + c.getMessage());
            }
                    
            file = new File("linking_data.ser");
            // Deserialization of the DeviceLinkingInfo
            try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                device_linking = (DeviceLinkingData) in.readObject();
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
            // Create the DeviceLinkedData class
            //device_linked = new DeviceLinkedData (user,device_linking, id_device, key);
            device_linked = new DeviceLinkedData (user,device, id_device, key);
            flag_creating_device = true;
        }
        else{
            flag_creating_device = false;
        }
        
        
        // Check if the class "DeviceLinkedData" is created correctly
        if (flag_creating_device){
            // Make the deserialization of the table file which is the database of the device
            file = new File("identifiant_and_key_table.ser");
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
                System.out.println("DeviceLinkingData class not found");
            }
        }
        else{
            System.out.println("'identifiant_and_key_table.ser' is not found in the current directory");
            flag_serialization = false;
        }

        // Check if the flag serialization is correct then make the serialization in the file   
        if (flag_serialization){
            // Make the serialization to save the new added device on the table
            file = new File("identifiant_and_key_table.ser");
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
            
            // Serialization test
            // Make the serialization to save the new added device on the table
            file = new File("linking_test.ser");
            try(FileOutputStream fileOut = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(device);
            }catch(IOException io){}
        }
        else{
            flag_end = false;
            System.out.println("impossible to read the 'identifiant_and_key_table.ser' file or the file is not found");
        }
        
        return flag_end;
    }
    
    /**
     * Methode : checkAccesData() allows you to check the acces data. 
     * @param data
     * @return true if the acces is authorized 
     * @throws java.lang.InterruptedException
     */
    private boolean checkAccesData(String [] data) throws InterruptedException {
        boolean flag_extraction = false;
        boolean flag_check_id = false;
        
        int checksum_received = 0; 
        int checksum_calcule = 0;
        
        String key_gotten = "";
        String id_gotten  = "";
        String acces_request = "";
        
        OwnerInformation user_info = null;
        //DeviceLinkedData device_linked = null;
        IdentifiantAndKeyTable table_id_key = null;
        
        if (data != null && data.length>=3){
            id_gotten  = data[0];
            key_gotten = data[1];
            acces_request = data[2];

            // Extract the checksum and convert it
            try{
                checksum_received = Integer.parseInt(data[3]);
            }catch(NumberFormatException ex){
                checksum_received = 0;
                System.out.println("Impossible de convert String to Intger : " + ex.getMessage());
            }
            
            // Calcul du checksul
            checksum_calcule = id_gotten.length()*1024 + key_gotten.length()*512 + acces_request.length()*256;
            
            // Test if the checksum is correct or not 
            if (checksum_calcule == checksum_received){
                System.out.println("CRC Correct");
                
                // Make the deserialization of the table file which is the database of the device
                File file = new File("identifiant_and_key_table.ser");
                try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                    table_id_key = (IdentifiantAndKeyTable) in.readObject();
                    
                    flag_extraction = true;

                }catch(IOException i){
                    flag_extraction = false;
                    System.out.println("IOException : " + i.getMessage());
                }catch(ClassNotFoundException c){
                   System.out.println("IdentifiantAndKeyTable class not found");
                   flag_extraction =false;
                }
            }
            else{
                flag_extraction = false;
                System.out.println("CRC inCorrect");
            }
            
            // Check if the extraction is done succesfully
            if (flag_extraction){
                // Got the key registered which corresponding to the id Sesame
                String key_registered = table_id_key.getCorrespondingKey(id_gotten);
                String [] all_id = table_id_key.getAllLinkedDeviceId();
                
                user_info = table_id_key.getCorrespondingUser(id_gotten);
                String id_registerd = "";
                
                // Check if the identifiant gotten is available on the database
                if (user_info != null){
                    id_registerd = user_info.getOwnerIdentifiant();
                }
                else{
                    id_registerd = "";
                }
                
                if (id_registerd.equals(id_gotten) && key_registered.equals(key_gotten)){
                    System.out.println("The id of the SESAME is correct");
                    flag_check_id = true;
                    // Check the acces_require
                    if (acces_request.equals(OUVRIR)){
                        System.out.println("Serrure ouverte");
                    }
                    else if(acces_request.equals(FERMER)){
                        System.out.println("Serrure fermée");
                    }
                    else{
                        // Nothing
                        System.out.println("Nothing to do");
                    }
                }
                else{
                    System.out.println("The id of the device is correct");
                    flag_check_id = true;
                }
                
            }
            else{
                // Nothing
            }
            
        }
        else{
            System.out.println("L'argument passé en paramètre est invalide");
        }
        return flag_extraction && flag_check_id;
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
        System.out.println("<--- BEGIN OF THE checkSavedData() methode -->");
        boolean flag = false;
        
        if ((first_data != null && first_data.equals(DEBUT_ENVOIE_INFORMATION_PROPRIETAIRE)) && 
            (last_data  != null  && last_data.equals(FIN_ENVOI_INFORMATION_PROPRIETAIRE))){
            System.out.println("First if");
            if(data != null){
                
                System.out.println("Before calling saveOwnerInformation()");
                flag = saveOwnerInformation(data);
                System.out.println("After calling saveOwnerInformation()");
                
                if(flag){
                    System.out.println("The data is saved correctly");
                    Thread.sleep(500);
                    this.sendData(PROPRITAIRE_ENREGISTREE_CORRECTEMENT);
                }
                else{
                    System.out.println("The data is not saved because it contains some invalid data");
                    Thread.sleep(500);
                    this.sendData(DONNEES_PROPRIETAIRE_RECUES_ERONNEES);
                }
            }
            else{
                System.out.println("The data is not saved because it contains some invalid data");
                Thread.sleep(500);
                this.sendData(DONNEES_PROPRIETAIRE_RECUES_ERONNEES);
                System.out.println(" le buffer est invalide");
                flag = false;
            }   
        }
        else if ((first_data != null && first_data.equals(DEBUT_ENVOI_INFORMATION_RATTACHEMENT)) && 
                 (last_data  != null  && last_data.equals(FIN_ENVOI_INFORMATION_RATTACHEMENT))){
            System.out.println("First if");
            if(data != null){
                
                System.out.println("Before calling saveLinkingDevice()");
                flag = saveLinkingData(data);
                System.out.println("After calling saveLinkingDevice()");
                
                System.out.println("statut of calling saveLinkingData() = " + flag);
                
                if(flag){
                    Thread.sleep(500);
                    this.sendData(SESAME_RATTACHE_CORRECTEMENT);
                    System.out.println("|SESAME_RATTACHE_CORRECTEMENT| is sent to the SESAME");
                }
                else{
                    Thread.sleep(500);
                    this.sendData(DONNEES_SESAME_RECUES_ERONNEES);
                    System.out.println("|DONNEES_SESAME_RECUES_ERONNEES| is sent to the SESAME because of some invalid data");
                }
            }
        }
        // ADD Device on the table which containing identifiant and key
        else if ((first_data != null && first_data.equals(DEBUT_ENVOIE_KEY_LINK)) && 
                 (last_data  != null  && last_data.equals(FIN_ENVOIE_KEY_LINK))){
            System.out.println("First if");
            if(data != null){
                
                System.out.println("Before calling add device on the table class");
                flag = addDeviceLink(data);
                System.out.println("After calling add device on the table class");
                
                System.out.println("Appel de la methode pour la sauvegarde = " + flag);
                if(flag){
                    Thread.sleep(500);
                    this.sendData(KEY_LINK_ENREGISTREE_CORRECTEMENT);
                    System.out.println("|KEY_LINK_ENREGISTREE_CORRECTEMENT| is sent to the SESAME because the key is saved correclty");
                }
                else{
                    Thread.sleep(500);
                    this.sendData(KEY_LINK_DONNEES_ERONNEES);
                    System.out.println("|KEY_LINK_DONNEES_ERONNEES| is sent to the SESAME because of the key is invalide");
                }
            }
        }
        // ===>
        // ADD Device on the table which containing identifiant and key
        else if ((first_data != null && first_data.equals(DEBUT_ENVOIE_DONNEES_ACCES)) && 
                 (last_data  != null  && last_data.equals(FIN_ENVOIE_DONNEES_ACCES))){
            System.out.println("First if");
            if(data != null){
                
                System.out.println("Before calling check data to authorize the acces");
                flag = checkAccesData(data);
                System.out.println("After calling check data to authorize the acces");
                
                System.out.println("Appel de la methode pour la vérification de l'accès = " + flag);
                if(flag){
                    Thread.sleep(500);
                    this.sendData(DEMANDE_ACCES_AUTORISEE);
                    System.out.println("|DEMANDE_ACCES_AUTORISEE| is sent to the SESAME because the key is saved correclty");
                }
                else{
                    Thread.sleep(500);
                    this.sendData(DEMANDE_ACCES_REFUSEE);
                    System.out.println("|DEMANDE_ACCES_REFUSEE| is sent to the SESAME because of the key is invalide");
                }
                
                Thread.sleep(10000);
                this.sendData(SERRURE_VERROUILLEE);
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
     * Methode checkBufferData() allows you to verify all the data saved in the buffer
     * @throws java.lang.InterruptedException
     */
    private void checkBufferData() throws InterruptedException{
        System.out.println("<--- BEGIN OF CALLING checkBufferData() methode --->");
        
        // Get the data saved in the buffer
        String [] data_in = SerialPortGPIO.extractBufferData(buffer.toString());
        
        System.out.println("The buffer is resetted");
        resetBufferReception();
        
        // Extract the first and last data to check the kind of request
        String first_data = data_in[0];
        String last_data  = data_in[data_in.length - 1];
        
        System.out.println("First = " + first_data);
        System.out.println("Last  = " + last_data);
        
        // Extract only the data about the request
        String [] data_temp = new String[data_in.length -2];
        for (int i=0; i<data_temp.length; i++){
            data_temp[i] = data_in[i+1];
            System.out.println("Valide data["+i+"] = " + data_temp[i]);
        }
        
        // Call the checkSavedData() methode to identify the kind of request
        boolean flag = checkSavedData(first_data, last_data, data_temp);
        
        System.out.println("<--- END OF CALLING checkBufferData() methode --->");
    }
    
    /**
     * Methode : sendLinkingConfirmation() allow you to send the confirmation of the linking to the Sesame
     * @return flag if the linking confirmation is sent
     * @throws java.lang.InterruptedException
     */
    private boolean sendLinkingConfirmation() throws InterruptedException{
        System.out.println("<--- BEGIN : sendLinkingConfirmation --->");
        boolean flag = false;
        DeviceLinkingData dev = null;
        String [] confirmation_rattachement = new String[4];
        
        File file = new File("linking_data.ser");
        
        // Make the serialization
        try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            device = (DeviceLinkingData) in.readObject();
            System.out.println("Identifiant du device before = " + identifiant);
            
            // Calcul the identifiant of the device
            identifiant = device.makeDeviceIdentifiant();
            System.out.println("Identifiant du device after = " + identifiant);
           
            int formule = identifiant.length()*128;

            // Make the table for the linking confirmation
            confirmation_rattachement[0] = DEBUT_ENVOIE_CONFIRMATION_RATTACHEMENT;
            confirmation_rattachement[1] = identifiant;
            confirmation_rattachement[2] = Integer.toString(formule);
            confirmation_rattachement[3] = FIN_ENVOIE_CONFIRMATION_RATTACHEMENT;

            flag = true;
            
        }catch(IOException i){
            flag = false;
            System.out.println("IOException : " + i.getMessage());
        }catch(ClassNotFoundException c){
           System.out.println("DeviceLinkingData class not found");
           flag =false;
        }

        if (flag){
        	// send the linking confirmation
            sendData(confirmation_rattachement);
            System.out.println("Identifiant of the Peripherique is sent to the Sesame");
        }
        else{
            System.out.println("Identifiant of the Peripherique is not sent to the Sesame");
        }
        System.out.println("<--- END   : sendLinkingConfirmation --->");

        return flag;
    }

    /**
     * Methode Main to launch the application
     * @param args
     * @throws IOException
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        SerialPortGPIO main = new SerialPortGPIO(9600);
        int count = 0;
        
        while (true){
            if (count<5){
                System.out.println("Count SESAME DOORS = " + count);
            }
            count ++;
            Thread.sleep(1000);
        }
    }
}