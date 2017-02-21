package com.kuehnenagel.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardGridView;

import static com.kuehnenagel.android.ConsigneeInput.cardGridArrayAdapter;
import static com.kuehnenagel.android.ConsigneeInput.cards;

/**
 * Created by stephineosoro on 23/09/2016.
 */

public class PalletPlanCard extends Card {


    protected String Shipper;
    protected String DispatchDate;
    protected String SealNo;
    protected String ProductType;
    protected String Truck;
    protected String TotalNo;
    protected String Action;
    protected Context context;
    static TextView input, Gross, Net;
    static boolean answer = false;
    static String spinner_1, spinner_2, spinner_3;

    public PalletPlanCard(Context context) {
        super(context, R.layout.pallet_detail);
        this.context = context;

    }


    public PalletPlanCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    protected void init() {
        CardHeader header = new CardHeader(getContext());
        header.setButtonOverflowVisible(true);
        header.setTitle(Shipper);
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

        /*TextView title = (TextView) view.findViewById(R.id.shipper);
        title.setText(Shipper);

        final TextView Dispatch = (TextView) view.findViewById(R.id.dispatch_date);
        Dispatch.setText(DispatchDate);

        final TextView subtitle = (TextView) view.findViewById(R.id.seal_no);
        subtitle.setText(SealNo);
        final TextView Pt = (TextView) view.findViewById(R.id.product_type);
        Pt.setText(ProductType);
        final TextView truck = (TextView) view.findViewById(R.id.truck);
        truck.setText(Truck);
        final TextView Tn = (TextView) view.findViewById(R.id.total_boxes);
        Tn.setText(TotalNo);
        final TextView action = (TextView) view.findViewById(R.id.action);

        action.setClickable(true);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Acceptance.class);
                intent.putExtra("seal_no",SealNo);
                Log.e("seal",SealNo);
                getContext().startActivity(intent);
            }
        });
*/

        final TextView action = (TextView) view.findViewById(R.id.proceed_build);

        action.setClickable(true);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(getContext(),"Saved successfully!",Toast.LENGTH_LONG);
                MyShortcuts.showToast("Saved successfully!",getContext());
//                Intent intent = new Intent(context, BuildUp.class);
//                intent.putExtra("name",getCardHeader().getTitle());
//                context.startActivity(intent);
                /*Intent intent = new Intent(getContext(),Acceptance.class);
                intent.putExtra("seal_no",SealNo);
                Log.e("seal",SealNo);
                getContext().startActivity(intent);*/


            }
        });

        final TextView action2 = (TextView) view.findViewById(R.id.add_new_entry);
        action2.setClickable(true);
        action2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();


                /*Intent intent = new Intent(getContext(),Acceptance.class);
                intent.putExtra("seal_no",SealNo);
                Log.e("seal",SealNo);
                getContext().startActivity(intent);*/
            }
        });


    }

    protected void showDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Add new pallet information");
        alertDialogBuilder.setMessage("New pallet entry form");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        Spinner spinner = new Spinner(context);
        List<String> dimension = new ArrayList<String>();
        dimension.add("100x33x20");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, dimension);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        layout.addView(spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = parent.getItemAtPosition(position).toString();
                spinner_1 = selected_item;
                Log.e("Box type", selected_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner2 = new Spinner(context);
        List<String> dimension2 = new ArrayList<String>();
        dimension2.add("PMC");

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, dimension2);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        layout.addView(spinner2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = parent.getItemAtPosition(position).toString();
                spinner_2 = selected_item;
                Log.e("Box type", selected_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spinner3 = new Spinner(context);
        List<String> dimension3 = new ArrayList<String>();
        dimension3.add("Q7");
        dimension3.add("Q6");

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, dimension3);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        layout.addView(spinner3);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = parent.getItemAtPosition(position).toString();
                spinner_3 = selected_item;
                Log.e("Box type", selected_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        EditText input = new EditText(context);
        input.setHint("Enter box count");
        layout.addView(input);


        alertDialogBuilder.setView(layout);


        try {

            if (!input.getText().toString().isEmpty()) {
                JSONArray json = new JSONArray(MyShortcuts.getDefaults("json", context));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dimension", spinner_1);
                jsonObject.put("uld", spinner_2);
                jsonObject.put("uldtype", spinner_3);
                jsonObject.put("box_count", input.getText().toString());
                json.put(jsonObject);
            } else{
                Toast.makeText(context,"Did not add the plan because you left some fields empty",Toast.LENGTH_SHORT);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(context, "Pallet entry added! ", Toast.LENGTH_SHORT);

//                MyShortcuts.setDefaults("pallet",json.toString(),context);
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

        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("Read Weight", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                input.setText("Tare: 20.0");
                Gross.setText("Gross: 20.0");
                Net.setText("Net: 20.0");
                WeightDialog("20.0", "20.0", "20.0");
                MyShortcuts.setDefaults("populate", "true", context);


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


    protected boolean ConfirmDelete(String message) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(message);
        alertDialogBuilder.setMessage("Are you sure you want to " + message + "?");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);


        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                answer = true;
                cards.remove(getCardView().getCard());
                cardGridArrayAdapter.notifyDataSetChanged();

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                answer = false;
            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
        return answer;
    }


}

