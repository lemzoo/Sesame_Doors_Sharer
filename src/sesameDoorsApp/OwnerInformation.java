//Peripherique

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesameDoorsApp;

import java.util.Date;
import javax.swing.JPasswordField;

/**
 *
 * @author LamineBA
 */
public class OwnerInformation implements java.io.Serializable {
    private String nom;
    private String prenom;
    private Date date_naissance;
    private String numero_mobile;
    private String adresse_mail;
    private int numero_voie;
    private String nom_voie;
    private int code_postale;
    private String ville;
    private String pays;
    private String user_id;
    private JPasswordField password;
    
    public OwnerInformation (){
        nom = "";
        prenom = "";
        date_naissance = null;
        numero_mobile = "";
        adresse_mail = "";
        numero_voie = 0;
        nom_voie = "";
        code_postale = 0;
        ville = "";
        pays = "";
        user_id = "";

        password = new JPasswordField(null,"",0);

        System.out.println("Owner Information is setted by empty data");
    }
    
    public OwnerInformation (boolean flag){
        nom = "TAGU";
        prenom = "Flaubert";
        date_naissance = new Date(2015, 8, 9);
        numero_mobile = "06 51 58 75 08";
        adresse_mail = "lamine.ba@everygates.com";
        numero_voie = 2;
        nom_voie = "Avenue du Petit Albert";
        code_postale = 92220;
        ville = "Bagneux";
        pays = "France";
        user_id = String.valueOf(nom.charAt(0)) + String.valueOf(prenom.charAt(0)) +
                  String.valueOf(date_naissance.getDate())+ 
                  String.valueOf(date_naissance.getMonth()) + 
                  String.valueOf(date_naissance.getYear()).charAt(3)+
                  String.valueOf(numero_mobile.charAt(7)) + String.valueOf(numero_mobile.charAt(9));

        password = new JPasswordField(null,"a",0);
        System.out.println("Owner Information is setted with the correct data");
    }
    
    @Override
    public String toString(){
        String chaine = "... Nom : " + nom + " ...\n" + 
                        "... Prenom : " + prenom + " ...\n" +
                        "... Date de naissance : " + date_naissance.getDay() + "-" + date_naissance.getMonth() + "-" + date_naissance.getYear() + "...\n" + 
                        "... Numero de telephone : " + numero_mobile + " ...\n" +
                        "... Adresse : " + numero_voie + " " + nom_voie + " ...\n" +
                        "...           " + ville + ", " + pays + " ...\n"; 
        
        return "... Les informations à propos du rattachement ...\n" + chaine;
    }
    
    /**
     * Methode getOwnerFirstName()
     * This methode returns the first name of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return nom
     */
    public String getOwnerFirstName(){
        return this.nom;
    }
    /**
     * Methode setOwnerFirstName(String first_name)
     * @param data
     */
    public void setOwnerFirstName(String data){
        this.nom = data;
    }
    
    /**
     * Methode getOwnerLastName()
     * This methode returns the last name of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return prenom
     */
    public String getOwnerLastName(){
        return this.prenom;
    }
    /**
     * Methode setOwnerLastName(String last_name)
     * @param last_name 
     */
    public void setOwnerLastName(String last_name){
        this.prenom = last_name;
    }
    
    /**
     * Methode getOwnerBirthdayDate()
     * This methode returns the birthday date of the user who is the 
     * owner of the SESAME. 
     * Returned type : Date
     * @return date_naissance
     */
    public Date getOwnerBirthdayDate(){
        return this.date_naissance;
    }
    /**
     * Methode getOwnerBirthdayDate(Data date)
     * @param date 
     */
    public void setOwnerBirthdayDate(Date date){
        this.date_naissance = date;
    }
    
    /**
     * Methode getOwnerPhoneNumber()
     * This methode returns the phone number of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return numero_mobile
     */
    public String getOwnerPhoneNumber(){
        return this.numero_mobile;
    }
    /**
     * Methode setOwnerPhoneNumber(String phone_number)
     * @param phone_number 
     */
    public void setOwnerPhoneNumber(String phone_number){
        this.numero_mobile = phone_number;
    }
    
    /**
     * Methode getOwnerEmailAddress()
     * This methode returns the phone number of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return adresse_mail
     */
    public String getOwnerEmailAddress(){
        return this.adresse_mail;
    }
    /**
     * Methode setOwnerEmailAddress(String email_address)
     * @param email_address 
     */
    public void setOwnerEmailAddress(String email_address){
        this.adresse_mail = email_address;
    }
    
    /**
     * Methode getOwnerStreetNumber()
     * This methode returns the street number of the home address of the user who is the 
     * owner of the SESAME. 
     * Returned type : int
     * @return numero_voie
     */
    public int getOwnerStreetNumber(){
        return this.numero_voie;
    }
    /**
     * Methode setOwnerStreetNumber(int street_number)
     * @param street_number 
     */
    public void setOwnerStreetNumber(int street_number){
        this.numero_voie = street_number;
    }
    
    /**
     * Methode getOwnerStreetName()
     * This methode returns the street name of the home address of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return nom_voie
     */
    public String getOwnerStreetName(){
        return this.nom_voie;
    }
    /**
     * Methode setOwnerStreetName(String street_name)
     * @param street_name 
     */
    public void setOwnerStreetName(String street_name){
        this.nom_voie = street_name;
    }
    
    /**
     * Methode getOwnerCodePostale()
     * This methode returns the Code Postale of the home address of the user who is the 
     * owner of the SESAME. 
     * Returned type : int
     * @return code_postale
     */
    public int getOwnerCodePostale(){
        return this.code_postale;
    }
    /**
     * Methode setOwnerCodePostale(int postale_code)
     * @param postale_code 
     */
    public void setOwnerCodePostale(int postale_code){
        this.code_postale = postale_code;
    }
    /**
     * Methode getOwnerCity()
     * This methode returns the city of home address of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return ville
     */
    public String getOwnerCity(){
        return this.ville;
    }
    /**
     * Methode setOwnerCity(String city)
     * @param city 
     */
    public void setOwnerCity(String city){
        this.ville = city;
    }
    
    /**
     * Methode getOwnerCountry()
     * This methode returns the country of the home address of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return pays
     */
    public String getOwnerCountry(){
        return this.pays;
    }
    /**
     * Methode setOwnerCountry(String country)
     * @param country 
     */
    public void setOwnerCountry(String country){
        this.pays = country;
    }
    
    /**
     * Methode getOwnerIdentifiant()
     * This methode returns the Id of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return user_id
     */
    public String getOwnerIdentifiant(){
        return this.user_id;
    }
    /**
     * Methode setOwnerIdentifiant(String country)
     * @param id 
     */
    public void setOwnerIdentifiant(String id){
        this.user_id = id;
    }
    
    /**
     * Methode getOwnerPassword()
     * This methode returns the password typed by the user who is the 
     * owner of the SESAME. 
     * Returned type : JPasswordField
     * @return password
     */
    public JPasswordField getOwnerPassword(){
        return this.password;
    }
    /**
     * Methode setOwnerPassword(pass)
     * This methode sets the new password of the user who is the 
     * owner of the SESAME.
     * @arg type : JPasswordField
     * @param pass
     */
    public void setOwnerPassword(JPasswordField pass){
        this.password = pass;
    }
    
    private static final long serialVersionUID = 42L; 
}