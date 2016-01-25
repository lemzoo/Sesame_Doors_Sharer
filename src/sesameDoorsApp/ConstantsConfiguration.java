// Peripherique
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
public interface ConstantsConfiguration {
    
    final String BONJOUR = "BR_01";
    
    final String DEMANDE_ENREGISTREMENT_PROPRIETAIRE   = "DEP_01";
    final String ENREGISTREMENT_PROPRIETAIRE_AUTORISEE = "EPA_01";
    
    // Debut et fin d'envoie de plusieurs infos => 7 caractères
    final String DEBUT_ENVOIE_INFORMATION_PROPRIETAIRE = "DEIP_01";
    final String FIN_ENVOI_INFORMATION_PROPRIETAIRE    = "FEIP_01";
    
    final String PROPRITAIRE_ENREGISTREE_CORRECTEMENT  = "PEC_01";
    final String DONNEES_PROPRIETAIRE_RECUES_ERONNEES  = "DPRE_01";
    
    final String DEMANDE_RATTACHEMENT_SESAME = "DRS_01";
    final String RATTACHEMENT_AUTORISEE_PERIPHERIQUE = "RAP_01";
    
    final String DEBUT_ENVOI_INFORMATION_RATTACHEMENT = "DEIR_01";
    final String FIN_ENVOI_INFORMATION_RATTACHEMENT = "FEIR_01";
    
    final String SESAME_RATTACHE_CORRECTEMENT = "SRC_01";
    final String DONNEES_SESAME_RECUES_ERONNEES = "DSRE_01";
    
    final String DEMANDE_CONFIRMATION_RATTACHEMENT = "DCR_01";
    
    final String DEBUT_ENVOIE_CONFIRMATION_RATTACHEMENT = "DECR_01";
    final String FIN_ENVOIE_CONFIRMATION_RATTACHEMENT   = "FECR_01";
    
    final String DEBUT_ENVOIE_KEY_LINK = "DEKL_01";
    final String FIN_ENVOIE_KEY_LINK   = "FEKL_01";
    
    final String KEY_LINK_ENREGISTREE_CORRECTEMENT = "KLEC_01";
    final String KEY_LINK_DONNEES_ERONNEES   = "KLDE_01";
    
    final String AUTRE_DEMANDE = "AD_01";
    final String MERCI = "MI_01";
    final String FIN_DE_LA_COMMUNICATION = "FC_01";
    
    final String BEGIN = "BGN_01";
    final String END = "END_01";
}
