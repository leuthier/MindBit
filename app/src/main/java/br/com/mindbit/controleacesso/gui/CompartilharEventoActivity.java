package br.com.mindbit.controleacesso.gui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    //private ArrayAdapter<String> adapter = null;
    private List<Amigo> pessoasDestinatarios;

    private ListView listaEventos;
    private Button btnCompartilhar;
    private AdapterCompartilharEvento adapterCompartilhar;

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
        btnCompartilhar = (Button) findViewById(R.id.btn_compartilhar_evento);
        btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compartilhar(v);
            }
        });

        adapterCompartilhar = new AdapterCompartilharEvento(this, listItems);

        //mostrarLista();
        try {
            iniciarLista();
        } catch (MindbitException e) {
            Log.d("Compartilhar evento activity", e.getMessage());
        }
    }

    /*private class MyCustomAdapter extends ArrayAdapter<Evento> {
        private ArrayList<Evento> eventoList;

        public MyCustomAdapter(Context context, int textViewResourceId,ArrayList<Evento> eventos) {
            super(context, textViewResourceId, eventos);
            this.eventoList = new ArrayList<Evento>();
            this.eventoList.addAll(eventos);
        }

        private class ViewHolder {

            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.eventos_checkbox, null);

                holder = new ViewHolder();

                holder.name = (CheckBox) convertView.findViewById(R.id.txt_nome_evento);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Evento evento = (Evento) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        //evento.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Evento evento = eventoList.get(position);

            holder.name.setText(evento.getNome());
            //holder.name.setChecked(evento.isSelected());
            holder.name.setTag(evento);

            return convertView;

        }

    }
*/
    public List<Evento> getEventosSelecionados(){

        return eventosMarcardos;
    }

    public void compartilhar(View v){
        int id = v.getId();
        switch (id){
            case R.id.btn_compartilhar_evento:
                String[] emailsDestino = new String[]{getDestinariosEmails()};
                //eventosPessoa NÃO está correto - deveria ser eventosMarcados!
                StringBuilder conteudoEmail = getAppConteudo(eventosPessoa);
                //JEITO CERTO ABAIXO
                //StringBuilder conteudoEmail = getAppConteudo(getEventosSelecionados());
                String subject = ("MindBit - Alguém compartilhou eventos com você!");
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_EMAIL, emailsDestino);
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT, conteudoEmail.toString());
                startActivity(Intent.createChooser(i, "Compartilhar para:"));
        }



        /*checkButtonClick();
        if (checkButtonClick()!=null) {

        }else{
            GuiUtil.exibirMsg(this,"Selecione algum evento");
        }*/
    }

    public String getDestinariosEmails(){
        Pessoa pessoa = pessoaLogada;
        String emailsDestino = new String();

//        if (pessoa.getAmigos() != null) {
//            List<Amigo> amigos = pessoa.getAmigos();
//            for (Amigo amigo : amigos) {
//                emailsDestino += amigo.getEmail() + ",";
//            }
//        }
        emailsDestino+="ariana@teste.com"+","+"victor.leuthier@ufrpe.br"+",";

        return emailsDestino;
    }

    public StringBuilder getAppConteudo(List<Evento> eventos){
        StringBuilder infoEventos = new StringBuilder();

       for (Evento evento : eventos) {
           infoEventos.append(getInfoEventoApp(evento));
       }

        infoEventos.append("\nAtenciosamente, " + "\n" + pessoaLogada.getNome()+".\n"+
        "via MindBit - https://sites.google.com/site/mindbitufrpe/");
        return infoEventos;
    }


    /*private class ContactListAdapter extends ArrayAdapter<String> {
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
            *//*((TextView) view.findViewById(R.id.txt_nome_evento)).setText(evento.getNome());

            verificaCheckbox(view);
            if (verificaCheckbox(view)){
                //evento eh objeto
                eventosMarcardos.add(evento);
            }else{
                eventosMarcardos.remove(evento);
            }*//*

            return view;
        }
    }*/

    /*private StringBuilder checkButtonClick() {
        StringBuilder mensagem = new StringBuilder();
        btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...");

                // ArrayList<String> countryList = adapter.countryList;
                for (int i = 0; i < list.size(); i++) {
                    String evento = list.get(i);
                    //if(evento.isSelected()){
                    responseText.append(evento);
                    //}
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_SHORT).show();

            }
        });

     return mensagem;
    }*/

    /*public void mostrarLista(){
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
    }*/

    public void iniciarLista() throws MindbitException{
        eventosPessoa = eventoNegocio.listarEventosProximo(pessoaLogada.getId());

        adapterCompartilhar = new AdapterCompartilharEvento(this, eventosPessoa);
        listaEventos.setAdapter(adapterCompartilhar);
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


