package br.com.mindbit.infra.gui;


public class MindbitException extends Exception {
    public MindbitException(String loginNulo){
        super(loginNulo);
    }
}
