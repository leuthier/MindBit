package br.com.mindbit;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText edtUser;
    private EditText edtPassword;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnEnter = (Button) findViewById(R.id.bt_signIn);
        btnEnter.setOnClickListener(this);

        Button btnCadastrar = (Button) findViewById(R.id.bt_signUp);
        btnCadastrar.setOnClickListener(this);

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

    private boolean validateFields() {
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
        if (!(user.length() > 3)) {
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.login_user_invalid));
            return false;
        } else if (!(pass.length() > 5)) {
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
                if (validateFields()){
                    startCalendarActivity();
                }
                break;
            case R.id.bt_signUp:
                    startSignUpActivity();
                break;
        }
    }

    public void startCalendarActivity() {
        GuiUtil.Msg(this, "TO FUNCIONANDO");
        //chamando uma tela
        startActivity(new Intent(this, CalendarActivity.class));
        finish();// encerra a tela atual
    }

    public void startSignUpActivity() {
        GuiUtil.Msg(this, "CHAMAR TELA CADASTRO!!! \\o/");
    }

}
