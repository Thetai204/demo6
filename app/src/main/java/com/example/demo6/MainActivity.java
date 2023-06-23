package com.example.demo6;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<hangHoa> list = new ArrayList<>();
    Adapter_hang adapter_hang ;
    public  static String KEY_DATA = "data";
    public  static String KEY_FILE = "data.txt";
    public void ghi (){
        try {
            FileOutputStream fileOutputStream = openFileOutput(KEY_FILE,MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (Exception e){

        }

    }
    public void doc(){
        try {
            FileInputStream fileInputStream = openFileInput(KEY_FILE);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            list = (ArrayList<hangHoa>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }catch (Exception e){

        }

    }
    ActivityResultLauncher getData = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==1){
                        Intent intent = result.getData();
                        hangHoa hh = (hangHoa) intent.getSerializableExtra(KEY_DATA);

                        list.add(hh);
                        ghi();
                        adapter_hang.notifyDataSetChanged();
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv_hh = findViewById(R.id.lv_list);
        Toolbar tb_menu = findViewById(R.id.tb_toolbar);
        setSupportActionBar(tb_menu);
        getSupportActionBar().setTitle("Danh sách hàng hóa");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        doc();
        if (list.size()==0){
            list.add(new hangHoa("Ferrari 458","Ferrari ",2021,9780000000.0));
            list.add(new hangHoa("Audi R8","Audi",2023,4500000000.0));
            list.add(new hangHoa("Rolls Royce Dawn ","Rolls-Royce",2023,360000000.0));
            list.add(new hangHoa("Bugatti Chiron Profilée","Bugatti",2016,9800000.0));
            list.add(new hangHoa("Peugeot 406 Coupe","Pininfarina",1997,4000.0));
        }

        adapter_hang = new Adapter_hang(list, MainActivity.this);
        lv_hh.setAdapter(adapter_hang);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        } else if (item.getItemId()==R.id.menu_add) {
            Intent intent = new Intent(MainActivity.this,AddHangLayoutActivity.class);
            getData.launch(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public class Adapter_hang extends BaseAdapter {
ArrayList <hangHoa > list ;
Activity activity ;

        public Adapter_hang() {
        }

        public Adapter_hang(ArrayList<hangHoa> list, Activity activity) {
            this.list = list;
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_hang,parent,false);
            hangHoa hh = list.get(position);
            TextView ten = convertView.findViewById(R.id.txt_ten);
            TextView hang = convertView.findViewById(R.id.txt_hang);
            TextView nam = convertView.findViewById(R.id.txt_nam);
            TextView gia = convertView.findViewById(R.id.txt_gia);
            Button xoa = convertView.findViewById(R.id.btn_xoa);


            ten.setText(hh.getTen());
            hang.setText(hh.getHang());
            nam.setText(hh.getNam()+"");
            gia.setText(hh.getGia()+"");

            notifyDataSetChanged();

            xoa.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setIcon(android.R.drawable.ic_menu_help);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xóa");
                    builder.setCancelable(true);


                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            list.remove(position);
                            ghi();
                            Toast.makeText(activity, "Đã xóa", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }

                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(activity, "Đã hủy thao tác xóa", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog =  builder.create();

                    alertDialog.show();



                }
            });
            return convertView;
        }
    }


}