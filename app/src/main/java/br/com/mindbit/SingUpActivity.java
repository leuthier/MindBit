package br.com.mindbit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Ariana on 16/05/2016.
 */
public class SingUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
    }

    public void onSingUpCLick(View v){
        if(v.getId()==R.id.bt_signUp){

        }
    }
}
