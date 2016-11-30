// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package br.com.robotrock.horaremedio.adapters;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // Log
    private static final String TAG = "DBAdapter";

    /*
     * Campos da Base de Dados
     *       // campos na base são declarados aqui
     */
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;

    public static final String KEY_NOME = "nome";
    public static final String KEY_TIPO_DOSAGEM = "tipo_dosagem";
    public static final String KEY_TEMPO_TRATAMENTO = "tempo_tratamento";
    public static final String KEY_DOSE = "dose";
    public static final String KEY_INTERVALO = "intervalo";
    public static final String KEY_QUANTIDADE = "quantidade";

    // Numero das colunas para utilização na classe helper
    public static final int COL_NOME = 1;
    public static final int COL_TIPO_DOSAGEM = 2;
    public static final int COL_TEMPO_TRATAMENTO = 3;
    public static final int COL_DOSE = 4;
    public static final int COL_INTERVALO = 5;
    public static final int COL_QUANTIDADE = 6;

    // A fazer: converter campos acima em array de strings: nomes de campos são mapeados
    //      em outro array, enquanto numero de colunas são os proprios indices
    public static final String[] ALL_KEYS = new String[] {  KEY_ROWID,
                                                            KEY_NOME,
                                                            KEY_TIPO_DOSAGEM,
                                                            KEY_TEMPO_TRATAMENTO,
                                                            KEY_DOSE,
                                                            KEY_INTERVALO,
                                                            KEY_QUANTIDADE  };


    public static final String DATABASE_NAME = "MyDb";
    public static final String TABLE_MEDICAMENTOS = "Medicamentos";

    public static final int DATABASE_VERSION = 3;

    private static final String DATABASE_CREATE_SQL =
            "create table " + TABLE_MEDICAMENTOS
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
			
			/*
			 * Mudar abaixo
			 */
                    + KEY_NOME + " text not null, "
                    + KEY_TIPO_DOSAGEM + " text not null, "
                    + KEY_TEMPO_TRATAMENTO + " text not null,"
                    + KEY_DOSE + " integer not null,"
                    + KEY_INTERVALO + " integer not null,"
                    + KEY_QUANTIDADE + " integer not null"
                    // Rest  of creation:
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /*
     *  Funções de manipulação da db
     */

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Abre conexão com a db
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }
    // Fecha conexão com a db
    public void close() {
        myDBHelper.close();
    }

    // (insertinto tabela Medicamentos)
    public long insertIntoMedicamento(String nome, String tipo_dosagem, String tempo_tratamento,
                          int dose, int intervalo, int quantidade) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOME, nome);
        initialValues.put(KEY_TIPO_DOSAGEM, tipo_dosagem);
        initialValues.put(KEY_TEMPO_TRATAMENTO, tempo_tratamento);
        initialValues.put(KEY_DOSE, dose);
        initialValues.put(KEY_INTERVALO, intervalo);
        initialValues.put(KEY_QUANTIDADE, quantidade);

        return db.insert(TABLE_MEDICAMENTOS, null, initialValues);
    }

    // (deleterow tabela Medicamentos)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(TABLE_MEDICAMENTOS, where, null) != 0;
    }

    // (deleteall tabela Medicamentos)
    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // (getall tabela Medicamentos)
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, TABLE_MEDICAMENTOS, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Retorna uma tupla baseado no id
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, TABLE_MEDICAMENTOS, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // (update table Medicamentos)
    public boolean updateRowInMedicamentos(long rowId, String nome, String tipo_dosagem, String tempo_tratamento,
                             int dose, int intervalo, int quantidade ) {
        String where = KEY_ROWID + "=" + rowId;

        // Cria tupla de dados
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NOME, nome);
        newValues.put(KEY_TIPO_DOSAGEM, tipo_dosagem);
        newValues.put(KEY_TEMPO_TRATAMENTO, tempo_tratamento);
        newValues.put(KEY_DOSE, dose);
        newValues.put(KEY_INTERVALO, intervalo);
        newValues.put(KEY_QUANTIDADE, quantidade);

        // retorna true se inserido no db
        return db.update(TABLE_MEDICAMENTOS, newValues, where, null) != 0;
    }



    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAMENTOS);

            // Recreate new database:
            onCreate(_db);
        }
    }
}