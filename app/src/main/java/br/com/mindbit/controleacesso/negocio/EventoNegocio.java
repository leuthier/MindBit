package br.com.mindbit.controleacesso.negocio;

import android.content.Context;

import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.persistencia.EventoDao;
import br.com.mindbit.infra.gui.MindbitException;

public class EventoNegocio {

    private static EventoDao eventoDao;
    private static EventoNegocio instancia = new EventoNegocio();

    private EventoNegocio(){}

    public static EventoNegocio getInstancia(Context context){
        eventoDao = EventoDao.getInstancia(context);
        return instancia;
    }

    public Evento pesquisarPorNome(String nome){
        Evento evento = null;
        evento = eventoDao.buscarEventoNome(nome);
        return evento;
    }

    public void validarCadastroEvento(Evento evento) throws MindbitException {
        Evento eventoBusca = eventoDao.buscarEventoNome(evento.getNome());
        if (eventoBusca != null){
            throw new MindbitException("Evento já cadastrado");
        }
        eventoDao.cadastrarEvento(evento);
    }
}
