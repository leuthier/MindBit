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

    /* singleton */
    public static EventoNegocio getInstancia(Context context){
        eventoDao = EventoDao.getInstancia(context);
        return instancia;
    }
    public static EventoNegocio getInstancia(){
        return instancia;
    }


    /**
     * @param id        id dos eventos que serao mostrados na lista
     * @param nome      nome dos eventos que serao mostrados na lista
     * @return          lista com os eventos encontrados
     * @throws MindbitException
     */
    public List<Evento> consultarNomeDescricaoParcial(int id, String nome) throws MindbitException {
        return eventoDao.buscarNomeDescricaoParcial(id, nome);
    }


    /**
     * metodo utilizado para fazer a procura dos eventos no EventoDao atraves do nome completo
     *
     * @param nome                          nome do evento que sera procurado
     * @return                              lista com os eventos encontrados
     * @throws MindbitException
     */
    public Evento pesquisarPorNome(String nome) throws MindbitException {
        Evento evento = eventoDao.buscarEventoNome(nome);
        return evento;
    }

    /**
     * metodo utilizado para fazer a checagem no EventoDao sobre a existencia de um objeto com
     * mesmo nome
     *
     * @param evento                    evento que sera registrado no EventoDao
     * @throws MindbitException         caso o evento ja tenha sido cadastrado
     */

    public void validarCadastroEvento(Evento evento) throws MindbitException {
        Evento eventoBusca = eventoDao.buscarEventoNome(evento.getNome());
        if (eventoBusca != null){
            throw new MindbitException("Evento já cadastrado");
        }
        eventoDao.cadastrarEvento(evento);
    }

    /**
     *
     * @param idPessoaCriadora      id do usuario que criou os eventos
     * @return                      lista de eventos criados pelo usuario chamado
     * @throws MindbitException
     */
    public ArrayList<Evento> listarEventoCriador(int idPessoaCriadora) throws MindbitException {
        return eventoDao.listarEventos(idPessoaCriadora);
    }

    /**
     * @param idPessoaCriadora     id do usuario que criou os eventos
     * @return                     lista de eventos criados pelo usuario
     * @throws MindbitException
     */
    public ArrayList<Evento> listarEventosProximo(int idPessoaCriadora) throws MindbitException {
        return eventoDao.listarEventoProximo(idPessoaCriadora);
    }

    /**
     * @param data                 dia dos eventos que serao procurados
     * @param idPessoaCriadora     id do usuario que criou os eventos
     * @return                     lista de eventos
     * @throws MindbitException
     */
    public ArrayList<Evento> listarEventosDia(String data, int idPessoaCriadora) throws MindbitException{
        return eventoDao.listarEventoData(data,idPessoaCriadora);
    }

    /**
     * metodo utilizado para registrar o evento no EventoDao
     *
     * @param evento               evento que sera registrado no EventoDao
     * @return                     retorna o evento informado
     * @throws MindbitException    caso o evento que esteja sendo salvo ja esteja registrado
     */
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
