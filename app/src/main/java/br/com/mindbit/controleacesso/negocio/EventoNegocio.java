package br.com.mindbit.controleacesso.negocio;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.persistencia.EventoDao;
import br.com.mindbit.infra.gui.MindbitException;

/**
 * Classe utilizada para fazer validacao e pesquisas atraves do banco quanto ao evento
 */
public class EventoNegocio {

    private static EventoDao eventoDao;

    private static EventoNegocio instancia = new EventoNegocio();

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
     * metodo utilizado para criar uma lista com os eventos que estao sendo pesquisados, a partir
     * de parte do que esta sendo escrito
     *
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
     * metodo utilizado para listar eventos pelo id da pessoa por ordem de data
     *
     * @param idPessoaCriadora     id do usuario que criou os eventos
     * @return                     lista de eventos criados pelo usuario
     * @throws MindbitException
     */
    public ArrayList<Evento> listarEventosProximo(int idPessoaCriadora) throws MindbitException {
        return eventoDao.listarEventoProximo(idPessoaCriadora);
    }

    /**
     * metodo utilizado para listar os eventos do dia
     *
     * @param data                 dia dos eventos que serao procurados
     * @param idPessoaCriadora     id do usuario que criou os eventos
     * @return                     lista de eventos
     * @throws MindbitException
     */
    public ArrayList<Evento> listarEventosDia(String data, int idPessoaCriadora) throws MindbitException{
        return eventoDao.listarEventoData(data,idPessoaCriadora);
    }
}
