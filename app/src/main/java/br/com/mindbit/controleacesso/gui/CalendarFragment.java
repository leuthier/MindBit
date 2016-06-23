package br.com.mindbit.controleacesso.gui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;

public class CalendarFragment extends Fragment{
    private CalendarView calendar;
    private View view;
    private SessaoUsuario sessao;
    private Pessoa pessoaLogada;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sessao = SessaoUsuario.getInstancia();
        pessoaLogada = sessao.getPessoaLogada();

        view =inflater.inflate(R.layout.activity_calendar, container, false);
        calendar = (CalendarView) view.findViewById(R.id.calendarView);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getActivity().getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
