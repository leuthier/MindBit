package br.com.mindbit.controleacesso.negocio;

import android.content.Context;

import java.util.List;

import br.com.mindbit.controleacesso.dominio.Disciplina;
import br.com.mindbit.controleacesso.persistencia.DisciplinaDao;
import br.com.mindbit.infra.gui.MindbitException;

public class DisciplinaNegocio {
    private static DisciplinaDao disciplinaDao;
    private static UsuarioNegocio usuarioNegocio;

    private static DisciplinaNegocio instancia = new DisciplinaNegocio();
    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();

    private DisciplinaNegocio(){}


    /* singleton */
    public static DisciplinaNegocio getInstancia(Context context){
        disciplinaDao = disciplinaDao.getInstancia(context);
        return instancia;
    }

    /**
     * metodo usado para fazer a procura das disciplinas no DisciplinaDao atraves de pedacos do
     * seu nome
     *
     * @param nome      nome(parte do nome) da disciplina que sera procurada
     * @return          disciplinas encontradas
     */

    public List<Disciplina> consultarDisciplinaPorNomeParcial(String nome){
        return disciplinaDao.buscarDisciplinaNomeParcial(nome);
    }

    /**
     * metodo usado para fazer a procura das disciplinas no DisciplinaDao atraves do nome completo
     *
     * @param nome      nome da disciplina que sera procurada
     * @return          disciplinas encontradas
     */

    public Disciplina pesquisarPorNome(String nome){
        Disciplina disciplina = null;
        disciplina = disciplinaDao.buscarDisciplinaNome(nome);
        return disciplina;
    }

    /**
     * metodo utilizado para fazer a checagem no DisciplinaDao sobre a existencia de um objeto com
     * mesmo nome
     *
     * @param disciplina            disciplina que sera buscada no DisciplinaDao
     * @throws MindbitException     disciplina ja foi registrada no DisciplinaDao
     */
    public void validarCadastroDisciplina(Disciplina disciplina) throws MindbitException {
        Disciplina disciplinaBusca = disciplinaDao.buscarDisciplinaNome(disciplina.getNome());
        if (disciplinaBusca != null){
            throw new MindbitException("Disciplina já cadastrada");
        }
        disciplinaDao.cadastrarDisciplina(disciplina);
    }

    /**
     * metodo utilizado para registrar a disciplina no DisciplinaDao
     *
     * @param disciplina            disciplina que sera registrada no DisciplinaDao
     * @return                      retorna a disciplina informada
     * @throws MindbitException     disciplina foi encontrada no DisciplinaDao
     */
    public Disciplina salvarDisciplina(Disciplina disciplina) throws MindbitException{
        disciplina = null;
        disciplina = disciplinaDao.buscarDisciplinaNome(disciplina.getNome());
        if (disciplina!= null) {
            throw new MindbitException("disciplina já existe");
        }
        disciplinaDao.cadastrarDisciplina(disciplina);
        return disciplina;
    }
}