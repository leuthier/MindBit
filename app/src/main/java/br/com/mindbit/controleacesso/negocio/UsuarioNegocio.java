package br.com.mindbit.controleacesso.negocio;


import android.content.Context;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.dominio.Usuario;
import br.com.mindbit.controleacesso.gui.LoginActivity;
import br.com.mindbit.controleacesso.persistencia.UsuarioDao;
import br.com.mindbit.infra.gui.MindbitException;

/**
 * Classe utilizada para fazer validacao e pesquisas através do banco quanto ao usuario
 */
public class UsuarioNegocio {
    private static UsuarioDao usuarioDao;
    private static UsuarioNegocio instanciaUsuarioNegocio = new UsuarioNegocio();

    private UsuarioNegocio() {}

    private Pessoa pessoaEncontrada;

    /* singleton */
    public static UsuarioNegocio getInstancia(Context context) {
        usuarioDao = UsuarioDao.getInstancia(context);
        return instanciaUsuarioNegocio;
    }

    /**
     * método utilizado para logar o usuario no sistema
     *
     * @param login campo com nome de usuario que sera validado
     * @param senha campo com a senha do usuario que sera validado
     * @return caso login e/ou senha sejam invalidos
     * @throws MindbitException caso login e/ou senha sejam invalidos
     */
    public Usuario logar(String login, String senha) throws MindbitException {
        Usuario usuario = usuarioDao.buscarUsuario(login, senha);
        String loginInvalido = "";

        if (usuario == null) {
            loginInvalido = LoginActivity.getContexto().getString(R.string.login_erro);
        }
        if (loginInvalido.length() > 0) {
            throw new MindbitException(loginInvalido);
        }

        SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
        sessaoUsuario.setUsuarioLogado(usuario);
        sessaoUsuario.setPessoaLogada(pesquisarPorId(usuario.getId()));

        return usuario;
    }

    /**
     * método utilizado para fazer a pesquisa do objeto pessoa no banco
     *
     * @param id id da pessoa que desejamos encontrar
     * @return pessoa que foi encontrada com o id informado
     */
    public Pessoa pesquisarPorId(int id) {
        Pessoa pessoa = null;
        pessoa = usuarioDao.buscarPessoaId(id);
        return pessoa;
    }


    /**
     * método utilizado para fazer a validação do cadastro que será inserido no sistema
     *
     * @param pessoa pessoa que sera cadastrada no sistema
     * @throws MindbitException caso o usuario informe um nome ou email ja cadastrados no sistema
     */
    public void validarCadastro(Pessoa pessoa) throws MindbitException {
        Usuario usuario = usuarioDao.buscarUsuarioLogin(pessoa.getUsuario().getLogin());
        if (usuario != null) {
            throw new MindbitException("Nome de usuário indisponível");
        }
        pessoaEncontrada = usuarioDao.buscaPessoaPorEmail(pessoa.getEmail());
        if (pessoaEncontrada != null) {
            throw new MindbitException("Email já cadastrado");
        }
        usuarioDao.cadastrarPessoa(pessoa);
    }
}
