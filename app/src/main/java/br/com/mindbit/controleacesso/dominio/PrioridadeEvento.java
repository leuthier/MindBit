package br.com.mindbit.controleacesso.dominio;

public enum PrioridadeEvento {
    VERDE(1, "Verde"),AMARELO(1,"Amarelo"), VERMELHOI(3,"Vermelho");

    private Integer codigo;
    private String descricao;

    private PrioridadeEvento(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo(){
        return codigo;
    }

    public String getDescricao(){
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

}
