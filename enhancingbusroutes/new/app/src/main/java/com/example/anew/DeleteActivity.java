package com.example.anew;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btn;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<user> list;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView =findViewById(R.id.userlist);
        btn = findViewById(R.id.home_btn_logout);


        database = FirebaseDatabase.getInstance("https://newdatabase-54573-default-rtdb.firebaseio.com").getReference("bus_route");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter =new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        int pos = position;

                        user user=list.get(position);
                        String no= user.key;

                            DatabaseReference dbref= FirebaseDatabase.getInstance("https://newdatabase-54573-default-rtdb.firebaseio.com").getReference("bus_route");


                            dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        // remove the value at reference
                                    Toast.makeText(DeleteActivity.this, ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
//                                        dataSnapshot.getRef(position).child(dataSnapshot.getKey()).removeValue();
//                                    Toast.makeText(DeleteActivity.this, ""+key, Toast.LENGTH_SHORT).show();
//                                    }
                                    deleteRecord(no);
//                                    dbref.child("bus_route").child(user.key).removeValue();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    user user = new user();
                    user.key=dataSnapshot.child("key").getValue().toString();
                    user.time=dataSnapshot.child("time").getValue().toString();
                    user.route=dataSnapshot.child("route").getValue().toString();
                    user.no=dataSnapshot.child("no").getValue().toString();
                    list.add(user);

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteActivity.this,"Fail to get the data",Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeleteActivity.this,LoginActivity.class));
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void deleteRecord(String no) {
        DatabaseReference def =FirebaseDatabase.getInstance().getReference("bus_route").child(no);
        Task<Void> mTask = def.removeValue();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DeleteActivity.this,"deleted" ,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DeleteActivity.this,DeleteActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DeleteActivity.this,"Error in deleting record" ,Toast.LENGTH_SHORT).show();
            }
        });
    }



}