package br.com.mindbit.controleacesso.gui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.infra.gui.GuiUtil;

public class EventoAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<Evento> itens;

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

            Calendar c = Calendar.getInstance();
            //Set time in milliseconds
            c.setTimeInMillis(item.getDataInicio());
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            int hr = c.get(Calendar.HOUR);
            int min = c.get(Calendar.MINUTE);

            view = mInflater.inflate(R.layout.list_item_pesquisar_evento, null);
            int mesCerto = mMonth +1;
            String data = mDay + "/" + mesCerto + "/" + mYear + " " + hr + ":" + min;
            ((TextView) view.findViewById(R.id.txtitem_nome_evento)).setText(item.getNome());
            ((TextView) view.findViewById(R.id.txtitem_descricao_evento)).setText(item.getDescricao());
            ((TextView) view.findViewById(R.id.txtitem_data_evento)).setText((data));

            return view;
        }
    }
