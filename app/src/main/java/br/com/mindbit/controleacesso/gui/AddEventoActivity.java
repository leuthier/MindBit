package br.com.mindbit.controleacesso.gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import br.com.mindbit.R;
import br.com.mindbit.controleacesso.negocio.EventoNegocio;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.controleacesso.persistencia.EventoDao;
import br.com.mindbit.infra.gui.MindbitException;
import br.com.mindbit.infra.gui.GuiUtil;

public class AddEventoActivity extends AppCompatActivity{

    private Resources resources;
    private static Context contexto;

    private Calendar calendar = Calendar.getInstance();
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute1;

    private EditText edtEventoNome;
    private EditText edtEventoDescricao;
    private EditText edtEventoHoraInicio;
    private EditText edtEventoHoraFim ;
    private EditText edtEventoDataInicio;
    private EditText edtEventoDataFim;
    private Button btnAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evento);

        contexto = this;

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute1 = calendar.get(Calendar.MINUTE);
        btnAdicionar = (Button) findViewById(R.id.btnAdicionar);
        edtEventoNome = (EditText) findViewById(R.id.edtEventoNome);
        edtEventoDescricao = (EditText) findViewById(R.id.edtEventoDescricao);
        edtEventoHoraInicio = (EditText) findViewById(R.id.edtEventoHoraInicio);
        edtEventoHoraFim = (EditText) findViewById(R.id.edtEventoHoraFim);
        edtEventoDataInicio = (EditText) findViewById(R.id.edtEventoDataInicio);
        edtEventoDataFim = (EditText) findViewById(R.id.edtEventoDataFim);
        edtEventoDataInicio.setFocusable(false);
        edtEventoDataFim.setFocusable(false);
        edtEventoHoraInicio.setFocusable(false);
        edtEventoHoraFim.setFocusable(false);


        btnAdicionar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cadastrarEvento();

            }
        });

        edtEventoHoraFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timepick = new TimePickerDialog(AddEventoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        edtEventoHoraFim.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute1, true
                );
                timepick.show();
            }
        });

        edtEventoHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timepick = new TimePickerDialog(AddEventoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        edtEventoHoraInicio.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute1, true
                );
                timepick.show();
            }
        });

        edtEventoDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepicker = new DatePickerDialog(AddEventoActivity.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int mesCerto = monthOfYear + 1;
                        edtEventoDataInicio.setText(dayOfMonth + "/" + mesCerto + "/" + year);
                    }
                },year,month,day);
                datepicker.show();
            }
        });
        edtEventoDataFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepicker = new DatePickerDialog(AddEventoActivity.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int mesCerto = monthOfYear + 1;
                        edtEventoDataFim.setText(dayOfMonth + "/" + mesCerto + "/" + year);
                    }
                },year,month,day);
                datepicker.show();
            }
        });

    }
    private boolean validateFieldsEvento() {
        String nome = edtEventoNome.getText().toString().trim();
        String descricao = edtEventoDescricao.getText().toString().trim();
        String dataInicio = edtEventoDataInicio.getText().toString().trim();
        String dataFim = edtEventoDataFim.getText().toString().trim();
        String horaInicio = edtEventoDataInicio.getText().toString().trim();
        String horaFim = edtEventoDataInicio.getText().toString().trim();

        return (!isEmptyFields(nome, descricao, dataInicio, dataFim, horaInicio, horaFim)
                && hasSizeValid(nome, descricao) && rangeTimeValid(dataInicio, dataFim));
    }

    private boolean isEmptyFields(String nome, String descr, String dataIn,
                                  String dataFi, String horaIn, String horaFi) {
        if (TextUtils.isEmpty(nome)) {
            edtEventoNome.requestFocus();
            edtEventoNome.setError(resources.getString(R.string.signUp_name_required));
            return true;
        } else if (TextUtils.isEmpty(descr)) {
            edtEventoDescricao.requestFocus();
            edtEventoDescricao.setError(resources.getString(R.string.addEvento_edt_descricao));
            return true;
        }else if (TextUtils.isEmpty(dataIn)) {
            edtEventoDataInicio.requestFocus();
            edtEventoDataInicio.setError(resources.getString(R.string.addEvento_edt_data_inicial));
            return true;
        }else if (TextUtils.isEmpty(dataFi)) {
            edtEventoDataFim.requestFocus();
            edtEventoDataFim.setError(resources.getString(R.string.addEvento_edt_data_final));
            return true;
        }else if (TextUtils.isEmpty(horaIn)) {
            edtEventoHoraInicio.requestFocus();
            edtEventoHoraInicio.setError(resources.getString(R.string.addEvento_edt_hora_inicial));
            return true;
        }else if (TextUtils.isEmpty(horaFi)) {
            edtEventoHoraFim.requestFocus();
            edtEventoHoraFim.setError(resources.getString(R.string.addEvento_edt_hota_final));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String nome, String descricao) {
        if (!(nome.length() > 10)) {
            edtEventoNome.requestFocus();
            edtEventoNome.setError(resources.getString(R.string.login_user_invalid));
            return false;
        } else if (!(descricao.length() > 10)) {
            edtEventoDescricao.requestFocus();
            edtEventoDescricao.setError(resources.getString(R.string.login_password_invalid));
            return false;
        }
        return true;
    }
    private boolean rangeTimeValid(String dataInicio, String dataFim){
        String[] partsInicio = dataInicio.split("/");
        String[] partsFim = dataFim.split("/");
        int anoInicio = Integer.parseInt(partsInicio[2]);
        int anoFim = Integer.parseInt(partsFim[2]);
        int mesInicio = Integer.parseInt(partsInicio[1]);
        int mesFim = Integer.parseInt(partsFim[1]);
        int diaInicio = Integer.parseInt(partsInicio[0]);
        int diaFim = Integer.parseInt(partsFim[0]);
        if (anoInicio <= anoFim){
            if (mesInicio <= mesFim){
                if (diaInicio <= diaFim){
                    return true;
                }else { return false; }
            }else { return false; }

        }else { return false; }

    }

    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();
    private EventoNegocio eventoNegocio = EventoNegocio.getInstancia(contexto);
    private  void cadastrarEvento(){
        if(validateFieldsEvento()){
            int idUsuarioLogado = sessaoUsuario.getUsuarioLogado().getId();
            //eventoNegocio.validarCadastroEvento();

            /*try {
            }catch (MindbitException e){
                GuiUtil.exibirMsg(AddEventoActivity.this, e.getMessage());

            }*/
        }
    }
}

