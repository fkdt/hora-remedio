package br.com.robotrock.horaremedio;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.robotrock.horaremedio.adapters.DBAdapter;
import br.com.robotrock.horaremedio.domain.Remedio;
import br.com.robotrock.horaremedio.fragments.RemFragment;


public class ListaRemedios extends ActionBarActivity {
    private static String TAG = "LOG";
    private Toolbar mToolbar;
    private Toolbar mToolbarBottom;
    private DBAdapter db;
    public int QTD_REM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_remedios);

        // Instancia e abre conexão com db
        db = new DBAdapter(this);
        db.open();

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Lista de Remedios");
        mToolbar.setSubtitle("Subtitulo");
       //  mToolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(mToolbar);

        mToolbarBottom = (Toolbar) findViewById(R.id.inc_tb_bottom);
        mToolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent it = null;

                switch (menuItem.getItemId()) {
                    case R.id.action_facebook:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.facebook.com"));
                        break;
                    case R.id.action_youtube:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.youtube.com"));
                        break;
                    case R.id.action_google_plus:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://plus.google.com"));
                        break;
                    case R.id.action_linkedin:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.linkedin.com"));
                        break;
                    case R.id.action_whatsapp:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.whatsapp.com"));
                        break;
                }

                startActivity(it);
                return true;
            }
        });
        mToolbarBottom.inflateMenu(R.menu.menu_bottom);

        mToolbarBottom.findViewById(R.id.iv_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListaRemedios.this, "Settings pressed", Toast.LENGTH_SHORT).show();
            }
        });


        // FRAGMENT
        RemFragment frag = (RemFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new RemFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
            ft.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_second_activity){
            startActivity(new Intent(this, SecondActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }



    public List<Remedio> getSetRemList(int qtd){

        List<Remedio> listaRemedios = new ArrayList<>();

        Cursor cursor = db.getAllRows();
        if( cursor.moveToFirst() ){
            for( int i  = 0; i < qtd; i++, cursor.moveToNext() ){
                Remedio r = new Remedio( cursor.getString( DBAdapter.COL_NOME ),
                                         cursor.getString( DBAdapter.COL_TIPO_DOSAGEM ),
                                         cursor.getString( DBAdapter.COL_TEMPO_TRATAMENTO ),
                                            cursor.getInt( DBAdapter.COL_DOSE ),
                                            cursor.getInt( DBAdapter.COL_INTERVALO ),
                                            cursor.getInt( DBAdapter.COL_QUANTIDADE ) );
                listaRemedios.add( r );
            }


        }

        QTD_REM = listaRemedios.size();
        cursor.close();

        return(listaRemedios);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        db.close();
    }
}
