package br.com.mindbit;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//extends AppCompatActivity
public class GuiUtil {

    public static void exibirMsg(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    public static final Uri IMAGEM_PADRAO = Uri.parse("android.resource://br.com.mindbit/"+R.drawable.ic_person);

    private static GuiUtil instanciaGuiUtil = new GuiUtil();
    private GuiUtil(){}
    public static GuiUtil getInstancia() {
        return instanciaGuiUtil;
    }

    public class EmailValidator {

        public boolean isEmailValid(String email) {
            if ((email == null) || (email.trim().length() == 0))
                return false;

            String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
            Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
}