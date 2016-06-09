package br.com.mindbit.controleacesso.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.negocio.EventoNegocio;
import br.com.mindbit.controleacesso.negocio.UsuarioNegocio;
import br.com.mindbit.infra.gui.GuiUtil;

public class PesquisarEventoActivity extends AppCompatActivity {

    private EventoNegocio eventoNegocio;
    private Context context;
    private String[] eventos;
    private ArrayList<Evento> listaEventos;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText editText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        eventoNegocio = EventoNegocio.getInstancia(context);
        setContentView(R.layout.activity_pesquisar_evento);
        listView=(ListView)findViewById(R.id.listview);
        editText=(EditText)findViewById(R.id.edtsearch);
        initList();
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
                if(s.toString().equals("")){
                    // reset listview
                    initList();
                }
                else{
                    // perform search
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void searchItem(String textToSearch){
        for(String item: eventos){
            if(!item.contains(textToSearch)){
                listaEventos.remove(item);
            }
        }
        adapter.notifyDataSetChanged();
    }


    public void initList(){
        GuiUtil.exibirMsg(this, "CHAMAR O PESQUISAR NOME PARCIAL");
        /*eventos = eventoNegocio.pesquisarPorNome();

        eventos = new String[]{"1 va mpoo","2 va mpoo","3 va mpoo","Final mpoo"};
        listaEventos = new ArrayList<>(Arrays.asList(eventos));
        adapter =new ArrayAdapter<String>(this,
                R.layout.list_item_pesquisar_evento, R.id.txtitem, listaEventos);
        listView.setAdapter(adapter);*/
    }
}