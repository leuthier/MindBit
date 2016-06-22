package br.com.mindbit.controleacesso.gui;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.dominio.PrioridadeEvento;
import br.com.mindbit.controleacesso.persistencia.EventoDao;
import br.com.mindbit.infra.gui.GuiUtil;

public class EventoAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<Evento> itens;
        private PrioridadeEvento prioridadeEvento;
        private EventoDao eventoDao;
        private Evento evento;

        public EventoAdapter(Context context, List<Evento> itens)
        {
            this.itens = itens;

            mInflater = LayoutInflater.from(context);
        }

        public int getCount()
        {
            if (itens != null) {
                return itens.size();
            } else {
                return 0;
            }
        }

        public Evento getItem(int posicao)
        {
            return itens.get(posicao);
        }

        public long getItemId(int posicao)
        {
            return posicao;
        }

        public View getView(int posicao, View view, ViewGroup parent)
        {

            Evento item = itens.get(posicao);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            view = mInflater.inflate(R.layout.list_item_pesquisar_evento, null);
            ((TextView) view.findViewById(R.id.txtitem_nome_evento)).setText(item.getNome());
            ((TextView) view.findViewById(R.id.txtitem_descricao_evento)).setText(item.getDescricao());
            String inicio = simpleDateFormat.format(addOneMonth(item.getDataInicio()));
            ((TextView) view.findViewById(R.id.txtitem_data_evento)).setText(inicio);
            if (item.getNivelPrioridadeEnum().ordinal() == 0 ) {
                ((ImageView) view.findViewById(R.id.img_evento)).setImageResource(R.drawable.bola_verde);
                return view;
            }else if (item.getNivelPrioridadeEnum().ordinal() == 1) {
                ((ImageView) view.findViewById(R.id.img_evento)).setImageResource(R.drawable.bola_amarela);
                return view;
            }else if (item.getNivelPrioridadeEnum().ordinal() == 2) {
                ((ImageView) view.findViewById(R.id.img_evento)).setImageResource(R.drawable.bola_vermelha);
                return view;
            }
            return view;
        }
    public static Date addOneMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }
    }
