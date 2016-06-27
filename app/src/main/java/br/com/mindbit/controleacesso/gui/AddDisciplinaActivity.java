package br.com.mindbit.controleacesso.gui;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Disciplina;
import br.com.mindbit.controleacesso.negocio.DisciplinaNegocio;
import br.com.mindbit.infra.gui.GuiUtil;
import br.com.mindbit.infra.gui.MindbitException;

public class AddDisciplinaActivity extends AppCompatActivity {

    private Resources resources;
    private static Context contexto;
    private static DisciplinaNegocio disciplinaNegocio;

    private EditText input_edtDisciplinaNome;
    private EditText input_edtDisciplinaCodigo;
    private Button btnAdicionarDisciplina;

    private String nomeDisciplina;
    private String codigoDisciplina;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disciplina);

        resources = getResources();

        contexto = this;

        this.setCamposAddDisciplina();

        btnAdicionarDisciplina = (Button) findViewById(R.id.btnAdicionarDisciplina);
        btnAdicionarDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarDisciplina();
            }
        });

    }

    public void onResume() {
        super.onResume();

        if (disciplinaNegocio == null) {
            disciplinaNegocio = DisciplinaNegocio.getInstancia(contexto);
        }
    }

    private void setCamposAddDisciplina() {
        btnAdicionarDisciplina = (Button) findViewById(R.id.btnAdicionarDisciplina);
        input_edtDisciplinaNome = (EditText) findViewById(R.id.input_edtDisciplinaNome);
        input_edtDisciplinaCodigo = (EditText) findViewById(R.id.input_edtDisciplinaCodigo);

    }

    private boolean validateFieldsDisciplina(){
        nomeDisciplina = input_edtDisciplinaNome.getText().toString().trim();
        codigoDisciplina = input_edtDisciplinaCodigo.getText().toString().trim();
        return (!isEmptyFields(nomeDisciplina, codigoDisciplina) && hasSizeValid(nomeDisciplina, codigoDisciplina));
    }

    private boolean isEmptyFields(String nomeDisciplina, String codigoDisciplina) {
        if (TextUtils.isEmpty(nomeDisciplina)) {
            input_edtDisciplinaNome.requestFocus();
            input_edtDisciplinaNome.setError(resources.getString(R.string.add_disciplina_nome_vazio));
            return true;
        } else if (TextUtils.isEmpty(codigoDisciplina)) {
            input_edtDisciplinaCodigo.requestFocus();
            input_edtDisciplinaCodigo.setError(resources.getString(R.string.add_disciplina_codigo_vazio));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String nomeDisciplina, String codigoDisciplina){
        if (!(nomeDisciplina.length() > 3)){
            input_edtDisciplinaNome.requestFocus();
            input_edtDisciplinaNome.setError(resources.getString(R.string.add_disciplina_tamanho_nome_invalido));
            return false;
        } else if (codigoDisciplina.length() != 5){
            input_edtDisciplinaCodigo.requestFocus();
            input_edtDisciplinaCodigo.setError(resources.getString(R.string.add_disciplina_tamanho_codigo_invalido));
            return false;
        }
        return true;

    }
    private void cadastrarDisciplina(){
        if (validateFieldsDisciplina()){

            try{
                Disciplina disciplina = new Disciplina();
                disciplina.setNome(nomeDisciplina);
                disciplina.setCodigo(codigoDisciplina);
                disciplinaNegocio.validarCadastroDisciplina(disciplina);
                GuiUtil.exibirMsg(this, resources.getString(R.string.add_disciplina_sucesso));
                startNavigationActivity();
            } catch (MindbitException e){
                GuiUtil.exibirMsg(AddDisciplinaActivity.this, e.getMessage());
            }
        }
    }

    public void startNavigationActivity(){
        startActivity(new Intent(this, PerfilActivity.class)); finish();
    }
}

