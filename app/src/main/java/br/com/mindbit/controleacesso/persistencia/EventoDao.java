package br.com.mindbit.controleacesso.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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

        //long foreign_key_id_pessoa = db.insert(DatabaseHelper.TABELA_PESSOA, null, values);

        values = new ContentValues();
        values.put(DatabaseHelper.EVENTO_DESCRICAO, evento.getDescricao().toString());
        values.put(DatabaseHelper.EVENTO_HORA_INICIO, evento.getHoraInicio().toString());
        values.put(DatabaseHelper.EVENTO_HORA_FIM, evento.getHoraFim().toString());
        values.put(DatabaseHelper.EVENTO_DATA_INICIO, evento.getDataInicio().toString());
        values.put(DatabaseHelper.EVENTO_DATA_FIM, evento.getDataFim().toString());
        values.put(DatabaseHelper.EVENTO_NIVEL_PRIORIDADE_ENUM, evento.getNivelPrioridadeEnum().toString());
        //values.put(DatabaseHelper.PESSOA_CRIADORA_ID, foreign_key_id_pessoa);
        values.put(DatabaseHelper.PESSOA_CRIADORA_ID, evento.getIdPessoaCriadora());

        db.insert(DatabaseHelper.TABELA_EVENTO, null, values);
        db.close();
    }

    public Evento criarEvento(Cursor cursor) {
        Evento evento = new Evento();
        evento.setId(cursor.getInt(0));
        evento.setNome(cursor.getString(1));
        evento.setDescricao(cursor.getString(2));
        evento.setHoraInicio(Time.valueOf(cursor.getString(3)));
        evento.setHoraFim(Time.valueOf(cursor.getString(4)));
        evento.setDataInicio(Date.valueOf(cursor.getString(5)));
        evento.setDataFim(Date.valueOf(cursor.getString(6)));
        evento.setNivelPrioridadeEnum(PrioridadeEvento.valueOf(cursor.getString(7)));

        /*evento.setHoraInicio(cursor.getString(3));
        evento.setHoraFim(cursor.getString(4));
        evento.setDataInicio(cursor.getString(5));
        evento.setDataFim(cursor.getString(6));
        evento.setNivelPrioridadeEnum(cursor.getString(7));
        */
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

    public List<Evento> buscarEventoNomeParcial(String nome){
        db = databaseHelper.getReadableDatabase();

        List<Evento> listaEventos = new ArrayList<Evento>();

        Cursor cursor = db.rawQuery("SELECT "+databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_ID+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_NOME+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_DESCRICAO+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_DATA_INICIO+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_HORA_INICIO+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_DATA_FIM+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_HORA_FIM+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_NIVEL_PRIORIDADE_ENUM+
                " FROM " + databaseHelper.TABELA_EVENTO + " WHERE "
                +databaseHelper.EVENTO_NOME+" LIKE ?", new String[] {"%"+nome+"%"});

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
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_HORA_INICIO+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_DATA_FIM+", "+
                databaseHelper.TABELA_EVENTO+"."+databaseHelper.EVENTO_HORA_FIM+", "+
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