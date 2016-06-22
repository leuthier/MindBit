package br.com.mindbit.controleacesso.gui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;

public class ContadorFragment extends Fragment {
    private TextView txtTimerDay;
    private TextView txtDias;
    private TextView txtNomeEvento;
    private Handler handler;
    private Runnable runnable;
    private View view;
    private ArrayList<Evento> eventosDia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view =inflater.inflate(R.layout.contador_fragment_layout, container, false);

        txtTimerDay = (TextView)view.findViewById(R.id.txtTimerDay);
        txtNomeEvento = (TextView)view.findViewById(R.id.texView_nome_evento);
        txtDias = (TextView)view.findViewById(R.id.txtDias);

        countDownStart();
        return view;
    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date futureDate = dateFormat.parse("2016-09-16");
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        txtTimerDay.setText("" + String.format("%02d", days));
                        txtDias.setText(R.string.dias);
                        txtNomeEvento.setText("Nome do Evento");
                    } else {
                        txtTimerDay.setText(R.string.sem_evento);
                        txtDias.setText("");
                        txtNomeEvento.setText("");
                    }
                } catch (ParseException e) {
                    Log.e("KS","contador error ",e);
                   //GuiUtil.exibirMsg(getParentFragment(), e.getMessage());
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }
}
