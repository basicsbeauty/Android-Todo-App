package com.example.briankwon21.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends ActionBarActivity {
    EditText etEditItemName;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String name = getIntent().getStringExtra("name");
        pos = getIntent().getIntExtra("pos", 0);
        etEditItemName = (EditText) findViewById(R.id.etEditItemName);
        etEditItemName.setText(name);
    }

    public void onSave(View view) {
        String editItemText = etEditItemName.getText().toString();
        Intent data = new Intent();
        data.putExtra("name", editItemText);
        data.putExtra("pos", pos);
        data.putExtra("code", 200);
        setResult(200, data);
        finish();
    }

}
