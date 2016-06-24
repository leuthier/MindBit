package br.com.mindbit.controleacesso.gui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.negocio.EventoNegocio;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.infra.gui.MindbitException;

public class CalendarFragment extends Fragment{
    private CalendarView calendar;
    private View view;

    private ArrayList<Evento> listaEventos;
    private EventoNegocio eventoNegocio;
    private SessaoUsuario sessao;
    private Pessoa pessoaLogada;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sessao = SessaoUsuario.getInstancia();
        pessoaLogada = sessao.getPessoaLogada();

        eventoNegocio = EventoNegocio.getInstancia(getActivity().getApplicationContext());

        view =inflater.inflate(R.layout.activity_calendar, container, false);
        calendar = (CalendarView) view.findViewById(R.id.calendarView);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String data = dayOfMonth + "-" + (month) + "-" + year;


                try {
                    listaEventos = eventoNegocio.listarEventosDia(data, pessoaLogada.getId());
                    if (!(listaEventos.isEmpty())) {
                        Intent it = new Intent(getActivity().getApplicationContext(), Pop.class);
                        it.putExtra("DATA",data);
                        startActivity(it);
                        }

                }catch (MindbitException e) {
                        e.printStackTrace();
                }


            }
        });
        return view;
    }
}


