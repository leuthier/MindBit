package br.com.mindbit.controleacesso.negocio;

import android.content.Context;

import java.util.List;

import br.com.mindbit.controleacesso.dominio.Amigo;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.gui.AddAmigoActivity;
import br.com.mindbit.controleacesso.persistencia.AmigoDao;
import br.com.mindbit.controleacesso.persistencia.UsuarioDao;
import br.com.mindbit.infra.gui.GuiUtil;
import br.com.mindbit.infra.gui.MindbitException;

public class AmigoNegocio {
    private static AmigoDao amigoDao;
    private static AmigoNegocio instancia = new AmigoNegocio();
    private SessaoUsuario sessaoUsuario;
    private AmigoNegocio() {
    }

    public static AmigoNegocio getInstancia(Context context) {
        amigoDao = AmigoDao.getInstancia(context);
        return instancia;
    }

    public static AmigoNegocio getInstancia() {
        return instancia;
    }

    public void adicionarAmigo(Amigo amigo) throws MindbitException {
        Amigo amigoEncontrado = amigoDao.buscarAmigoPorEmail(amigo.getEmail());
        if (amigoEncontrado == null){
            amigoDao.addAmigo(amigo);
        }else{
            throw new MindbitException("Amigo já existe.");
        }
    }

    public List<Amigo> listarAmigos() throws MindbitException{
        int idPessoa = sessaoUsuario.getPessoaLogada().getId();
        return amigoDao.listarAmigos(idPessoa);
    }

}
