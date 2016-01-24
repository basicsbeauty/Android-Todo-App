package com.example.briankwon21.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class EditItemActivity extends ActionBarActivity {
    EditText etEditItemName;
    int pos;
    long itemId;
    String itemText;

    private TextView textDate;
    private DatePicker dpDueDate;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        setContentView(R.layout.activity_edit_item);
        pos = getIntent().getIntExtra("pos", 0);
        itemId = getIntent().getLongExtra("itemId", 1);
        itemText = getIntent().getStringExtra("itemText");
        year = getIntent().getIntExtra("year", 0);
        month = getIntent().getIntExtra("month", 0);
        day = getIntent().getIntExtra("day", 0);

        etEditItemName = (EditText) findViewById(R.id.etEditItemName);
        etEditItemName.setText(itemText);
        if (year == 0 || day == 0) {
            setDate(currentYear, currentMonth, currentDay);
        }
        else {
            setDate(year, month, day);
        }
    }

    public void onSave(View view) {
        String editItemText = etEditItemName.getText().toString();
        Intent data = new Intent();
        data.putExtra("pos", pos);
        data.putExtra("itemId", itemId);
        data.putExtra("itemText", editItemText);
        data.putExtra("year", year);
        data.putExtra("month", month);
        data.putExtra("day", day);
        data.putExtra("code", 200);
        setResult(200, data);
        finish();
    }

    public void setDate(int newYear, int newMonth, int newDay) {
        dpDueDate = (DatePicker) findViewById(R.id.dpDueDate);
        year = newYear;
        month = newMonth;
        day = newDay;
        dpDueDate.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int newYear, int newMonth, int newDay) {
                year = newYear;
                month = newMonth;
                day = newDay;
            }
        });
    }

}
