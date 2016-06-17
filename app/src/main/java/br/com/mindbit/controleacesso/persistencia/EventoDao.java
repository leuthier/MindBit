package br.com.mindbit.controleacesso.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.dominio.PrioridadeEvento;

public class EventoDao {

    private static DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
    private static Context contexto;
    private static EventoDao instanciaEventoDao = new EventoDao();

    private EventoDao() {
    }

    public static EventoDao getInstancia(Context contexto) {
        EventoDao.databaseHelper = new DatabaseHelper(contexto);
        return instanciaEventoDao;
    }

    public void cadastrarEvento(Evento evento) {
        db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.EVENTO_ID, evento.getId());
        values.put(DatabaseHelper.EVENTO_NOME, evento.getNome());

        //long foreing_key_id_pessoa_criadora = db.insert(DatabaseHelper.TABELA_EVENTO, null, values);

        values = new ContentValues();
        values.put(DatabaseHelper.EVENTO_NOME, evento.getNome());
        values.put(DatabaseHelper.EVENTO_DESCRICAO, evento.getDescricao().toString());
        values.put(DatabaseHelper.EVENTO_DATA_INICIO, evento.getDataInicio());
        values.put(DatabaseHelper.EVENTO_DATA_FIM, evento.getDataFim());
        values.put(DatabaseHelper.EVENTO_NIVEL_PRIORIDADE_ENUM, evento.getNivelPrioridadeEnum().ordinal());
        int idPessoa = SessaoUsuario.getInstancia().getPessoaLogada().getId();
        values.put(DatabaseHelper.PESSOA_CRIADORA_ID, idPessoa);

        db.insert(DatabaseHelper.TABELA_EVENTO, null, values);
        db.close();
    }

    public Evento criarEvento(Cursor cursor) {
        Evento evento = new Evento();
        evento.setId(cursor.getInt(0));
        evento.setNome(cursor.getString(1));
        evento.setDescricao(cursor.getString(2));
        long initialDate = cursor.getLong(3);
        long finalDate = cursor.getLong(4);

        //try {
            evento.setDataInicio(initialDate);
            evento.setDataFim(finalDate);
        /*} catch (ParseException e) {
            e.printStackTrace();
        }*/


        PrioridadeEvento nivelPrioridade = PrioridadeEvento.values()[cursor.getInt(5)];
        evento.setNivelPrioridadeEnum(nivelPrioridade);
        //Pessoa pessoa = daoPessoa.getPessoa(cursor.getInt(8));
        //evento.setPessoaCriadora(pessoa);
        //System.out.println(evento.getIdPessoaCriadora());



        return evento;
    }

    public Evento buscarEventoNome(String nome) {
        SQLiteDatabase db;
        db = databaseHelper.getReadableDatabase();

        Evento evento = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABELA_EVENTO +
                " WHERE " + DatabaseHelper.EVENTO_NOME + " =?", new String[]{nome});

        while (cursor.moveToNext()){
            evento = criarEvento(cursor);
        }
        db.close();
        cursor.close();
        return evento;
    }

    public ArrayList<Evento> buscarEventoNomeParcial(int id, String nome){
        db = databaseHelper.getReadableDatabase();

        ArrayList<Evento> listaEventos = new ArrayList<Evento>();

        Cursor cursor = db.rawQuery("SELECT "+databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_ID+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_NOME+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_DESCRICAO+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_DATA_INICIO+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_DATA_FIM+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_NIVEL_PRIORIDADE_ENUM+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.PESSOA_CRIADORA_ID+
                " FROM " + databaseHelper.TABELA_EVENTO + " WHERE "+ databaseHelper.PESSOA_CRIADORA_ID+" = ? AND "
                +databaseHelper.EVENTO_NOME+" LIKE ?", new String[] {String.valueOf(id),"%"+nome+"%"});

        Evento evento = null;
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                evento = criarEvento(cursor);
                listaEventos.add(evento);
            }
        }
        cursor.close();
        return listaEventos;
    }

    public List<Evento> buscarEventoDescricaoParcial(String descricao) {
        db = databaseHelper.getReadableDatabase();

        List<Evento> listaEventos = new ArrayList<Evento>();

        Cursor cursor = db.rawQuery("SELECT "+databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_ID+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_NOME+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_DESCRICAO+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_DATA_INICIO+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_DATA_FIM+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_NIVEL_PRIORIDADE_ENUM+", "+
                " FROM " + databaseHelper.TABELA_EVENTO + " WHERE "
                +databaseHelper.EVENTO_DESCRICAO+" LIKE ?", new String[] {"%"+descricao+"%"});

        Evento evento = null;
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                evento = criarEvento(cursor);
                listaEventos.add(evento);
            }
        }
        cursor.close();
        return listaEventos;
    }

    public Evento buscarEventoId(int id) {
        Evento evento = null;
        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + databaseHelper.TABELA_EVENTO + " WHERE " +
                databaseHelper.EVENTO_ID + " =?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            evento = criarEvento(cursor);
        }

        db.close();
        cursor.close();
        return evento;
    }

    public ArrayList<Evento> listarEventos(int id){
        Evento evento = null;
        ArrayList<Evento> listaEventos = new ArrayList<Evento>();

        db=databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + databaseHelper.TABELA_EVENTO + " WHERE " +
                databaseHelper.PESSOA_CRIADORA_ID + " =?", new String[]{String.valueOf(id)});

        while (cursor.moveToNext()){
            evento = criarEvento(cursor);
            listaEventos.add(evento);
        }

        db.close();
        cursor.close();
        return listaEventos;
    }
}