/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesameDoorsApp;

import java.util.ArrayList;

/**
 * Class : DeviceLinkedData() it's a class which contains the information of the device. 
 * -> Attributs : device_info, device_id, device_key. 
 * -> Methode : accessors get and set of the attribut. 
 * @author LamineBA
 */
public class DeviceLinkedData implements java.io.Serializable {

    private OwnerInformation user;
    private DeviceLinkingData device_info;
    private String device_id;
    private String device_key;

    public DeviceLinkedData(OwnerInformation user, DeviceLinkingData device, String id, String key) {
        this.user        = user;
        this.device_info = device;
        this.device_id   = id;
        this.device_key  = key;
    }

    /**
     * Methode : getDeviceLinkingInformation() allow you to get the information about the linked device
     * @return device_info
     */
    public DeviceLinkingData getDeviceLinkingInformation() {
        return this.device_info;
    }
    /**
     * Methode setDeviceLinkingInformation() allow you to set the information about the device
     * @param device_info 
     */
    public void setDeviceLinkingInformation(DeviceLinkingData device_info) {
        if (device_info != null){
            this.device_info = device_info;
        }
        else{
            System.out.println("La classe passée en paramètre est null");
        }
    }
    
    /**
     * Methode : getOwnerInformation() allow you to get the information about the user
     * @return user
     */
    public OwnerInformation getOwnerInformation() {
        return this.user;
    }
    /**
     * Methode setOwnerInformation() allow you to set the information about the device
     * @param user 
     */
    public void setOwnerInformation(OwnerInformation user) {
        if (user != null){
            this.user = user;
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