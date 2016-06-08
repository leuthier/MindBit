package br.com.mindbit.controleacesso.gui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;

import br.com.mindbit.R;

public class AddEventoActivity extends AppCompatActivity{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evento);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute1 = calendar.get(Calendar.MINUTE);
        edtEventoNome = (EditText) findViewById(R.id.edtEventoNome);
        edtEventoDescricao = (EditText) findViewById(R.id.edtEventoDescricao);
        edtEventoHoraInicio = (EditText) findViewById(R.id.edtEventoHoraInicio);
        edtEventoHoraFim = (EditText) findViewById(R.id.edtEventoHoraFim);
        edtEventoDataInicio = (EditText) findViewById(R.id.edtEventoDataInicio);
        edtEventoDataFim = (EditText) findViewById(R.id.edtEventoDataFim);

        /*edtEventoHoraFim.setOnClickListener(this);
        edtEventoDataInicio.setOnClickListener(this);
        edtEventoDataFim.setOnClickListener(this);
        edtEventoHoraInicio.setOnClickListener(this);*/





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
                // datepicker.setTitle("Select date");
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
                // datepicker.setTitle("Select date");
                datepicker.show();
            }
        });

    }

/*    @Override
    public void onClick(View v) {
        int opcao = v.getId();
        switch (opcao){
            case R.id.edtEventoHoraInicio:

            break;
            case R.id.edtEventoHoraFim:

            break;
            case R.id.edtEventoDataInicio:

            break;
            case R.id.edtEventoDataFim:

            break;
        }


    public void escolherHora(View q){
        TimePickerDialog timepick = new TimePickerDialog(AddEventoActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                q.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute1, true
        );
        timepick.show();
    }*/



        /*if(v.getId()== R.id.edtEventoHoraFim || v.getId()== R.id.edtEventoHoraInicio){
            View opcao = ;
            TimePickerDialog timepick = new TimePickerDialog(AddEventoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    opcao.setText(hourOfDay + ":" + minute);
                }
            }, hour, minute1, true
            );
            timepick.show();
        }*/

}

