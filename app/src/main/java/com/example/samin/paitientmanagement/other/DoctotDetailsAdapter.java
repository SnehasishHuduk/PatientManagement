package com.example.samin.paitientmanagement.other;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.activity.PersonalInfo;

import java.util.ArrayList;

public class DoctotDetailsAdapter extends RecyclerView.Adapter<DoctotDetailsAdapter.DetailsViewHolder> {
    ArrayList<DoctorDetails> doctorDetailses= new ArrayList<>();
    Context ctx;

    public DoctotDetailsAdapter(ArrayList<DoctorDetails> doctotsdetails, Context ctx)
    {
        Log.d("Adapter","Constractor Called");
        this.doctorDetailses = doctotsdetails;
        this.ctx=ctx;
    }
    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Adapter","OnCreate View Holder Called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_doctor_layout,parent,false);
        DetailsViewHolder detailsViewHolder= new DetailsViewHolder(view,ctx,doctorDetailses);
        return detailsViewHolder;
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, int position) {
        Log.d("Adapter","OnBind View Holder Called");

        DoctorDetails details = doctorDetailses.get(position);
        Log.d(" "+ doctorDetailses.get(position),"OnBind View Holder Called");
        holder.doctor_image.setImageResource(details.getImage_id());
        holder.doctor_name.setText(details.getName());
        holder.doctor_email.setText(details.getEmail());
        //holder.doctor_phone.setText(details.getPhone());
        holder.doctor_spec.setText(details.getSpec());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        Log.d("Adapter","get Count Called");
        return doctorDetailses.size();
    }
    public static class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView doctor_image;
        TextView doctor_name,doctor_email,doctor_phone,doctor_spec;
        ArrayList<DoctorDetails> details = new ArrayList<DoctorDetails>();
        Context ctx;
        public DetailsViewHolder(View view,Context ctx,ArrayList<DoctorDetails> details) {
            super(view);
            this.details = details;
            this.ctx=ctx;
            view.setOnClickListener(this);
            Log.d("Adapter","DetailsViewHolder Called");
            doctor_image= (ImageView) view.findViewById(R.id.doctor_image);
            doctor_name=(TextView)view.findViewById(R.id.your_appointment);
            doctor_email=(TextView)view.findViewById(R.id.doctor_email);
           // doctor_phone=(TextView)view.findViewById(R.id.doctor_phno);
            doctor_spec=(TextView)view.findViewById(R.id.doctor_spec);
           // txt=(TextView)v.findViewById(R.id.doctor_email);

            Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/RobotoCondensed-Bold.ttf");
            Typeface font2 = Typeface.createFromAsset(ctx.getAssets(), "fonts/RobotoCondensed-LightItalic.ttf");
            doctor_name.setTypeface(font);
            doctor_email.setTypeface(font2);
            doctor_spec.setTypeface(font2);

        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            DoctorDetails details = this.details.get(position);
            Intent intent = new Intent(this.ctx, PersonalInfo.class);
            intent.putExtra("image_id",details.getImage_id());
            intent.putExtra("email",details.getEmail());
            intent.putExtra("name",details.getName());
            intent.putExtra("phone",details.getPhone());
            intent.putExtra("spec",details.getSpec());
            this.ctx.startActivity(intent);
        }
    }
}
