package br.com.mindbit.controleacesso.dominio;

public enum PrioridadeEvento {
    VERDE("Verde"),AMARELO("Amarelo"), VERMELHO("Vermelho");

    private String descricao;

    PrioridadeEvento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

}
