package br.com.mindbit.controleacesso.gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
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

public class AddEventoActivity extends AppCompatActivity{

    private Resources resources;
    private static Context contexto;
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

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date format_DataInicio;
    private Date format_DataFim;
    private Time format_HoraInicio;
    private Time format_HoraFim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evento);

        resources = getResources();

        contexto = this;

        this.setCamposAddEvento();
        eventoNegocio = EventoNegocio.getInstancia(contexto);
        sessaoUsuario = SessaoUsuario.getInstancia();
        pessoaLogada = sessaoUsuario.getPessoaLogada();

        edtEventoHoraFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TimePickerDialog timepick = new TimePickerDialog(AddEventoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String strHoraFim = hourOfDay+":"+minute;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        Date data = null;
                        try {
                            data = simpleDateFormat.parse(strHoraFim);
                            Time timeHoraFim = new Time(data.getTime());
                            format_HoraFim = timeHoraFim;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        edtEventoHoraFim.setText(hourOfDay + ":" + minute);

                    }
                }, hour, minute, true
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
                        String strHoraInicio = hourOfDay+":"+minute;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        Date data = null;
                        try {
                            data = simpleDateFormat.parse(strHoraInicio);
                            Time timeHoraInicio = new Time(data.getTime());
                            format_HoraInicio = timeHoraInicio;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        edtEventoHoraInicio.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true
                );
                timepick.show();
            }
        });

        edtEventoDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepicker = new DatePickerDialog(AddEventoActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        int mesCerto = monthOfYear + 1;

                        Calendar data = Calendar.getInstance();
                        data.set(year, mesCerto, dayOfMonth);
                        format_DataInicio = data.getTime();

                        edtEventoDataInicio.setText(dayOfMonth + "/" + mesCerto + "/" + year);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });
        edtEventoDataFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepicker = new DatePickerDialog(AddEventoActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int mesCerto = monthOfYear + 1;

                        Calendar data = Calendar.getInstance();
                        data.set(year, mesCerto, dayOfMonth);
                        format_DataFim = data.getTime();

                        edtEventoDataFim.setText(dayOfMonth + "/" + mesCerto + "/" + year);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });

        switch (spinner.getSelectedItemPosition()){
            case 0:
                GuiUtil.exibirMsg(this, "SETAR prio VERDE");
                break;
            case 1:
                GuiUtil.exibirMsg(this, "SETAR prio AMARELA");
                break;
            case 2:
                GuiUtil.exibirMsg(this, "SETAR prio VERMELHA");
                break;
        }


        btnAdicionar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    cadastrarEvento();

            }
        });
    }

    private void setCamposAddEvento(){

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        Spinner spinner = (Spinner) findViewById(R.id.prioridade_spinner);
        spinner.setAdapter(new ArrayAdapter<PrioridadeEvento>(this, android.R.layout.simple_list_item_1, PrioridadeEvento.values()));
        btnAdicionar = (Button) findViewById(R.id.btnAdicionar);

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


    private boolean validateFieldsEvento()  {

        nomeEvento = edtEventoNome.getText().toString().trim();
        descricaoEvento = edtEventoDescricao.getText().toString().trim();
        dataInicio = edtEventoDataInicio.getText().toString().trim();
        dataFim = edtEventoDataFim.getText().toString().trim();
        horaInicio = edtEventoDataInicio.getText().toString().trim();
        horaFim = edtEventoDataInicio.getText().toString().trim();

        //format_DataInicio = formatarData(dataInicio);
        //format_DataFim = formatarData(dataFim);
        //format_HoraInicio = formatarHora(horaInicio);
        //format_HoraFim = formatarHora(horaFim);

        return (!isEmptyFields(nomeEvento, descricaoEvento, dataInicio, dataFim, horaInicio, horaFim)
                && hasSizeValid(nomeEvento, descricaoEvento) && rangeDateValid(format_DataInicio,format_DataFim));

    }

    private boolean isEmptyFields(String nome, String descricao, String dataInicio,
                                  String dataFim, String horaInicio, String horaFim) {
        if (TextUtils.isEmpty(nome)) {
            edtEventoNome.requestFocus();
            edtEventoNome.setError(resources.getString(R.string.signUp_name_required));
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
            edtEventoNome.setError(resources.getString(R.string.login_user_invalid));
            return false;
        } else if (!(descricao.length() > 4)) {
            edtEventoDescricao.requestFocus();
            edtEventoDescricao.setError(resources.getString(R.string.addEvento_edt_small_description));
            return false;
        }
        return true;
    }
    private boolean rangeDateValid(Date date1, Date date2) {

        if(date1.compareTo(date2) == 1){
            GuiUtil.exibirMsg(this,resources.getString(R.string.addEvento_edt_data_hora_erro));
            return false;
        }
        return true;
    }



    private  void cadastrarEvento(){
        if(validateFieldsEvento()) {
            Toast.makeText(this,"validado",Toast.LENGTH_LONG).show();
        }
            /*try{

                int idUsuarioLogado = sessaoUsuario.getUsuarioLogado().getId();
                Evento evento = new Evento();
                evento.setId(idUsuarioLogado);
                evento.setNome(nomeEvento);
                evento.setDescricao(descricaoEvento);
                evento.setDataInicio(format_DataInicio);
                evento.setDataFim(format_DataFim);
                evento.setHoraInicio(format_HoraInicio);
                evento.setHoraFim(format_HoraFim);
                eventoNegocio.validarCadastroEvento(evento);


            }catch (MindbitException e){
                GuiUtil.exibirMsg(AddEventoActivity.this, e.getMessage());

            }*/
            int idUsuarioLogado = sessaoUsuario.getUsuarioLogado().getId();
            Evento evento = new Evento();
            evento.setId(idUsuarioLogado);
            evento.setNome(nomeEvento);
            evento.setDescricao(descricaoEvento);
            evento.setDataInicio(format_DataInicio);
            evento.setDataFim(format_DataFim);
            evento.setHoraInicio(format_HoraInicio);
            evento.setHoraFim(format_HoraFim);
            //eventoNegocio.validarCadastroEvento(evento);}
    }


}

