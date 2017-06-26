package com.tble.brgo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import layout.Calendar;
import layout.Handbook;
import layout.News;
import layout.Planner;
import layout.StudentID;
import layout.Temp;
import layout.Websites;
import layout.About;

public class BRGO extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brgo);
        News initial = new News();
        FragmentTransaction transfer = getSupportFragmentManager().beginTransaction();
        transfer.replace(R.id.fragmentcontainer, initial).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextAppearance(R.style.itemFont);
        getWindow().setStatusBarColor(Color.parseColor("#1995AD"));
        updateTitle();

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
         getMenuInflater().inflate(R.menu.brgo, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void updateTitle(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        switch(sharedPref.getInt("School",14273))
        {
            case 14273:
                getSupportActionBar().setTitle("High School");
                break;
            case 14276:
                getSupportActionBar().setTitle("Middle School");
                break;
            case 14274:
                getSupportActionBar().setTitle("Hillside");
                break;
            case 14271:
                getSupportActionBar().setTitle("Eisenhower");
                break;
            case 14278:
                getSupportActionBar().setTitle("Van Holten");
                break;
            case 14277:
                getSupportActionBar().setTitle("Milltown");
                break;
            case 14275:
                getSupportActionBar().setTitle("J.F.K.");
                break;
            case 14272:
                getSupportActionBar().setTitle("Hamilton");
                break;
            case 14269:
                getSupportActionBar().setTitle("Crim");
                break;
            case 14268:
                getSupportActionBar().setTitle("Bradely Gardens");
                break;
            case 14264:
                getSupportActionBar().setTitle("Adamsville");
                break;
            default:
                getSupportActionBar().setTitle("High School");
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        //noinspection SimplifiableIfStatement
        if (id == R.id.S14273) {
         editor.putInt("School", 14273);
            editor.commit();
            updateTitle();
            return true;
        }
        else if(id == R.id.S14276){
            editor.putInt("School", 14276);
            editor.commit();
            updateTitle();
            return true;
        }
        else if(id == R.id.S14274){
            editor.putInt("School", 14274);
            editor.commit();
            updateTitle();
            return true;
        }
        else if(id == R.id.S14271){
            editor.putInt("School", 14271);
            editor.commit();
            updateTitle();
            return true;
        }
        else if(id == R.id.S14278){
            editor.putInt("School", 14278);
            editor.commit();
            updateTitle();
            return true;
        }
        else if(id == R.id.S14277){
            editor.putInt("School", 14277);
            editor.commit();
            updateTitle();
            return true;
        }
        else if(id == R.id.S14275){
            editor.putInt("School", 14275);
            editor.commit();
            updateTitle();
            return true;
        }
        else if(id == R.id.S14272){
            editor.putInt("School", 14272);
            editor.commit();
            updateTitle();
            return true;
        }
        else if(id == R.id.S14269){
            editor.putInt("School", 14269);
            editor.commit();
            updateTitle();
            return true;
        }
        else if(id == R.id.S14268){
            editor.putInt("School", 14268);
            editor.commit();
            updateTitle();
            return true;
        }
        else if(id == R.id.S14264){
            editor.putInt("School", 14264);
            editor.commit();
            updateTitle();
            return true;
        }
        updateTitle();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        switch (id) {
            case R.id.nav_News:
                fragment = News.newInstance();
                break;
            case R.id.nav_Calendar:
                fragment = Calendar.newInstance();
                break;
            case R.id.nav_Websites:
                fragment = Websites.newInstance();
                break;
            case R.id.nav_About:
                fragment = About.newInstance();
                break;
            case R.id.nav_Handbook:
                fragment = Handbook.newInstance();
                break;
            case R.id.nav_ID:
                fragment = StudentID.newInstance();
                break;
            case R.id.nav_Planner:
                fragment = Planner.newInstance();
                break;
            default:
                fragment = Temp.newInstance();
                break;
        }
        FragmentTransaction transfer = getSupportFragmentManager().beginTransaction();
        transfer.replace(R.id.fragmentcontainer,fragment).addToBackStack("tag").commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
