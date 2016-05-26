package br.com.mindbit.controleacesso.gui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.negocio.UsuarioNegocio;

/**
 * Created by Ariana on 16/05/2016.
 */
public class CadastroActivity extends Activity {

    private EditText editPessoaNome;
    private EditText editUsuarioLogin;
    private EditText editPessoaEmail;
    private EditText editUsuarioSenha;
    private EditText editUsuarioSenhaConfirmar;
    private ImageView imageView;
    private Resources resources;

    private Button btnCadastrar;
    private UsuarioNegocio usuarioNegocio;
    private static Context contexto;

    /*public static final int IMAGEM_INTERNA = 12;
    public static final int TIRAR_FOTO = 1;
    Uri FOTO = GuiUtil.FOTO_PADRAO;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        contexto = this;
        usuarioNegocio = UsuarioNegocio.getInstanciaUsuarioNegocio(contexto);

        editPessoaNome = (EditText) findViewById(R.id.input_name);
        editUsuarioLogin = (EditText) findViewById(R.id.input_username);
        editPessoaEmail = (EditText) findViewById(R.id.input_email);
        editUsuarioSenha = (EditText) findViewById(R.id.input_password);
        editUsuarioSenhaConfirmar = (EditText) findViewById(R.id.input_password2);
        //imageView = (ImageView) findViewById(R.id.imageView);

        //imageView.setImageURI(GuiUtil.FOTO_PADRAO);

        initViews();

        btnCadastrar = (Button) findViewById(R.id.btn_signup);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = editPessoaNome.getText().toString().trim();

                String email = editPessoaEmail.getText().toString().trim();
                String login = editUsuarioLogin.getText().toString().trim();
                String senha = editUsuarioSenha.getText().toString().trim();
                String senhaConfirmar = editUsuarioSenhaConfirmar.getText().toString().trim();
                Toast.makeText(CadastroActivity.this, "cadastrar", Toast.LENGTH_LONG).show();
                cadastrar(v);
                //ImageView iv = (ImageView) findViewById(R.id.imageView);

                //Usuario usuario = new Usuario(login,senha);
                //Pessoa pessoa = new Pessoa(usuario,nome,formatarCpf(cpf),dataNascimento,FOTO);
                //cadastrar(pessoa, senhaConfirmar);
            }
        });

    }
    private void initViews() {
        resources = getResources();
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        editPessoaNome = (EditText) findViewById(R.id.input_name);
        editPessoaNome.addTextChangedListener(textWatcher);

        editUsuarioLogin = (EditText) findViewById(R.id.input_username);
        editUsuarioLogin.addTextChangedListener(textWatcher);

        editPessoaEmail = (EditText) findViewById(R.id.input_email);
        editPessoaEmail.addTextChangedListener(textWatcher);

        editUsuarioSenha = (EditText) findViewById(R.id.input_password);
        editUsuarioSenha.addTextChangedListener(textWatcher);

        editUsuarioSenhaConfirmar = (EditText) findViewById(R.id.input_password2);
        editUsuarioSenhaConfirmar.addTextChangedListener(textWatcher);

    }

    @Override
    public void onResume() {
        super.onResume();
        //atribuindo aqui pois no oncreate o app ainda nao tem contexto.
        if (usuarioNegocio == null) {
            usuarioNegocio = UsuarioNegocio.getInstanciaUsuarioNegocio(contexto);
        }
    }

    private boolean validateFieldsCadastro(){
        String nome = editPessoaNome.getText().toString().trim();
        String user = editUsuarioLogin.getText().toString().trim();
        String email = editPessoaEmail.getText().toString().trim();
        String pass = editUsuarioSenha.getText().toString().trim();
        String pass2 = editUsuarioSenhaConfirmar.getText().toString().trim();
        return (!isEmptyFieldsCadastro(nome, user, email, pass, pass2)
                && hasSizeValidCadastro(user, email, pass, pass2) && !noHasSpaceCadastro(user, email));
    }

    private boolean isEmptyFieldsCadastro(String nome, String user, String email, String pass, String pass2) {
        if (TextUtils.isEmpty(nome)) {
            editPessoaNome.requestFocus();
            editPessoaNome.setError(resources.getString(R.string.signUp_name_required));
            return true;
        } else if (TextUtils.isEmpty(user)) {
            editUsuarioLogin.requestFocus();
            editUsuarioLogin.setError(resources.getString(R.string.login_user_required));
            return true;
        } else if (TextUtils.isEmpty(email)) {
            editPessoaEmail.requestFocus();
            editPessoaEmail.setError(resources.getString(R.string.signUP_email_required));
            return true;
        } else if (TextUtils.isEmpty(pass)) {
            editUsuarioSenha.requestFocus();
            editUsuarioSenha.setError(resources.getString(R.string.login_password_required));
            return true;
        } else if (TextUtils.isEmpty(pass2)) {
            editUsuarioSenhaConfirmar.requestFocus();
            editUsuarioSenhaConfirmar.setError(resources.getString(R.string.signUP_password_confirm_required));
            return true;
        }
        return false;
    }

    private boolean hasSizeValidCadastro(String user, String email, String pass, String pass2) {
        if (!(user.length() > 4)) {
            editUsuarioLogin.requestFocus();
            editUsuarioLogin.setError(resources.getString(R.string.login_user_invalid));
            return false;
        } else if (!(email.length() > 4)) {
            editPessoaEmail.requestFocus();
            editPessoaEmail.setError(resources.getString(R.string.signUp_email_invalid));
            return false;
        }else if (!(pass.length() > 4)) {
            editUsuarioSenha.requestFocus();
            editUsuarioSenha.setError(resources.getString(R.string.login_password_invalid));
            return false;
        }  else if (!(pass2.length() > 4)) {
            editUsuarioSenhaConfirmar.requestFocus();
            editUsuarioSenhaConfirmar.setError(resources.getString(R.string.login_password_invalid));
            return false;
        }
        return true;
    }

    private boolean noHasSpaceCadastro(String user, String email) {
        int idxuser = user.indexOf(" ");
        int idxemail = email.indexOf(" ");
        if (idxuser != -1){
            editUsuarioLogin.requestFocus();
            editUsuarioLogin.setError(resources.getString(R.string.login_user_has_space));
            return true;
        } else if (idxemail != -1){
            editPessoaEmail.requestFocus();
            editPessoaEmail.setError(resources.getString(R.string.login_user_has_space));
            return true;
        }return false;
    }

    private  void cadastrar(View view){

        if(validateFieldsCadastro()){
            Toast.makeText(CadastroActivity.this, "Verificação dos campos", Toast.LENGTH_LONG).show();
            /*try {
                String user = editUsuarioLogin.getText().toString();
                String password = edtPassword.getText().toString();

                Usuario usuario = usuarioNegocio.logar(user, password);
                GuiUtil.exibirSaudacao(this);
                startCalendarActivity();
            }catch (MindbitException e){
                GuiUtil.exibirMsg(LoginActivity.this, e.getMessage());

            }*/
        }
    }



}
