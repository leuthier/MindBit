package br.com.mindbit.controleacesso.negocio;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.gui.LoginActivity;
import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Usuario;
import br.com.mindbit.controleacesso.persistencia.UsuarioDao;
import br.com.mindbit.infra.gui.GuiUtil;

public class UsuarioNegocio {
    private static UsuarioDao usuarioDao;
    private Resources resources;
    private Context context;
    private static UsuarioNegocio instanciaUsuarioNegocio = new UsuarioNegocio();
    private Activity loginActivity;
    private UsuarioNegocio(){

    }
    public static UsuarioNegocio getInstanciaUsuarioNegocio(Context context){
        usuarioDao = UsuarioDao.getInstancia(context);
        instanciaUsuarioNegocio.setLoginActivity(context);
        instanciaUsuarioNegocio.setContext(context);
        return instanciaUsuarioNegocio;
    }

    private void setLoginActivity(Context context){
        loginActivity = (LoginActivity) context;
    }
    private void setContext(Context context){
        this.context = context;
    }

    public boolean logar(String login, String senha) {
        Usuario usuario = usuarioDao.buscarUsuarioLogin(login);
        boolean ok = (usuario != null && usuario.getSenha().equals(senha));
        if (!ok){
            GuiUtil.exibirMsg(loginActivity, context.getString(R.string.login_error));
        } else {
            SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
            sessaoUsuario.setUsuarioLogado(usuario);
            sessaoUsuario.setPessoaLogada(pesquisarPorId(usuario.getId()));
        }
        return ok;
    }

    public Pessoa pesquisarPorId(int id){
        Pessoa pessoa = null;
        pessoa = usuarioDao.buscarPessoaId(id);
        return pessoa;
    }

}
