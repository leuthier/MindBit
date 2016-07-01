package br.com.mindbit.controleacesso.gui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Amigo;
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
    private ArrayList<Evento> eventosMarcardos;
    private ArrayList<Evento> listItems = new ArrayList<>();
    private String todosEmails;

    private ListView listaEventos;
    private Button btnCompartilhar;
    private Button btnEscolher;
    private AdapterCompartilharEvento adapterCompartilhar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartilhar_evento);

        resources = getResources();

        eventoNegocio = EventoNegocio.getInstancia(this);
        sessaoUsuario = SessaoUsuario.getInstancia();
        pessoaLogada = sessaoUsuario.getPessoaLogada();

        listaEventos = (ListView) findViewById(R.id.ListView_compartilhar_evento);
        btnCompartilhar = (Button) findViewById(R.id.btn_compartilhar_evento);
        btnEscolher = (Button) findViewById(R.id.btn_escolher_amigos);
        btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compartilhar(v);
            }
        });

        btnEscolher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEscolherAmigo(v);
            }
        });

        adapterCompartilhar = new AdapterCompartilharEvento(this, listItems);

        try {
            iniciarLista();
        } catch (MindbitException e) {
            Log.d("CompartilharEventoActvt", e.getMessage());
        }
    }

    public ArrayList<Evento> getEventosSelecionados(){
        ArrayList<String> nomes = adapterCompartilhar.getNomesEventos();
        eventosMarcardos = new ArrayList<>();

        for (String nome:nomes){
            try {
                if (eventoNegocio.pesquisarPorNome(nome)!=null) {
                    Evento evento = eventoNegocio.pesquisarPorNome(nome);
                    eventosMarcardos.add(evento);
                }
            }catch (MindbitException e){
                Log.d("CompartilharEventoActvt", e.getMessage());
            }
        }
        return eventosMarcardos;
    }

    public void abrirEscolherAmigo(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_escolher_amigos:
                StringBuilder conteudoEmail = getAppConteudo(getEventosSelecionados());
                Intent i = new Intent(this,EscolherAmigoActivity.class);
                i.putExtra("message",conteudoEmail.toString());
                startActivity(i);
        }
    }
    public void compartilhar(View v){
        int id = v.getId();
        switch (id){
            case R.id.btn_compartilhar_evento:
                construirMensagemCompartilhada();
        }
    }
    public void construirMensagemCompartilhada(){
        String[] emailsDestino = new String[]{getAmigosEmails()};
        StringBuilder conteudoEmail = getAppConteudo(getEventosSelecionados());

        if (getEventosSelecionados().size()==0){
            GuiUtil.exibirMsg(this,"Selecione algum evento");
        }else {
            String subject = ("MindBit - Alguém compartilhou eventos com você!");
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(android.content.Intent.EXTRA_EMAIL, emailsDestino);
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            i.putExtra(Intent.EXTRA_TEXT, conteudoEmail.toString());
            startActivity(Intent.createChooser(i, "Compartilhar para:"));
        }
    }

    public String getAmigosEmails(){
        List<Amigo> todosAmigos = pessoaLogada.getAmigos();

        if(todosAmigos != null){
            for (Amigo amigo: todosAmigos){
                        todosEmails += amigo.getEmail()+",";
                    }
            }
        return todosEmails;
    }

    public StringBuilder getAppConteudo(List<Evento> eventos){
        StringBuilder infoEventos = new StringBuilder();

       for (Evento evento : eventos) {
           infoEventos.append(getInfoEventoApp(evento));
       }

        infoEventos.append("\nAtenciosamente, " + "\n" + pessoaLogada.getNome() + "." +"\n"+
                "\nvia MindBit - https://sites.google.com/site/mindbitufrpe/");
        return infoEventos;
    }

    public void iniciarLista() throws MindbitException{
        eventosPessoa = eventoNegocio.listarEventosProximo(pessoaLogada.getId());

        adapterCompartilhar = new AdapterCompartilharEvento(this, eventosPessoa);
        listaEventos.setAdapter(adapterCompartilhar);
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


