package com.kuehnenagel.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.kuehnenagel.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardGridView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static CardGridArrayAdapter cardGridArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getIntent().getStringExtra("session");



        GetData();
       /* ArrayList<Card> cards = new ArrayList<Card>();
        GplayGridCard gplayGridCard = new GplayGridCard(this);
        gplayGridCard.Shipper = "Mt Elgon";
        gplayGridCard.DispatchDate = "26-07-16";
        gplayGridCard.SealNo = "323223";
        gplayGridCard.ProductType = "Flower";
        gplayGridCard.Truck = "KBM 446Y";
        gplayGridCard.TotalNo = "300";
        gplayGridCard.init();
        cards.add(gplayGridCard);

        GplayGridCard gplayGridCard2 = new GplayGridCard(this);
        gplayGridCard2.Shipper = "Oserian";
        gplayGridCard2.DispatchDate = "26-07-16";
        gplayGridCard2.SealNo = "323223";
        gplayGridCard2.ProductType = "Flower";
        gplayGridCard2.Truck = "KBM 446Y";
        gplayGridCard2.TotalNo = "300";
        gplayGridCard2.init();
        cards.add(gplayGridCard2);
*/
       /* GplayGridCard gplayGridCard3 = new GplayGridCard(this);
        gplayGridCard3.Shipper="Isinya";
        gplayGridCard3.DispatchDate="26-07-16";
        gplayGridCard3.SealNo= "323223";
        gplayGridCard3.ProductType="Flower";
        gplayGridCard3.Truck="KBM 446Y";
        gplayGridCard3.TotalNo ="300";
        gplayGridCard3.init();
        cards.add(gplayGridCard3);*/


       /* cardGridArrayAdapter = new CardGridArrayAdapter(this, cards);

        CardGridView cardGridView = (CardGridView) findViewById(R.id.carddemo_grid_base1);

        if (cardGridView != null) {
            cardGridView.setAdapter(cardGridArrayAdapter);
        }
*/
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Undo", Snackbar.LENGTH_LONG)
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void GetData() {
        // Toast.makeText(getBaseContext(), "Inside function!", Toast.LENGTH_SHORT).show();
        // Tag used to cancel the request

//        Log.e("JSON serializing", js.toString());
        String tag_string_req = "req_Categories";

        Log.e("url is", MyShortcuts.baseURL() + "/cargo_handling/api/acceptance/?sessionId="+ getIntent().getStringExtra("session"));
        StringRequest strReq = new StringRequest(Request.Method.GET, MyShortcuts.baseURL() + "/cargo_handling/api/acceptance/?sessionId="+ getIntent().getStringExtra("session"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response from server is", response.toString());

                ArrayList<Card> cards = new ArrayList<Card>();
                String success = null;
                try {
                    JSONObject jObj = new JSONObject(response);
//                            res = jObj.getJSONArray("All");
                    //successfully gotten matatu data
//                        String regno = jObj.getString("regno");

                    JSONArray res = jObj.getJSONArray("Transport");

                    Log.e("result array: ", res.toString());

/*
                    JSONObject shipper = res.getJSONObject(0);
                    GplayGridCard gplayGridCard3 = new GplayGridCard(getBaseContext());
                    gplayGridCard3.Shipper=shipper.getString("ShipperName");
                    gplayGridCard3.DispatchDate=shipper.getString("DispatchDate");
                    gplayGridCard3.SealNo= shipper.getString("SealNo");
                    gplayGridCard3.ProductType=shipper.getString("ProductType");
                    gplayGridCard3.Truck=shipper.getString("Truck");
                    gplayGridCard3.TotalNo =shipper.getString("TotalNoOfBoxes");
                    gplayGridCard3.init();
                    cards.add(gplayGridCard3);*/


//                    JSONArray res1 = jObj.getJSONArray("Transport");


                    // looping through All res
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject c = res.getJSONObject(i);

                        if (i % 2 == 0) {

                            JSONObject shipper = res.getJSONObject(i);
                            GplayGridCard gplayGridCard3 = new GplayGridCard(MainActivity.this);
                            gplayGridCard3.Shipper = shipper.getString("ShipperName");
                            gplayGridCard3.DispatchDate = shipper.getString("DispatchDate");
                            gplayGridCard3.SealNo = shipper.getString("SealNo");
                            gplayGridCard3.ProductType = shipper.getString("ProductType");
                            gplayGridCard3.Truck = shipper.getString("Truck");
                            gplayGridCard3.TotalNo = shipper.getString("TotalNoOfBoxes");
                            gplayGridCard3.setId(shipper.getString("TransportId"));
                            gplayGridCard3.init();
                            cards.add(gplayGridCard3);

                        }




//
                    }
                    if (res.length() == 0) {
                        Toast.makeText(getBaseContext(), "No data Available now! check later ", Toast.LENGTH_LONG).show();
                    }
                    cardGridArrayAdapter = new CardGridArrayAdapter(getBaseContext(), cards);

                    CardGridView listView = (CardGridView) findViewById(R.id.carddemo_grid_base1);
                    if (listView != null) {
                        listView.setAdapter(cardGridArrayAdapter);
                    }


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
//                    Toast.makeText(getBaseContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("JSON ERROR", e.toString());
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyError", "Error: " + error.getMessage());
//                hideProgressDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                setRetryPolicy(new DefaultRetryPolicy(5 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
                setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
                headers.put("Content-Type", "application/json; charset=utf-8");

                String creds = String.format("%s:%s", "admin","demo");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
//                Log.e("category id", getIntent().getStringExtra("category_id"));
//                params.put("categoryId", 2 + "");


                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        Log.e("request is", strReq.toString());
    }


}
