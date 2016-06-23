package br.com.mindbit.controleacesso.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;

public class EventoAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<Evento> eventos;
        private Evento evento;

        public EventoAdapter(Context context, List<Evento> eventos){
            this.eventos = eventos;

            mInflater = LayoutInflater.from(context);
        }

        public int getCount(){
            if (eventos != null) {
                return eventos.size();
            } else {
                return 0;
            }
        }

        public Evento getItem(int posicao)
        {
            return eventos.get(posicao);
        }

        public long getItemId(int posicao)
        {
            return posicao;
        }

        public View getView(int posicao,View view, ViewGroup parent){

            evento = eventos.get(posicao);

            view = mInflater.inflate(R.layout.list_item_pesquisar_evento, null);

            ((TextView) view.findViewById(R.id.txtitem_nome_evento)).setText(evento.getNome());
            ((TextView) view.findViewById(R.id.txtitem_descricao_evento)).setText(evento.getDescricao());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String inicio = simpleDateFormat.format(addOneMonth(evento.getDataInicio()));
            ((TextView) view.findViewById(R.id.txtitem_data_evento)).setText(inicio);

            setIconePrioridade(view);

            return view;
        }

    public static Date addOneMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    public View setIconePrioridade(View view){
        int nivelPrioridade = evento.getNivelPrioridadeEnum().ordinal();

        if (nivelPrioridade == 0 ) {
            ((ImageView) view.findViewById(R.id.img_evento)).setImageResource(R.drawable.bola_verde);
            return view;
        }else if (nivelPrioridade == 1) {
            ((ImageView) view.findViewById(R.id.img_evento)).setImageResource(R.drawable.bola_amarela);
            return view;
        }else if (nivelPrioridade == 2) {
            ((ImageView) view.findViewById(R.id.img_evento)).setImageResource(R.drawable.bola_vermelha);
            return view;
        }return view;
    }

}
