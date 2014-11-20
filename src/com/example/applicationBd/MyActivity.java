package com.example.applicationBd;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.Toast;
import metier.Livre;

import java.util.List;

public class MyActivity extends Activity implements View.OnClickListener {
    private Button btAjouter;
    private Button btAfficher;
    private Button btQuitter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
// On utilise le listener pour les boutons
        btAjouter = (Button) findViewById(R.id.btn_ajout);
        btAfficher = (Button) findViewById(R.id.btn_affichage);
        btQuitter = (Button) findViewById(R.id.btn_quitter);
        btAjouter.setOnClickListener(this);
        btAfficher.setOnClickListener(this);
        btQuitter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ( v== btAjouter ) {
            try {
                Intent intent = new Intent(MyActivity.this, SaisieLivre.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        if (v== btAfficher ) {
            try {
                Intent intent = new Intent(MyActivity.this, AffichageLivre.class);
                startActivity(intent);
            } catch (Exception e) {
                messageErreur(e ,"Erreur !");
            }
        }
        else
        if (v==btQuitter) {
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
                        Toast.makeText(MyActivity.this, msg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
