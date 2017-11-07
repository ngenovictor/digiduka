package com.digiduka.digiduka.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.MainActivityFragmentsAdapter;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.User;
import com.digiduka.digiduka.utils.Constants;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private MainActivityFragmentsAdapter mainActivityFragmentsAdapter;
    private ViewPager mainActivityViewPager;
    private TabLayout homeNavTabLayout;
    private static final int RC_SIGN_IN = 123;
    private ArrayList<Category> categories = new ArrayList<>();
    private FirebaseUser loggedInUser;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private TextView userName;
    private TextView email;
    private ImageView avatar;
    private NavigationView navigationView;
    private View headerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        getSupportActionBar().setTitle(R.string.app_name);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            // not signed in

            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(
                                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))

                            .build(),
                    RC_SIGN_IN);

        } else {
            mAuth = FirebaseAuth.getInstance();

            loggedIn();
        }
    }

    public void loggedIn() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getCategories();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.username);
        email = headerView.findViewById(R.id.useremail);
        avatar = headerView.findViewById(R.id.imageView);
        if (mAuth.getCurrentUser().getDisplayName() != null) {
            userName.setText(mAuth.getCurrentUser().getDisplayName());
            email.setText(mAuth.getCurrentUser().getEmail());
            Picasso.with(getApplicationContext()).load(mAuth.getCurrentUser().getPhotoUrl()).into(avatar);

        } else {
            userName.setText("");
        }


        mainActivityViewPager = findViewById(R.id.mainActivityViewPager);
        homeNavTabLayout = findViewById(R.id.homeNavTabLayout);
        homeNavTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mainActivityViewPager));
        mainActivityViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(homeNavTabLayout));


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.log_out:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(MainActivity.this, MainActivity.class));
                                finish();
                            }
                        });
                return true;
            case R.id.about:
                showAbout();
                break;
            case R.id.intro:
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
            case R.id.generateReports:
                Intent intent1 = new Intent(MainActivity.this, GenerateReportsActivity.class);
                startActivity(intent1);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.recordDebt) {
            Intent intent = new Intent(MainActivity.this, RecordDebtActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {
                FirebaseAuth auth = FirebaseAuth.getInstance();


                Toast.makeText(getApplicationContext(), "You are Signed in as: " + auth.getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
                Log.v("number", String.valueOf(auth.getCurrentUser().getPhoneNumber()));
                loggedIn();

            } else {
                finish();
            }
        }
    }

    public void showAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("About digiduka");
        builder.setMessage("Built by a great team. Victor Ng'eno, Elvis Mosoti and Morris Warachi.");
        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                    }
                });
        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    public void getCategories() {
        mAuth = FirebaseAuth.getInstance();

        loggedInUser = mAuth.getCurrentUser();

        reference = FirebaseDatabase.getInstance()
                .getReference(loggedInUser.getUid()).child(Constants.CATEGORY_DB_KEY);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    categories.add(data.getValue(Category.class));
                }
                mainActivityFragmentsAdapter = new MainActivityFragmentsAdapter(getSupportFragmentManager(), categories);

                mainActivityViewPager.setAdapter(mainActivityFragmentsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    //loggedIn();
}
