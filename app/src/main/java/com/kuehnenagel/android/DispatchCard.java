package com.kuehnenagel.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * Created by stephineosoro on 23/09/2016.
 */

public class DispatchCard extends Card {


    protected String FLightName;
    protected String ShipmentDate;
    protected String PNumber;
    protected String Contour;
    protected String PalletID;
    protected String PalletNo;
    protected String pallet;
    protected Context cxt, context;
    protected String Action;
    static TextView input, Gross, Net;

    public DispatchCard(Context context) {
        super(context, R.layout.dispatch_content_detail);
        cxt = context;
        context = cxt;

    }


    public DispatchCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    protected void init() {

        CardHeader header = new CardHeader(getContext());
        header.setButtonOverflowVisible(true);
        header.setTitle(FLightName);
        header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                String selected = card.getId();

            }
        });

        addCardHeader(header);

          /*  OnCardClickListener clickListener = new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    //Do something
                }
            };

            addPartialOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW, clickListener);*/
         /*   setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
//                    Do something
                    String selected= card.getId();
                    Toast.makeText(getBaseContext(), "Item ID is" + selected, Toast.LENGTH_LONG).show();
                   *//* Intent intent =new Intent(getBaseContext(),ProductDetail.class);
                    intent.putExtra("id",selected);
                    intent.putExtra("product_name",card.getTitle());
                    startActivity(intent);*//*
                }
            });*/


    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        TextView title = (TextView) view.findViewById(R.id.flight_name);
        title.setText(FLightName);

        final TextView Dispatch = (TextView) view.findViewById(R.id.shipment_date);
        Dispatch.setText(ShipmentDate);

        final TextView subtitle = (TextView) view.findViewById(R.id.pallet_number);
        subtitle.setText(PNumber);
        final TextView Pt = (TextView) view.findViewById(R.id.contour);
        Pt.setText(Contour);
        final TextView truck = (TextView) view.findViewById(R.id.pallet_id);
        truck.setText(PalletID);

        final TextView action = (TextView) view.findViewById(R.id.action);

        action.setClickable(true);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HomePage.class);
                dispatch();
//                intent.putExtra("seal_no",PNumber);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                /*intent.putExtra("name",getCardHeader().getTitle());
                intent.putExtra("TransportId",getId());
                Log.e("transport id is ",getId());*/
                cxt.startActivity(intent);
            }
        });


    }

    private void dispatch() {
        final JSONObject jsonObject = new JSONObject();
        final JSONObject finalJsonObject = new JSONObject();
        try {
            jsonObject.put("FlightId",getId());
            jsonObject.put("PalletId",PalletID);
            jsonObject.put("FlightName",FLightName);
            jsonObject.put("ShipmentDate",ShipmentDate);
            jsonObject.put("PalletNo",PNumber);
            jsonObject.put("Contour",Contour);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            finalJsonObject.put("UserId",MyShortcuts.getDefaults("UserId",context));
            JSONArray jsonArray= new JSONArray();
            jsonArray.put(jsonObject);
            finalJsonObject.put("BuildUp",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Post.PostData(MyShortcuts.getDefaults("url",context)+"/cargo_handling/api/palletDispatch/put", finalJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response",jsonObject.toString());
            }
        });
    }

    protected void WeightDialog(String a, String b, String c) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Read weight");
        alertDialogBuilder.setMessage("Click read weight to read weight");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);


        input = new TextView(context);
        input.setText("Tare: " + a);
        layout.addView(input);

        Gross = new TextView(context);
        Gross.setText("Gross: " + b);
        layout.addView(Gross);

        Net = new TextView(context);
        Net.setText("Net: " + c);
        layout.addView(Net);

        Button button = new Button(context);
        button.setText("Add manually");
        layout.addView(button);

        alertDialogBuilder.setView(layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manual();
            }
        });

        alertDialogBuilder.setPositiveButton("Read Weight", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                input.setText("Tare: 20.0");
                Gross.setText("Gross: 20.0");
                Net.setText("Net: 20.0");
                WeightDialog("20.0", "20.0", "20.0");
                MyShortcuts.setDefaults("populate", "true", context);
                Intent intent = new Intent(context, Dispatch.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


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

    protected void manual() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Add weight manually");
        alertDialogBuilder.setMessage("Click read weight to read weight");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        System.out.println(format.format(new Date()));

        TextView tx= new TextView(context);
        tx.setText(format.format(new Date()));
        layout.addView(tx);

        EditText et4 = new EditText(context);
        et4.setHint("Temperature at Dispatch");
        layout.addView(et4);

        EditText et = new EditText(context);
        et.setHint("Tare");
        layout.addView(et);

        EditText et2 = new EditText(context);
        et2.setHint("Gross");
        layout.addView(et2);

        EditText et3 = new EditText(context);
        et3.setHint("Net");

        layout.addView(et3);


        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                input.setText("Tare: 20.0");
//                Gross.setText("Gross: 20.0");
//                Net.setText("Net: 20.0");
                MyShortcuts.setDefaults("populate", "true", context);
                Intent intent = new Intent(context, Dispatch.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


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


}

