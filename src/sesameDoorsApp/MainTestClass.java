/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesameDoorsApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 *
 * @author LamineBA
 */
public class MainTestClass {
    
    /**
     * Methode : sampleDataToSend allow you to sample the data that will sent by the uart
     * @param data_to_send
     * @return String[] containing all the sampled data
     */
    public static String [] sampleDataToSend(String data_to_send){
        String data_to_send_in = data_to_send;
        String [] data_sampled_temp = new String[20];
        String [] data_sampled_out;
        
        boolean flag_data = false; 
        String data_to_send_temp = "";
        
        int size_original = data_to_send_in.length();
        char[] charArray = data_to_send_in.toCharArray();
        System.out.println("Char = " + Arrays.toString(charArray));
        int size_rest = 0; 
        int number_char_to_delete = 10;
        int count = 0;
        
        if (size_original >10){
            System.out.println("Size of the buffer is superior to 10");
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
                //System.out.println("Buffer before = " + data_to_send);
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
            //System.out.println("Size of the buffer is not superior to 10");
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
            System.out.println("Data extraction methode["+i+"] = " + data_sampled_out[i]);
        }
        
        return data_sampled_out; 
    }
    
    
    public static void main(String [] args){
        IdentifiantAndKeyTable table_id_key = new IdentifiantAndKeyTable();
        System.out.println(table_id_key);
        boolean flag = false;
        boolean flag_serialization = false;
        
        // Make the serialization to save the new added device on the table
        File file = new File("identifiant_and_key_table.ser");
        try(FileOutputStream fileOut = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(table_id_key);
            flag = true;
        }catch(IOException io){
            System.out.println("Exception for IdentifiantAndKeyTable : " + io.getMessage());
            flag = false;
        }
        // End of the Serialization for IdentifiantAndKeyTable
        System.out.println("Serialized of the new IdentifiantAndKeyTable is saved in identifiant_and_key_table.ser");

        
        String [] data = {"Maison", "Proprietaire", "Rez de Chaussee", "Porte d'entree principale", "Ma residence principale",
                          "13", "Avenue Maximilien Robespierre", "94400", "Vitry sur Seine", "France"};
        DeviceLinkingData device_linking = new DeviceLinkingData(data);
        System.out.println(device_linking);
        
        String identifiant = "SESAME DOORS";
        String key = "AZERTYUIOPQSDKFGFGKFGJkfgjsdffsdjfsdkfjdsAZSQAZSQWXCDFRTGHVBCFDjshfjsdfksdfjdsfhsdjfdjfkjqkfjqfhqjdfjdjfdjfhdsjfhsdjfdskfjsdkf";
        
        DeviceLinkedData device_linked = null; //new DeviceLinkedData (device_linking, key);
        
        IdentifiantAndKeyTable table_temp = null;
        // Deserialization 
        if (flag){
            // Make the deserialization of the table file which is the database of the device
            //file = new File("identifiant_and_key_table.ser");
            try (FileInputStream fileIn = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                table_temp = (IdentifiantAndKeyTable) in.readObject();

                // Add the device link information on the table
                System.out.println("Content of the table before");
                System.out.println(table_temp);
                table_temp.addDeviceForLink(device_linked);
                System.out.println("Content of the table after");
                System.out.println(table_temp);
                flag_serialization = true;
                
            }catch(IOException io){
                //flag_serialization = false;
                System.out.println("IOException : " + io.getMessage());
            }catch(ClassNotFoundException c){
               System.out.println("DeviceLinkingData class not found");
               //flag_serialization =false;
            }
        }
        else{
            System.out.println("Nothing and flag_serialization = false");
            flag_serialization = false;
        }
        
        if (flag_serialization){
            System.out.println("Make the serialization to save the new added device on the table");
            //file = new File("identifiant_and_key_table.ser");
            try(FileOutputStream fileOut = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(table_temp);
                // End of the Serialization for IdentifiantAndKeyTable
                System.out.println("Serialized of the new IdentifiantAndKeyTable is saved in identifiant_and_key_table.ser");

            }catch(IOException io){
                System.out.println("Exception for IdentifiantAndKeyTable : " + io.getMessage());
                System.out.println("Serialized of the new IdentifiantAndKeyTable is not done : check error");
            }
        }
     
        String key_temp = device_linked.getDeviceKey();
        System.out.println("Key = " + key_temp + ", key size = " + key_temp.length());
        
        String [] sampled_data = sampleDataToSend(key_temp);
        for (int i=0; i<sampled_data.length; i++){
            System.out.println("data["+i+"] = " + sampled_data[i]);
        }
    }
    
}
