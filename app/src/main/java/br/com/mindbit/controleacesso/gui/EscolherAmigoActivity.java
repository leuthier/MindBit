package br.com.mindbit.controleacesso.gui;

import android.content.Context;
import android.content.Intent;
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
    private AmigoNegocio amigoNegocio;
    private SessaoUsuario sessaoUsuario;
    private Pessoa pessoaLogada;

    private List<Amigo> amigosPessoa;
    private ArrayList<String> nomesAmigos = new ArrayList<>();
    private ArrayList<String> emailsDestinatarios = new ArrayList<>();

    private ListView listaAmigos;
    private CheckBox checkbox;
    private Button btnCompartilhar;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_amigo);

        amigoNegocio = AmigoNegocio.getInstancia(this);
        sessaoUsuario = SessaoUsuario.getInstancia();
        pessoaLogada = sessaoUsuario.getPessoaLogada();

        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");

        listaAmigos = (ListView) findViewById(R.id.ListView_escolher_amigo);
        checkbox = (CheckBox) findViewById(R.id.chkbx_escolhe_amigo);
        btnCompartilhar = (Button) findViewById(R.id.btn_enviarEmail);
        btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarEmail(v);
            }
        });

        mostrarLista();
    }

    public String getDestinariosEmails(List<Amigo> destinatarios){
        String emailsDestino = new String();

/*        for (Pessoa pessoa : destinatarios) {
            emailsDestino += pessoa.getEmail()+",";

        }*/

        emailsDestino+="ariana@teste.com"+","+"victor.leuthier@ufrpe.br"+",";

        return emailsDestino;
    }

    public void mostrarLista(){
        ArrayList<String> nomeAmigos = new ArrayList<>();

        try{
            amigosPessoa = amigoNegocio.listarAmigos(pessoaLogada.getId());
            for (Amigo amigo:amigosPessoa){
                nomeAmigos.add(amigo.getNome());
                //precisa de emailsDestinatarios?
                emailsDestinatarios.add(amigo.getEmail());
            }
        }catch(MindbitException e){
            GuiUtil.exibirMsg(this, e.getMessage());
        }

        if (listaAmigos!=null){
            ArrayAdapter<String> adapter = new ContactListAdapter(this,nomeAmigos);
            listaAmigos.setAdapter(adapter);
        }
    }

    private class ContactListAdapter extends ArrayAdapter<String> {
        public ContactListAdapter(Context context, ArrayList<String> s) {
            super(context, 0, s);
        }


        public View getView(int position, View view, ViewGroup parent) {
            String s = getItem(position);

            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.amigos_checkbox, parent, false);
            }

            TextView name = (TextView) view.findViewById(R.id.txt_nome_amigo);
            name.setText(s);

            return view;
        }
    }

    public void enviarEmail(View v){
        String[] emailsDestino = new String[]{getDestinariosEmails(amigosPessoa)};

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_EMAIL, emailsDestino);
        //i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(i, "Compartilhar para:"));
    }
}
