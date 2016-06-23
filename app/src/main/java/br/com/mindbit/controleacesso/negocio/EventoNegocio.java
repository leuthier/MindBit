package br.com.mindbit.controleacesso.negocio;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.persistencia.EventoDao;
import br.com.mindbit.infra.gui.MindbitException;

public class EventoNegocio {

    private static EventoDao eventoDao;
    private static UsuarioNegocio usuarioNegocio;

    private static EventoNegocio instancia = new EventoNegocio();
    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();

    private EventoNegocio(){}

    public static EventoNegocio getInstancia(Context context){
        eventoDao = EventoDao.getInstancia(context);
        return instancia;
    }

    public static EventoNegocio getInstancia(){
        return instancia;
    }

    public List<Evento> consultarNomeDescricaoParcial(int id, String nome) throws MindbitException {
        return eventoDao.buscarNomeDescricaoParcial(id, nome);
    }


    public Evento pesquisarPorNome(String nome) throws MindbitException {
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

    public ArrayList<Evento> listarEventoCriador(int idPessoaCriadora) throws MindbitException {
        return eventoDao.listarEventos(idPessoaCriadora);
    }

    public ArrayList<Evento> listarEventosProximo(int idPessoaCriadora) throws MindbitException {
        return eventoDao.listarEventoProximo(idPessoaCriadora);
    }

    public ArrayList<Evento> listarEventosDia(String data, int idPessoaCriadora) throws MindbitException{
        return eventoDao.listarEventoData(data,idPessoaCriadora);
    }

    public Evento salvarEvento(Evento evento) throws MindbitException{
        evento = null;
        evento = eventoDao.buscarEventoNome(evento.getNome());
        if (evento!= null) {
            throw new MindbitException("evento já existe");
        }
            eventoDao.cadastrarEvento(evento);
            return evento;
    }
}

