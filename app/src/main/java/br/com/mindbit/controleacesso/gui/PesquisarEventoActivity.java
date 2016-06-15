package br.com.mindbit.controleacesso.gui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.negocio.EventoNegocio;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;

public class PesquisarEventoActivity extends AppCompatActivity {
    private Resources resources;

    private ArrayList<Evento> eventosPessoa;
    private ArrayList<String> nomesEventos = new ArrayList<>();
    private ArrayList<String> descricoesEvento = new ArrayList<>();
    private ArrayList<Evento> listItems;
    private ArrayAdapter<String> nomeAdapter;
    private ArrayAdapter<String> descricaoAdapter;

    private Context context;
    private ListView listView;
    private EditText campoPesquisa;

    private EventoNegocio eventoNegocio;
    private SessaoUsuario sessao;
    private Pessoa pessoaLogada;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        sessao = SessaoUsuario.getInstancia();
        pessoaLogada = sessao.getPessoaLogada();

        eventoNegocio = EventoNegocio.getInstancia(context);
        setContentView(R.layout.activity_pesquisar_evento);
        listView = (ListView)findViewById(R.id.listview_eventos);
        campoPesquisa = (EditText)findViewById(R.id.edtsearch);
        initList();

        campoPesquisa.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
                if (s.toString().equals("")) {
                    initList();
                } else {
                    searchItem(s.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void searchItem(String textToSearch){
        eventosPessoa = eventoNegocio.listarEventoCriador(pessoaLogada.getId());
        for(Evento evento: eventosPessoa){
            if(!evento.getNome().contains(textToSearch)){
                nomesEventos.remove(evento.getNome());
            }
        }if(nomesEventos.isEmpty()){
            nomesEventos.add(getString(R.string.activity_search_evento_not_found));
        }
        nomeAdapter.notifyDataSetChanged();
    }

    public void initList(){
        eventosPessoa = eventoNegocio.listarEventoCriador(pessoaLogada.getId());
        if(!nomesEventos.isEmpty()){
            nomesEventos.remove(0);
        }
        for (Evento evento: eventosPessoa){
            nomesEventos.add(evento.getNome());
            descricoesEvento.add(evento.getDescricao());
        }

        nomeAdapter = new ArrayAdapter<>(this, R.layout.list_item_pesquisar_evento, R.id.txtitem_nome_evento, nomesEventos);
       // nomeAdapter = new ArrayAdapter<>(this, R.layout.list_item_pesquisar_evento, R.id.txtitem_descricao_evento, descricoesEvento);
        listView.setAdapter(nomeAdapter);
//        listView.setAdapter(descricaoAdapter);


    }
}