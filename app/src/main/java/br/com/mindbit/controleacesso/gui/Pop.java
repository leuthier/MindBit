package br.com.mindbit.controleacesso.gui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.negocio.EventoNegocio;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.infra.gui.MindbitException;

public class Pop extends Activity{

    private Context context;
    private ArrayList<Evento> listaEventos;
    private ArrayAdapter<String> adapter;
    private ListView listViewEvento;
    private EventoNegocio eventoNegocio;
    private SessaoUsuario sessao;
    private Pessoa pessoaLogada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        context = this;
        sessao = SessaoUsuario.getInstancia();
        pessoaLogada = sessao.getPessoaLogada();

        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);

        eventoNegocio = EventoNegocio.getInstancia(context);

        listViewEvento = (ListView) findViewById(R.id.pop_lista_evento);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));



        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey("DATA")){
            String data = bundle.getString("DATA");
            try {
                listaEventos = eventoNegocio.listarEventosDia(data,pessoaLogada.getId());
            } catch (MindbitException e) {
                e.printStackTrace();
            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        for(Evento evento : listaEventos){
            String formatad = evento.getNome() + " "+ simpleDateFormat.format(evento.getDataInicio());
            adapter.add(formatad);

        }
        listViewEvento.setAdapter(adapter);


    }
}
