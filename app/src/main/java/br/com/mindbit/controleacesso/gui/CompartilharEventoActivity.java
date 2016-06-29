package br.com.mindbit.controleacesso.gui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.negocio.EventoNegocio;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.infra.gui.GuiUtil;
import br.com.mindbit.infra.gui.MindbitException;

public class CompartilharEventoActivity extends AppCompatActivity{
    private Resources resources;

    private EventoNegocio eventoNegocio;
    private SessaoUsuario sessaoUsuario;
    private Pessoa pessoaLogada;

    private ArrayList<Evento> eventosPessoa;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter = null;
    private List<Pessoa> pessoasDestinatarios;
    private List<Evento> eventosMarcardos;

    private ListView listaEventos;
    private CheckBox checkbox;
    private Button btnCompartilhar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartilhar_evento);
        //Bundle bundle = getIntent().getExtras();

        resources = getResources();

        eventoNegocio = EventoNegocio.getInstancia(this);
        sessaoUsuario = SessaoUsuario.getInstancia();
        pessoaLogada = sessaoUsuario.getPessoaLogada();

        listaEventos = (ListView) findViewById(R.id.ListView_compartilhar_evento);
        checkbox = (CheckBox) findViewById(R.id.chkbx_escolhe_evento);
        btnCompartilhar = (Button) findViewById(R.id.btn_compartilhar_evento);
        btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compartilhar(v);
            }
        });


       mostrarLista();
    }

    public void compartilhar(View v){

        String[] emailsDestino = new String[]{getDestinariosEmails(pessoasDestinatarios)};
        //eventosPessoa NÃO está correto - deveria ser eventosMarcados!
        StringBuilder conteudoEmail = getAppConteudo(eventosPessoa);
        //ArrayList<String> emailConteudo = getAppConteudo(eventosMarcardos);
        String subject = ("MindBit - Alguém compartilhou eventos com você!");


        checkButtonClick();
        if (checkButtonClick()!=null) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(android.content.Intent.EXTRA_EMAIL, emailsDestino);
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            i.putExtra(Intent.EXTRA_TEXT, conteudoEmail.toString());
            startActivity(Intent.createChooser(i, "Compartilhar para:"));
        }else{
            GuiUtil.exibirMsg(this,"Selecione algum evento");
        }
    }

    public String getDestinariosEmails(List<Pessoa> destinatarios){
        String emailsDestino = new String();

/*        for (Pessoa pessoa : destinatarios) {
            emailsDestino += pessoa.getEmail()+",";

        }*/

        emailsDestino+="ariana@teste.com"+","+"victor.leuthier@ufrpe.br"+",";

        return emailsDestino;
    }

    public StringBuilder getAppConteudo(List<Evento> eventos){
        StringBuilder infoEventos = new StringBuilder();

       for (Evento evento : eventos) {
           infoEventos.append(getInfoEventoApp(evento));
       }

        infoEventos.append("\nAtenciosamente, " + "\n" + pessoaLogada.getNome()+".");
        return infoEventos;
    }

    private class ContactListAdapter extends ArrayAdapter<String> {
        public ContactListAdapter(Context context, ArrayList<String> s) {
            super(context, 0, s);
        }


        public View getView(int position, View view, ViewGroup parent) {
            String s = getItem(position);

            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.eventos_checkbox, parent, false);
            }

            TextView name = (TextView) view.findViewById(R.id.txt_nome_evento);
            name.setText(s);

            return view;
        }
    }

    private StringBuilder checkButtonClick() {
        StringBuilder mensagem = new StringBuilder();
        btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                // ArrayList<String> countryList = adapter.countryList;
                for (int i = 0; i < list.size(); i++) {
                    String evento = list.get(i);
                    //if(evento.isSelected()){
                    responseText.append("\n" + evento);
                    //}
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_SHORT).show();

            }
        });

     return mensagem;
    }

    public void mostrarLista(){
        ArrayList<String> eventosInformacoes = new ArrayList<String>();

        try {
            eventosPessoa = eventoNegocio.listarEventosProximo(pessoaLogada.getId());
            for (Evento evento:eventosPessoa) {
                eventosInformacoes.add(mostrarInfoEvento(evento));
            }
        } catch (MindbitException e) {
            GuiUtil.exibirMsg(CompartilharEventoActivity.this, e.getMessage());
        }

        if (listaEventos!=null) {
            ArrayAdapter<String> adapter = new ContactListAdapter(this, eventosInformacoes);
            listaEventos.setAdapter(adapter);
        }
    }

    public String mostrarInfoEvento(Evento evento){
        String nomeEvento = evento.getNome();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String dataInicio = simpleDateFormat.format(addOneMonth(evento.getDataInicio()));
        String dataFim = simpleDateFormat.format(addOneMonth(evento.getDataFim()));

        String informacoes = nomeEvento + "\n" + dataInicio + " até\n" + dataFim;
        return informacoes;
    }

    public String getInfoEventoApp(Evento evento){
        String nomeEvento = evento.getNome();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String dataInicio = simpleDateFormat.format(addOneMonth(evento.getDataInicio()));
        String dataFim = simpleDateFormat.format(addOneMonth(evento.getDataFim()));

        String informacoes = "\n" + nomeEvento + "\nData/Hora de início: " + dataInicio +
                "\nData/Hora de término: " + dataFim + "\n";
        return informacoes;
    }

    public static Date addOneMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

}


