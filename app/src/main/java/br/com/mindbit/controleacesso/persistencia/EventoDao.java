package br.com.mindbit.controleacesso.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.FormatException;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.dominio.PrioridadeEvento;

public class EventoDao {

    private static DatabaseHelper databaseHelper;

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
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.EVENTO_ID, evento.getId());
        values.put(DatabaseHelper.EVENTO_NOME, evento.getNome());

        //long foreing_key_id_pessoa_criadora = db.insert(DatabaseHelper.TABELA_EVENTO, null, values);

        values = new ContentValues();
        values.put(DatabaseHelper.EVENTO_NOME, evento.getNome());
        values.put(DatabaseHelper.EVENTO_DESCRICAO, evento.getDescricao().toString());
        values.put(DatabaseHelper.EVENTO_NIVEL_PRIORIDADE_ENUM, evento.getNivelPrioridadeEnum().ordinal());
        int idPessoa = SessaoUsuario.getInstancia().getPessoaLogada().getId();
        values.put(DatabaseHelper.PESSOA_CRIADORA_ID, idPessoa);

        //apagar dps
        values.put(DatabaseHelper.EVENTO_DATA_INICIO,evento.getDataInicio().toString());
        values.put(DatabaseHelper.EVENTO_DATA_FIM,evento.getDataFim().toString());

        //correção gabriel
       /* SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dataInicio = evento.getDataInicio().toString();
        String dataFim = evento.getDataFim().toString();
        try{
            values.put(DatabaseHelper.EVENTO_DATA_INICIO,simpleDateFormat.format(dataInicio));
            values.put(DatabaseHelper.EVENTO_DATA_FIM,simpleDateFormat.format(dataFim));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        db.insert(DatabaseHelper.TABELA_EVENTO, null, values);
        db.close();
    }

    public Evento criarEvento(Cursor cursor) {
        Evento evento = new Evento();
        evento.setId(cursor.getInt(0));
        evento.setNome(cursor.getString(1));
        evento.setDescricao(cursor.getString(2));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String initialDate = cursor.getString(3);
        String finalDate = cursor.getString(4);
        try {
            evento.setDataInicio(simpleDateFormat.parse(initialDate));
            evento.setDataFim(simpleDateFormat.parse(finalDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }


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

    public ArrayList<Evento> buscarNomeDescricaoParcial(int id, String nome){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        ArrayList<Evento> listaEventos = new ArrayList<Evento>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + databaseHelper.TABELA_EVENTO + " WHERE "+ databaseHelper.PESSOA_CRIADORA_ID+" = ? AND ("
                +databaseHelper.EVENTO_NOME+" LIKE ? OR " +databaseHelper.EVENTO_DESCRICAO+ " LIKE ?)", new String[] {String.valueOf(id),"%"+nome+"%","%"+nome+"%"});

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
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
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

        SQLiteDatabase db=databaseHelper.getReadableDatabase();
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