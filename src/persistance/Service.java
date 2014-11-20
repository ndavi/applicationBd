package persistance;

/**
 * Created by ndavi on 20/11/2014.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import metier.*;
import java.util.*;
import meserreurs.*;

public class Service implements InterfaceService {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "livres.db";
    private static String COL_ID;
    private static final int NUM_COL_ID = 0;
    private static String COL_ISBN;
    private static final int NUM_COL_ISBN = 1;
    private static String COL_TITRE;
    private static final int NUM_COL_TITRE = 2;
    private SQLiteDatabase bdd;
    private static Context unCtxt;
    private static InterfaceService instance = null;
    private static CreationBdSqlLite maBaseSQLite;

    /**
     * Constructeur : un Service doit référencer
     * la page qui l'a appelé
     *
     * @param c
     */
    private Service(Context c) {
        unCtxt = c;
    }
    /**
     * On utilise un singleton pour n'obtenir
     * qu'une instance de la BD dans l'application.
     * @param c
     * @return
     * @throws MonException
     */
    public static InterfaceService getInstance(Context c) throws MonException
    {
// Si instance n'est pas null, c'est qu'elle
// a déjà été créée
        if (instance ==null)
        {
            instance = new Service(c);
            try {
                creationBDD();
            } catch (MonException e) {
// On remonte l'exception
                throw e;
            }
        }
        return instance;
    }
    /**
     * Création de la base et de la table
     * @throws MonException
     */
    public static void creationBDD () throws MonException
    {
//On créer la BDD et sa table
        maBaseSQLite = new CreationBdSqlLite(unCtxt, NOM_BDD, null, VERSION_BDD);
        COL_ID =maBaseSQLite.getColId();
        COL_ISBN=maBaseSQLite.getColIsbn();
        COL_TITRE=maBaseSQLite.getColTitre();
    }
    /**
     * @see persistance.InterfaceService#open()
     */
    @Override
    public void open(){
//on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }
    /**
     * @see persistance.InterfaceService#close()
     */
    @Override
    public void close(){
//on ferme l'accès à la BDD
        bdd.close();
    }
    /**
     * @see persistance.InterfaceService#getBDD()
     * @return bdd
     */
    @Override
    public SQLiteDatabase getBDD(){
        return bdd;
    }
    /**
     * @see persistance.InterfaceService#insertLivre(metier.Livre)
     * @param livre
     * @return
     * @throws MonException
     */
    @Override
    public long insertLivre(Livre livre) throws MonException {
        long lg=0;
// Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
// On lui ajoute une valeur associé à une clé
// (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_ISBN, livre.getIsbn());
        values.put(COL_TITRE, livre.getTitre());
// Insertion de l'objet dans la BDD via le ContentValues
        lg= bdd.insert(maBaseSQLite.getTableLivres(), null, values);
        if( lg <0 )
            new MonException("Erreur d'insertion", "erreur de BD");
        return lg;
    }
    /**
     * @see persistance.InterfaceService#updateLivre(int, metier.Livre)
     * @param id
     * @param livre
     * @return
     * @throws MonException
     */
    /*
    @Override
    public int updateLivre(int id, Livre livre) throws MonException {
        int lg=0;
// La mise à jour d'un livre dans la BDD fonctionne plus ou moins
// comme une insertion, il faut simplement préciser quel livre
// on doit mettre à jour grâce à l'ID
/* A vous de compléter
        lg= bdd.update(maBaseSQLite.getTableLivres(),livre.getIsbn()), COL_ID + " = " +id, null);
        if( lg <0 )
/* A vous de compléter
            return lg;
    }*/
    /**
     * @see persistance.InterfaceService#removeLivreWithID(int)
     * @param id
     * @return
     * @throws MonException
     */
    @Override
    public int removeLivreWithID(int id) throws MonException {
        int lg=0;
//Suppression d'un livre de la BDD grâce à l'ID
        lg= bdd.delete(maBaseSQLite.getTableLivres(), COL_ID + " = " +id, null);
        if( lg <0 )
            new MonException("Erreur de suppression","Erreur de BD");
        return lg;
    }
    /**
     * @see persistance.InterfaceService#getTousLeslivres()
     * @return
     * @throws MonException
     */
    @Override
    public List<Livre> getTousLeslivres() throws MonException {
        List<Livre> mesLivres = new ArrayList<Livre>();
        try {
// Ouverture de la base de données
            this.open();
            Cursor cursor = bdd.query(maBaseSQLite.getTableLivres(),
                    new String[] {COL_ID, COL_ISBN, COL_TITRE} ,null,null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Livre unLivre = cursorToLivre(cursor);
                mesLivres.add(unLivre);
                cursor.moveToNext();
            }
// Make sure to close the cursor
            cursor.close();
            this.close();
        }
        catch( Exception e) {
            new MonException("Erreur recherche des livres", e.getMessage());
        }
        return mesLivres;
    }
    /**
     * Méthode permettant de convertir un cursor en un livre
     * @param c : curseur contenant l'occurrence de table_livre
     * issue de la requête dans la BdD
     * @return
     */
    private Livre cursorToLivre(Cursor c){
        Livre unLivre = null;
        if (c.getCount() != 0) {
            unLivre = new Livre();
            unLivre.setId(c.getInt(NUM_COL_ID));
            unLivre.setIsbn(c.getString(NUM_COL_ISBN));
            unLivre.setTitre(c.getString(NUM_COL_TITRE));
        }
        return unLivre;
    }
}
