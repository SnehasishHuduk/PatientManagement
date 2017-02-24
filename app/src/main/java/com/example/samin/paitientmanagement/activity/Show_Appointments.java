package com.example.samin.paitientmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.samin.paitientmanagement.R;
//import com.example.samin.paitientmanagement.other.Show_appointment_data_adaptor;
import com.example.samin.paitientmanagement.other.Show_appointment_data_item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Show_Appointments extends AppCompatActivity {


    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;

    public FirebaseAuth firebaseAuth;
    public String UserID;
    DatabaseReference myRef;
    private FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder>
            mFirebaseAdapter;
    Button refresh_button;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_appointment_layout);
        refresh_button = (Button)findViewById(R.id.show_appointment_refresh_button);

        Log.d("LOGGED", "----------------------- START NEW --------------------------" );

        //Send a Query to database
        firebaseAuth = FirebaseAuth.getInstance();
        //database = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Log.d("LOGGED","USER"+ user.toString());
        UserID=user.getEmail().replace("@","").replace(".","");
        myRef = FirebaseDatabase.getInstance().getReference("User_Appointment").child(UserID);
        Log.d("LOGGED", "MyRef-Database-Path" + myRef.toString());

        //Recycler View
        recyclerView= (RecyclerView) findViewById(R.id.show_appointment_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("LOGGED", "RecyclerView" + recyclerView.toString());

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void refresh_layout(View view) {
        Log.d("LOGGED", "Refresh - OnClick Called");
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }


    //View Holder For Recycler View
 public static class MyViewHolder extends RecyclerView.ViewHolder{
    View mView;
    private final TextView post_doctor_name,post_doctor_email,post_doctor_phone,post_patient_name,post_patient_phone,post_appointment_date,post_appointment_reason;

    public MyViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        post_doctor_name = (TextView)mView.findViewById(R.id.fetch_doctor_name);
        post_doctor_email = (TextView)mView.findViewById(R.id.fetch_doctor_email);
        post_doctor_phone = (TextView)mView.findViewById(R.id.fetch_doctor_phone);
        post_patient_name = (TextView)mView.findViewById(R.id.fetch_patient_name);
        post_patient_phone = (TextView)mView.findViewById(R.id.fetch_patient_phone);
        post_appointment_date = (TextView)mView.findViewById(R.id.fetch_Appointment_date);
        post_appointment_reason = (TextView)mView.findViewById(R.id.fetch_Appointment_reason);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("http://www.google.co.in/"));
//                Intent browserChooserIntent = Intent.createChooser(browserIntent, "Choose browser of your choice");
//                v.getContext().startActivity(browserChooserIntent);
//            }
//        });
    }

        private void setAppointment_Doctor_Name(String title){
        Log.d("LOGGED", "Setting Text as Doctor Name ");
        post_doctor_name.setText(title);
    }

        private void setAppointment_Doctor_Phone(String title){
        Log.d("LOGGED", "Setting Text as Doctor Name ");
        post_doctor_phone.setText(title);
    }

        private void setAppointment_Doctor_Email(String title){
        Log.d("LOGGED", "Setting Text as Doctor Name ");
        post_doctor_email.setText(title);
    }

        private void setAppointment_Patient_Name(String title){
        Log.d("LOGGED", "Setting Text as Doctor Name ");
        post_patient_name.setText(title);
    }

        private void setAppointment_Patient_Phone(String title){
        Log.d("LOGGED", "Setting Text as Doctor Name ");
        post_patient_phone.setText(title);
    }

        private void setAppointment_Date(String title){
        Log.d("LOGGED", "Setting Text as Doctor Name ");
        post_appointment_date.setText(title);
    }

        private void setAppointment_Reason(String title){
        Log.d("LOGGED", "Setting Text as Doctor Name ");
        post_appointment_reason.setText(title);
    }

  }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LOGGED", "onResume will call 4m onStart" );
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder>( Show_appointment_data_item.class,R.layout.show_appointment_single_item,MyViewHolder.class,myRef)
        {

            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Log.d("LOGGED", "onResume-Called 4m inside" );

                ViewGroup view = (ViewGroup)
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.show_appointment_single_item, parent, false);
                return super.onCreateViewHolder(view, viewType);
            }

            @Override
            public void populateViewHolder( MyViewHolder viewHolder, Show_appointment_data_item model,int position)
            {
                viewHolder.setAppointment_Doctor_Name(model.getAppointment_Doctor_Name());
                viewHolder.setAppointment_Doctor_Email(model.getAppointment_Doctor_Email());
                viewHolder.setAppointment_Doctor_Phone(model.getAppointment_Doctor_phone());
                viewHolder.setAppointment_Patient_Name(model.getPatient_Name());
                viewHolder.setAppointment_Patient_Phone(model.getPatient_Phone());
                viewHolder.setAppointment_Date(model.getAppointment_Date());
                viewHolder.setAppointment_Reason(model.getAppointment_Reason());

                Log.d("LOGGED", "populateViewHolder: Called ");
            }
        };

        Log.d("LOGGED", "Setting Adapter ");
        Log.d("LOGGED","Checking myRef"+ myRef.toString());
        recyclerView.setAdapter(mFirebaseAdapter);

    }
}