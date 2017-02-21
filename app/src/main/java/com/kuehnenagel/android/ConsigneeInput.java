package com.kuehnenagel.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;

public class ConsigneeInput extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected static CardGridArrayAdapter cardGridArrayAdapter;
    static TextView input, Gross, Net;
    protected static ArrayList<Card> cards = new ArrayList<Card>();
    protected static List<String> dimension = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignee_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("name"));
        cards.clear();

        GetData();
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Complete form", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        /*cards = new ArrayList<Card>();
        Consignee consignee = new Consignee(this);
        consignee.Shipper = "Fresco";
        consignee.init();
        Consignee consignee2 = new Consignee(this);
        consignee2.Shipper = "IPL";
        consignee2.init();
        cards.add(consignee);
        cards.add(consignee2);


        cardGridArrayAdapter = new CardGridArrayAdapter(this, cards);

        CardGridView cardGridView = (CardGridView) findViewById(R.id.carddemo_grid_base1);

        if (cardGridView != null) {
            cardGridView.setAdapter(cardGridArrayAdapter);
        }*/


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
        getMenuInflater().inflate(R.menu.consignee_input, menu);
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

   /* protected void showDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ConsigneeInput.this);

        alertDialogBuilder.setTitle("Box Count and weight");
        alertDialogBuilder.setMessage("Select Box dimension and input the box count");
        EditText input = new EditText(ConsigneeInput.this);
        input.setHint("Enter box count");
        alertDialogBuilder.setView(input);
        Spinner spinner = new Spinner(ConsigneeInput.this);
        dimension.add("100x33x20");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConsigneeInput.this,
                android.R.layout.simple_spinner_item, dimension);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        alertDialogBuilder.setView(spinner);

        alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                WeightDialog();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();

    }



*/
   private void GetData() {
       // Toast.makeText(getBaseContext(), "Inside function!", Toast.LENGTH_SHORT).show();
       // Tag used to cancel the request

//        Log.e("JSON serializing", js.toString());
       String tag_string_req = "req_Categories";

       Log.e("url is", MyShortcuts.baseURL() + "cargo_handling/api/acceptance/?sessionId="+ MyShortcuts.getDefaults("session",getBaseContext()));
       StringRequest strReq = new StringRequest(Request.Method.GET, MyShortcuts.baseURL() + "/cargo_handling/api/acceptance/?sessionId="+MyShortcuts.getDefaults("session",getBaseContext()), new Response.Listener<String>() {
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


                           if (shipper.getString("ShipperName").equals(getIntent().getStringExtra("name"))&&shipper.getString("TransportId").equals(getIntent().getStringExtra("TransportId"))){
                               JSONObject consignee = res.getJSONObject(i+1);


                               JSONArray jsonArray= consignee.getJSONArray("Consignees");

                               if (jsonArray.length()==0){
                                   MyShortcuts.showToast("No consignees!",getBaseContext());
                               }

                               for (int j = 0; j <jsonArray.length(); j++) {
                                   Consignee consignee2 = new Consignee(getBaseContext());
                                   JSONObject js= jsonArray.getJSONObject(i);
                                   consignee2.Consignee = js.getString("ConsigneeName");
                                   consignee2.TotalBoxes=js.getString("Boxes");
                                   consignee2.mawb=js.getString("MAWB");
                                   consignee2.hawb=js.getString("HAWB");
                                   consignee2.setId(js.getString("ConsigneeId"));

                                   consignee2.init();
                                   cards.add(consignee2);
                               }


                           }

/*
                           GplayGridCard gplayGridCard3 = new GplayGridCard(getBaseContext());
                           gplayGridCard3.Shipper = shipper.getString("ShipperName");
                           gplayGridCard3.DispatchDate = shipper.getString("DispatchDate");
                           gplayGridCard3.SealNo = shipper.getString("SealNo");
                           gplayGridCard3.ProductType = shipper.getString("ProductType");
                           gplayGridCard3.Truck = shipper.getString("Truck");
                           gplayGridCard3.TotalNo = shipper.getString("TotalNoOfBoxes");
                           gplayGridCard3.init();
                           cards.add(gplayGridCard3);*/
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
