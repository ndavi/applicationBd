package metier;

/**
 * Created by ndavi on 20/11/2014.
 */
import java.util.List;
import persistance.InterfaceService;
import persistance.Service;
import android.content.Context;
import meserreurs.MonException;

public class Livre {
    private int id;
    private String isbn;
    private String titre;
    private String auteur;
    private Context unCtxt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Livre() {
    }

    public Livre(Context c) {
        unCtxt = c;
    }
    /**
     * Ajout d'un livre dans la BdD
     * @param isbn
     * @param titre
     * @throws MonException
     */
    public void ajoutLivre (String isbn, String titre,String auteur) throws MonException {
        String m;
        try {
// Instanciation de la classe Service
            InterfaceService unService = Service.getInstance(unCtxt);
// Affectation des paramètres aux propriétés
            this.setIsbn(isbn);
            this.setTitre(titre);
            this.setAuteur(auteur);
// Ouverture de la base de données pour écrire dedans
            unService.open();
// Insertion du livre
            unService.insertLivre(this);
            unService.close();
        }
        catch(MonException e) {
            throw e;
        }
    }
    /**
     * Liste de tous les livres
     * @return Collection de Livre
     * @throws MonException
     */
    public List<Livre> rechercheTousLesLivres() throws MonException {
        List<Livre> mesLivres;
        try {
            InterfaceService unService = Service.getInstance(unCtxt);
            mesLivres = unService.getTousLeslivres();
            return mesLivres;
        }
        catch(MonException e) {
            throw e;
        }
    }
    @Override
    public String toString()
    {
        return this.getTitre();
    }
}
