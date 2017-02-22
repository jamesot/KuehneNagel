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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.wdullaer.materialdatetimepicker.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;

public class BuildUp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {
    TextView dateTextView;
    protected CardGridArrayAdapter cardGridArrayAdapter;
    static TextView input;
    protected static ArrayList<Card> cards = new ArrayList<Card>();
    protected JSONArray build = null;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    ArrayList<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        GetFlights();

        Button button = (Button) findViewById(R.id.date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        BuildUp.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpd.show(getFragmentManager(), "Date Picker");
            }
        });
        /*spinner = (Spinner) findViewById(R.id.flight);
        adapter=new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, data);;
        spinner.setAdapter(adapter);*/

        spinner = (Spinner) findViewById(R.id.consignee);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = parent.getItemAtPosition(position).toString();
                if (!selected_item.equals("Choose consignee")) {
//                    TODO show table contents

                    AcceptedConsignmentCard acceptedConsignmentCard = new AcceptedConsignmentCard(BuildUp.this);
                    acceptedConsignmentCard.Shipper = "Oserian";
                    acceptedConsignmentCard.init();
                    cards.add(acceptedConsignmentCard);
                    AcceptedConsignmentCard acceptedConsignmentCard2 = new AcceptedConsignmentCard(BuildUp.this);
                    acceptedConsignmentCard2.Shipper = "Mt. Elgon";
                    acceptedConsignmentCard2.init();
                    cards.add(acceptedConsignmentCard2);
                    cardGridArrayAdapter = new CardGridArrayAdapter(getBaseContext(), cards);

                    CardGridView cardGridView = (CardGridView) findViewById(R.id.carddemo_grid_base1);

                    if (cardGridView != null) {
                        cardGridView.setAdapter(cardGridArrayAdapter);
                    }

                }

                Log.e("Box type", selected_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        getMenuInflater().inflate(R.menu.build_up, menu);
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

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        dateTextView = (TextView) findViewById(R.id.chosen_date);
        dateTextView.setText(date);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

    }


    private void GetFlights() {
        // Toast.makeText(getBaseContext(), "Inside function!", Toast.LENGTH_SHORT).show();
        // Tag used to cancel the request

//        Log.e("JSON serializing", js.toString());
        String tag_string_req = "req_Categories";

        Log.e("url is", MyShortcuts.baseURL() + "/cargo_handling/api/buildup/?sessionId=" + MyShortcuts.getDefaults("session", getBaseContext()));
        StringRequest strReq = new StringRequest(Request.Method.GET, MyShortcuts.baseURL() + "/cargo_handling/api/acceptance/?sessionId=" + getIntent().getStringExtra("session"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response from server is", response.toString());

                ArrayList<Card> cards = new ArrayList<Card>();
                String success = null;
                try {
                    JSONObject jObj = new JSONObject(response);

                    JSONArray res = jObj.getJSONArray("BuildUp");
                    build = res;


                    // looping through All res
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject c = res.getJSONObject(i);

                        if (i % 2 == 0) {

                            JSONObject shipper = res.getJSONObject(i);
                            String flight = shipper.getString("FlightName");
                            data.add(flight);


                          /*  GplayGridCard gplayGridCard3 = new GplayGridCard(getBaseContext());
                            gplayGridCard3.Shipper = shipper.getString("ShipperName");
                            gplayGridCard3.DispatchDate = shipper.getString("DispatchDate");
                            gplayGridCard3.SealNo = shipper.getString("SealNo");
                            gplayGridCard3.ProductType = shipper.getString("ProductType");
                            gplayGridCard3.Truck = shipper.getString("Truck");
                            gplayGridCard3.TotalNo = shipper.getString("TotalNoOfBoxes");
                            gplayGridCard3.setId(shipper.getString("TransportId"));
                            gplayGridCard3.init();
                            cards.add(gplayGridCard3);*/

                        }


//
                    }

                  /*  adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, data);
                    spinner.setAdapter(adapter);*/
                    adapter.notifyDataSetChanged();
                    if (res.length() == 0) {
                        Toast.makeText(getBaseContext(), "No data Available now! check later ", Toast.LENGTH_LONG).show();
                    }
                   /* cardGridArrayAdapter = new CardGridArrayAdapter(getBaseContext(), cards);

                    CardGridView listView = (CardGridView) findViewById(R.id.carddemo_grid_base1);
                    if (listView != null) {
                        listView.setAdapter(cardGridArrayAdapter);
                    }
*/

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

                String creds = String.format("%s:%s", "admin", "demo");
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



    private void GetData() {
        // Toast.makeText(getBaseContext(), "Inside function!", Toast.LENGTH_SHORT).show();
        // Tag used to cancel the request

//        Log.e("JSON serializing", js.toString());
        String tag_string_req = "req_Categories";

        Log.e("url is", MyShortcuts.baseURL() + "/cargo_handling/api/buildup/?sessionId=" + MyShortcuts.getDefaults("session", getBaseContext()));
        StringRequest strReq = new StringRequest(Request.Method.GET, MyShortcuts.baseURL() + "/cargo_handling/api/acceptance/?sessionId=" + getIntent().getStringExtra("session"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response from server is", response.toString());

                ArrayList<Card> cards = new ArrayList<Card>();
                String success = null;
                try {
                    JSONObject jObj = new JSONObject(response);

                    JSONArray res = jObj.getJSONArray("BuildUp");
                    build = res;


                    // looping through All res
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject c = res.getJSONObject(i);

                        if (i % 2 != 0) {

                            JSONObject shipper = res.getJSONObject(i);



                            AcceptedConsignmentCard acceptedConsignmentCard = new AcceptedConsignmentCard(BuildUp.this);
                            acceptedConsignmentCard.Shipper = shipper.getString("ShipperName");
                            acceptedConsignmentCard.box_dimension=shipper.getString("BoxTypeDimName");
                            acceptedConsignmentCard.TotalNo=shipper.getString("BoxesPlanned");
                            acceptedConsignmentCard.init();
                            cards.add(acceptedConsignmentCard);

                        }


//
                    }

                  /*  adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, data);
                    spinner.setAdapter(adapter);*/

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

                String creds = String.format("%s:%s", "admin", "demo");
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
