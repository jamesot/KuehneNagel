package com.kuehnenagel.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;

import static com.kuehnenagel.android.ConsigneeInput.cardGridArrayAdapter;
import static com.kuehnenagel.android.ConsigneeInput.cards;

public class PalletPlan extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected CardGridArrayAdapter cardGridArrayAdapter;
    static TextView input;
    protected ArrayList<Card> cards = new ArrayList<Card>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pallet_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("name"));
        JSONArray json = new JSONArray();
        MyShortcuts.setDefaults("buildupjson", json.toString(), this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                ConfirmBuild();

            }
        });

        String p = getIntent().getStringExtra("pallet");

        try {
            JSONArray jsonArray = new JSONArray(p);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject js = jsonArray.getJSONObject(i);

                PalletPlanCard palletPlanCard = new PalletPlanCard(PalletPlan.this);
                palletPlanCard.Shipper = getIntent().getStringExtra("shipper");
                palletPlanCard.boxDimension = js.getString("BoxTypeDimName");
                palletPlanCard.planned = js.getString("BoxesPlanned");
                palletPlanCard.unit = js.getString("UnitNo");
                palletPlanCard.setId(js.getString("BookingId"));
                palletPlanCard.ForeCastId = js.getString("ForecastId");
                palletPlanCard.BoxId = js.getString("BoxTypeDimId");
                palletPlanCard.ContourId = js.getString("ContourId");
                palletPlanCard.bookingId = js.getString("BookingId");
                palletPlanCard.BoxesAverage = js.getString("BoxAverVol");
//                TODO add data for the spinners

//                    TODO add the new entry here
                palletPlanCard.init();
                cards.add(palletPlanCard);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


      /*  try {
            JSONArray jsonArray = new JSONArray(MyShortcuts.getDefaults("json",this));
            if (jsonArray.length()>0){
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    cards = new ArrayList<Card>();
                    PalletPlanCard palletPlanCard = new PalletPlanCard(this);
//                    TODO add the new entry here
                    palletPlanCard.init();
                    cards.add(palletPlanCard);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        /*PalletPlanCard palletPlanCard = new PalletPlanCard(this);
        palletPlanCard.init();
        cards.add(palletPlanCard)*/
        ;
        cardGridArrayAdapter = new CardGridArrayAdapter(getBaseContext(), cards);

        CardGridView cardGridView = (CardGridView) findViewById(R.id.carddemo_grid_base1);

        if (cardGridView != null) {
            cardGridView.setAdapter(cardGridArrayAdapter);
        }

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
        getMenuInflater().inflate(R.menu.pallet_plan, menu);
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
            Intent intent = new Intent(getBaseContext(), HomePage.class);
            startActivity(intent);
        } else if (id == R.id.acceptance) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.build) {
            Intent intent = new Intent(getBaseContext(), BuildUp.class);
            startActivity(intent);

        } else if (id == R.id.post) {
            setURL();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void setURL() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PalletPlan.this);

        alertDialogBuilder.setTitle("Set URL");
        alertDialogBuilder.setMessage("Add URL below");

        LinearLayout layout = new LinearLayout(PalletPlan.this);
        layout.setOrientation(LinearLayout.VERTICAL);


        final EditText et = new EditText(PalletPlan.this);
        layout.addView(et);


        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyShortcuts.setDefaults("url", et.getText().toString(), PalletPlan.this);


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

    protected void ConfirmBuild() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PalletPlan.this);

        alertDialogBuilder.setTitle("Not reversible");
        alertDialogBuilder.setMessage(" Please verify the pallet plan before you submit this. This step is not reversible Are you sure you want to complete this build up?");


        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getBaseContext(), BuildUp.class);
                intent.putExtra("name", getIntent().getStringExtra("name"));
                startActivity(intent);


            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

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
