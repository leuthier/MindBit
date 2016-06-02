package br.com.mindbit.infra.gui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.gui.LoginActivity;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;

public class GuiUtil {
    private static String nomePessoaLogada;

    public static void exibirMsg(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    public static void exibirSaudacao(Activity activity){
        Context context;
        context = LoginActivity.getContext();
        nomePessoaLogada = SessaoUsuario.getInstancia().getPessoaLogada().getNome();
        Toast.makeText(activity, context.getString(R.string.login_sucess)+" "+nomePessoaLogada+"!", Toast.LENGTH_LONG).show();
    }



    public static final Uri IMAGEM_PADRAO = Uri.parse("android.resource://br.com.mindbit/"+ R.drawable.user);

    private static GuiUtil instanciaGuiUtil = new GuiUtil();
    private GuiUtil(){}
    public static GuiUtil getInstancia() {
        return instanciaGuiUtil;
    }

}