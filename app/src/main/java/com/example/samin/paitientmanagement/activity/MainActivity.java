package com.example.samin.paitientmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samin.paitientmanagement.BuildConfig;
import com.example.samin.paitientmanagement.R;
import com.example.samin.paitientmanagement.fragment.AppointmentFragment;
import com.example.samin.paitientmanagement.fragment.BloodBankFragment;
import com.example.samin.paitientmanagement.fragment.HealthTipsFragment;
import com.example.samin.paitientmanagement.fragment.HomeFragment;
import com.example.samin.paitientmanagement.fragment.NotificationFragment;
import com.example.samin.paitientmanagement.fragment.PathologyLabsFragment;
import com.example.samin.paitientmanagement.fragment.PatientFragment;
import com.example.samin.paitientmanagement.fragment.ProfileFragment;
import com.example.samin.paitientmanagement.fragment.SettingsFragment;
import com.example.samin.paitientmanagement.other.CircleTransform;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    //private FloatingActionButton fab;
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_APPOINTMENT = "make appointment";
    private static final String TAG_PATIENT = "patient";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_HEALTH_TIPS = "health tips";
    private static final String TAG_PATHOLOGY_LABS = "pathology labs";
    private static final String TAG_BLOOD_BANKS = "blood banks";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    //private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private FirebaseAuth firebaseAuth;
    Firebase mRoofRef;
    FirebaseUser user;

    static public String UserID,LoggedIn_User_Email;
    AlertDialog.Builder builder;
    String check;
    String version;
    //static public String user_designation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneSignal.startInit(this).init();
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null)
        {
            //User NOT logged In
            finish();
            startActivity(new Intent(getApplicationContext(),login_activity.class));
        }


        user = firebaseAuth.getCurrentUser();
        UserID = user.getEmail().replace("@", "").replace(".", "");

        LoggedIn_User_Email = user.getEmail();


        OneSignal.sendTag("User_ID", LoggedIn_User_Email);



        mRoofRef = new Firebase("https://patient-management-11e26.firebaseio.com/").child("User_Details").child(UserID);
//        if(UserID.equals("saminali500gmailcom"))
//            user_designation ="Doctor";


            setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        version = BuildConfig.VERSION_NAME;
        //Shared Preference
        /**
         * Update - is the shared file name
         * check will get updated value iff no updated value found
         * it will take Base version as default value
         */
        SharedPreferences sharedPreferences = getSharedPreferences("Update", MODE_PRIVATE);
        check =sharedPreferences.getString("update_later",version);
        Log.d("LOGGED", " 1st Check value 4m Shared : " +check);

        //Async Task
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
       // fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        View navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.navBar_profile_image);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }



        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    navItemIndex = 8;
                    CURRENT_TAG = TAG_PROFILE;
                    loadHomeFragment();
            }
        });



    }



    private void loadNavHeader() {

        Glide.with(this)
                .load(R.drawable.nav_menu_header_bg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imgNavHeaderBg);


        Toast.makeText(MainActivity.this, "Welcome  !!  ", Toast.LENGTH_LONG).show();
        txtName.setText("Welcome,");


        mRoofRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, String> map = dataSnapshot.getValue(Map.class);

                String retrieve_name = map.get("Name");
                String retrieve_url = map.get("Image_URL");
                if (retrieve_name != null)
                {
                    if (retrieve_name.equals("Null"))
                        txtWebsite.setText(user.getEmail());
                    else
                        txtWebsite.setText(retrieve_name);
                }

                if (retrieve_url != null)
                {
                    if (retrieve_url.equals("Null"))
                    {
                        Glide.with(getApplicationContext())
                                .load(R.drawable.invalid_person_image)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imgProfile);
                    }
                    else
                    {
                        Glide.with(getApplicationContext())
                                .load(retrieve_url)
                                .crossFade()
                                .thumbnail(0.5f)
                                .bitmapTransform(new CircleTransform(getApplicationContext()))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imgProfile);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {   }
        });
        navigationView.getMenu().getItem(6).setActionView(R.layout.menu_dot);


       // final String version = BuildConfig.VERSION_NAME;
        mRoofRef = new Firebase("https://patient-management-11e26.firebaseio.com/Update_APK");
        mRoofRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.getValue(Map.class);
                final String retrieve_url = map.get("Download_URL");
                final String retrieve_version = map.get("Version");
                final String retrieve_changelog = map.get("Changelog");


                if (!check.equals(retrieve_version)) {
                    if (!retrieve_version.equals(version))
                    {

                        //Log.d("LOGGED", "Inside IF " +check);
                        builder = new AlertDialog.Builder(MainActivity.this);
                    //builder.setMessage("A latest Version is Available ").setCancelable(true)
                    builder.setMessage(Html.fromHtml(retrieve_changelog)).setCancelable(true)
                            .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(retrieve_url));
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "Check Settings to Update.", Toast.LENGTH_LONG).show();
                                    SharedPreferences sharedPreferences = getSharedPreferences("Update", MODE_PRIVATE);
                                    SharedPreferences.Editor mEditor = sharedPreferences.edit();
                                    mEditor.putString("update_later", retrieve_version);
                                    mEditor.apply();
                                    //Log.d("LOGGED", "Updated value " +retrieve_version);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.setTitle("Update Available ! ");
                    dialog.show();
                }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null)
        {
            drawer.closeDrawers();

            // show or hide the fab button
           // toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                return new HomeFragment();
            case 1:
                return new AppointmentFragment();
            case 2:
                return new PatientFragment();
            case 3:
                return new HealthTipsFragment();
            case 4:
                return new PathologyLabsFragment();
            case 5:
                return new BloodBankFragment();
            case 6:
                return new NotificationFragment();
            case 7:
                return new ProfileFragment();
            case 8:
                //settings fragment
                return new SettingsFragment();
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;

                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;

                    case R.id.nav_appointment:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_APPOINTMENT;
                        break;

                    case R.id.nav_patient:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_PATIENT;
                        break;

                    case R.id.nav_health:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_HEALTH_TIPS;
                        break;
                    case R.id.nav_pathology:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_PATHOLOGY_LABS;
                        break;
                    case R.id.nav_bloodbank:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_BLOOD_BANKS;
                        break;

                    case R.id.nav_notifications:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_NOTIFICATIONS;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;


                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0)
            {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
       // }

        //super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0 || navItemIndex==1 || navItemIndex==2 || navItemIndex==3 || navItemIndex==4 || navItemIndex==5 || navItemIndex==7 || navItemIndex==8) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 6) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,login_activity.class));
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
//    private void toggleFab() {
//        if (navItemIndex == 0)
//            fab.show();
//        else
//            fab.hide();
//    }
}
