package com.example.samin.paitientmanagement.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.other.DoctorDetails;
import com.example.samin.paitientmanagement.other.DoctotDetailsAdapter;

import java.util.ArrayList;

public class AppointmentFragment extends Fragment {

    private Spinner spinner;
    TextView txt;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DoctorDetails> list= new ArrayList<DoctorDetails>();
    int[] image_ids={R.drawable.doctor_1,R.drawable.doctor_2,R.drawable.doctor_3,R.drawable.doctor_4,
            R.drawable.doctor_5,R.drawable.doctor_6,R.drawable.doctor_7,R.drawable.doctor_8,R.drawable.doctor_9,
            R.drawable.doctor_10,R.drawable.doctor_11};

    String [] name,email,phone,spec;

    public AppointmentFragment() {
        // Required empty public constructor
        Log.d("Fragment","AppointmentFragment Called");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment","onCreate Called");
        name = getResources().getStringArray(R.array.doctor_name);
        email = getResources().getStringArray(R.array.doctor_email);
       phone = getResources().getStringArray(R.array.doctor_phone);
        spec = getResources().getStringArray(R.array.doctor_qualification);


        int count = 0;
        for(String Name : name)
        {
            DoctorDetails doctorDetails = new DoctorDetails(image_ids[count],Name,email[count],phone[count],spec[count]);
            count++;
            list.add(doctorDetails);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_appointment, container, false);
        Log.d("Fragment","onCreateView Called");


        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        adapter = new DoctotDetailsAdapter(list,getContext());
        recyclerView.setAdapter(adapter);

//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(getActivity());
       recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        txt=(TextView)v.findViewById(R.id.tv_department);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        txt.setTypeface(font);

       spinner = (Spinner) v.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            String firstItem = String.valueOf(spinner.getSelectedItem());
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (firstItem.equals(String.valueOf(spinner.getSelectedItem())))
                {
                    // ToDo when first item is selected
                } else
                {
                    Toast.makeText(parent.getContext(),
                            "Doctors in " + parent.getItemAtPosition(position).toString(),
                            Toast.LENGTH_SHORT).show();
                    // Todo when item is selected by the user
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        return v;
    }
}

