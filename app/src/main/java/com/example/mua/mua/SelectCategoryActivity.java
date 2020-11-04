package com.example.mua.mua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mua.R;

public class SelectCategoryActivity extends AppCompatActivity {


    TextView tv_makeup, tv_hairdo, tv_nailart, tv_henaart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        init_view();
        tv_makeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCategoryActivity.this, AddServiceActivity.class);
                intent.putExtra("category_id", "1");
                startActivity(intent);
            }
        });
        tv_hairdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCategoryActivity.this, AddServiceActivity.class);
                intent.putExtra("category_id", "2");
                startActivity(intent);
            }
        });
        tv_henaart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCategoryActivity.this, AddServiceActivity.class);
                intent.putExtra("category_id", "4");
                startActivity(intent);
            }
        });
        tv_nailart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCategoryActivity.this, AddServiceActivity.class);
                intent.putExtra("category_id", "3");
                startActivity(intent);
            }
        });
    }

    private void init_view() {
        tv_makeup = findViewById(R.id.tvMakeUp);
        tv_hairdo = findViewById(R.id.tvHairDo);
        tv_nailart = findViewById(R.id.tvNail);
        tv_henaart = findViewById(R.id.tvHena);
    }
}