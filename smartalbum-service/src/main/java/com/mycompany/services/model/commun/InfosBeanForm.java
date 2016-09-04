package com.mycompany.services.model.commun;

import org.hibernate.validator.constraints.Email;

import com.mycompany.filesystem.model.FileMeta;
import com.mycompany.smartalbum.back.form.validator.constraint.Password;


public class InfosBeanForm {
    
    private String[] allDepartment = {"01 Ain","02 Aisne","03 Allier","04 Alpes-de-Haute-Provence","05 Hautes-Alpes","06 Alpes-Maritimes","07 Ardèche","08 Ardennes","09 Ariège","10 Aube","11 Aude","12 Aveyron","13 Bouches-du-Rhône","14 Calvados","15 Cantal","16 Charente","17 Charente-Maritime","18 Cher","19 Corrèze","20 Corse","21 Côte-d\'Or","22 Côtes-d\'Armor","23 Creuse","24 Dordogne","25 Doubs","26 Drôme","27 Eure","28 Eure-et-Loir","29 Finistère","30 Gard","31 Haute-Garonne","32 Gers","33 Gironde","34 Hérault","35 Ille-et-Vilaine","36 Indre","37 Indre-et-Loire","38 Isère","39 Jura","40 Landes","41 Loir-et-Cher","42 Loire","43 Haute-Loire","44 Loire-Atlantique","45 Loiret","46 Lot","47 Lot-et-Garonne","48 Lozère","49 Maine-et-Loire","50 Manche","51 Marne","52 Haute-Marne","53 Mayenne","54 Meurthe-et-Moselle","55 Meuse","56 Morbihan","57 Moselle","58 Nièvre","59 Nord","60 Oise","61 Orne","62 Pas-de-Calais","63 Puy-de-Dôme","64 Pyrénées-Atlantiques","65 Hautes-Pyrénées","66 Pyrénées-Orientales","67 Bas-Rhin","68 Haut-Rhin","69 Rhône","70 Haute-Saône","71 Saône-et-Loire","72 Sarthe","73 Savoie","74 Haute-Savoie","75 Paris","76 Seine-Maritime","77 Seine-et-Marne","78 Yvelines","79 Deux-Sèvres","80 Somme","81 Tarn","82 Tarn-et-Garonne","83 Var","84 Vaucluse","85 Vendée","86 Vienne","87 Haute-Vienne","88 Vosges","89 Yonne","90 Territoire de Belfort","91 Essonne","92 Hauts-de-Seine","93 Seine-Saint-Denis","94 Val-de-Marne","95 Val-d'Oise"};
    private String[] months = {"Mois","Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"};
    private String civ;
    private String adresse;
    private String codePostal;
    private String complementAdresse;
    private String ville;
    private String telephone;
    private Boolean newsletter;
    private String login;
    private String errorMessage;
    
    private boolean avatarUploaded;
    private String action;
    
    /*
     * gestion de champ de type fichier:
     * - <attrib>File transporte le fichier recu en HTTP
     * - delete<Attrib> vaut "on" si il faut supprimer l'attribut, null sinon
     * ne pas oublier de mettre l'enctype dans le formulaire !!!
     */
    /** Photo file. */
    private FileMeta avatarData;
    
    private String jour;
    private String mois;
    private String annee;
    
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String departement;
    
    private String username;
    
    @Email
    private String email;
    
    //@Password
    private String password;
    
    private String confirmPassword;
    
    private String oldPassword;
    
    private String description;
    
    public String getCiv() {
		return civ;
	}
	public String getAdresse() {
		return adresse;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public String getComplementAdresse() {
		return complementAdresse;
	}
	public String getVille() {
		return ville;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getEmail() {
		return email;
	}
	
	public String getJour() {
		return jour;
	}
	
	public String getMois() {
		return mois;
	}
	public String getAnnee() {
		return annee;
	}
	
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setCiv(String civ) {
		this.civ = civ;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public void setComplementAdresse(String complementAdresse) {
		this.complementAdresse = complementAdresse;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setJour(String jour) {
		this.jour = jour;
	}
	
	public void setMois(String mois) {
		this.mois = mois;
	}
	public void setAnnee(String annee) {
		this.annee = annee;
	}
	
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
    /**
     * @return the departement
     */
    public String getDepartement() {
        return departement;
    }
    /**
     * @param departement the departement to set
     */
    public void setDepartement(String departement) {
        this.departement = departement;
    }
    public String[] getAllDepartment() {
        return allDepartment;
    }
    public void setAllDepartment(String[] allDepartment) {
        this.allDepartment = allDepartment;
    }
    public String[] getMonths() {
        return months;
    }
    public void setMonths(String[] months) {
        this.months = months;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getNewsletter() {
        return newsletter;
    }
    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
	public FileMeta getAvatarData() {
		return avatarData;
	}
	public void setAvatarData(FileMeta avatarData) {
		this.avatarData = avatarData;
	}
	public boolean isAvatarUploaded() {
		return avatarUploaded;
	}
	public void setAvatarUploaded(boolean avatarUploaded) {
		this.avatarUploaded = avatarUploaded;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
}
