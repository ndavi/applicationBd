package persistance;

/**
 * Created by ndavi on 20/11/2014.
 */
import java.util.List;
import metier.Livre;
import android.database.sqlite.SQLiteDatabase;
import meserreurs.*;

public interface InterfaceService {
    // Ouverture de la base de données
    public abstract void open();
    // Fermeture de la base de données
    public abstract void close();
    // Instance de la base courante
    public abstract SQLiteDatabase getBDD();
    /**
     * Insertion d'un livre dans la base
     * @param livre
     * @return
     * @throws MonException
     */
    public abstract long insertLivre(Livre livre) throws MonException;
    // Mise à jour d'un livre
    //public abstract int updateLivre(int id, Livre livre) throws MonException;
    // Suppresion d'un livre de la base
    public abstract int removeLivreWithID(int id) throws MonException;
    // Liste de tous les livres
    public abstract List<Livre> getTousLeslivres() throws MonException;
    // Livre sur le titre
    //public abstract Livre getLivreWithTitre(String titre) throws MonException;
}
