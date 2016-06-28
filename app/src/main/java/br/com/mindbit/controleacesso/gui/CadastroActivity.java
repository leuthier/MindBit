package br.com.mindbit.controleacesso.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.dominio.Usuario;
import br.com.mindbit.controleacesso.negocio.UsuarioNegocio;
import br.com.mindbit.controleacesso.negocio.Criptografia;
import br.com.mindbit.infra.gui.GuiUtil;
import br.com.mindbit.infra.gui.MindbitException;

public class CadastroActivity extends Activity {
    private ImageView imgFoto;
    private File caminhoFoto;
    public static final int TIRA_FOTO = 1;
    Uri foto = FOTO_PADRAO;

    private EditText editPessoaNome;
    private EditText editUsuarioLogin;
    private EditText editPessoaEmail;
    private EditText editUsuarioSenha;
    private EditText editUsuarioSenhaConfirma;
    private Resources resources;

    private String nome;
    private String login;
    private String email;
    private String senha;
    private String senhaConfirma;

    private Button btnCadastrar;
    private UsuarioNegocio usuarioNegocio;
    private Criptografia criptografia;
    private String senhaCriptografada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        criptografia = Criptografia.getInstancia();

        imgFoto = (ImageView) findViewById(R.id.userPicture);
        initViews();

