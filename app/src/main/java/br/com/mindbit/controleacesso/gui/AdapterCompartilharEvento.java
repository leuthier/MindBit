package br.com.mindbit.controleacesso.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.negocio.EventoNegocio;


public class AdapterCompartilharEvento extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Evento> eventosLista;
    private ArrayList<String> nomesEventos = new ArrayList<>();
    private Evento evento;
    private Context context;

    public AdapterCompartilharEvento(Context context, List<Evento> eventosLista){
        this.context = context;
        this.eventosLista = eventosLista;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(eventosLista != null){
            return eventosLista.size();
        }else {
            return 0;
        }
    }

    @Override
    public Evento getItem(int position) {
        return eventosLista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        evento = eventosLista.get(position);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String inicio = simpleDateFormat.format(addOneMonth(evento.getDataInicio()));


        view = layoutInflater.inflate(R.layout.list_item_compartilhar_evento, null);

        ((TextView) view.findViewById(R.id.txt_compartilhar_data_evento)).setText(inicio);
        ((TextView) view.findViewById(R.id.txt_compartilhar_nome_evento)).setText(evento.getNome());
         final String nomeEvento = ((TextView) view.findViewById(R.id.txt_compartilhar_nome_evento)).getText().toString();
        ((TextView) view.findViewById(R.id.txt_compartilhar_descricao_evento)).setText(evento.getDescricao());
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.chkBox_evento);


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox eventoCheckbox = (CheckBox) v;
                if (eventoCheckbox.isChecked()) {
                    nomesEventos.add(nomeEvento);
                } else {
                    nomesEventos.remove(nomeEvento);
                }
            }
        });
        return view;
    }

    public ArrayList<String> getNomesEventos(){
        return nomesEventos;
    }

    public static Date addOneMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

}
