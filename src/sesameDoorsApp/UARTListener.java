// Peripherique


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesameDoorsApp;

import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UARTListener implements SerialDataListener, ConstantsConfiguration {
	
    SerialPortGPIO uart;
    private StringBuffer new_received_data;
    private StringBuffer old_received_data;
    
    public UARTListener(SerialPortGPIO uart){
        this.uart = uart;
        new_received_data = new StringBuffer();
        old_received_data = new StringBuffer();
    }

    /**
     * Methode dataReceived () for receiving the data available on the UART Buffer
     */
    @Override
    public void dataReceived(SerialDataEvent event) {
        if (event.getData() != null){
            // Get the new data and put it in the temp string
            String str_new_data = event.getData();
            // Replace the "\n" and "\r" by empty char
            String str_replace = str_new_data.replace("\n", "").replace("\r", "");
            
            // reset the buffer
            resetBufferReception(new_received_data);
            new_received_data.insert(0, str_replace);
            
            System.out.println("Received Data = [" + new_received_data.toString() + "]");
            
            /* 
             * Check if the rattachement flag is true. if the flag is true, copy the RX Data on the table
             * to save the information about the Owner of the Sesame. 
             */

            try {
                uart.analyzeDataReceived(new_received_data.toString());
            } catch (InterruptedException ex) {}
            
            // Start to write the Owner information in the buffer which allocated
            if (uart.getSavingFlag()){
                if(new_received_data.toString() == null ? BEGIN != null : !new_received_data.toString().equals(BEGIN)){
                    uart.setBufferReception(new_received_data.toString());
                }
                else if (new_received_data.toString() == null ? END != null : new_received_data.toString().equals(END)){
                    uart.setSavingFlag(false);
                }
                else{
                    // Nothing
                }
            }
            else{
                // Nothing
            }
        }
        else{
            System.out.println("No data available on the GPIO Buffer");    
        }
    }
    
    /**
     * Methode : resetBufferReception(String data)
     */
    private void resetBufferReception (StringBuffer str_buffer){
        int len = str_buffer.length();
        str_buffer.delete(0, len);        
    }
}