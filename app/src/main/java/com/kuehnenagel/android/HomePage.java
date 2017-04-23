package com.kuehnenagel.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import developer.shivam.library.DiagonalView;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (MyShortcuts.checkDefaults("url",this)){

        }else{
            MyShortcuts.setDefaults("url","http://10.38.32.7:8081/knap2",this);
        }
        setTitle("Home");

        DiagonalView diagonalView = new DiagonalView(this);
        diagonalView.setAngle(15);
        diagonalView.setDiagonalGravity(DiagonalView.LEFT);
        diagonalView.setBackgroundColor(Color.WHITE);

        Button button = (Button) findViewById(R.id.but2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                getIntent().getStringExtra("session");
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.but3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),BuildUp.class);
                getIntent().getStringExtra("session");
                startActivity(intent);
            }
        });

        Button button3 = (Button) findViewById(R.id.but4);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),Dispatch.class);
                getIntent().getStringExtra("session");
                startActivity(intent);
            }
        });

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
            Intent intent = new Intent(getBaseContext(),HomePage.class);
            startActivity(intent);
        } else if (id == R.id.acceptance) {
            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);

        }  else if (id == R.id.build) {
            Intent intent = new Intent(getBaseContext(),BuildUp.class);
            startActivity(intent);

        } else if (id == R.id.dispatch) {
            Intent intent = new Intent(getBaseContext(), Dispatch.class);
            startActivity(intent);

        }else if (id == R.id.post) {
            setURL();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void setURL() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomePage.this);

        alertDialogBuilder.setTitle("Set URL");
        alertDialogBuilder.setMessage("Add URL below");

        LinearLayout layout = new LinearLayout(HomePage.this);
        layout.setOrientation(LinearLayout.VERTICAL);


        final EditText et = new EditText(HomePage.this);
        layout.addView(et);


        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyShortcuts.setDefaults("url", et.getText().toString(), HomePage.this);


            }
        });

        alertDialogBuilder.setNegativeButton("Close & Finish", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();


            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();

    }
}
