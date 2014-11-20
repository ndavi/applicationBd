package com.example.applicationBd;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.Toast;
import meserreurs.MonException;
import metier.Livre;
import persistance.InterfaceService;
import persistance.Service;

public class SaisieLivre extends Activity implements View.OnClickListener  {
    private Button btAjouter;
    private Button btAnnuler;
    private EditText etCodeISBN;
    private EditText etTitreLivre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saisie);
        btAjouter = (Button)findViewById(R.id.btn_saisie_ajout);
        btAnnuler = (Button)findViewById(R.id.btn_saisie_annuler);
        etCodeISBN = (EditText) findViewById(R.id.input_saisie_ISBN);
        etTitreLivre = (EditText) findViewById(R.id.input_saisie_titre);
        btAjouter.setOnClickListener(this);
        btAnnuler.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == btAjouter) {
            String isbn;
            String titre;
            Livre livre = new Livre(this);
// on récupère les données
            isbn = etCodeISBN.getText().toString();
            titre = etTitreLivre.getText().toString();
            try {
                livre.ajoutLivre(isbn,titre);
            } catch (MonException e) {
                messageErreur(e ,"Erreur sur insertion des données !");
            }
            etTitreLivre.setText("");
            etCodeISBN.setText("");
        } else if (v == btAnnuler) {
            finish();
        }
    }
    private void messageErreur(Exception e ,final String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Erreur")
                .setMessage(e.getMessage())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//Bouton cliqué, on affiche
                        Toast.makeText(SaisieLivre.this, msg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
