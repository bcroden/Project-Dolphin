package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Class;

import java.util.LinkedList;
import java.util.List;

public class SaveClassActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_save);

        ((TextView) findViewById(R.id.simple_save_title_view)).setText("Class Title");
        ((TextView) findViewById(R.id.simple_save_weight_view)).setText("Class Weight");

        long db_id = getIntent().getLongExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, -1);
        if(db_id > 0) {
            _class = DBAccessHelper.getInstance(getApplicationContext()).getClassByID(db_id);

            EditText title = (EditText) findViewById(R.id.simple_save_title_field);
            EditText weight = (EditText) findViewById(R.id.simple_save_weight_field);

            title.setText(_class.getTitle());
            weight.setText(_class.getWeightAsString());
        } else {
            _class = new Class("Empty class", 1.0);
        }
    }

    public void onSimpleSave(View view) {
        List<String> errors = new LinkedList<>();

        SaveErrorChecker.processTitle(_class, (EditText) findViewById(R.id.simple_save_title_field), errors);
        SaveErrorChecker.processWeight(_class, (EditText) findViewById(R.id.simple_save_weight_field), errors);

        if(!SaveErrorChecker.shouldContinue(errors, this))
            return;

        DBAccessHelper.getInstance(getApplicationContext()).putClass(_class);

        Intent intent = new Intent(this, ClassViewActivity.class);
        startActivity(intent);
    }


    private Class _class;
}
