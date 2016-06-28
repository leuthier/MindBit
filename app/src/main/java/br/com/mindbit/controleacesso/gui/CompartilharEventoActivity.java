package br.com.mindbit.controleacesso.gui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.negocio.EventoNegocio;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.infra.gui.GuiUtil;
import br.com.mindbit.infra.gui.MindbitException;

public class CompartilharEventoActivity extends AppCompatActivity{
    private Resources resources;
    private static Context contexto;

    private EventoNegocio eventoNegocio;
    private SessaoUsuario sessaoUsuario;
    private Pessoa pessoaLogada;
    private ArrayList<Evento> eventosPessoa;

    private ListView listaEventos;
    private Button btnCompartilhar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartilhar_evento);

        resources = getResources();
        contexto = this;

        eventoNegocio = EventoNegocio.getInstancia(contexto);
        sessaoUsuario = SessaoUsuario.getInstancia();
        pessoaLogada = sessaoUsuario.getPessoaLogada();

        listaEventos = (ListView) findViewById(R.id.ListView_compartilhar_evento);
        btnCompartilhar = (Button) findViewById(R.id.btn_compartilhar_evento);
        btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compartilhar(v);

            }
        });


        ArrayList<String> list = new ArrayList<String>();

        try {
            initList();
            for (Evento evento:initList()) {
                list.add(evento.getNome()+" -> " +
                        "\n"+evento.getDataInicio().getDate()+"/"+evento.getDataInicio().getMonth()+"/"+evento.getDataInicio().getYear()+
                        " às "+evento.getDataInicio().getHours()+":"+evento.getDataInicio().getMinutes()+
                        " até \n"+evento.getDataFim().getDate()+"/"+evento.getDataFim().getMonth()+"/"+evento.getDataFim().getYear()+
                        " às "+evento.getDataFim().getHours()+":"+evento.getDataFim().getMinutes());

            }
        } catch (MindbitException e) {
            GuiUtil.exibirMsg(CompartilharEventoActivity.this, e.getMessage());
        }


        if (listaEventos!=null) {
            ArrayAdapter<String> adapter = new ContactListAdapter(this, list);
            listaEventos.setAdapter(adapter);
        }
    }

    public List<Evento> initList() throws MindbitException{
        eventosPessoa = eventoNegocio.listarEventosProximo(pessoaLogada.getId());
        return eventosPessoa;
    }

    public void compartilhar(View v){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, new String[] {"amiguinho@email.com","amigo@email.com"} );
        i.putExtra(Intent.EXTRA_SUBJECT, "Evento...");
        i.putExtra(Intent.EXTRA_TEXT, "Aqui vem o texto da mensagem");

        startActivity(Intent.createChooser(i, "Compartilhar para:"));
    }

    private class ContactListAdapter extends ArrayAdapter<String> {
        public ContactListAdapter(Context context, ArrayList<String> s) {
            super(context, 0, s);
        }


        public View getView(int position, View view, ViewGroup parent) {
            String s = getItem(position);

            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.eventos_checkbox, parent, false);
            }

            TextView name = (TextView) view.findViewById(R.id.txt_nome_evento);
            name.setText(s);

            return view;
        }
    }
}


