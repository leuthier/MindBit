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

    private Pessoa pessoaEncontrada;

    public static UsuarioNegocio getInstancia(Context context){
        usuarioDao = UsuarioDao.getInstancia(context);
        return instanciaUsuarioNegocio;
    }

    public Usuario logar(String login, String senha) throws MindbitException{
        Usuario usuario = usuarioDao.buscarUsuario(login, senha);
        String loginInvalido = "";

        if (usuario==null){
            loginInvalido = LoginActivity.getContexto().getString(R.string.login_erro);
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

    public void validarCadastro(Pessoa pessoa) throws MindbitException {
        Usuario usuario = usuarioDao.buscarUsuarioLogin(pessoa.getUsuario().getLogin());
        if (usuario != null){
            throw new MindbitException("Nome de usuário indisponível");
        }
        pessoaEncontrada = usuarioDao.buscaPessoaPorEmail(pessoa.getEmail());
        if (pessoaEncontrada != null){
            throw new MindbitException("Email já cadastrado");
        }
        usuarioDao.cadastrarPessoa(pessoa);
    }
}
