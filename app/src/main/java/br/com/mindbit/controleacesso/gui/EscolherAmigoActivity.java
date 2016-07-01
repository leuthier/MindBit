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

import java.util.ArrayList;
import java.util.List;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Amigo;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.negocio.AmigoNegocio;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.infra.gui.GuiUtil;
import br.com.mindbit.infra.gui.MindbitException;

public class EscolherAmigoActivity extends AppCompatActivity{

        private Resources resources;

        private AmigoNegocio amigoNegocio;
        private SessaoUsuario sessaoUsuario;

        private Pessoa pessoaLogada;

        private List<Amigo> amigosPessoa;
        private String emailsSelecionados;
        private ArrayList<Amigo> listItems = new ArrayList<>();
        private List<Amigo> pessoasDestinatarios;

        private ListView listaAmigos;
        private Button btnEnviar;

        private AdapterEscolherAmigo adapterEscolher;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_escolher_amigo);

            resources = getResources();

            amigoNegocio = AmigoNegocio.getInstancia(this);
            sessaoUsuario = SessaoUsuario.getInstancia();
            pessoaLogada = sessaoUsuario.getPessoaLogada();

            listaAmigos = (ListView) findViewById(R.id.ListView_escolher_amigo);
            btnEnviar = (Button) findViewById(R.id.btn_enviarEmail);
            btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enviar(v);
                }
            });

            adapterEscolher = new AdapterEscolherAmigo(this, listItems);
            try{
                iniciarLista();
            }catch(MindbitException e){
                Log.d("EscolherAmigoActvt", e.getMessage());
            }
        }

        public String getEmailsSelecionados(){
            ArrayList<String> emailsAmigos = adapterEscolher.getNomesAmigos();

            for (String email: emailsAmigos){
                try {
                    if (amigoNegocio.listarAmigos(pessoaLogada.getId())!=null) {
                        Amigo amigo = amigoNegocio.buscarPorEmail(pessoaLogada.getId(), email);
                        emailsSelecionados += amigo.getEmail();
                    }
                }catch (MindbitException e){
                    Log.d("EscolherAmigoActvt", e.getMessage());
                }
            }
            return emailsSelecionados;
        }

        public void enviar(View v){
            int id = v.getId();
            switch (id){
                case R.id.btn_enviarEmail:
                    String[] emailsDestino = new String[]{getEmailsSelecionados()};

                    //StringBuilder conteudoEmail = getAppConteudo(getEventosSelecionados());
                    //if (getEventosSelecionados().size()==0){
                        GuiUtil.exibirMsg(this,"Selecione algum evento");
                    //}else {
                        String subject = ("MindBit - Alguém compartilhou eventos com você!");
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(android.content.Intent.EXTRA_EMAIL, emailsDestino);
                        i.putExtra(Intent.EXTRA_SUBJECT, subject);
                        //i.putExtra(Intent.EXTRA_TEXT, conteudoEmail.toString());
                        startActivity(Intent.createChooser(i, "Compartilhar para:"));
                    //}
            }

        }

//        public String getDestinariosEmails(){
//            Pessoa pessoa = pessoaLogada;
//            String emailsDestino = new String();
//
//            if (pessoa.getAmigos() != null) {
//                List<Amigo> amigos = pessoa.getAmigos();
////                for (Amigo amigo : amigos) {
////                    emailsDestino += amigo.getEmail() + ",";
////                }
//            }
//            emailsDestino+="ariana@teste.com"+","+"victor.leuthier@ufrpe.br"+",";
//
//            return emailsDestino;
//        }

//        public StringBuilder getAppConteudo(List<Evento> eventos){
//            StringBuilder infoEventos = new StringBuilder();
//
//            for (Evento evento : eventos) {
//                infoEventos.append(getInfoEventoApp(evento));
//            }
//            infoEventos.append("\nAtenciosamente, " + "\n" + pessoaLogada.getNome()+".\n"+
//                    "via MindBit - https://sites.google.com/site/mindbitufrpe/");
//            return infoEventos;
//        }

        public void iniciarLista() throws MindbitException{
            amigosPessoa = amigoNegocio.listarAmigos(pessoaLogada.getId());

            adapterEscolher = new AdapterEscolherAmigo(this, amigosPessoa);
            listaAmigos.setAdapter(adapterEscolher);
        }

}