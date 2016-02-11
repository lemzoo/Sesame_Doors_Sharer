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
    private String received_data;
    
    public UARTListener(SerialPortGPIO uart){
            this.uart = uart;
            //received_data = "";
    }

    /**
     * Methode dataReceived () for receiving the data available on the UART Buffer
     */
    @Override
    public void dataReceived(SerialDataEvent event) {
        if (event.getData() != null){

            received_data = event.getData();
            char[] charArray = received_data.toCharArray();
            int size = received_data.length();
            received_data = String.valueOf(charArray[0]);
            for (int i=1; i<size - 2; i++){
                received_data = received_data + String.valueOf(charArray[i]); 
                received_data = received_data.replace("\n", "").replace("\r", "");
            }
            System.out.println("received_data = [" + received_data + "]");
            
            /* 
             * Check if the rattachement flag is true. if the flag is true, copy the RX Data on the table
             * to save the information about the Owner of the Sesame. 
             */

            uart.setDataBufferReception(received_data);
            try {
                uart.analyzeDataReceived();
            } catch (InterruptedException ex) {
                Logger.getLogger(UARTListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Start to write the Owner information in the buffer which allocated
            if (uart.getSavingFlag()){
                if(received_data == null ? BEGIN != null : !received_data.equals(BEGIN)){
                    uart.setBufferData(received_data);
                }
                else if (received_data == null ? END != null : received_data.equals(END)){
                    uart.setSavingFlag(false);
                }
                else{
                    // nothing
                }
            }
            else{
                //System.out.println("Else de l'UART SESAME DOORS et Data received = ["+received_data+"]");
                /*uart.setDataBufferReception(received_data);
                try {
                    uart.analyzeDataReceived();
                } catch (InterruptedException ex) {
                    Logger.getLogger(UARTListener.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
        }
        else{
            // Nothing
        }
    }	
}

/*
 1. Validation de l'enregistrement du propriétaire par le mot de passe
    du propriétaire du Sesame. La confirmation doit être envoyé après 
    spécifié par le périphérique via un Random et un offset de 20 secondes. 
 2. Envoie de la validation via le soft. Le périphérique renverra une confirmation
    de validation avec tempo de validation du rattachement. Le tempo est aussi un 
    random() plus un offset de 30 secondes. Passé cette délai la confirmation n'est pas
    prise en compte. 
    >> Après reception de la confirmation, le périphérique effectue le calcul de son identifiant
        en se basant sur les éléments du propriétaire du Sesame. Puis envoie son identifiant sous forme de chiffre liée
        à un table de correspondance pour retrouver l'identifiant original. 
        Le Sesame retrouve l'identifiant du périphérique avec la table de correspondance. Ensuite il sauvegarde l'identifiant avec
        les élements du périphérique a savoir les elements d'identification. 
 3. Après rattachement, le Sesame peut maintenant autorisé le cas d'utilisation 
    << acceder >>. Avant tout on vérifie s'il y'a au moins un périphériqe qui 
    est rattaché au Sesame. Puis ensuite on effectue on ouvre la communication en
    envoyant des données sur le cas d'utilisation en question. Voir documentation.

 4. Calcul de l'identifiant du Sesame et du Peripherique 
    4.1 Identifiant du Sesame
        Les paramètres : Nom, Prenom, Date de naissance, Numero de telephone
        Formule : Taille (nom) + Taille (Prenom) + jour de naissance + mois de naissance + fonction(année de naissance) + fonction(numero de telephone) + checksum.
        fonction(année de naissance) = (année en cours - année de naissance)/2;
        fonction (numero de telephone) = decomposer le numero en cinq sous nombe de 2 elements.
        

*/