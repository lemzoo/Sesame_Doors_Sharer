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

/**
 *
 * @author LamineBA
 */
public class MainTestClass {
    
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
        String key = "AZERTYUIOP";
        
        DeviceLinkedData device_linked = new DeviceLinkedData (device_linking, identifiant, key);
        
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
        
    }
    
}
