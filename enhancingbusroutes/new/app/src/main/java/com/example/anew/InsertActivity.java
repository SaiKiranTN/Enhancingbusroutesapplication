package com.example.anew;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class InsertActivity extends AppCompatActivity {




    EditText busno;
    EditText route;
    EditText time;
    Button insert;
    String[] list;
    int i=0;

    DatabaseReference busDbRef; //database reference to get the data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        busno = findViewById(R.id.txt_busno);
        route = findViewById(R.id.txt_route);
        time = findViewById(R.id.txt_time);
        insert = findViewById(R.id.insert);


        busDbRef = FirebaseDatabase.getInstance("https://newdatabase-54573-default-rtdb.firebaseio.com").getReference("bus_route");

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertStudentData();
            }
        });


    }
    private void insertStudentData(){
        String sbusno = busno.getText().toString();
        String sroute = route.getText().toString();
        String stime = time.getText().toString();

        String id = busDbRef.push().getKey();

        user user =new user(id,sbusno,sroute,stime);
        assert id != null;
        busDbRef.child(id).setValue(user);
        Toast.makeText(InsertActivity.this,"data inserted",Toast.LENGTH_SHORT).show();
    }

}
