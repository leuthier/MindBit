package br.com.mindbit.controleacesso.negocio;

import android.content.Context;

import java.util.List;

import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.persistencia.UsuarioDao;
import br.com.mindbit.infra.gui.MindbitException;

public class AmigoNegocio {
    private static UsuarioDao usuarioDao;
    private static AmigoNegocio instancia = new AmigoNegocio();
    private SessaoUsuario sessaoUsuario;
    private AmigoNegocio() {
    }

    public static AmigoNegocio getInstancia(Context context) {
        usuarioDao = UsuarioDao.getInstancia(context);
        return instancia;
    }

    public static AmigoNegocio getInstancia() {
        return instancia;
    }

    public void adicionarAmigo(Pessoa amigo) throws MindbitException {
        Pessoa amigoEncontrado = usuarioDao.buscaPessoaPorEmail(amigo.getEmail());
        if (amigoEncontrado != null){
            usuarioDao.adicionarAmigo(amigo);
        }else{
            throw new MindbitException("Não foi possível adicionar um amigo. :(");
        }
    }

    public List<Pessoa> listarAmigos(int idPessoa) throws MindbitException{
        return usuarioDao.listarAmigos(idPessoa);
    }

}
