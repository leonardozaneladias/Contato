package br.com.caelum.contato;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android7583 on 18/07/18.
 */

public class AlunoDAO extends SQLiteOpenHelper {

    List<Aluno> alunos = new ArrayList<>();
    private  static final int VERSAO = 2;

    public AlunoDAO(Context context) {
        super(context, "Cadastros", null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, telefone TEXT, endereco TEXT, site TEXT, nota REAL, photo TEXT);";
        sqLiteDatabase.execSQL(sql);

        Log.i("sqlTest", "case create");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        switch (oldVersion){
            case 1:
                String sqlv1 = "DROP TABLE IF EXISTS alunos;";
                sqLiteDatabase.execSQL(sqlv1);
                onCreate(sqLiteDatabase);
                Log.i("sqlTest", "case 1");
            case 2:
                sqLiteDatabase.execSQL("ALTER TABLE alunos ADD COLUMN photo TEXT");
                Log.i("sqlTest", "case 2");
        }

    }

    public void salva(Aluno aluno)
    {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("photo", aluno.getPhoto());

        getWritableDatabase().insert("alunos", null, values);
    }

    public List<Aluno> getLista(){

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM alunos", null);
        while (cursor.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));

            alunos.add(aluno);
        }
        cursor.close();
        return alunos;

    }

    public void editar(Aluno aluno){

        SQLiteDatabase database = getWritableDatabase();
        String[] args = {aluno.getId().toString()};
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("photo", aluno.getPhoto());
        database.update("alunos", values ,"id = ?", args);

    }

    public void exclui(Aluno aluno){

        SQLiteDatabase database = getWritableDatabase();
        String[] args = {aluno.getId().toString()};
        database.delete("alunos", "id = ?", args);

    }

}
