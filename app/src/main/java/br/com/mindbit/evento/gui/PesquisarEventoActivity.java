package br.com.mindbit.evento.gui;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.mindbit.R;

public class PesquisarEventoActivity extends Activity {
    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        for(String item:items){
            if(!item.contains(textToSearch)){
                listItems.remove(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void initList(){
        items=new String[]{"1 va mpoo","2 va mpoo","3 va mpoo","Final mpoo"};
        listItems=new ArrayList<>(Arrays.asList(items));
        adapter=new ArrayAdapter<String>(this,
                R.layout.list_item_pesquisar_evento, R.id.txtitem, listItems);
        listView.setAdapter(adapter);
    }
}