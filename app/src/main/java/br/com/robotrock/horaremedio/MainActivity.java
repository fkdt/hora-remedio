package br.com.robotrock.horaremedio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.robotrock.horaremedio.adapters.DBAdapter;

/* Passos para usar a DB:
 * 1. Instancia o Adaptador DB
 * 2. Abrir a DB
 * 3. Usar get, insert, delete, para mudar dados
 * 4. Fechar a DB
 */

public class MainActivity extends Activity {

    DBAdapter db;

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.activity_main );
        openDB();
    }
    private void openDB() {
        // this:context onde será usado
        db = new DBAdapter( this );
        db.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }
    private void closeDB() {
        db.close();
    }

    private void displayText(String message ){
    }

    public void clickCadRemedio(View view){
        Intent i = new Intent( this, CadRemedio.class );
        startActivity( i );
    }

    public void clickLsRemedios( View view ){
        Intent i = new Intent( this, ListaRemedios.class );
        startActivity( i );
    }

    public void clickCfAlarmes( View view ){
    }





}
