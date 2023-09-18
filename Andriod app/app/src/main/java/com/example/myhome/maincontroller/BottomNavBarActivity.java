
package com.example.myhome.maincontroller;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.myhome.authentication.ChangePasswordActivity;
import com.example.myhome.authentication.EditProfileActivity;
import com.example.myhome.faq.FaqActivity;
import com.example.myhome.miscellaneous.AutoOnOffActivity;
import com.example.myhome.R;
import com.example.myhome.authentication.LoginActivity;
import com.example.myhome.electricity.TabLayoutMeterFragment;
import com.example.myhome.miscellaneous.PrivacyPolicyActivity;
import com.example.myhome.miscellaneous.UserIDAvtivity;
import com.example.myhome.reportissue.ReportProblemActivity;
import com.example.myhome.miscellaneous.SettingsActivity;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class BottomNavBarActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private MediaPlayer mp;
    private FirebaseAuth firebaseAuth;
    private BubbleNavigationLinearView bubbleNavigationLinearView;
    private SwitchCompat drawerSwitch;

    private Fragment fragment1 = new DashboardFragment();
    private Fragment fragment3 = new TabLayoutMeterFragment();
    private FragmentManager fm = getSupportFragmentManager();
    private Fragment active = fragment1;
    private Advance3DDrawerLayout drawer;
    private Toolbar toolbar;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav_bar);

        mp = MediaPlayer.create(BottomNavBarActivity.this, R.raw.buttonsound);

        toolbar = findViewById(R.id.toolbarNav);
        bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_view_linear);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (Advance3DDrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.setViewScale(GravityCompat.START, 0.96f);
        drawer.setRadius(GravityCompat.START, 20);
        drawer.setViewElevation(GravityCompat.START, 8);
        drawer.setViewRotation(GravityCompat.START, 15);
        drawer.setElevation(0);

        firebaseAuth = FirebaseAuth.getInstance();


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }

        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").commit();

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (view.getId()) {
                    case R.id.l_item_home:
                       fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        toolbar.setTitle("DASHBOARD");
                        mp.start();
                        break;

                    case R.id.l_item_elect:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        toolbar.setTitle("ELECTRICITY");
                        mp.start();
                        break;

                }
        }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_profile:
                Intent prof = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(prof);
                mp.start();
                break;
            case R.id.nav_auto:
                Intent auto = new Intent(getApplicationContext(), AutoOnOffActivity.class);
                startActivity(auto);
                mp.start();
                break;
            case R.id.nav_settings:
                Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settings);
                mp.start();
                break;
            case R.id.nav_cp:
                Intent cp = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(cp);
                mp.start();
                break;
            case R.id.nav_faq:
                Intent faq = new Intent(getApplicationContext(), FaqActivity.class);
                startActivity(faq);
                mp.start();
                break;
            case R.id.nav_reportbug:
                Intent reportbug = new Intent(getApplicationContext(), ReportProblemActivity.class);
                startActivity(reportbug);
                mp.start();
                break;
            case R.id.nav_pp:
                Intent pp = new Intent(getApplicationContext(), PrivacyPolicyActivity.class);
                startActivity(pp);
                mp.start();
                break;

            case R.id.nav_userid:
                Intent userid = new Intent(getApplicationContext(), UserIDAvtivity.class);
                startActivity(userid);
                mp.start();
                break;




            case R.id.nav_logout:
                MaterialDialog mDialog = new MaterialDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Do you really want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Logout", R.mipmap.logout, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                firebaseAuth.signOut();
                                finish();
                                mp.start();
                                dialogInterface.dismiss();
                                startActivity(new Intent(BottomNavBarActivity.this, LoginActivity.class));

                            }

                        })
                        .setNegativeButton("Cancel", R.mipmap.close, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                DynamicToast.makeError(getApplicationContext(), "CANCELLED", 10).show();
                                mp.start();
                                dialogInterface.dismiss();
                            }
                        })
                        .build();

                mDialog.show();

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


}