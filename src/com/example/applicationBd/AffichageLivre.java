package com.example.applicationBd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import meserreurs.MonException;
import metier.Livre;
import android.content.DialogInterface;

import java.util.List;

/**
 * Created by ndavi on 20/11/2014.
 */
public class AffichageLivre extends Activity implements View.OnClickListener{
    private Button btQuitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage);
        btQuitter = (Button) findViewById(R.id.btn_affichage_quitter);
        btQuitter.setOnClickListener(this);
        GridView unGv = (GridView)findViewById(R.id.gdv);
        Livre unlivre = new Livre(this);
        try {
// Appel de la méthode rechercheTousLesLivres()
            List<Livre> mesLivres = unlivre.rechercheTousLesLivres();
                    String[] tabLivres = new String[mesLivres.size() * 3] ;
            int i=0;
            for ( Livre l : mesLivres ) {
                tabLivres[i++]= l.getIsbn();
                tabLivres[i++]= l.getTitre();
                tabLivres[i++]= l.getAuteur();
            }
//Création et initialisation de l'Adapter pour les personnes
            unGv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tabLivres));
            unGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Toast.makeText(AffichageLivre.this, "" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (MonException e) {
            messageErreur(e ,"Erreur sur affichage du Grid !");
        }
    }
    /**
     * Affichage d'une erreur
     * @param e l'exception qui s'est produite
     * @param msg message personnalisé
     */
    private void messageErreur(Exception e ,final String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Erreur")
                .setMessage(e.getMessage())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//Bouton cliqué, on affiche
                        Toast.makeText(AffichageLivre.this, msg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onClick(View v) {
        if (v == btQuitter) {
            finish();
        }
    }
}
