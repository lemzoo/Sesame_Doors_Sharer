/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesameDoorsApp;

import java.util.ArrayList;

/**
 *
 * @author LamineBA
 */
public class IdentifiantAndKeyTable implements java.io.Serializable {
    
    private ArrayList<DeviceLinkedData> table_device_info;
    
    public IdentifiantAndKeyTable (){        
        table_device_info = new ArrayList<>();
    }
    
    /**
     * Methode addDeviceForLink () allow you to add the identifiant and key on the table
     * @param device
     */
    public void addDeviceForLink(DeviceLinkedData device){
        OwnerInformation user_info;
        String id;
        String key;
        
        if (device != null){
            user_info = (OwnerInformation)(device.getDeviceOwnerInformation());
            id = (String)(device.getDeviceId());
            key = (String)(device.getDeviceKey());
            DeviceLinkedData devi = new DeviceLinkedData(user_info, key);
            table_device_info.add(devi);
            
        }
        else{
            System.out.println("Nothing to add");
        }
    }
    
    public String [] getAllLinkedDeviceId(){
        
        String [] all_device_id_temp = new String[100];
        String [] all_device_id_out;
        
        for (int i=0; i<table_device_info.size(); i++){
            all_device_id_temp[i] = table_device_info.get(i).getDeviceId();
        }
        
        int count = 0;
        while(all_device_id_temp[count]!= null){
            count ++;
        }
        
        all_device_id_out = new String[count];
        System.arraycopy(all_device_id_temp, 0, all_device_id_out, 0, count);
        
        return all_device_id_out;
    }
    
    /**
     * Methode : getCorrespondingKey() allow you to get the corresponding to the identifiant passed on the argument
     * @param device_id : is the id of the device that you want to get the key
     * @return key if the device id passed is available on the table
     */
    public String getCorrespondingKey(String device_id){
        String key = "";
        
        for (int i=0; i<table_device_info.size(); i++){
            if (table_device_info.get(i).getDeviceId().equals(device_id)){
                key = table_device_info.get(i).getDeviceKey();
                System.out.println("The key corresponding on this device = '" + device_id + "' is = '" + key + "'");
                i = table_device_info.size();
            }
            else{
                key = null;
            }
        }
        return key;
    }
    
    /**
     * Methode : getCorrespondingUser() allow you to get the corresponding user
     * @param device_id : is the id of the device that you want to get the key
     * @return OwnerInformation if the device id passed is available on the table
     */
    public OwnerInformation getCorrespondingUser(String device_id){
        OwnerInformation user = null;
        for (int i=0; i<table_device_info.size(); i++){
            if (table_device_info.get(i).getDeviceId().equals(device_id)){
                user = table_device_info.get(i).getDeviceOwnerInformation();
                i = table_device_info.size();
            }
            else{
                user = null;
            }
        }
        return user;
    }
    
    /**
     * Methode : toString print all the identifiant and key saved on the table
     * @return 
     */
    @Override
    public String toString(){
        String chaine = "<---------------------------------------------------->" + "\n"+
                        "<---  Table containing the identifiants and keys  --->" + "\n";
        int count = 0;
        
        if (table_device_info.isEmpty()){
            chaine += "Il n'y a aucun périphérique rattaché" + "\n";
        }
        else{
            while (count < table_device_info.size()){
                String device_number = String.format("%02d", (count +1));
                chaine += "<---------------------------------------------------->" + "\n";
                chaine += "Device Number : " + device_number +  "\n"; 
                chaine += table_device_info.get(count).getDeviceOwnerInformation().toString() + "\n"; 
                chaine += "Device Id = " + table_device_info.get(count).getDeviceId() + "\n"; 
                chaine += "Device Key = " + table_device_info.get(count).getDeviceKey() + "\n";
                count ++;
            }
        }
        chaine += "<---------------------------------------------------->";
        return chaine;
    }
    
    private static final long serialVersionUID = 42L; 
    
    /*public static void main (String [] args){
        IdentifiantAndKeyTable id = new IdentifiantAndKeyTable();
        String ide = "SESAME DOORS";
        String key = "AZERTYUIOP";
        String [] data = {"Maison", "Proprietaire", "Rez de Chaussee", "Porte d'entree principale", "Ma residence principale",
                          "13", "Avenue Maximilien Robespierre", "94400", "Vitry sur Seine", "France"};
        DeviceLinkingData dev = new DeviceLinkingData(data);
        id.addDeviceForLink(dev, ide, key);
        
        ide = "SESAME HAND";
        key = "QSDFGHJKLM";
        id.addDeviceForLink(dev, ide, key);
        
        ide = "SESAME CAR";
        key = "WXCVBN";
        id.addDeviceForLink(dev, ide, key);
        
        System.out.println(id);   
    }*/
}

/**
 * Class : DeviceLinkedData() it's a class which contains the information of the device. 
 * -> Attributs : device_info, device_id, device_key. 
 * -> Methode : accessors get and set of the attribut. 
 * @author LamineBA
 */
class DeviceLinkedData implements java.io.Serializable {

     private OwnerInformation device_user;
     private String device_id;
     private String device_key;

    public DeviceLinkedData(OwnerInformation user, String key) {
        this.device_user = user;
        this.device_id   = user.getOwnerIdentifiant();
        this.device_key  = key;
    }

    /**
     * Methode : getDeviceOwnerInformation() allow you to get the information about the owner of the device
     * @return device_info
     */
    public OwnerInformation getDeviceOwnerInformation() {
        return this.device_user;
    }
    /**
     * Methode setDeviceOwnerInformation() allow you to set the information about the device
     * @param device_info 
     */
    public void setDeviceOwnerInformation(OwnerInformation user_info) {
        if (user_info != null){
            this.device_user = user_info;
        }
        else{
            System.out.println("La classe passée en paramètre est null");
        }
    }
    
    /**
     * Methode : getDeviceId() allow you to get the Id of the device
     * @return device_id
     */
    public String getDeviceId() {
        return this.device_id;
    }
    /**
     * Methode setDeviceId() allow you to set the id of the device
     * @param id 
     */
    public void setDeviceId(String id) {
        this.device_id = id;
    }
    
    /**
     * Methode : getDeviceKey() allow you to get the key of the device
     * @return device_key
     */
    public String getDeviceKey() {
        return this.device_key;
    }
    /**
     * Methode setDeviceKey() allow you to set the key of the device
     * @param key 
     */
    public void setDeviceKey(String key) {
        this.device_key = key;
    }
    
    private static final long serialVersionUID = 42L; 
}