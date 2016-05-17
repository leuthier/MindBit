package br.com.mindbit;

import br.com.mindbit.dominio.Pessoa;
import br.com.mindbit.dominio.Usuario;

public class SessaoUsuario {

        private static SessaoUsuario instanciaSessaoUsuario = new SessaoUsuario();
        private SessaoUsuario(){}
        public static SessaoUsuario getInstancia() {
            return instanciaSessaoUsuario;
        }

        private Usuario usuarioLogado;
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

        public Usuario getUsuarioLogado(){
            return usuarioLogado;
        }

}
