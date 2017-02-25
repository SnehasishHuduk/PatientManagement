package com.example.samin.paitientmanagement.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.other.Show_appointment_data_item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Show_Appointments extends AppCompatActivity {

    private RecyclerView recyclerView;
    public FirebaseAuth firebaseAuth;
    public String UserID;
    DatabaseReference myRef;
    private FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder> mFirebaseAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_appointment_layout);


        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.show_appointment_appBarLayout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Your Appointments");

        Log.d("LOGGED", "----------------------- START NEW --------------------------" );

        //Send a Query to database
        firebaseAuth = FirebaseAuth.getInstance();
        //database = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Log.d("LOGGED "," USER "+ user.toString());
        UserID=user.getEmail().replace("@","").replace(".","");
        myRef = FirebaseDatabase.getInstance().getReference("User_Appointment").child(UserID);
        Log.d("LOGGED", " MyRef-Database-Path " + myRef.toString());

        //Recycler View
        recyclerView= (RecyclerView) findViewById(R.id.show_appointment_recycler_view);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(mFirebaseAdapter);
        Log.d("LOGGED", "ADAPTER 1st " + mFirebaseAdapter);
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
            Log.d("LOGGED", " Setting Text as Doctor Name ");
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
        Log.d("LOGGED", "IN onStart " );
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_appointment_data_item, MyViewHolder>( Show_appointment_data_item.class,R.layout.show_appointment_single_item,MyViewHolder.class,myRef)
        {

//            @Override
//            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                Log.d("LOGGED", " onStart-Called 4m inside " );
//
//                ViewGroup view = (ViewGroup)
//                        LayoutInflater.from(parent.getContext())
//                                .inflate(R.layout.show_appointment_single_item, parent, false);
//                return super.onCreateViewHolder(view, viewType);
//            }

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
        Log.d("LOGGED", "ADAPTER 2nd " + mFirebaseAdapter);
        recyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_appointment_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            case R.id.action_refresh:
                recyclerView.invalidate();
                onStart();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
