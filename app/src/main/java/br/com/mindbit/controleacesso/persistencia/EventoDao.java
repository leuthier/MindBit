package br.com.mindbit.controleacesso.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.FormatException;
import android.util.Log;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.mindbit.controleacesso.gui.PesquisarEventoActivity;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.dominio.PrioridadeEvento;
import br.com.mindbit.infra.gui.GuiUtil;
import br.com.mindbit.infra.gui.MindbitException;

/**
 * Classe do banco de eventos
 */
public class EventoDao {

    private static DatabaseHelper databaseHelper;
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
    private static Context contexto;
    private static EventoDao instanciaEventoDao = new EventoDao();

    private EventoDao() {}

    /* singleton */
    public static EventoDao getInstancia(Context contexto) {
        EventoDao.databaseHelper = new DatabaseHelper(contexto);
        return instanciaEventoDao;
    }


    /**
     * @param evento                    evento a ser cadastrado no db
     * @throws MindbitException         caso o evento nao consiga ser cadastrado
     */
    public void cadastrarEvento(Evento evento) throws MindbitException {
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

        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DATE_FORMATTER;

        try{
            values.put(DatabaseHelper.EVENTO_DATA_INICIO,simpleDateFormat.format(evento.getDataInicio()));
            values.put(DatabaseHelper.EVENTO_DATA_FIM,simpleDateFormat.format(evento.getDataFim()));
        } catch (Exception e) {
            throw new MindbitException(e.getMessage());
        }

        db.insert(DatabaseHelper.TABELA_EVENTO, null, values);
        db.close();
    }

    /**
     *
     * @param cursor                        cursor a ser usado na criacao do evento
     * @return                              objeto evento preenchido
     * @throws MindbitException             caso o evento nao possa ser criado
     */
    public Evento criarEvento(Cursor cursor) throws MindbitException {
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
            throw new MindbitException(e.getMessage());
        }


        PrioridadeEvento nivelPrioridade = PrioridadeEvento.values()[cursor.getInt(5)];
        evento.setNivelPrioridadeEnum(nivelPrioridade);


        return evento;
    }

    /**
     * @param nome                      nome do evento que sera encontrado
     * @return                          evento com o nome desejado encontrado
     * @throws MindbitException         caso o evento nao possa ser encontrado
     */
    public Evento buscarEventoNome(String nome) throws MindbitException {
        SQLiteDatabase db;
        db = databaseHelper.getReadableDatabase();

        Evento evento = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + databaseHelper.TABELA_EVENTO +
                " WHERE " + databaseHelper.EVENTO_NOME + " =?", new String[]{nome});

        if (cursor.moveToFirst()){
            evento = criarEvento(cursor);
        }
        db.close();
        cursor.close();
        return evento;
    }


    /**
     * @param id                        id dos eventos que serao procurados
     * @param nome                      nome dos eventos que sao procurados
     * @return                          lista de eventos contendo partes do nome procurado
     * @throws MindbitException
     */
    public ArrayList<Evento> buscarNomeDescricaoParcial(int id, String nome) throws MindbitException {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        ArrayList<Evento> listaEventos = new ArrayList<Evento>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + databaseHelper.TABELA_EVENTO + " WHERE " + databaseHelper.PESSOA_CRIADORA_ID + " = ? AND ("
                + databaseHelper.EVENTO_NOME + " LIKE ? OR " + databaseHelper.EVENTO_DESCRICAO + " LIKE ?)", new String[]{String.valueOf(id), "%" + nome + "%", "%" + nome + "%"});

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


    /**
     * @param id                         id do evento que sera procurado
     * @return                           evento encontrado
     * @throws MindbitException
     */
    public Evento buscarEventoId(int id) throws MindbitException {
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


    /**
     * @param id                          id dos eventos que serao mostrados
     * @return                            lista de eventos com id procurado
     * @throws MindbitException
     */
    public ArrayList<Evento> listarEventos(int id) throws MindbitException {
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


    /**
     * @param id                          id dos eventos que serao mostrados
     * @return                            lista com os eventos
     * @throws MindbitException
     */
    public ArrayList<Evento> listarEventoProximo(int id) throws MindbitException {
        Evento evento = null;
        ArrayList<Evento> eventosCriador = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + databaseHelper.TABELA_EVENTO + " WHERE " +
                databaseHelper.PESSOA_CRIADORA_ID + " =? ORDER BY "+ databaseHelper.EVENTO_DATA_INICIO +" ASC" , new String[]{String.valueOf(id)});

        while (cursor.moveToNext()){
            evento = criarEvento(cursor);
            eventosCriador.add(evento);
        }

        db.close();
        cursor.close();
        return eventosCriador;
    }

    /**
     *
     * @param data                         data dos eventos que serao mostrados
     * @param id                           id dos eventos que serao mostrados
     * @return                             lista com os eventos encontrados na data informada
     * @throws MindbitException
     */
    public ArrayList<Evento> listarEventoData(String data, int id)throws MindbitException{
        Evento evento = null;
        ArrayList eventosDia = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + databaseHelper.TABELA_EVENTO + " WHERE " + databaseHelper.PESSOA_CRIADORA_ID + " =? AND "
                + databaseHelper.EVENTO_DATA_INICIO +" LIKE ? ORDER BY "+databaseHelper.EVENTO_DATA_INICIO + " ASC" , new String[]{String.valueOf(id),"%"+data+"%"});

        while (cursor.moveToNext()){
            evento = criarEvento(cursor);
            eventosDia.add(evento);
        }
        db.close();
        cursor.close();
        return eventosDia;
    }

}