package br.com.mindbit.controleacesso.gui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Amigo;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.negocio.AmigoNegocio;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.controleacesso.negocio.UsuarioNegocio;
import br.com.mindbit.infra.gui.GuiUtil;
import br.com.mindbit.infra.gui.MindbitException;


public class AddAmigoActivity extends AppCompatActivity {
    private Resources resources;

    private SessaoUsuario sessaoUsuario;
    private static AmigoNegocio amigoNegocio;

    private EditText input_edtAmigoEmail;
    private EditText input_edtAmigoNome;
    private Button btnAdicionarAmigo;
    private String email;
    private String nome;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_amigo);
        amigoNegocio = AmigoNegocio.getInstancia(this);
        sessaoUsuario = SessaoUsuario.getInstancia();


        resources = getResources();

        btnAdicionarAmigo = (Button) findViewById(R.id.btnAdicionarAmigo);
        input_edtAmigoEmail = (EditText) findViewById(R.id.input_edtAmigoEmail);
        input_edtAmigoNome = (EditText) findViewById(R.id.input_edtAmigoNome);

        btnAdicionarAmigo = (Button) findViewById(R.id.btnAdicionarAmigo);
        btnAdicionarAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAmigo();
            }
        });

    }


    private boolean validateFieldsAmigo(){
        nome = input_edtAmigoNome.getText().toString().trim();
        email = input_edtAmigoEmail.getText().toString().trim();
        return (!isEmptyFields(nome, email) && isValidEmail(email));
    }

    private boolean isValidEmail(CharSequence email) {
        if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            input_edtAmigoEmail.requestFocus();
            input_edtAmigoEmail.setError(resources.getString(R.string.cadastro_email_invalido));
            return false;
        }
        return true;
    }

    private boolean isEmptyFields(String nome, String email) {
        if (TextUtils.isEmpty(nome)) {
            input_edtAmigoNome.requestFocus();
            input_edtAmigoNome.setError(resources.getString(R.string.nome_vazio));
            return true;
        }else if (TextUtils.isEmpty(email)) {
            input_edtAmigoEmail.requestFocus();
            input_edtAmigoEmail.setError(resources.getString(R.string.email_vazio));
            return true;
        }return false;
    }

    private void addAmigo(){
        if (validateFieldsAmigo()){
            try{
                Amigo amigo = new Amigo();
                amigo.setNome(nome);
                amigo.setEmail(email);
                amigoNegocio.adicionarAmigo(amigo);
                GuiUtil.exibirMsg(this, "Amig@ adicionad@ com sucesso");
            }catch (MindbitException e){
                GuiUtil.exibirMsg(AddAmigoActivity.this, e.getMessage());
            }
        }

    }
}