package br.com.mindbit.controleacesso.gui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Amigo;
import br.com.mindbit.controleacesso.dominio.Evento;
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

        private ListView listaAmigos;
        private Button btnEnviar;
        private AdapterCompartilharEvento adapterCompartilhar;
        private CompartilharEventoActivity compartilharEventoActivity = new CompartilharEventoActivity();

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
            ArrayList<String> amigosSelecionados = adapterEscolher.getEmailsAmigos();

            if(amigosSelecionados.size() == 0){
                GuiUtil.exibirMsg(this,"Selecione algum amigoo");
            }else {
                for (String emailAmigo : amigosSelecionados) {
                    try {
                        if (amigoNegocio.listarAmigos(pessoaLogada.getId()) != null) {
                            Amigo amigo = amigoNegocio.buscarPorEmail(emailAmigo);
                            emailsSelecionados += amigo.getEmail() + ",";
                        }
                    } catch (MindbitException e) {
                        Log.d("EscolherAmigoActvt", e.getMessage());
                    }
                }
            }
            return emailsSelecionados;
        }

        public void enviar(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_enviarEmail:
                    construirEmail();

            }
        }

        public void construirEmail(){
            String[] emailsDestino = new String[]{getEmailsSelecionados()};
            ArrayList<Evento> eventosSelecionados = compartilharEventoActivity.getEventosSelecionados();

            if (getEmailsSelecionados().length()==0){
                GuiUtil.exibirMsg(this,"Selecione algum amigo");
            }else {
                StringBuilder conteudoEmail = getAppConteudo(eventosSelecionados);
                String subject = ("MindBit - Alguém compartilhou eventos com você!");
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_EMAIL, emailsDestino);
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT, conteudoEmail.toString());
                startActivity(Intent.createChooser(i, "Compartilhar para:"));
            }
        }

        public StringBuilder getAppConteudo(List<Evento> eventos){
            StringBuilder infoEventos = new StringBuilder();

            for (Evento evento : eventos) {
                String infoEvento = compartilharEventoActivity.getInfoEventoApp(evento);
                infoEventos.append(infoEvento);
            }
            infoEventos.append("\nAtenciosamente, " + "\n" + pessoaLogada.getNome()+".\n"+
                    "via MindBit - https://sites.google.com/site/mindbitufrpe/");
            return infoEventos;
        }

        public void iniciarLista() throws MindbitException{
            amigosPessoa = amigoNegocio.listarAmigos(pessoaLogada.getId());

            adapterEscolher = new AdapterEscolherAmigo(this, amigosPessoa);
            listaAmigos.setAdapter(adapterEscolher);
        }

}