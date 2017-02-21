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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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

public class Consignee extends Card {


    protected String Consignee;
    protected String DispatchDate;
    protected String mawb;
    protected String hawb;
    protected String deliveryNoteNumber;
    protected String TotalBoxes;
    protected String ActualCount;
    protected Context context;
    static TextView input, Gross, Net;
    static boolean answer = false;

    public Consignee(Context context) {
        super(context, R.layout.consignee_detail);
        this.context = context;
        MyShortcuts.setDefaults("populate", "false", context);
    }


    public Consignee(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    protected void init() {
        CardHeader header = new CardHeader(getContext());
        header.setButtonOverflowVisible(true);
        header.setTitle(Consignee);
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

        TextView title = (TextView) view.findViewById(R.id.consignee);
        title.setText(Consignee);



        final TextView TotalB = (TextView) view.findViewById(R.id.t_boxes);
        TotalB.setText(TotalBoxes);

        final TextView subtitle = (TextView) view.findViewById(R.id.actual_count);
        subtitle.setText(ActualCount);
        final TextView Pt = (TextView) view.findViewById(R.id.mawb);
        Pt.setText(mawb);
        final TextView truck = (TextView) view.findViewById(R.id.hawb);
        truck.setText(hawb);
        /*final TextView Tn = (TextView) view.findViewById(R.id.total_boxes);
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

        final TextView action = (TextView) view.findViewById(R.id.accept);

        action.setClickable(true);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);

                context.startActivity(intent);
                /*Intent intent = new Intent(getContext(),Acceptance.class);
                intent.putExtra("seal_no",SealNo);
                Log.e("seal",SealNo);
                getContext().startActivity(intent);*/
                cards.remove(getCardView().getCard());
                cardGridArrayAdapter.notifyDataSetChanged();

            }
        });

        final TextView action2 = (TextView) view.findViewById(R.id.reject);
        cardGridArrayAdapter.notifyDataSetChanged();
        action2.setClickable(true);
        action2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);*/
                ;
                if (ConfirmDelete("delete")) {
//                    cards.remove(getCardView().getCard());
//                    cardGridArrayAdapter.notifyDataSetChanged();
                }
                cardGridArrayAdapter.notifyDataSetChanged();


                /*Intent intent = new Intent(getContext(),Acceptance.class);
                intent.putExtra("seal_no",SealNo);
                Log.e("seal",SealNo);
                getContext().startActivity(intent);*/
            }
        });
        Spinner xray = (Spinner) view.findViewById(R.id.xray);
        xray.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = parent.getItemAtPosition(position).toString();
                Log.e("XRAY", selected_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner box_type = (Spinner) view.findViewById(R.id.box_type);
        box_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = parent.getItemAtPosition(position).toString();
                if (selected_item.equals("Standard")) {
                    showDialog();
                }

                Log.e("Box type", selected_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*
        *
        *  RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioOptions);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                  @Override
                                                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                      // checkedId is the RadioButton selected

                                                      RadioButton rb = (RadioButton) findViewById(checkedId);
                                                      if (rb.getText().toString().equals("Paybill Number")) {
                                                          type = "  Paybill Number  ";
                                                          Log.e("Paybill","bills");
                                                      } else {
                                                          type = "Buy Goods/Services";
                                                          Log.e("Buy Gooods","goods");
                                                      }

                                                  }


                                              }
        );*/

    }

    protected void showDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Box Count and weight");
        alertDialogBuilder.setMessage("choose Box-dimension and type box count");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);


        EditText input = new EditText(context);
        input.setHint("Enter box count");
        layout.addView(input);


        Spinner spinner = new Spinner(context);
        List<String> dimension = new ArrayList<String>();
        dimension.add("100x33x20");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, dimension);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        layout.addView(spinner);

        alertDialogBuilder.setView(layout);

        alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

// TODO Will do the variance checking here
                WeightDialog("0.0", "0.0", "0.0");
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

        Button button= new Button(context);
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


    protected boolean ConfirmDelete(String message) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(message);
        alertDialogBuilder.setMessage("Are you sure you want to "+message+"?");

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

