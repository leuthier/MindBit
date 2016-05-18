package br.com.mindbit.controleacesso.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.mindbit.controleacesso.dominio.Usuario;
import br.com.mindbit.infra.gui.CalendarActivity;
import br.com.mindbit.R;
import br.com.mindbit.controleacesso.negocio.UsuarioNegocio;
import br.com.mindbit.infra.gui.GuiUtil;


public class LoginActivity extends Activity implements View.OnClickListener {
    private ImageView icone;
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
        icone = (ImageView) findViewById(R.id.imageView);

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
                callClearErrors(s);
            }
        };

        edtUser = (EditText) findViewById(R.id.userLogin);
        edtUser.addTextChangedListener(textWatcher);

        edtPassword = (EditText) findViewById(R.id.userPassword);
        edtPassword.addTextChangedListener(textWatcher);

    }

        private void callClearErrors(Editable s){
            if (!s.toString().isEmpty()) {
                clearErrorFields(edtUser);
            }
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

    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
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

            String user = edtUser.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            boolean logado = usuarioNegocio.logar(user, password);

            if (logado) {
                GuiUtil.exibirMsg(this, context.getString(R.string.login_sucess));
                startCalendarActivity();
            }
        }
        return;
    }

    public void startCalendarActivity() {
        startActivity(new Intent(this, CalendarActivity.class));
        finish();
    }

    public void startSignUpActivity() {
        GuiUtil.exibirMsg(this, "Próxima entrega :)");
    }

    public static Context getContext(){
        return context;
    }

}
