package com.example.calendarcapital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Edit_Update_Activity extends AppCompatActivity {

    private EditText eventNameET, eventCommentET;
    private TextView eventDateTV, eventTimeTV, changeTimeTV, changeDateTV;
    int hour, min;
    private LocalDate date;
    private static LocalTime time;
    String id_row,title,comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_update);
        initWidgets();
        getSetIntentData();

//        time = LocalTime.parse(CalendarUtils.formattedShortTime(LocalTime.now()));
//        eventTimeTV.setText("Time: " + CalendarUtils.formattedShortTime(time));
//        date = CalendarUtils.selectedDate;
//        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(date));

        changeTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeTime(LocalTime.now().getHour(), LocalTime.now().getMinute());
            }
        });

        changeDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeDate(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth());
            }
        });

        findViewById(R.id.menu_new_upd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.refreshItemOnLay:
                                AllEventsList.reloadActivity(Edit_Update_Activity.this);
                                return true;
                            case R.id.previousAct:
                                onBackPressed();


                        }

                        return false;
                    }
                });
                popupMenu.inflate(R.menu.menu_up_inlayout);
                popupMenu.show();
            }
        });
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameETupd);
        eventCommentET = findViewById(R.id.eventCommentETupd);
        eventDateTV = findViewById(R.id.eventDateETupd);
        eventTimeTV = findViewById(R.id.eventTimeETupd);
        changeTimeTV = findViewById(R.id.changeTimeTVupd);
        changeDateTV = findViewById(R.id.changeDateTVupd);


    }

    public void showChangeDate(int year, int month, int dayofmonth) {
        final DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int trueMonth = monthOfYear + 1;

                if (trueMonth < 10 && dayOfMonth>=10) {
                    String str = String.format("%02d", trueMonth);
                    eventDateTV.setText("Date: " + dayOfMonth + " " + str + " " + year);

                }else if (dayOfMonth<10 && trueMonth<10)
                {String strMonth = String.format("%02d", trueMonth);
                    String strDay =String.format("%02d", dayOfMonth);
                    eventDateTV.setText("Date: " + strDay + " " + strMonth + " " + year);
                }else if (dayOfMonth<10 && trueMonth>=10)
                {                    String strDay =String.format("%02d", dayOfMonth);
                    eventDateTV.setText("Date: " + strDay + " " + trueMonth + " " + year);

                }else
                {
                    eventDateTV.setText("Date: " + dayOfMonth + " " + trueMonth + " " + year);
                }
                date = LocalDate.of(year, trueMonth, dayOfMonth);

            }

        }, year, month, dayofmonth);
        StartTime.setTitle("Select Date");
        StartTime.show();

    }


    public void showChangeTime(int hours, int minute) {
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(Edit_Update_Activity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                min = selectedMinute;

                if (hour<10 && min<10)
                {   String hourStr = String.format("%02d", hour);
                    String minStr = String.format("%02d",min);
                    String allTime = hourStr + ":" + minStr;

                    eventTimeTV.setText("Time: " + hourStr + ":" + minStr);
                    time = LocalTime.parse(allTime, DateTimeFormatter.ISO_TIME);


                }else if(hour<10 && min>=10)
                {
                    String hourStr = String.format("%02d", hour);
                    String allTime = hourStr + ":" + min;

                    eventTimeTV.setText("Time: " + hourStr + ":" + min);
                    time = LocalTime.parse(allTime, DateTimeFormatter.ISO_TIME);
                }else if (hour >=10 && min<10)
                {
                    String minStr = String.format("%02d",min);

                    String allTime = hour + ":" + minStr;

                    eventTimeTV.setText("Time: " + hour + ":" + minStr);
                    time = LocalTime.parse(allTime, DateTimeFormatter.ISO_TIME);


                }else
                {
                    String allTime = hour + ":" + min;

                    eventTimeTV.setText("Time: " + hour + ":" + min);
                    time = LocalTime.parse(allTime, DateTimeFormatter.ISO_TIME);

                }




            }
        }, hours, minute, true);//Yes 24 hour time
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    void getSetIntentData()
    {   if (getIntent().hasExtra("id")&& getIntent().hasExtra("title") && getIntent().hasExtra("comment") && getIntent().hasExtra("date") && getIntent().hasExtra("time"))
    {
        id_row = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        comment = getIntent().getStringExtra("comment");
        date = LocalDate.parse(getIntent().getStringExtra("date"));
        time = LocalTime.parse(getIntent().getStringExtra("time"));

        eventNameET.setText(title);
        eventCommentET.setText(comment);
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(date));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedShortTime(time));


    }


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);


        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void updEventAction(View view) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(Edit_Update_Activity.this);
        if (getIntent().hasExtra("id"))
        {
            id_row = getIntent().getStringExtra("id");
        }
        String eventName = eventNameET.getText().toString();
        String eventComment = eventCommentET.getText().toString();
        myDB.updateData(id_row,eventName, eventComment, date, time);

        Intent i1 = new Intent(Edit_Update_Activity.this,MainActivity.class);

        finish();
        overridePendingTransition(0, 0);
        startActivity(i1);
        overridePendingTransition(0, 0);


    }
}