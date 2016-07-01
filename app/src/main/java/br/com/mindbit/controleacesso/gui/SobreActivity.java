package br.com.mindbit.controleacesso.gui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.mindbit.R;

public class SobreActivity extends AppCompatActivity {

    private TextView mindBit;
    private ImageView logo;
    private TextView descricao;
    private TextView desenvolvedores;
    private TextView ariana;
    private TextView bernardo;
    private TextView bruna;
    private TextView gabriel;
    private TextView tiago;
    private TextView victor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);


        mindBit = (TextView) findViewById(R.id.textView_mindbit);
        logo = (ImageView) findViewById(R.id.fotoPerfil);
        descricao = (TextView) findViewById(R.id.sobre_descricao);
        desenvolvedores = (TextView) findViewById(R.id.textView_desenvolvedores);
        ariana = (TextView) findViewById(R.id.textView_Ariana);
        bernardo = (TextView) findViewById(R.id.textView_Bernardo);
        bruna = (TextView) findViewById(R.id.textView_Bruna);
        gabriel = (TextView) findViewById(R.id.textView_Gabriel);
        tiago = (TextView) findViewById(R.id.textView_Tiago);
        victor = (TextView) findViewById(R.id.textView_Victor);

    }
}
