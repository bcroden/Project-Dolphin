package com.projectdolphin.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Class;

import java.util.LinkedList;
import java.util.List;

public class SaveClassActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_class);

        long db_id = getIntent().getLongExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, -1);
        if(db_id > 0) {
            _class = DBAccessHelper.getInstance(getApplicationContext()).getClassByID(db_id);

            EditText title = (EditText) findViewById(R.id.class_title);
            EditText weight = (EditText) findViewById(R.id.class_weight);

            title.setText(_class.getTitle());
            weight.setText(_class.getWeightAsString());
        } else {
            _class = new Class("Empty class", 1.0);
        }
    }

    //Get the content
    public void onAddClass(View view) {
        List<String> errors = new LinkedList<>();

        String title = ((EditText) findViewById(R.id.class_title)).getText().toString();
        if(title == null || title.equals(""))
            errors.add("Please enter a title");

        double weight;
        try {
            weight = Double.parseDouble(((EditText) findViewById(R.id.class_weight)).getText().toString());
            if(weight > 0)
                _class.setWeight(weight);
            else
                errors.add("Weight must be a positive number");
        } catch (NumberFormatException e) {
            errors.add("Weight must be a number");
        }

        if(errors.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < errors.size(); i++) {
                if(i != 0)
                    sb.append("\n");
                sb.append(errors.get(i));
            }

            new AlertDialog.Builder(this)
                    .setTitle("Data Format Error")
                    .setMessage(sb.toString())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
            return;

        }

        _class.setTitle(title);

        DBAccessHelper.getInstance(getApplicationContext()).putClass(_class);

        Intent intent = new Intent(this, ClassViewActivity.class);
        startActivity(intent);
    }


    private Class _class;
}
