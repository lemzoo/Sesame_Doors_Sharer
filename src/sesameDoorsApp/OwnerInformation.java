/*
 * This class contains all information about the owner of the SESAME. 
 */
package sesameDoorsApp;


import java.util.Date;

/**
 * Classe OwnerInformation create an object which contains all data about the owner of the Sesame and 
 * the object create a identity. 
 * @author LamineBA
 */
public class OwnerInformation extends UserIdentification implements java.io.Serializable {

    private String user_id;
    private String password;
    
    /**
     * Constructor "OwnerInformation" 
     * @param data contains all data about the owner except the identity and password of the owner
     */
    public OwnerInformation (String [] data){
        super(data);
        password = "a";
        user_id = String.valueOf(super.getUserFirstName().charAt(0)) + String.valueOf(super.getUserLastName().charAt(0)) +
                  String.valueOf(super.getUserBirthdayDate().getDate())+ String.valueOf(super.getUserBirthdayDate().getMonth()) + String.valueOf(super.getUserBirthdayDate().getYear()).charAt(3)+
                  String.valueOf(super.getUserPhoneNumber().charAt(7)) + String.valueOf(super.getUserPhoneNumber().charAt(9));
    }
    
    /**
     * Constructor "OwnerInformation" without aragument is used only for the deserialization
     */
    public OwnerInformation(){
        super();
    }
    @Override
    public String toString(){
        String chaine = super.toString();
        chaine +=   "..... Information complémentaire ......" + "\n" + 
                    "..... Identifiant de l'utilisateur  : " + user_id + " ....." + "\n" + 
                    "..... Mot de passe de l'utilisateur : " + password + " ....." + "\n"+
                    "...................................................";
        return chaine;
    }
    
    /**
     * Methode getUserFirstName()
     * This methode returns the first name of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return nom
     */
    public String getOwnerFirstName(){
        return super.getUserFirstName();
    }
    /**
     * Methode setOwnerFirstName(String first_name)
     * @param data
     */
    public void setOwnerFirstName(String data){
        super.setUserFirstName(data);
    }
    
    /**
     * Methode getOwnerLastName()
     * This methode returns the last name of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return prenom
     */
    public String getOwnerLastName(){
        return super.getUserLastName();
    }
    /**
     * Methode setOwnerLastName(String last_name)
     * @param last_name 
     */
    public void setOwnerLastName(String last_name){
        super.setUserLastName(last_name);
    }
    
    /**
     * Methode getOwnerBirthdayDate()
     * This methode returns the birthday date of the user who is the 
     * owner of the SESAME. 
     * Returned type : Date
     * @return date_naissance
     */
    public Date getOwnerBirthdayDate(){
        return super.getUserBirthdayDate();
    }
    /**
     * Methode getOwnerBirthdayDate(Data date)
     * @param date 
     */
    public void setOwnerBirthdayDate(Date date){
        super.setUserBirthdayDate(date);
    }
    
    /**
     * Methode getOwnerPhoneNumber()
     * This methode returns the phone number of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return numero_mobile
     */
    public String getOwnerPhoneNumber(){
        return super.getUserPhoneNumber();
    }
    /**
     * Methode setOwnerPhoneNumber(String phone_number)
     * @param phone_number 
     */
    public void setOwnerPhoneNumber(String phone_number){
        super.setUserPhoneNumber(phone_number);
    }
    
    /**
     * Methode getOwnerEmailAddress()
     * This methode returns the phone number of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return adresse_mail
     */
    public String getOwnerEmailAddress(){
        return super.getUserEmailAddress();
    }
    /**
     * Methode setOwnerEmailAddress(String email_address)
     * @param email_address 
     */
    public void setOwnerEmailAddress(String email_address){
        super.setUserEmailAddress(email_address);
    }
    
    /**
     * Methode getOwnerStreetNumber()
     * This methode returns the street number of the home address of the user who is the 
     * owner of the SESAME. 
     * Returned type : int
     * @return numero_voie
     */
    public int getOwnerStreetNumber(){
        return super.getUserStreetNumber();
    }
    /**
     * Methode setOwnerStreetNumber(int street_number)
     * @param street_number 
     */
    public void setOwnerStreetNumber(int street_number){
        super.setUserStreetNumber(street_number);
    }
    
    /**
     * Methode getOwnerStreetName()
     * This methode returns the street name of the home address of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return nom_voie
     */
    public String getOwnerStreetName(){
        return super.getUserStreetName();
    }
    /**
     * Methode setOwnerStreetName(String street_name)
     * @param street_name 
     */
    public void setOwnerStreetName(String street_name){
        super.setUserStreetName(street_name);
    }
    
    /**
     * Methode getOwnerCodePostale()
     * This methode returns the Code Postale of the home address of the user who is the 
     * owner of the SESAME. 
     * Returned type : int
     * @return code_postale
     */
    public int getOwnerCodePostale(){
        return super.getUserCodePostale();
    }
    /**
     * Methode setOwnerCodePostale(int postale_code)
     * @param postale_code 
     */
    public void setOwnerCodePostale(int postale_code){
        super.setUserCodePostale(postale_code);
    }
    /**
     * Methode getOwnerCity()
     * This methode returns the city of home address of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return ville
     */
    public String getOwnerCity(){
        return super.getUserCity();
    }
    /**
     * Methode setOwnerCity(String city)
     * @param city 
     */
    public void setOwnerCity(String city){
        super.setUserCity(city);
    }
    
    /**
     * Methode getOwnerCountry()
     * This methode returns the country of the home address of the user who is the 
     * owner of the SESAME. 
     * Returned type : String
     * @return pays
     */
    public String getOwnerCountry(){
        return super.getUserCountry();
    }
    /**
     * Methode setOwnerCountry(String country)
     * @param country 
     */
    public void setOwnerCountry(String country){
        super.setUserCountry(country);
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
     * Returned type : String
     * @return password
     */
    public String getOwnerPassword(){
        return this.password;
    }
    /**
     * Methode setOwnerPassword(pass)
     * This methode sets the new password of the user who is the 
     * owner of the SESAME.
     * @arg type : String
     * @param pass
     */
    public void setOwnerPassword(String pass){
        this.password = pass;
    }
    
    /**
     * Methode getOwnerInformationForLinking() allow you to get the data about the owner of the Sesame
     * for the linking with the Sesame Doors
     * @return String[] which contains the data
     */
    public String [] getOwnerInformationForLinking(){
        String [] data_temp = super.getUserInformation();
        int size_data = data_temp.length;
        String [] data_out = new String[size_data + 2];
        System.arraycopy(data_temp, 0, data_out, 0, size_data);
        data_out[size_data] = user_id;
        data_out[size_data+1] = password;
        // End of the arrange data
        
        return data_out;
    }
    
    /**
     * Methode getOwnerInformationForSharing() allow you to get the data about the owner of the Sesame
     * for linking with another Sesame
     * @return String[] which contains the data
     */
    public String [] getOwnerInformationForSharing(){
        return super.getUserInformation();
    }
    
    private static final long serialVersionUID = 42L; 
    
    public static void main (String [] args){
        String [] data = null;
        UserIdentification user = new UserIdentification(data);
        System.out.println(user);
        System.out.println("<---------------------------------------->");
        OwnerInformation owner = new OwnerInformation(data);
        System.out.println(owner);
    }
}
