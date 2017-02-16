package com.example.samin.paitientmanagement.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.samin.paitientmanagement.Manifest;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.activity.MainActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
//import android.Manifest;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import static android.app.Activity.RESULT_OK;
import static com.example.samin.paitientmanagement.R.drawable.design_ic_visibility;
import static com.example.samin.paitientmanagement.R.drawable.invalid_person_image;


public class ProfileFragment extends Fragment{

    private EditText user_name, user_phone, user_address, user_age, user_height, user_weight, user_bloodgroup;
    private Button user_save_button;
    private ImageView user_image,change_image;

    private Firebase mRoofRef;
    private FirebaseAuth firebaseAuth;
    private Uri mImageUri=null;
    private DatabaseReference mdatabaseRef;

    private StorageReference mStorage,mStorageRef_mod;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog;
    public String UserID;
    Context context;

    public static final int READ_EXTERNAL_STORAGE = 0;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        context = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment

        user_name= (EditText) v.findViewById(R.id.profile_edit_name);
        user_phone= (EditText) v.findViewById(R.id.profile_edit_phone);
        user_address= (EditText) v.findViewById(R.id.profile_edit_address);
        user_age= (EditText) v.findViewById(R.id.profile_edit_age);
        user_height= (EditText) v.findViewById(R.id.profile_edit_height);
        user_weight= (EditText) v.findViewById(R.id.profile_edit_weight);
        user_bloodgroup= (EditText) v.findViewById(R.id.profile_edit_bloodgroup);

        user_image =(ImageView)v.findViewById(R.id.profile_edit_image);
        change_image =(ImageView)v.findViewById(R.id.change_user_image);


        //For image
        mProgressDialog = new ProgressDialog(getContext());

       // change_image.setOnClickListener(this);
        change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // if(checkS){}

                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Call for Permission", Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                }
                else {

                    callgalary();
                }
            }
        });


        user_save_button=(Button)v.findViewById(R.id.profile_save_button);

        mdatabaseRef= FirebaseDatabase.getInstance().getReference();


        FirebaseUser user = firebaseAuth.getCurrentUser();
        UserID=user.getEmail().replace("@","").replace(".","");
        mRoofRef= new Firebase("https://patient-management-11e26.firebaseio.com/"+UserID);

        mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://patient-management-11e26.appspot.com/");
        mStorageRef_mod = FirebaseStorage.getInstance().getReferenceFromUrl("gs://patient-management-11e26.appspot.com/"+UserID);

//        Glide.with(context)
//                .using(new FirebaseImageLoader())
//                .load(mStorageRef_mod)
//                //.error(mDefaultBackground)
//                .placeholder(R.drawable.invalid_person_image)
//                .crossFade()
//                .fitCenter()
//                .into(user_image);




        mRoofRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Map<String, String> map = dataSnapshot.getValue(Map.class);

                String retrieve_name= map.get("Name");
                String retrieve_phone= map.get("Phone");
                String retrieve_address= map.get("Address");
                String retrieve_age= map.get("Age");
                String retrieve_height= map.get("Height");
                String retrieve_weight= map.get("Weight");
                String retrieve_bloodgroup= map.get("Bloodgroup");
                String retrieve_url= map.get("Image_URL");


                //Drawable mDefaultBackground = ContextCompat.getDrawable(getActivity(), R.drawable.invalid_person_image);
                //Drawable mDefaultBackground = getResources().getDrawable(R.drawable.invalid_person_image);
                //Drawable mDefaultBackground = getActivity().getDrawable(R.drawable.invalid_person_image);
                    // Load the image using Glide


                //Picasso.with(getContext()).load(mStorage+UserID).into(user_image);
               // Glide.clear(user_image);
//                Glide.with(context)
//                            .using(new FirebaseImageLoader())
//                            .load(mStorageRef_mod)
//                            //.error(mDefaultBackground)
//                            .placeholder(R.drawable.invalid_person_image)
//                            .crossFade()
//                            .fitCenter()
//                            .into(user_image);

                if(retrieve_name!=null)
                {
                    user_name.setText(retrieve_name);
                }

                if(retrieve_phone!=null)
                {
                    user_phone.setText(retrieve_phone);
                }

                if(retrieve_address!=null)
                {
                    user_address.setText(retrieve_address);
                }

                if(retrieve_age!=null)
                {
                    user_age.setText(retrieve_age);
                }

                if(retrieve_height!=null)
                {
                    user_height.setText(retrieve_height);
                }

                if(retrieve_weight!=null)
                {
                    user_weight.setText(retrieve_weight);
                }

                if(retrieve_bloodgroup!=null)
                {
                    user_bloodgroup.setText(retrieve_bloodgroup);
                }

                if(retrieve_url!=null)
                {
                    Picasso.with(getContext()).load(retrieve_url).into(user_image);
                }
                else
                {
                    Picasso.with(context).load(R.drawable.invalid_person_image).into(user_image);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        user_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Uploading Details....");
                mProgressDialog.show();
                final String mName=user_name.getText().toString();
                final String mPhone=user_phone.getText().toString();
                final String mAddress=user_address.getText().toString();
                final String mAge=user_age.getText().toString();
                final String mHeight=user_height.getText().toString();
                final String mWeight=user_weight.getText().toString();
                final String mBloodgroup=user_bloodgroup.getText().toString();
                Firebase childRef_name = mRoofRef.child("Name");
                childRef_name.setValue(mName);

                Firebase childRef_phone = mRoofRef.child("Phone");
                childRef_phone.setValue(mPhone);

                Firebase childRef_address = mRoofRef.child("Address");
                childRef_address.setValue(mAddress);

                Firebase childRef_age = mRoofRef.child("Age");
                childRef_age.setValue(mAge);

                Firebase childRef_height = mRoofRef.child("Height");
                childRef_height.setValue(mHeight);

                Firebase childRef_weight = mRoofRef.child("Weight");
                childRef_weight.setValue(mWeight);

                Firebase childRef_bloodgroup = mRoofRef.child("Bloodgroup");
                childRef_bloodgroup.setValue(mBloodgroup);
                mProgressDialog.dismiss();


            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_INTENT && resultCode== RESULT_OK) {

            mImageUri = data.getData();
            user_image.setImageURI(mImageUri);

            StorageReference filePath= mStorage.child("User_Images").child(mImageUri.getLastPathSegment());

            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgressDialog.setMessage("Uploading Details....");
                    mProgressDialog.show();
                    Uri downloadUri  = taskSnapshot.getDownloadUrl();



                    mRoofRef.child("Image_URL").setValue(downloadUri.toString());

                    //Picasso.with(getContext()).load(downloadUri).into(user_image);
                    Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            });

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE:
                Toast.makeText(getContext(), "Call Req Prmssn", Toast.LENGTH_SHORT).show();
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getContext(), "Inside If", Toast.LENGTH_SHORT).show();
                    callgalary();
        }
        Toast.makeText(getContext(), "Outside If", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Glide.clear(user_image);
        Glide.get(getActivity()).clearMemory();

    }


    private void callgalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }
}
