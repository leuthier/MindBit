package br.com.mindbit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void logar(View view) {
        GuiUtil.Msg(this, "TO FUNCIONANDO");
    }

    public void cadastrar(View view) {
        GuiUtil.Msg(this, "TO FUNCIONANDO TB!!! \\o/");
    }

}
