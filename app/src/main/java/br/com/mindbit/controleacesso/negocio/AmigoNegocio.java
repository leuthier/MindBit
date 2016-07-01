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

/**
 * Classe utilizada para fazer validacao e pesquisa no banco
 */
public class AmigoNegocio {
    private static AmigoDao amigoDao;
    private static AmigoNegocio instancia = new AmigoNegocio();
    private AmigoNegocio() {
    }

    /* singleton */
    public static AmigoNegocio getInstancia(Context context) {
        amigoDao = AmigoDao.getInstancia(context);
        return instancia;
    }
    public static AmigoNegocio getInstancia() {
        return instancia;
    }


    /**
     * metodo utilizado para adicionar amigo
     *
     * @param amigo                 amigo que sera adicionado
     * @throws MindbitException     caso amigo ja exista no banco
     */
    public void adicionarAmigo(Amigo amigo) throws MindbitException {

        Amigo amigoEncontrado = amigoDao.buscarAmigoPorEmail(amigo.getEmail());
        if (amigoEncontrado == null){
            amigoDao.addAmigo(amigo);

        }else{
            throw new MindbitException("Amigo já existe.");
        }
    }


    /**
     * metodo utilizado para fazer uma listagem dos amigos do usuario
     *
     * @param idPessoa  id do usuario que tera amigos listados
     * @return          lista de amigos do usuario
     * @throws MindbitException
     */
    public List<Amigo> listarAmigos(int idPessoa) throws MindbitException{
        return amigoDao.listarAmigos(idPessoa);
    }

    /**
     * metodo utilizado para fazer a pesquisa dos amigos a partir do email
     *
     * @param email email que sera utilizado na busca
     * @return      amigo que possui o email fornecido
     * @throws MindbitException
     */
    public Amigo buscarPorEmail(String email) throws MindbitException{
        return amigoDao.buscarAmigoPorEmail(email);
    }
}
