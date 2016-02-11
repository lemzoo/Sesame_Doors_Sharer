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
    
    //<========================== DEBUT CAS "RATTACHER" =======================>
    
    /*                            ----------------                            */
    
    //<=========================== DEBUT "RATTACHER" =========================>//
    // Code de terminaison "01" correspond à la fonction "RATTACHER"
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
    //<========================== FIN CAS "RATTACHER" =========================>
    
    /*                            ----------------                            */
    
    //<=========================== DEBUT "ACCEDER" ==========================>//
    // Code de terminaison "02" correspond à la fonction "ACCEDER"
    final String DEMANDE_ETABLISSEMENT_CONNEXION_ACCES = "DECA_02";
    final String ETABLISSEMENT_CONNEXION_ACCES_ETABLIE = "ECAE_02";
    
    final String DEBUT_ENVOIE_DONNEES_ACCES = "DEDA_02";
    final String FIN_ENVOIE_DONNEES_ACCES   = "FEDA_02";
    
    final String DEMANDE_ACCES_AUTORISEE = "DAA_02";
    final String DEMANDE_ACCES_REFUSEE   = "DAR_02";
    
    final String OUVRIR = "ON_02";
    final String FERMER = "OFF_02";
    
    final String SERRURE_DEVEROUILLEE = "SD_02";
    final String SERRURE_VERROUILLEE   = "SV_02";
    final String ETAT_SERRURE_INCONNU = "ESI_02";
    //<========================== FIN CAS "ACCEDER" ===========================>
    
    /*                            ----------------                            */
    
    //<=========================== DEBUT "PARTAGE CLE ACCES" ================>//
    // Code de terminaison "03" correspond à la fonction "PARTAGER"
    final String SCAN_SESAME_ENVIRONNANT = "SSE_03";
    
    final String DEMANDE_PARTAGE_ACCES   = "DPA_03";
    final String PARTAGE_ACCES_AUTORISEE = "PAA_03";
    
    final String DEBUT_ENVOIE_INFORMATION_UTILISATEUR_PARTAGEANT = "DEIUP_03";
    final String FIN_ENVOIE_INFORMATION_UTILISATEUR_PARTAGEANT   = "FEIPA_03";
    
    final String INFORMATION_PARTAGEUR_ENREGISTRE_CORRECTEMENT  = "IPEC_03";
    final String INFORMATION_PARTAGEUR_DONNEES_ERONEES          = "IPDE_03";
    
    final String DEMANDE_INFORMATION_IDENTIFICATION_ACCREDITEE = "DIIA_03";
    
    final String DEBUT_ENVOIE_INFORMATION_UTILISATEUR_ACCREDITE = "DEIUA_03";
    final String FIN_ENVOIE_INFORMATION_UTILISATEUR_ACCREDITE   = "FEIUA_03";
    
    final String INFORMATION_ACCREDITE_ENREGISTRE_CORRECTEMENT = "IAEC_03";
    final String INFORMATION_ACCREDITE_DONNEES_ERONNEES        = "IADE_03";
    //<=========================== FIN PARTAGE ACCES =========================>//
    
    final String AUTRE_DEMANDE = "AD_01";
    final String MERCI = "MI_01";
    final String FIN_DE_LA_COMMUNICATION = "FC_01";
    
    final String BEGIN = "BGN_01";
    final String END = "END_01";
}