package com.kuehnenagel.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;

public class Dispatch extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,  TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {
    private static CardGridArrayAdapter cardGridArrayAdapter;
    TextView dateTextView;
    private ProgressDialog mProgressDialog;
    String TheDate = null, TheFlight = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                Dispatch.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.show(getFragmentManager(), "Date Picker");

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }

    private void GetDispatchs(String theDate) {

        String tag_string_req = "req_Categories";

        Log.e("url is", MyShortcuts.getDefaults("url",getBaseContext())  + "/cargo_handling/api/palletDispatch/?shipmentDate="+theDate+"&sessionId=" + MyShortcuts.getDefaults("session", getBaseContext()));
        StringRequest strReq = new StringRequest(Request.Method.GET, MyShortcuts.getDefaults("url",getBaseContext()) + "/cargo_handling/api/palletDispatch/?shipmentDate="+theDate+"&sessionId=" + MyShortcuts.getDefaults("session", getBaseContext()), new Response.Listener<String>() {
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

                    JSONArray res = jObj.getJSONArray("PalletDispatch");

                    Log.e("result array: ", res.toString());


                    // looping through All res
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject c = res.getJSONObject(i);

//                        if (i % 2 == 0) {

                        JSONObject shipper = res.getJSONObject(i);

                        DispatchCard dispatchCard = new DispatchCard(Dispatch.this);
                        dispatchCard.FLightName = shipper.getString("FlightName");
                        dispatchCard.ShipmentDate = shipper.getString("ShipmentDate");
                        dispatchCard.PNumber = shipper.getString("PalletNo");
                        dispatchCard.PalletID = shipper.getString("PalletId");
                        dispatchCard.Contour = shipper.getString("Contour");
                        dispatchCard.pallet=shipper.toString();
                        dispatchCard.setId(shipper.getString("FlightId"));
                        dispatchCard.init();
                        cards.add(dispatchCard);


//                        }


//
                    }
                    if (res.length() == 0) {
                        Toast.makeText(getBaseContext(), "No data Available now! check later ", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                    cardGridArrayAdapter = new CardGridArrayAdapter(getBaseContext(), cards);

                    CardGridView listView = (CardGridView) findViewById(R.id.carddemo_grid_base1);
                    if (listView != null) {
                        listView.setAdapter(cardGridArrayAdapter);
                    }


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    mProgressDialog.dismiss();

//                    Toast.makeText(getBaseContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("JSON ERROR", e.toString());
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyError", "Error: " + error.getMessage());
                mProgressDialog.dismiss();

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

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Dispatch.this);

        alertDialogBuilder.setTitle("Read weight");
        alertDialogBuilder.setMessage("Click read weight to read weight");

        LinearLayout layout = new LinearLayout(Dispatch.this);
        layout.setOrientation(LinearLayout.VERTICAL);


        final EditText et = new EditText(Dispatch.this);
        layout.addView(et);


        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyShortcuts.setDefaults("url", et.getText().toString(), Dispatch.this);


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
    public void onBackPressed() {

            super.onBackPressed();

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
       /* dateTextView = (TextView) findViewById(R.id.chosen_date);
        dateTextView.setText(date);*/
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
            GetDispatchs(TheDate);
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

    }
}
