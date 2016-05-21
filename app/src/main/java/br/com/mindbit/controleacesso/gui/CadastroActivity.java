package br.com.mindbit.controleacesso.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.com.mindbit.R;
import br.com.mindbit.infra.gui.GuiUtil;

/**
 * Created by Ariana on 16/05/2016.
 */
public class CadastroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
    }

    public void onSingUpCLick(View v){
        if(v.getId()==R.id.bt_cadastrar)
        {
            EditText nome = (EditText)findViewById(R.id.TFnome);
            EditText email = (EditText)findViewById(R.id.TFemail);
            EditText cpf = (EditText)findViewById(R.id.TFcpf);
            EditText senha1 = (EditText)findViewById(R.id.TFsenha1);
            EditText senha2 = (EditText)findViewById(R.id.TFsenha2);

            String nomestr = nome.getText().toString();
            String emailstr = email.getText().toString();
            String cpfstr = cpf.getText().toString();
            String senha1str = senha1.getText().toString();
            String senha2str = senha2.getText().toString();

            if(!senha1str.equals(senha2str))
            {
                //pipocar mensagem
                //GuiUtil.exibirMsg(CadastroActivity.this , R.string.password_not_mach);
                GuiUtil.exibirMsg(CadastroActivity.this, "Senhas diferentes");
            }

        }
    }
}
