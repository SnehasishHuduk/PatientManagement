package com.example.samin.paitientmanagement.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samin.paitientmanagement.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PersonalInfo extends AppCompatActivity
{
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    ImageView imageView;
    TextView  tx_name,tx_email,tx_phone,tx_spec,personal_info_text,tv_make_appo;
    Button appointment_button;
    private EditText your_name,your_phone,your_appoint_date,your_appoint_reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_activity_main);

        your_name=(EditText)findViewById(R.id.appoint_Your_name);
        your_phone = (EditText)findViewById(R.id.appoint_Phone);
        your_appoint_date = (EditText)findViewById(R.id.appoint_Visit_Date);
        your_appoint_reason = (EditText)findViewById(R.id.appoint_Visit_reason);


        appointment_button= (Button) findViewById(R.id.makeappointment_button);
        personal_info_text= (TextView) findViewById(R.id.personal_info_txt);
        tv_make_appo= (TextView) findViewById(R.id.tv_make_appo);

        imageView = (ImageView) findViewById(R.id.image_id);
        imageView.setImageResource(getIntent().getIntExtra("image_id",00));

        tx_email =(TextView)findViewById(R.id.d_doctor_email);
        tx_phone=(TextView)findViewById(R.id.d_doctor_phone);
        tx_email.setText("Email : "+getIntent().getStringExtra("email"));
        tx_phone.setText("Phone : "+getIntent().getStringExtra("phone"));

        Typeface txt =Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-LightItalic.ttf");
        Typeface txt2 =Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-BoldItalic.ttf");
        tx_email.setTypeface(txt);
        tx_phone.setTypeface(txt);
        personal_info_text.setTypeface(txt2);
        tv_make_appo.setTypeface(txt2);




        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
        your_appoint_date.setText( sdf.format( new Date() ));



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra("name"));

        //dynamicToolbarColor();
        toolbarTextAppearance();
    }

//    private void dynamicToolbarColor() {
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.doctor_9);
//
//
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
//                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
//            }
//        });
//    }


    private void toolbarTextAppearance() {
        //        final Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/FrutigerLTStd-Light.otf");
//        collapsingToolbar.setCollapsedTitleTypeface(tf);
//        collapsingToolbar.setExpandedTitleTypeface(tf);
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-LightItalic.ttf");
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(font);
        collapsingToolbarLayout.setExpandedTitleTypeface(font);
    }



   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void make_appointment(View view) {
       // Toast.makeText(PersonalInfo.this, "Database Was Not Present", Toast.LENGTH_SHORT).show();

        String getName = your_name.getText().toString().trim();
        String getPhone = your_phone.getText().toString().trim();
        String getDate = your_appoint_date.getText().toString().trim();
        String getReason = your_appoint_reason.getText().toString().trim();
        if(TextUtils.isEmpty(getName)) {
            Toast.makeText(this, "Name Require", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(getPhone)) {
            Toast.makeText(this, "Phone No Require", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(getDate)) {
            Toast.makeText(this, "Date Require", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(getReason)) {
            Toast.makeText(this, "Reason Require", Toast.LENGTH_SHORT).show();
            return;
        }

        //Toast.makeText(this, "Details are : "+getName+" \n " + getPhone + "\n" + getDate + "\n" + getReason, Toast.LENGTH_LONG).show();



    }
}
