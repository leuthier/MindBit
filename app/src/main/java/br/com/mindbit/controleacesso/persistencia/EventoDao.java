package br.com.mindbit.controleacesso.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.sql.Time;

import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.dominio.PrioridadeEvento;

public class EventoDao {
    private static DatabaseHelper databaseHelper;
    private static Context contexto;

    private static EventoDao instanciaEventoDao = new EventoDao();

    private EventoDao() {
    }

    public static EventoDao getInstancia(Context contexto) {
        EventoDao.contexto = contexto;
        EventoDao.databaseHelper = new DatabaseHelper(contexto);
        return instanciaEventoDao;
    }

    private SQLiteDatabase db;
    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();

    public void cadastrarEvento(Evento evento) {
        db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //long foreing_key_id_evento = db.insert(DatabaseHelper.TABELA_EVENTO, null, values);


        values.put(DatabaseHelper.EVENTO_ID, evento.getId());
        values.put(DatabaseHelper.EVENTO_NOME, evento.getNome());

        long foreign_key_id_pessoa = db.insert(DatabaseHelper.TABELA_PESSOA, null, values);

        values = new ContentValues();
        //todos os .toString() podem dar treta em algum lugar
        values.put(DatabaseHelper.EVENTO_DESCRICAO, evento.getDescricao().toString());
        values.put(DatabaseHelper.EVENTO_HORA_INICIO, evento.getHoraInicio().toString());
        values.put(DatabaseHelper.EVENTO_HORA_FIM, evento.getHoraFim().toString());
        values.put(DatabaseHelper.EVENTO_DATA_INICIO, evento.getDataInicio().toString());
        values.put(DatabaseHelper.EVENTO_DATA_FIM, evento.getDataFim().toString());
        values.put(DatabaseHelper.EVENTO_NIVEL_PRIORIDADE_ENUM, evento.getNivelPrioridadeEnum().toString());
        values.put(DatabaseHelper.PESSOA_CRIADORA_ID, foreign_key_id_pessoa);

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
        return evento;
    }

    public Evento buscarEventoNome(String nome) {
        SQLiteDatabase db;
        db = databaseHelper.getReadableDatabase();

        Evento evento = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABELA_EVENTO +
                " WHERE " + DatabaseHelper.EVENTO_NOME + " =?", new String[]{nome});
        if (cursor.moveToFirst()) {
            evento = criarEvento(cursor);
        }
        db.close();
        cursor.close();
        return evento;
    }

    public Evento buscarEventoDescricao(String descricao) {
        SQLiteDatabase db;
        db = databaseHelper.getReadableDatabase();

        Evento evento = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABELA_EVENTO +
                " WHERE " + DatabaseHelper.EVENTO_DESCRICAO + " =?", new String[]{descricao});
        if (cursor.moveToFirst()) {
            evento = criarEvento(cursor);
        }
        db.close();
        cursor.close();
        return evento;
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
}