package br.com.robotrock.horaremedio;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.robotrock.horaremedio.adapters.DBAdapter;

/**
 * Created by robotrock on 11/28/16.
 */


public class CadRemedio extends Activity {

    private AppCompatEditText nome, tipo_dosagem,
            tempo_tratamento, dose, intervalo, quantidade;
    private DBAdapter db;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_cad_remedio );

        /* Recolhe campos */
        nome = (AppCompatEditText) findViewById( R.id.in_nome );
        tipo_dosagem = (AppCompatEditText) findViewById( R.id.in_tipo_dosagem );
        tempo_tratamento = (AppCompatEditText) findViewById( R.id.in_tempo_tratamento );
        dose = (AppCompatEditText) findViewById( R.id.in_dose );
        intervalo = (AppCompatEditText) findViewById( R.id.in_intervalo );
        quantidade = (AppCompatEditText) findViewById( R.id.in_quantidade );


        AppCompatButton btn1 = (AppCompatButton)findViewById(R.id.btn_insereMedicamento);
        btn1.setOnClickListener(btnListener);

        AppCompatButton btn2 = (AppCompatButton)findViewById(R.id.btn_deleta);
        btn2.setOnClickListener(btnListener);
        // Abre conexão com db
        openDB();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    public void ck_teste(){

        Toast.makeText(this, String.valueOf(R.drawable.remedio) , Toast.LENGTH_SHORT).show();
    }

    public void ck_insereRemedio(){
        String text_nome = nome.getText().toString();
        String text_tipo_dosagem = tipo_dosagem.getText().toString();
        String text_tempo_tratamento = tempo_tratamento.getText().toString();
        int text_dose = Integer.parseInt(dose.getText().toString());
        int text_intervalo = Integer.parseInt(intervalo.getText().toString());
        int text_quantidade = Integer.parseInt(quantidade.getText().toString());

        db.insertIntoMedicamento( text_nome, text_tipo_dosagem, text_tempo_tratamento,
                text_dose, text_intervalo, text_quantidade );
    }

    /* Lollilop possui bug em methodo onCLick de views, então é necessário implementar listener */
    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch( view.getId() ){
                case R.id.btn_insereMedicamento:
                    ck_insereRemedio();
                    break;
                case R.id.btn_deleta :
                    db.deleteAll();
                    break;
                default:

            }
        }
    };
/*
    public void click_insereRemedio(View view){
        this.view = view;
        String text_nome = nome.getText().toString();
        String text_tipo_dosagem = tipo_dosagem.getText().toString();
        String text_tempo_tratamento = tempo_tratamento.getText().toString();
        int text_dose = Integer.parseInt(dose.getText().toString());
        int text_intervalo = Integer.parseInt(intervalo.getText().toString());
        int text_quantidade = Integer.parseInt(quantidade.getText().toString());

        db.insertIntoMedicamento( text_nome, text_tipo_dosagem, text_tempo_tratamento,
                text_dose, text_intervalo, text_quantidade );
    }
    */

    /* Funções DB */
    public void openDB(){
        db = new DBAdapter(this);
        db.open();
    }

    public void closeDB(){
        db.close();
    }




}