        btnCadastrar = (Button) findViewById(R.id.btn_signup);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
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
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    editUsuarioSenha.setText(result);
                    editUsuarioSenha.setSelection(result.length());
                    // alguma mensagem para o usuario proibindo espaço
                }
            }
        };

        editPessoaNome = (EditText) findViewById(R.id.input_nome);
        editPessoaNome.addTextChangedListener(textWatcher);

        editUsuarioLogin = (EditText) findViewById(R.id.input_login);
        editUsuarioLogin.addTextChangedListener(textWatcher);

        editPessoaEmail = (EditText) findViewById(R.id.input_email);
        editPessoaEmail.addTextChangedListener(textWatcher);

        editUsuarioSenha = (EditText) findViewById(R.id.input_senha);
        editUsuarioSenha.addTextChangedListener(textWatcher);

        editUsuarioSenhaConfirma = (EditText) findViewById(R.id.input_senha_confirma);
        editUsuarioSenhaConfirma.addTextChangedListener(textWatcher);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (usuarioNegocio == null) {
            usuarioNegocio = UsuarioNegocio.getInstancia(this);
        }
    }

    private boolean validateFieldsCadastro(){
        nome = editPessoaNome.getText().toString().trim();
        login = editUsuarioLogin.getText().toString().trim();
        email = editPessoaEmail.getText().toString().trim();
        senha = editUsuarioSenha.getText().toString();
        senhaConfirma = editUsuarioSenhaConfirma.getText().toString();

        return (!isEmptyFieldsCadastro(nome, login, email, senha, senhaConfirma)
                && hasSizeValidCadastro(login, email, senha, senhaConfirma) &&
                !noHasSpaceCadastro(login, email, senha) &&
                isValidEmail(email));
    }

    private boolean isEmptyFieldsCadastro(String nome, String login, String email, String senha, String senhaConfirma) {
        if (TextUtils.isEmpty(nome)) {
            editPessoaNome.requestFocus();
            editPessoaNome.setError(resources.getString(R.string.cadastro_nome_vazio));
            return true;
        } else if (TextUtils.isEmpty(login)) {
            editUsuarioLogin.requestFocus();
            editUsuarioLogin.setError(resources.getString(R.string.login_vazio));
            return true;
        } else if (TextUtils.isEmpty(email)) {
            editPessoaEmail.requestFocus();
            editPessoaEmail.setError(resources.getString(R.string.cadastro_email_vazio));
            return true;
        } else if (TextUtils.isEmpty(senha)) {
            editUsuarioSenha.requestFocus();
            editUsuarioSenha.setError(resources.getString(R.string.login_senha_vazia));
            return true;
        } else if (TextUtils.isEmpty(senhaConfirma)) {
            editUsuarioSenhaConfirma.requestFocus();
            editUsuarioSenhaConfirma.setError(resources.getString(R.string.cadastro_senha_confirm_vazia));
            return true;
        }
        return false;
    }

    private boolean hasSizeValidCadastro(String user, String email, String pass, String pass2) {
        if (!(user.length() > 4)) {
            editUsuarioLogin.requestFocus();
            editUsuarioLogin.setError(resources.getString(R.string.login_curto));
            return false;
        } else if (!(email.length() > 4)) {
            editPessoaEmail.requestFocus();
            editPessoaEmail.setError(resources.getString(R.string.cadastro_email_curto));
            return false;
        }else if (!(pass.length() > 4)) {
            editUsuarioSenha.requestFocus();
            editUsuarioSenha.setError(resources.getString(R.string.login_senha_curta));
            return false;
        }  else if (!(pass2.length() > 4)) {
            editUsuarioSenhaConfirma.requestFocus();
            editUsuarioSenhaConfirma.setError(resources.getString(R.string.login_senha_curta));
            return false;
        } else if (!(pass.equals(pass2))){
            editUsuarioSenhaConfirma.requestFocus();
            editUsuarioSenhaConfirma.setError(resources.getString(R.string.cadastro_senhas_diferentes));
            return false;
        }
        return true;
    }

    private boolean noHasSpaceCadastro(String login, String email, String senha) {

        if (login.indexOf(" ") != -1){
            editUsuarioLogin.requestFocus();
            editUsuarioLogin.setError(resources.getString(R.string.login_invalido));
            return true;
        } else if (email.indexOf(" ") != -1){
            editPessoaEmail.requestFocus();
            editPessoaEmail.setError(resources.getString(R.string.login_invalido));
            return true;
        }else if (senha.indexOf(" ") != -1){
            editUsuarioSenha.requestFocus();
            editUsuarioSenha.setError(resources.getString(R.string.cadastro_senha_invalida));
            return true;
        }return false;
    }

    private boolean isValidEmail(CharSequence email) {
        if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            editPessoaEmail.requestFocus();
            editPessoaEmail.setError(resources.getString(R.string.cadastro_email_invalido));
            return false;
        }
        return true;
    }

    private  void cadastrar(){
        if(validateFieldsCadastro()){

            try {
                Usuario usuario = new Usuario();
                usuario.setLogin(login);
                criptografia.receberSenhaOriginal(senha);
                senhaCriptografada = criptografia.getSenhaCriptografada();
                usuario.setSenha(senhaCriptografada);

                Pessoa pessoa = new Pessoa();
                pessoa.setNome(nome);
                pessoa.setEmail(email);
                pessoa.setFoto(foto);
                pessoa.setUsuario(usuario);

                usuarioNegocio.validarCadastro(pessoa);
                GuiUtil.exibirMsg(this, getString(R.string.cadastro_sucesso));
                startLoginActivity();

            }catch (MindbitException e){
                GuiUtil.exibirMsg(CadastroActivity.this, e.getMessage());

            }
        }
    }

    public void tirarFoto(View v){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String nomeFoto = DateFormat.format("yyyy-MM-dd_hhmmss.png", new Date()).toString();

            caminhoFoto = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nomeFoto);

            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminhoFoto));
            startActivityForResult(i, TIRA_FOTO);
        }else{
            caminhoFoto = null;
        }
    }

    public static final Uri FOTO_PADRAO = Uri.parse("android.resource://br.com.mindbit/"+R.drawable.user);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == TIRA_FOTO && ajustarFoto() != null){
            foto = GuiUtil.getInstancia().getImageUri(CadastroActivity.this , ajustarFoto());
        }else{
            foto = FOTO_PADRAO;
        }
    }

    private Bitmap ajustarFoto() {
        if(caminhoFoto != null){
            int targetwidth = imgFoto.getWidth();
            int targetHeight = imgFoto.getHeight();

            BitmapFactory.Options bmOption = new BitmapFactory.Options();

            bmOption.inJustDecodeBounds = false;
            BitmapFactory.decodeFile(caminhoFoto.getAbsolutePath(), bmOption);
            int photoW = bmOption.outWidth;
            int photoH = bmOption.outHeight;

            int scaleFactor = Math.min(photoW / targetwidth, photoH / targetHeight);
            bmOption.inSampleSize = scaleFactor;

            Bitmap bmp = BitmapFactory.decodeFile(caminhoFoto.getAbsolutePath(), bmOption);

            imgFoto.setImageBitmap(bmp);
            return bmp;
        } return null;
    }

    public void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
