package br.com.mindbit.controleacesso.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Amigo;

public class AdapterEscolherAmigo extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Amigo> amigosLista;
    private List<Amigo> amigosSelecionados;
    private ArrayList<String> nomesAmigos = new ArrayList<>();
    private ArrayList<String> emailsAmigos = new ArrayList<>();
    private Amigo amigo;
    private Context context;

    public AdapterEscolherAmigo(Context context, List<Amigo> amigosLista){
        this.context = context;
        this.amigosLista = amigosLista;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(amigosLista != null){
            return amigosLista.size();
        }else {
            return 0;
        }
    }

    @Override
    public Amigo getItem(int position) {
        return amigosLista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        amigo = amigosLista.get(position);

        view = layoutInflater.inflate(R.layout.list_item_escolher_amigo, null);

        ((TextView) view.findViewById(R.id.txt_compartilhar_nome_amigo)).setText(amigo.getNome());
        ((TextView) view.findViewById(R.id.txt_compartilhar_email_amigo)).setText(amigo.getEmail());
        final String nomeAmigo = ((TextView) view.findViewById(R.id.txt_compartilhar_nome_amigo)).getText().toString();
        final String emailAmigo = ((TextView) view.findViewById(R.id.txt_compartilhar_email_amigo)).getText().toString();
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.chkBox_amigo);


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox amigoCheckbox = (CheckBox) v;
                if (amigoCheckbox.isChecked()) {

                    //nomesAmigos.add(nomeAmigo);
                    emailsAmigos.add(emailAmigo);
                    //amigosSelecionados.add(amigo);
                } else {
                    //nomesAmigos.remove(nomeAmigo);
                    emailsAmigos.remove(emailAmigo);
                    //amigosSelecionados.remove(amigo);
                }
            }
        });
        return view;
    }

    public ArrayList<String> getNomesAmigos(){
        return nomesAmigos;
    }

    public ArrayList<String> getEmailsAmigos(){
        return emailsAmigos;
    }

    public List<Amigo> getAmigosSelecionados(){return amigosSelecionados;}
}
