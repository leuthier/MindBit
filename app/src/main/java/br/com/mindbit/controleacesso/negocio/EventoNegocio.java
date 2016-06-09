package br.com.mindbit.controleacesso.negocio;

import android.content.Context;

import java.util.List;

import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.persistencia.EventoDao;
import br.com.mindbit.infra.gui.MindbitException;

public class EventoNegocio {

    private static EventoDao eventoDao;
    private static UsuarioNegocio usuarioNegocio;

    private static EventoNegocio instancia = new EventoNegocio();
    private EventoNegocio(){}
    public static EventoNegocio getInstancia(Context context){
        eventoDao = EventoDao.getInstancia(context);
        return instancia;
    }

    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();

    public List<Evento> consultarRemedioPorNomeParcial(String nome){
        return eventoDao.buscarEventoNomeParcial(nome);
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
