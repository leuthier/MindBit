package br.com.mindbit.controleacesso.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.dominio.Usuario;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;

public class PerfilActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessaoUsuario sessaoUsuario;
    private Pessoa pessoaLogada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        sessaoUsuario = SessaoUsuario.getInstancia();
        pessoaLogada = sessaoUsuario.getPessoaLogada();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);

        TextView nome = (TextView) view.findViewById(R.id.txtNomePerfil);
        TextView email = (TextView) view.findViewById(R.id.txtEmailPerfil);
        ImageView fotoPerfil = (ImageView) view.findViewById(R.id.fotoPerfil);

        if (pessoaLogada.getFoto() != null){
            fotoPerfil.setImageURI(pessoaLogada.getFoto());
        }else{
            fotoPerfil.setImageURI(CadastroActivity.FOTO_PADRAO);
        }
        nome.setText(pessoaLogada.getNome());
        email.setText(pessoaLogada.getEmail());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            deslogar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deslogar(){
        sessaoUsuario.invalidarSessao();
        startLoginActivity();
    }

    public void startLoginActivity() {
        Intent i = new Intent(PerfilActivity.this, LoginActivity.class);
        startActivity(i);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_add_disciplina) {
            Intent i = new Intent(this, AddDisciplinaActivity.class);
            startActivity(i);

        } else if(id == R.id.nav_pesquisar_eventos) {
            Intent i = new Intent(this, PesquisarEventoActivity.class);
            startActivity(i);

        }else if(id == R.id.nav_add_evento) {
            Intent i = new Intent(this,AddEventoActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_amigos) {

        } else if (id == R.id.nav_config) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_sobre) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
