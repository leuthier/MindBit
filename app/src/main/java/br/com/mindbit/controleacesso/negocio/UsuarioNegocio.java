package br.com.mindbit.controleacesso.negocio;


import android.content.Context;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.dominio.Usuario;
import br.com.mindbit.controleacesso.persistencia.UsuarioDao;
import br.com.mindbit.infra.gui.MindbitException;

public class UsuarioNegocio {
    private static UsuarioDao usuarioDao;
    private static UsuarioNegocio instanciaUsuarioNegocio = new UsuarioNegocio();
    private UsuarioNegocio(){

    }
    public static UsuarioNegocio getInstanciaUsuarioNegocio(Context context){
        usuarioDao = UsuarioDao.getInstancia(context);
        return instanciaUsuarioNegocio;
    }



    public Usuario logar(String login, String senha) throws MindbitException{
        Usuario usuario = usuarioDao.buscarUsuario(login, senha);
        StringBuilder builder = new StringBuilder();
        if(usuario != null && usuario.getSenha().equals(senha)) {
            SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
            sessaoUsuario.setUsuarioLogado(usuario);
            sessaoUsuario.setPessoaLogada(pesquisarPorId(usuario.getId()));
        }
        else{
            builder.append("Login e/ou Senha inválido!");
            }
        if (builder.length()>0){
            throw new MindbitException(builder.toString());
        }
            //exceção aqui
           // GuiUtil.exibirMsg(loginActivity, context.getString(R.string.login_error));

        return usuario;
    }

    public Pessoa pesquisarPorId(int id){
        Pessoa pessoa = null;
        pessoa = usuarioDao.buscarPessoaId(id);
        return pessoa;
    }

}
