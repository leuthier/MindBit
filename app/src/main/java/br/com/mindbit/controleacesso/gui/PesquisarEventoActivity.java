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
import br.com.mindbit.infra.gui.GuiUtil;

public class PesquisarEventoActivity extends AppCompatActivity {
    private Resources resources;

    private ArrayList<Evento> items;
    private ArrayList<Evento> listItems;
    private ArrayAdapter<Evento> adapter;

    private Context context;
    private String[] eventos;
    private ArrayList<Evento> listaEventos;
   // private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText campoPesquisa;
    private String nome;

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
        listView=(ListView)findViewById(R.id.listview);
        campoPesquisa =(EditText)findViewById(R.id.edtsearch);
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
                    // reset listview
                    initList();
                } else {
                    nome = campoPesquisa.getText().toString().trim();
                    nome = nome.toLowerCase();
                    eventoNegocio.consultarEventoPorNomeParcial(nome);
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

   /* public void searchItem(String textToSearch){
        for(String item: eventos){
            if(!item.contains(textToSearch)){
                listaEventos.remove(item);
            }
        }
        adapter.notifyDataSetChanged();
    }*/

    public void searchItem(String textToSearch){
        for(Evento item:items){
            if(!item.getNome().contains(textToSearch)){
                listItems.remove(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void initList(){
        GuiUtil.exibirMsg(this, "CHAMAR O PESQUISAR NOME PARCIAL");
        items = eventoNegocio.listarEventoCriador(pessoaLogada.getId());
        adapter=new ArrayAdapter<>(this,
                R.layout.list_item_pesquisar_evento, R.id.txtitem, items);
        listView.setAdapter(adapter);
       /* eventos = eventoNegocio.pesquisarPorNomeParcial(campoPesquisa.getText().toString());

        listaEventos = new ArrayList<Evento>(Arrays.<Evento>asList(eventos));
        adapter = new ArrayAdapter<String>(this,R.id.listview ,listaEventos);
        listView.setAdapter(adapter);*/
    }
}