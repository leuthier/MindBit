package br.com.mindbit.controleacesso.negocio;

import android.content.Context;

import java.util.List;

import br.com.mindbit.controleacesso.dominio.Disciplina;
import br.com.mindbit.controleacesso.persistencia.DisciplinaDao;
import br.com.mindbit.infra.gui.MindbitException;

/**
 * Classe utilizada para fazer validacao e pesquisas atraves do banco quanto a disciplina
 */
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

}