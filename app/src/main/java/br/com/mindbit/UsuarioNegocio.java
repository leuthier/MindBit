package br.com.mindbit;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import br.com.mindbit.dominio.Usuario;

public class UsuarioNegocio {
    private static UsuarioDao usuarioDao;
    private Resources resources;
    private Context context;
    private static UsuarioNegocio instanciaUsuarioNegocio = new UsuarioNegocio();
    private Activity loginActivity = (LoginActivity) context;
    private UsuarioNegocio(){
    }
    public static UsuarioNegocio getInstanciaUsuarioNegocio(Context context){
        usuarioDao = UsuarioDao.getInstancia(context);
        return instanciaUsuarioNegocio;
    }

    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();

    public void logar(String login, String senha){
        Usuario usuario = usuarioDao.buscarLogin(login);

        if (usuario == null || !usuario.getLogin().equals(senha)){
            GuiUtil.exibirMsg(loginActivity, context.getString(R.string.login_error));
        }
        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        sessaoUsuario.setUsuarioLogado(usuario);
        //sessaoUsuario.setPessoaLogada(pesquisarPorId(usuario.getId()));
    }

}
