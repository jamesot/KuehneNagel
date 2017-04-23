package com.kuehnenagel.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    protected ArrayList<Card> cards = new ArrayList<Card>();
    protected JSONArray build = null;
    Spinner spinner, spinner2;
    ArrayAdapter<String> adapter, adapter2;
    ArrayList<String> data = new ArrayList<String>();
    ArrayList<String> data2 = new ArrayList<String>();
    ArrayList<String> data3 = new ArrayList<String>();
    ArrayList<String> data4 = new ArrayList<String>();
    ArrayList<String> data5 = new ArrayList<String>();

    String TheDate = null, TheFlight = null;
    private JSONObject object = null;
    private ProgressDialog mProgressDialog;

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
        spinner2 = (Spinner) findViewById(R.id.flight);
        data.add("Choose flight");
        adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, data);
        spinner2.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.consignee);
        data2.add("Choose consignee");
        adapter2 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, data2);
        spinner.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!adapterView.getItemAtPosition(i).toString().equals("Choose flight")) {


                    if (TheDate == null) {
                        MyShortcuts.showToast("Please pick a date first! ", getBaseContext());

                    } else {
                        TheFlight = adapterView.getItemAtPosition(i).toString();
                        Log.e("consignees par.", adapterView.getItemAtPosition(i).toString() + TheDate);
//                        data2 = new ArrayList<String>();
                        data2.clear();
                        GetConsignees(adapterView.getItemAtPosition(i).toString(), TheDate);

                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = parent.getItemAtPosition(position).toString();
                if (!selected_item.equals("Choose consignee")) {
//                    TODO show table contents

                   /* AcceptedConsignmentCard acceptedConsignmentCard = new AcceptedConsignmentCard(BuildUp.this);
                    acceptedConsignmentCard.Shipper = "Oserian";
                    acceptedConsignmentCard.box_dimension = "100x33x20";
                    acceptedConsignmentCard.init();
                    cards.add(acceptedConsignmentCard);
                    AcceptedConsignmentCard acceptedConsignmentCard2 = new AcceptedConsignmentCard(BuildUp.this);
                    acceptedConsignmentCard2.Shipper = "Mt. Elgon";
                    acceptedConsignmentCard.box_dimension = "100x33x20";
                    acceptedConsignmentCard2.init();
                    cards.add(acceptedConsignmentCard2);
                    cardGridArrayAdapter = new CardGridArrayAdapter(getBaseContext(), cards);

                    CardGridView cardGridView = (CardGridView) findViewById(R.id.carddemo_grid_base1);

                    if (cardGridView != null) {
                        cardGridView.setAdapter(cardGridArrayAdapter);
                    }*/
                    cards.clear();
                    GetAll(TheFlight, TheDate, selected_item);

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

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BuildUp.this);

        alertDialogBuilder.setTitle("Set URL");
        alertDialogBuilder.setMessage("Add URL below");
        LinearLayout layout = new LinearLayout(BuildUp.this);
        layout.setOrientation(LinearLayout.VERTICAL);


        final EditText et = new EditText(BuildUp.this);
        layout.addView(et);


        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyShortcuts.setDefaults("url", et.getText().toString(), BuildUp.this);


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

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        dateTextView = (TextView) findViewById(R.id.chosen_date);
        dateTextView.setText(date);
        String month, day;
        if (monthOfYear < 10) {
            month = "0" + monthOfYear;
        } else {
            month = monthOfYear + "";
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = dayOfMonth + "";
            ;

        }
        TheDate = year + "-" + month + "-" + day;
        Log.e("the date", TheDate);
        if (MyShortcuts.hasInternetConnected(this)) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Getting data ...");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            GetBuildUp(TheDate);
        }


    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

    }


    private void GetBuildUp(String theDate) {
        // Toast.makeText(getBaseContext(), "Inside function!", Toast.LENGTH_SHORT).show();
        // Tag used to cancel the request

//        Log.e("JSON serializing", js.toString());
        String tag_string_req = "req_Categories";

        Log.e("url is", MyShortcuts.getDefaults("url", getBaseContext()) + "/cargo_handling/api/buildup/?shipmentDate"+theDate+"&sessionId=" + MyShortcuts.getDefaults("session", getBaseContext()));
        StringRequest strReq = new StringRequest(Request.Method.GET, MyShortcuts.getDefaults("url",getBaseContext()) + "/cargo_handling/api/buildup/?shipmentDate="+theDate+"&sessionId=" + MyShortcuts.getDefaults("session", getBaseContext()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response from server is", response.toString());

                ArrayList<Card> cards = new ArrayList<Card>();
                String success = null;
                try {
                    JSONObject jObj = new JSONObject(response);
                    object = jObj;
                    mProgressDialog.dismiss();


                    MyShortcuts.setDefaults("UserId", jObj.getString("UserId"), getBaseContext());

                    JSONArray res = jObj.getJSONArray("BuildUp");
                    build = res;

                    GetFlight(TheDate);

                   /* // looping through All res
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject c = res.getJSONObject(i);

                        if (i % 2 == 0) {

                            JSONObject shipper = res.getJSONObject(i);
                            String flight = shipper.getString("FlightName");
                            data.add(flight);

                        }


//
                    }

                  *//*  adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, data);
                    spinner.setAdapter(adapter);*//*
                    adapter.notifyDataSetChanged();*/
                    if (res.length() == 0) {
                        Toast.makeText(getBaseContext(), "No data Available now! check later ", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
//                    Toast.makeText(getBaseContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("JSON ERROR", e.toString());
                    mProgressDialog.dismiss();
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyError", "Error: " + error.getMessage());
//                hideProgressDialog();
                mProgressDialog.dismiss();
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

    private void GetFlight(String date) {
        try {
            JSONArray res = object.getJSONArray("BuildUp");
            build = res;


            // looping through All res
            for (int i = 0; i < res.length(); i++) {
                JSONObject c = res.getJSONObject(i);

                if (i % 2 == 0) {
                    JSONObject shipper = res.getJSONObject(i);

                    if (date.equals(shipper.getString("ShipmentDate"))) {
                        String flight = shipper.getString("FlightName");
                        data.add(flight);
                    }

                }


//
            }

                  /*  adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, data);
                    spinner.setAdapter(adapter);*/
            adapter.notifyDataSetChanged();
            if (res.length() == 0) {
                Toast.makeText(getBaseContext(), "No data Available now! check later ", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            // JSON error
            e.printStackTrace();
//                    Toast.makeText(getBaseContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("JSON ERROR", e.toString());
        }
    }


    private void GetConsignees(String flight, String date) {
        try {
            JSONArray res = object.getJSONArray("BuildUp");

//            data2 = new ArrayList<String>();
//            data2.add("Choose consignee");
            if (data2.size()<1){
                data2.add("Choose consignee");

            }
            adapter2.notifyDataSetChanged();
            for (int i = 0; i < res.length(); i++) {
                if (i % 2 == 0) {
                    JSONObject shipper = res.getJSONObject(i);

                    if (flight.equals(shipper.getString("FlightName")) && date.equals(shipper.getString("ShipmentDate"))) {
                        JSONObject consignees = res.getJSONObject(i + 1);
//                        Log.e("consignee",consignees.toString());
                        JSONArray jsonArray = consignees.getJSONArray("Consignees");

                        for (int j = 0; j < jsonArray.length(); j++) {

                            if (j % 2 == 0) {
                                JSONObject consignee = jsonArray.getJSONObject(j);
                                data2.add(consignee.getString("ConsigneeName"));
                                adapter2.notifyDataSetChanged();
                                Log.e("adding data", consignee.getString("ConsigneeName"));
                            }

                        }


                    }

                }

            }
            if (data2.size()==1){

            }
            adapter2.notifyDataSetChanged();

        } catch (JSONException e) {
            // JSON error
            e.printStackTrace();
//                    Toast.makeText(getBaseContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("JSON ERROR", e.toString());
        }

    }

    private void GetAll(String flight, String date, String Consignee) {
        try {
            JSONArray res = object.getJSONArray("BuildUp");
            data5.add("choose");

            for (int i = 0; i < res.length(); i++) {
                if (i % 2 == 0) {
                    JSONObject shipper = res.getJSONObject(i);
//&&Consignee.equals(shipper.getString("ConsigneeName"))
                    if (flight.equals(shipper.getString("FlightName")) && date.equals(shipper.getString("ShipmentDate"))) {
                        JSONObject consignees = res.getJSONObject(i + 1);
//                        Log.e("consignee",consignees.toString());
                        JSONArray jsonArray = consignees.getJSONArray("Consignees");

                        for (int j = 0; j < jsonArray.length(); j++) {

                            if (j % 2 == 0) {
                                AcceptedConsignmentCard acceptedConsignmentCard;
                                JSONObject consignee = jsonArray.getJSONObject(j);


                                if (Consignee.equals(consignee.getString("ConsigneeName"))) {
                                    int k = j + 1;
                                    JSONObject shippers = jsonArray.getJSONObject(k);

                                    JSONArray jsonArray1 = shippers.getJSONArray("Shippers");

                                    JSONObject jsonObject = jsonArray1.getJSONObject(0);
                                    JSONObject jsonObject2 = jsonArray1.getJSONObject(1);
                                    JSONArray jsonArray2 = jsonObject2.getJSONArray("Pallets");


                                    Log.e("paleet", jsonArray2.toString());

                                    for (int l = 0; l < jsonArray2.length(); l++) {
//                                        Adding data to ULD Type, Contour Name and Unit No
                                        JSONObject js = jsonArray2.getJSONObject(l);

                                        Log.e("ULD", js.getString("ULDTypeName"));
                                        data3.add(js.getString("ULDTypeName"));
                                        data4.add(js.getString("ContourName"));
                                        data5.add(js.getString("UnitNo"));


                                    }

                                    if (jsonArray2.length() >= 1) {
                                        Log.e("pallet analyzed", jsonArray2.toString());

                                        acceptedConsignmentCard = new AcceptedConsignmentCard(BuildUp.this);
                                        acceptedConsignmentCard.consignee = consignee.getString("ConsigneeName");
                                        acceptedConsignmentCard.palletPlan = jsonArray2.toString();
                                        acceptedConsignmentCard.Shipper = jsonObject.getString("ShipperName");
                                        acceptedConsignmentCard.data3 = data3;
                                        acceptedConsignmentCard.data4 = data4;
                                        acceptedConsignmentCard.data5 = data5;
                                        acceptedConsignmentCard.setId(jsonObject.getString("ShipperId"));
                                 /*   acceptedConsignmentCard.planned =*/
                                        acceptedConsignmentCard.init();
                                        cards.add(acceptedConsignmentCard);

                                    }


                                }
//                               Log.e("adding data","adding data");
                            }

                        }


                    }

                }

            }

            cardGridArrayAdapter = new CardGridArrayAdapter(getBaseContext(), cards);

            CardGridView cardGridView = (CardGridView) findViewById(R.id.carddemo_grid_base1);

            if (cardGridView != null) {
                cardGridView.setAdapter(cardGridArrayAdapter);
            }
//            adapter2.notifyDataSetChanged();

        } catch (JSONException e) {
            // JSON error
            e.printStackTrace();
//                    Toast.makeText(getBaseContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("JSON ERROR", e.toString());
        }

    }

    private void GetData() {
        // Toast.makeText(getBaseContext(), "Inside function!", Toast.LENGTH_SHORT).show();
        // Tag used to cancel the request

//        Log.e("JSON serializing", js.toString());
        String tag_string_req = "req_Categories";

        Log.e("url is", MyShortcuts.baseURL() + "/cargo_handling/api/buildup/?sessionId=" + MyShortcuts.getDefaults("session", getBaseContext()));
        StringRequest strReq = new StringRequest(Request.Method.GET, MyShortcuts.baseURL() + "/cargo_handling/api/buildup/?sessionId=" + getIntent().getStringExtra("session"), new Response.Listener<String>() {
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
                            JSONArray jsonArray = shipper.getJSONArray("Consignees");

                            for (int j = 0; j < jsonArray.length(); j++) {

                                if (j % 2 != 0) {
                                    JSONObject consignee = jsonArray.getJSONObject(i);
                                    consignee.getString("ShipperName");
                                }

                            }


                            AcceptedConsignmentCard acceptedConsignmentCard = new AcceptedConsignmentCard(BuildUp.this);
                            acceptedConsignmentCard.Shipper = shipper.getString("ShipperName");
                            acceptedConsignmentCard.box_dimension = shipper.getString("BoxTypeDimName");
                            acceptedConsignmentCard.TotalNo = shipper.getString("BoxesPlanned");
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
