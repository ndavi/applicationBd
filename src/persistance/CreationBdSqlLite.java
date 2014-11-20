package persistance;

/**
 * Created by ndavi on 20/11/2014.
 */
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import meserreurs.*;

public class CreationBdSqlLite extends SQLiteOpenHelper {
    private static final String TABLE_LIVRE = "table_livre";
    private static final String COL_ID = "ID";
    private static final String COL_ISBN = "ISBN";
    private static final String COL_TITRE = "Titre";
    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_LIVRE +
            " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ISBN +
            " TEXT NOT NULL, " + COL_TITRE + " TEXT NOT NULL);";
    public CreationBdSqlLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    // On écrit tous les getteurs
    public static String getTableLivres() {
        return TABLE_LIVRE;
    }
    public static String getColId() {
        return COL_ID;
    }
    public static String getColIsbn() {
        return COL_ISBN;
    }
    public static String getColTitre() {
        return COL_TITRE;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
// On redéfinit la méthode onCreate @Override
// on créé la table table_livres à partir de la requête
// écrite dans la variable CREATE_BDD
            db.execSQL(CREATE_BDD);
        }
        catch (SQLException e) {
            new MonException(e.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
//On supprime la table puis on la recrée
            db.execSQL("DROP TABLE " + TABLE_LIVRE + ";");
            onCreate(db);
        }
        catch (SQLException e) {
            new MonException(e.getMessage());
        }
    }
}
