package models;
import etu2033.annotation.url;
import etu2033.process.ModelView;

public class Emp {

    String nom;
    String prenom;

    @url(value="emp-nomComplet")
    public ModelView nomComplet(){
        ModelView modelView = new ModelView();
        modelView.setView("nom.jsp");
        String nom =  "Rakoto";
        modelView.addItem("koto", nom);
        return modelView;
    }

    @url(value="emp-prenom")
    public ModelView prenom(){
        ModelView modelView = new ModelView();
        modelView.addItem("ty",this);
        modelView.setView("prenom.jsp");
        return modelView;
    }


    public Emp(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    public Emp() {
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }



    
}
