/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesameDoorsApp;

/**
 *
 * @author LamineBA
 */
public class DeviceLinkingData implements java.io.Serializable{
    private String type_de_bien; 
    private String type_appartenance; 
    private String emplacement; 
    private String type_porte; 
    private String commentaire; 
    private int numero_voie;
    private String nom_voie;
    private int code_postale;
    private String ville;
    private String pays;
    private String device_identifiant;
    
    public DeviceLinkingData (){
        type_de_bien = ""; 
        type_appartenance = ""; 
        emplacement = ""; 
        type_porte = ""; 
        commentaire = ""; 
        numero_voie = 0;
        nom_voie = "";
        code_postale = 0;
        ville = "";
        pays = "";
        device_identifiant = "";
    }
    public DeviceLinkingData(String [] data){
        if (data != null && data.length>0){
            type_de_bien = data[0]; 
            type_appartenance = data[1]; 
            emplacement = data[2]; 
            type_porte = data[3]; 
            commentaire = data[4];       
            //numero_voie = Integer.parseInt(data[5]);
            nom_voie = data[6];
            //code_postale = Integer.parseInt(data[7]);
            ville = data[8];
            pays = data[9];  
            
            try{
                numero_voie = Integer.parseInt(data[5]);
                code_postale = Integer.parseInt(data[7]);                
            }catch(NumberFormatException ex){
                numero_voie = 0;
                code_postale = 0;
            }
        }
    }
    
    @Override
    public String toString(){
        String chaine =   "<<============================================>>" + "\n"
                        + "... Device Information for the Identification ..." + "\n"
                        + "<<============================================>>" + "\n"
                        + "... TYPE DE BIEN : "+type_de_bien + " ..." + "\n" 
                        + "... TYPE D'APPARTENANCE : " + type_appartenance + " ..." + "\n"
                        + "... EMPLACEMENT : " + emplacement + " ..." + "\n"
                        + "... TYPE DE PORTE : " + type_porte + " ..." + "\n"
                        + "... COMMENTAIRE : " + commentaire + " ..." + "\n"
                        + "... ADRESSE : " + numero_voie + " " + nom_voie + "..." + "\n"
                        + ".............. " + code_postale + " " + ville + " ..." + "\n" 
                        + ".............. " + pays + " ..." + "\n"
                        + "<<============================================>>";
        return chaine; 
    }
    
    /**
     * Methode makeDeviceIdentifiant () allows you to make the identifiant of the device
     * @return identifiant made through the parameters of the device 
     */
    public String makeDeviceIdentifiant(){
        String identifiant = "";
        
        identifiant  = String.valueOf(type_de_bien.charAt(0)) + String.valueOf(type_appartenance.charAt(0));
        identifiant += String.valueOf(emplacement.charAt(0))+ String.valueOf(type_porte.charAt(0)); 
        identifiant += String.valueOf(commentaire.charAt(0));
        /* Les parametres : 
            - type de bien
            - type d'appartenance
            - emplacement
            - type de porte
            - commentaire
            - Adresse :  
                - Numero + nom du rue 
                - code postale + ville + pays
        */
        return identifiant;
    }
    
    /**
     * Methode : getDeviceIdentifiant() allow you to get the id of the device
     * @return device_identifiant is the variable which contains the identifiant of the device
     */
    public String getDeviceIdentifiant(){
        return this.device_identifiant;
    }
    
    /**
     * Methode : setDeviceIdentifiant() allow you to set the id of the device
     * @param identifiant : is the identifiant that will write on the device
     */
    public void setDeviceIdentifiant(String identifiant){
        device_identifiant =  identifiant;
    }
    
    /**
     * Methode : getTypeDeBien() allow you to get "TYPE DE BIEN"
     * @return type_de_bien
     */
    public String getTypeDeBien(){
        return this.type_de_bien;
    }
    
    /**
     * Methode : getTypeAppartenance() allow you to get "TYPE D'APPARTENANCE"
     * @return type_appartenance
     */
    public String getTypeAppartenance(){
        return this.type_appartenance;
    }
    
    /**
     * Methode : getEmplacement() allow you to get "EMPLACEMENT"
     * @return emplacement
     */
    public String getEmplacement(){
        return this.emplacement;
    }
    
    /**
     * Methode : getTypeDePorte() allow you to get "TYPE DE PORTE"
     * @return type_porte
     */
    public String getTypeDePorte(){
        return this.type_porte;
    }
    
    /**
     * Methode : getNumeroVoie() allow you to get "NUMERO DE LA VOIE"
     * @return numero_voie
     */
    public String getNumeroRue(){
        return Integer.toString(this.numero_voie);
    }
    
    /**
     * Methode : getNomVoie() allow you to get "NOM DE LA VOIE"
     * @return nom_voie
     */
    public String getNomVoie(){
        return this.nom_voie;
    }
    
    /**
     * Methode : getCodePostale() allow you to get "CODE POSTALE"
     * @return code_postale
     */
    public String getCodePostale(){
        return Integer.toString(this.code_postale);
    }
    
    /**
     * Methode : getVille() allow you to get "VILLE"
     * @return ville
     */
    public String getVille(){
        return this.ville;
    }
    
    /**
     * Methode : getPays() allow you to get "pays"
     * @return pays
     */
    public String getPays(){
        return this.pays;
    }
    
    private static final long serialVersionUID = 42L; 
    
    
    /*public static void main(String[] args) throws InterruptedException {
        String [] data = {"Maison", "Proprietaire", "Rez de Chaussee", "Porte d'entree principale", "Ma residence principale",
                          "13", "Avenue Maximilien Robespierre", "94400", "Vitry sur Seine", "France"};
        DeviceLinkingData dev = new DeviceLinkingData(data);
        System.out.println(dev);
        
        //boolean flag = dev.sendLinkingConfirmation();

    }*/
}