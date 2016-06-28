package br.com.mindbit.controleacesso.negocio;

import android.content.Context;

import br.com.mindbit.controleacesso.dominio.Amigo;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.persistencia.AmigoDao;
import br.com.mindbit.infra.gui.MindbitException;

public class AmigoNegocio {
    public static AmigoDao amigoDao;
    public static AmigoNegocio instancia = new AmigoNegocio();

    private AmigoNegocio() {
    }

    public static AmigoNegocio getInstancia(Context context) {
        amigoDao = AmigoDao.getInstancia(context);
        return instancia;
    }

    public static AmigoNegocio getInstancia() {
        return instancia;
    }

    public void validarAmigo(Amigo amigo) throws MindbitException {
        Amigo amigoEncontrado = amigoDao.buscarAmigoPorEmail(amigo.getEmail());
        if (amigoEncontrado != null){
            throw new MindbitException("Amigo já cadastrado");
        }

        amigoDao.addAmigo(amigo);
    }
}
