package br.com.mindbit.controleacesso.negocio;


import android.content.Context;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.dominio.Usuario;
import br.com.mindbit.controleacesso.gui.LoginActivity;
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
        String loginInvalido = "";

        if (usuario==null){
            loginInvalido = LoginActivity.getContext().getString(R.string.login_error);
        }
        if (loginInvalido.length()>0){
            throw new MindbitException(loginInvalido);
        }

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        sessaoUsuario.setUsuarioLogado(usuario);
        sessaoUsuario.setPessoaLogada(pesquisarPorId(usuario.getId()));

        return usuario;
    }

    public Pessoa pesquisarPorId(int id){
        Pessoa pessoa = null;
        pessoa = usuarioDao.buscarPessoaId(id);
        return pessoa;
    }

    public void validarCadastro(Pessoa pessoa, String user, String email) throws MindbitException {
        Usuario usuario = usuarioDao.buscarUsuarioLogin(user);
        if (usuario != null){
            throw new MindbitException("Nome de usuário indisponível");
        }
        pessoa = usuarioDao.buscaPessoaPorEmail(email);
        if (pessoa != null){
            throw new MindbitException("Email já cadastrado");
        }
        usuarioDao.cadastrarPessoa(pessoa);
    }


}
