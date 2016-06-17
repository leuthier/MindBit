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
    private ArrayList<Evento> eventosPessoa;
    private ArrayList<Evento> eventosEncontrados;
    private ArrayList<Evento> listItems = new ArrayList<>();

    private Context context;
    private ListView listView;
    private EditText campoPesquisa;

    private EventoNegocio eventoNegocio;
    private SessaoUsuario sessao;
    private Pessoa pessoaLogada;
    private EventoAdapter adapter;


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
        adapter = new EventoAdapter(this,listItems);

        campoPesquisa.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    initList();
                } else {
                    searchItem(s.toString().trim());
                }
            }
        });
    }

    public void searchItem(String textToSearch){

        int id = pessoaLogada.getId();
        eventosEncontrados = (ArrayList<Evento>) eventoNegocio.consultarEventoPorNomeParcial(id, textToSearch);

        adapter = new EventoAdapter(this, eventosEncontrados);
        listView.setAdapter(adapter);
    }

    public void initList(){
        eventosPessoa = eventoNegocio.listarEventoCriador(pessoaLogada.getId());
        adapter = new EventoAdapter(this, eventosPessoa);

        listView.setAdapter(adapter);
    }

}