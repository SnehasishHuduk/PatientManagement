package com.example.samin.paitientmanagement.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samin.paitientmanagement.R;


public class PersonalInfo extends AppCompatActivity
{
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    ImageView imageView;
    TextView  tx_name,tx_email,tx_phone,tx_spec,personal_info_text,tv_make_appo;
    Button appointment_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_activity_main);
        
        
        appointment_button= (Button) findViewById(R.id.signup_button);
        personal_info_text= (TextView) findViewById(R.id.personal_info_txt);
        tv_make_appo= (TextView) findViewById(R.id.tv_make_appo);
        imageView = (ImageView) findViewById(R.id.image_id);
        tx_email =(TextView)findViewById(R.id.d_doctor_email);
        tx_phone=(TextView)findViewById(R.id.d_doctor_phone);
        imageView.setImageResource(getIntent().getIntExtra("image_id",00));
        tx_email.setText("Email : "+getIntent().getStringExtra("email"));
        tx_phone.setText("Phone : "+getIntent().getStringExtra("phone"));
        Typeface txt =Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-LightItalic.ttf");
        Typeface txt2 =Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-BoldItalic.ttf");
        tx_email.setTypeface(txt);
        tx_phone.setTypeface(txt);
        personal_info_text.setTypeface(txt2);
        tv_make_appo.setTypeface(txt2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra("name"));

        //dynamicToolbarColor();
        toolbarTextAppernce();
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


    private void toolbarTextAppernce() {
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
        return super.onOptionsItemSelected(item);
    }


    public void make_appointment(View view) {
        Toast.makeText(PersonalInfo.this, "Database Not Present", Toast.LENGTH_SHORT).show();
    }
}
