package br.com.mindbit.controleacesso.negocio;

import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.dominio.Usuario;

/**
 * Classe utilizada para manter o status do usuario
 */
public class SessaoUsuario {

        /* singleton */
        private static SessaoUsuario instanciaSessaoUsuario = new SessaoUsuario();
        private SessaoUsuario(){}
        public static SessaoUsuario getInstancia() { return instanciaSessaoUsuario; }

        private Usuario usuarioLogado = null;
        private Pessoa pessoaLogada = null;

        public Pessoa getPessoaLogada() {
            return pessoaLogada;
        }

        public void setPessoaLogada(Pessoa pessoa) {
            this.pessoaLogada = pessoa;
        }

        public void setUsuarioLogado(Usuario usuario) {
            this.usuarioLogado = usuario;
        }

        public void invalidarSessao(){
            this.setUsuarioLogado(null);
            this.setPessoaLogada(null);
        }
}
