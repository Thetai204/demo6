package com.example.demo6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddHangLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_hang_layout);

        EditText ten = findViewById(R.id.edt_ten);
        EditText hang = findViewById(R.id.edt_hang);
        EditText nam = findViewById(R.id.edt_nam);
        EditText gia = findViewById(R.id.edt_gia);
        Button them = findViewById(R.id.btn_them);

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                 if(!ten.getText().toString().equals("")&&!hang.getText().toString().equals("")&&!nam.getText().toString().equals("")){

                     if (Integer.parseInt(nam.getText().toString())<1980||Integer.parseInt(nam.getText().toString())>2023) {
                         Toast.makeText(AddHangLayoutActivity.this, "Năm sai", Toast.LENGTH_SHORT).show();
                     } else if (Double.parseDouble(gia.getText().toString())<=0) {
                         Toast.makeText(AddHangLayoutActivity.this, "Giá không được thấp hơn 0 hoặc bằng không ", Toast.LENGTH_SHORT).show();
                     } else {
                         int a = Integer.parseInt(nam.getText().toString());
                         Double price = Double.parseDouble(gia.getText().toString());
                         hangHoa hh = new hangHoa(ten.getText().toString(),hang.getText().toString(),a,price);
                         intent.putExtra(MainActivity.KEY_DATA,hh);
                         setResult(1,intent);
                         finish();
                     }
                } else {
                   Toast.makeText(AddHangLayoutActivity.this, "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}