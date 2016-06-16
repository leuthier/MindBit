package br.com.mindbit.controleacesso.negocio;

import android.content.Context;

import java.util.List;

import br.com.mindbit.controleacesso.dominio.Disciplina;
import br.com.mindbit.controleacesso.persistencia.DisciplinaDao;
import br.com.mindbit.infra.gui.MindbitException;

/**
 * Created by Tiago on 15/06/2016.
 */
public class DisciplinaNegocio {
    private static DisciplinaDao disciplinaDao;
    private static UsuarioNegocio usuarioNegocio;

    private static DisciplinaNegocio instancia = new DisciplinaNegocio();
    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();

    private DisciplinaNegocio(){}

    public static DisciplinaNegocio getInstancia(Context context){
        disciplinaDao = disciplinaDao.getInstancia(context);
        return instancia;
    }

    public List<Disciplina> consultarDisciplinaPorNomeParcial(String nome){
        return disciplinaDao.buscarDisciplinaNomeParcial(nome);
    }

    public Disciplina pesquisarPorNome(String nome){
        Disciplina disciplina = null;
        disciplina = disciplinaDao.buscarDisciplinaNome(nome);
        return disciplina;
    }

    public void validarCadastroDisciplina(Disciplina disciplina) throws MindbitException {
        Disciplina disciplinaBusca = disciplinaDao.buscarDisciplinaNome(disciplina.getNome());
        if (disciplinaBusca != null){
            throw new MindbitException("Disciplina já cadastrada");
        }
        disciplinaDao.cadastrarDisciplina(disciplina);
    }

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

