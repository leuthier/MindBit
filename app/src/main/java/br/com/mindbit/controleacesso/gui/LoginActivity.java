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

import br.com.mindbit.controleacesso.dominio.Usuario;
import br.com.mindbit.R;
import br.com.mindbit.controleacesso.negocio.UsuarioNegocio;
import br.com.mindbit.infra.gui.GuiUtil;
import br.com.mindbit.infra.gui.MindbitException;


public class LoginActivity extends Activity implements View.OnClickListener {
    private ImageView imgPhoto;
    private File caminhoFoto;
    public static final int TIRA_FOTO = 1;

    private EditText edtUser;
    private EditText edtPassword;

    private Button btnEnter;
    private Button btnCadastrar;

    private Resources resources;
    private static Context context;
    private UsuarioNegocio usuarioNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        usuarioNegocio = UsuarioNegocio.getInstanciaUsuarioNegocio(context);
        imgPhoto = (ImageView) findViewById(R.id.imageView);

        btnEnter = (Button) findViewById(R.id.bt_signIn);
        btnEnter.setOnClickListener(this);
        btnCadastrar = (Button) findViewById(R.id.bt_signUp);
        btnCadastrar.setOnClickListener(this);

        edtUser = (EditText) findViewById(R.id.userLogin);
        edtPassword = (EditText) findViewById(R.id.userPassword);

        initViews();

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

        edtUser = (EditText) findViewById(R.id.userLogin);
        edtUser.addTextChangedListener(textWatcher);

        edtPassword = (EditText) findViewById(R.id.userPassword);
        edtPassword.addTextChangedListener(textWatcher);

    }

    private boolean validateFields(){
        String user = edtUser.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        return (!isEmptyFields(user, pass) && hasSizeValid(user, pass) && !noHasSpaceLogin(user));
    }

    private boolean isEmptyFields(String user, String pass) {
        if (TextUtils.isEmpty(user)) {
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.login_user_required));
            return true;
        } else if (TextUtils.isEmpty(pass)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.login_password_required));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String user, String pass) {
        if (!(user.length() > 4)) {
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.login_user_invalid));
            return false;
        } else if (!(pass.length() > 4)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.login_password_invalid));
            return false;
        }
        return true;
    }

    private boolean noHasSpaceLogin(String user) {
        int idx = user.indexOf(" ");
        if (idx != -1){
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.login_user_has_space));
            return true;
        }return false;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.bt_signIn:
                logar(v);
                break;
            case R.id.bt_signUp:
                startSignUpActivity();
                break;
        }
    }

    private void logar(View view){
        if (validateFields()){
            try {
                String user = edtUser.getText().toString();
                String password = edtPassword.getText().toString();

                Usuario usuario = usuarioNegocio.logar(user, password);
                GuiUtil.exibirSaudacao(this);
                startCalendarActivity();
            }catch (MindbitException e){
                GuiUtil.exibirMsg(LoginActivity.this, e.getMessage());

            }
        }
    }

    public void startCalendarActivity() {
        startActivity(new Intent(this, CalendarActivity.class));
        finish();
    }

    public void startSignUpActivity() {
        GuiUtil.exibirMsg(this, "Próxima entrega :..) é agora");
        Intent i = new Intent(LoginActivity.this,CadastroActivity.class);
        startActivity(i);
    }

    public static Context getContext(){ return context; }

    public void takePicture(View v){
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == TIRA_FOTO){
            atualizarFoto();
        }
    }

    private void atualizarFoto() {
        if(caminhoFoto != null){
            int targetwidth = imgPhoto.getWidth();
            int targetHeight = imgPhoto.getHeight();
            //obter largura e altura da foto
            BitmapFactory.Options bmOption = new BitmapFactory.Options();

            bmOption.inJustDecodeBounds = false;
            BitmapFactory.decodeFile(caminhoFoto.getAbsolutePath(), bmOption);
            int photoW = bmOption.outWidth;
            int photoH = bmOption.outHeight;

            //redimensionamento
            int scaleFactor = Math.min(photoW/ targetwidth, photoH/ targetHeight);
            bmOption.inSampleSize = scaleFactor;

            Bitmap bmp = BitmapFactory.decodeFile(caminhoFoto.getAbsolutePath(), bmOption);

            imgPhoto.setImageBitmap(bmp);
        }
    }

}
