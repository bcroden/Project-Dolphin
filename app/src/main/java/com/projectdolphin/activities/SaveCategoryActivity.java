package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Category;
import com.projectdolphin.data.model.Class;

import java.util.LinkedList;
import java.util.List;

public class SaveCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_save);

        ((TextView) findViewById(R.id.simple_save_title_view)).setText("Category Title");
        ((TextView) findViewById(R.id.simple_save_weight_view)).setText("Category Weight");

        long catDB_ID = getIntent().getLongExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, -1);
        long parDB_ID = getIntent().getLongExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, -1);
        if(catDB_ID >= 0) {
            isEditMode = true;
            category = DBAccessHelper.getInstance(getApplicationContext()).getCategoryByID(catDB_ID);
        } else if(parDB_ID >= 0) {
            isEditMode = false;
            category = new Category(parDB_ID, "Empty Category", 1.0);
        }
    }

    public void onSimpleSave(View view) {
        List<String> errors = new LinkedList<>();

        SaveErrorChecker.processTitle(category, (EditText) findViewById(R.id.simple_save_title_field), errors);
        SaveErrorChecker.processWeight(category, (EditText) findViewById(R.id.simple_save_weight_field), errors);

        if(!SaveErrorChecker.shouldContinue(errors, this))
            return;

        DBAccessHelper.getInstance(getApplicationContext()).putCategory(category);

        if(!isEditMode) {
            Class _class = DBAccessHelper.getInstance(getApplicationContext()).getClassByID(category.getParentDB_ID());
            _class.getCategoryIDs().add(category.getDB_ID());
            DBAccessHelper.getInstance(getApplicationContext()).putClass(_class);
        }

        Intent intent = new Intent(this, CategoryViewActivity.class);
        intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, category.getParentDB_ID());
        startActivity(intent);
    }

    private boolean isEditMode;
    private Category category;
}