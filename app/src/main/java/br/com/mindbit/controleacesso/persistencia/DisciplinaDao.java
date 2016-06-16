package br.com.mindbit.controleacesso.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.mindbit.controleacesso.dominio.Disciplina;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;

/**
 * Created by Tiago on 15/06/2016.
 */
public class DisciplinaDao {

    private static DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
    private static Context context;

    private static DisciplinaDao instanciaDisciplinaDao = new DisciplinaDao();

    private DisciplinaDao(){}

    public static DisciplinaDao getInstancia(Context context){
        DisciplinaDao.databaseHelper = new DatabaseHelper(context);
        //DisciplinaDao.context = context;
        return instanciaDisciplinaDao;
    }

    public void cadastrarDisciplina(Disciplina disciplina){
        db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(DatabaseHelper.DISCIPLINA_ID, disciplina.getId());
        values.put(DatabaseHelper.DISCIPLINA_NOME, disciplina.getNome());
        values.put(DatabaseHelper.DISCIPLINA_CODIGO, disciplina.getCodigo());

        //long foreing_key_id_pessoa_disciplina = db.insert(DatabaseHelper.TABELA_DISCIPLINA, null, values);

        int idPessoa = SessaoUsuario.getInstancia().getPessoaLogada().getId();
        values.put(DatabaseHelper.PESSOA_CRIADORA_DISCIPLINA_ID, idPessoa);

        db.insert(DatabaseHelper.TABELA_DISCIPLINA, null, values);
        db.close();
    }

    private Disciplina criarDisciplina(Cursor cursor){
        Disciplina disciplina = new Disciplina();
        disciplina.setId(cursor.getInt(0));
        disciplina.setNome(cursor.getString(1));
        disciplina.setCodigo(cursor.getString(2));

        return disciplina;
    }

    public Disciplina buscarDisciplinaNome(String nome){
        SQLiteDatabase db;
        db = databaseHelper.getReadableDatabase();

        Disciplina disciplina = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABELA_DISCIPLINA +
                " WHERE " + DatabaseHelper.DISCIPLINA_NOME + " =?", new String[]{nome});
        if (cursor.moveToNext()){
            disciplina = criarDisciplina(cursor);
        }
        db.close();
        cursor.close();
        return disciplina;
    }

    public List<Disciplina> buscarDisciplinaNomeParcial(String nome){
        db = databaseHelper.getReadableDatabase();
        List<Disciplina> listaDisciplinas = new ArrayList<Disciplina>();

        Cursor cursor = db.rawQuery("SELECT "+ databaseHelper.TABELA_DISCIPLINA+"."+ databaseHelper.DISCIPLINA_ID+", "+
                databaseHelper.TABELA_DISCIPLINA+"."+databaseHelper.DISCIPLINA_NOME+", "+
                databaseHelper.TABELA_DISCIPLINA+"."+databaseHelper.DISCIPLINA_CODIGO+", "+
                " FROM " + databaseHelper.TABELA_DISCIPLINA + " WHERE " +
                databaseHelper.DISCIPLINA_NOME+" LIKE ?", new String[] {"%"+nome+"%"});
        Disciplina disciplina = null;
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                disciplina = criarDisciplina(cursor);
                listaDisciplinas.add(disciplina);
            }
        }
        cursor.close();
        return listaDisciplinas;
    }

    public Disciplina buscarDisciplinaId(int id) {
        Disciplina disciplina = null;
        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + databaseHelper.TABELA_DISCIPLINA + " WHERE " +
                databaseHelper.DISCIPLINA_ID + " =?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            disciplina = criarDisciplina(cursor);
        }
        db.close();
        cursor.close();
        return disciplina;
    }
     // public ArrayList<Disciplina> listarDisciplina(int id){
     // Disciplina disciplina = null;
     // ArrayList<Disciplina> listaDisciplina = new ArrayList<Disciplina>();
     //
     // db=databaseHelper.getReadableDatabase();
     // Cursor cursor = db.rawQuery("SELECT * FROM " + databaseHelper.TABELA_EVENTO + " WHERE " +
     // databaseHelper.PESSOA_CRIADORA_ID + " =?", new String[]{String.valueOf(id)});
     // // while (cursor.moveToNext()){
     // disciplina = criarDisciplina(cursor);
     // listaDisciplina.add(disciplina);
     // }
     //
     // db.close();
     // cursor.close();
     // return listaDisciplina;
     // } }
}