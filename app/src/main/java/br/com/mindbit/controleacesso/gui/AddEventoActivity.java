package br.com.mindbit.controleacesso.gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.dominio.Evento;
import br.com.mindbit.controleacesso.dominio.Pessoa;
import br.com.mindbit.controleacesso.dominio.PrioridadeEvento;
import br.com.mindbit.controleacesso.negocio.EventoNegocio;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.infra.gui.GuiUtil;
import br.com.mindbit.infra.gui.MindbitException;

public class AddEventoActivity extends AppCompatActivity{

    private Resources resources;
    private EventoNegocio eventoNegocio;
    private SessaoUsuario sessaoUsuario;
    private Pessoa pessoaLogada;

    private EditText edtEventoNome;
    private EditText edtEventoDescricao;
    private EditText edtEventoHoraInicio;
    private EditText edtEventoHoraFim;
    private EditText edtEventoDataInicio;
    private EditText edtEventoDataFim;
    private Button btnAdicionar;
    private Spinner spinner;

    private Calendar calendar = Calendar.getInstance();
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    private String nomeEvento;
    private String descricaoEvento;
    private String horaInicio;
    private String horaFim;
    private String dataInicio;
    private String dataFim;
    private PrioridadeEvento prioridade;

    private Date dataAtual;
    private Date inicio;
    private Date fim;
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evento);

        resources = getResources();

        this.setCamposAddEvento();
        eventoNegocio = EventoNegocio.getInstancia(this);
        sessaoUsuario = SessaoUsuario.getInstancia();
        pessoaLogada = sessaoUsuario.getPessoaLogada();

        edtEventoHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTimePicker(edtEventoHoraInicio);
            }
        });

        edtEventoHoraFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTimePicker(edtEventoHoraFim);
            }
        });

        edtEventoDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolherDataInicio();
            }
        });
        edtEventoDataFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolherDataFim();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prioridade = ((PrioridadeEvento) parent.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    cadastrarEvento();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }
        private void setCamposAddEvento(){

            calendar = Calendar.getInstance();
            setActualMoment();

            spinner = (Spinner) findViewById(R.id.prioridade_spinner);
            spinner.setAdapter(new ArrayAdapter<PrioridadeEvento>(this, android.R.layout.simple_list_item_1, PrioridadeEvento.values()));
            btnAdicionar = (Button) findViewById(R.id.btnAdicionarEvento);

            edtEventoNome = (EditText) findViewById(R.id.input_edtEventoNome);
            edtEventoDescricao = (EditText) findViewById(R.id.input_edtEventoDescricao);
            edtEventoHoraInicio = (EditText) findViewById(R.id.input_edtEventoHoraInicio);
            edtEventoHoraFim = (EditText) findViewById(R.id.input_edtEventoHoraFim);
            edtEventoDataInicio = (EditText) findViewById(R.id.input_edtEventoDataInicio);
            edtEventoDataFim = (EditText) findViewById(R.id.input_edtEventoDataFim);
            edtEventoDataInicio.setFocusable(false);
            edtEventoDataFim.setFocusable(false);
            edtEventoHoraInicio.setFocusable(false);
            edtEventoHoraFim.setFocusable(false);
        }
    private void setActualMoment(){
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }
    private boolean validateFieldsEvento() throws ParseException {

        nomeEvento = edtEventoNome.getText().toString().trim();
        descricaoEvento = edtEventoDescricao.getText().toString().trim();
        horaInicio = edtEventoHoraInicio.getText().toString().trim();
        horaFim = edtEventoHoraFim.getText().toString().trim();
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DATE_FORMATTER;
        inicio = simpleDateFormat.parse(dataInicio + " " + horaInicio );
        fim = simpleDateFormat.parse(dataFim + " " + horaFim);
        setActualMoment();
        String atual = day +"-"+ month +"-"+year +" "+hour+":"+minute;
        dataAtual = simpleDateFormat.parse(atual);

        return (!isEmptyFields(nomeEvento, descricaoEvento, dataInicio, dataFim, horaInicio, horaFim)
                && hasSizeValid(nomeEvento, descricaoEvento) && dateValid(inicio, fim, dataAtual));
    }

    private boolean validateStartTime(Date dateI,Date actualD){
        if (actualD.compareTo(dateI) < 1){
            return true;
            }
        return false;
        }

    private boolean isEmptyFields(String nome, String descricao, String dataInicio,
                                               String dataFim, String horaInicio, String horaFim) {

        if (TextUtils.isEmpty(nome)) {
            edtEventoNome.requestFocus();
            edtEventoNome.setError(resources.getString(R.string.cadastro_nome_vazio));
            return true;
        } else if (TextUtils.isEmpty(descricao)) {
            edtEventoDescricao.requestFocus();
            edtEventoDescricao.setError(resources.getString(R.string.addEvento_edt_descricao));
            return true;
        }else if (TextUtils.isEmpty(dataInicio)) {
            edtEventoDataInicio.requestFocus();
            edtEventoDataInicio.setError(resources.getString(R.string.addEvento_edt_data_inicial));
            return true;
        }else if (TextUtils.isEmpty(dataFim)) {
            edtEventoDataFim.requestFocus();
            edtEventoDataFim.setError(resources.getString(R.string.addEvento_edt_data_final));
            return true;
        }else if (TextUtils.isEmpty(horaInicio)) {
            edtEventoHoraInicio.requestFocus();
            edtEventoHoraInicio.setError(resources.getString(R.string.addEvento_edt_hora_inicial));
            return true;
        }else if (TextUtils.isEmpty(horaFim)) {
            edtEventoHoraFim.requestFocus();
            edtEventoHoraFim.setError(resources.getString(R.string.addEvento_edt_hota_final));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String nome, String descricao) {
        if (!(nome.length() > 4)) {
            edtEventoNome.requestFocus();
            edtEventoNome.setError(resources.getString(R.string.login_curto));
            return false;
        } else if (!(descricao.length() > 4)) {
            edtEventoDescricao.requestFocus();
            edtEventoDescricao.setError(resources.getString(R.string.addEvento_edt_descricao_curta));
            return false;
        }
        return true;
    }
    private boolean dateValid(Date initialDate, Date finalDate,Date actualDate) {

        if( initialDate.compareTo(finalDate) < 1 && validateStartTime(initialDate,actualDate)){
            return true;
        }
        GuiUtil.exibirMsg(this, resources.getString(R.string.addEvento_edt_data_hora_erro));
        return false;
    }

    private  void cadastrarEvento() throws ParseException {
        if (validateFieldsEvento()) {
            try {
                int idPessoaLogada = sessaoUsuario.getPessoaLogada().getId();
                Evento evento = new Evento();
                evento.setIdPessoaCriadora(idPessoaLogada);
                evento.setNome(nomeEvento);
                evento.setDescricao(descricaoEvento);
                evento.setDataInicio(inicio);
                evento.setDataFim(fim);
                evento.setNivelPrioridadeEnum(prioridade);
                eventoNegocio.validarCadastroEvento(evento);
                GuiUtil.exibirMsg(this, getString(R.string.add_evento_sucesso));
                startNavigationActivity();
            } catch (MindbitException e) {
                GuiUtil.exibirMsg(AddEventoActivity.this, e.getMessage());

            }
        }
    }

    public void escolherDataFim(){
        final DatePickerDialog datepicker = new DatePickerDialog(AddEventoActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int mesCerto = monthOfYear + 1;
                edtEventoDataFim.setText(dayOfMonth + "/" + mesCerto + "/" + year);
                dataFim = dayOfMonth + "-" + monthOfYear + "-" + year;
            }
        }, year, month, day);
        datepicker.show();
    }

    public void escolherDataInicio(){
        DatePickerDialog datepicker = new DatePickerDialog(AddEventoActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int mesCerto = monthOfYear + 1;
                edtEventoDataInicio.setText(dayOfMonth + "/" + mesCerto + "/" + year);
                dataInicio = dayOfMonth + "-" + monthOfYear + "-" + year;
            }
        }, year, month, day);
        datepicker.show();
    }

    public void mostrarTimePicker(final EditText editText){
        final TimePickerDialog timepick = new TimePickerDialog(AddEventoActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                editText.setText(hourOfDay + ":" + minute);

            }
        }, hour, minute, true
        );
        timepick.show();
    }

    public void startNavigationActivity() {
        startActivity(new Intent(this, PerfilActivity.class));
        finish();
    }

}

