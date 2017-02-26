package com.kuehnenagel.android;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    protected String TotalNo;
    protected Context cxt;
    protected String Action;

    public DispatchCard(Context context) {
        super(context, R.layout.dispatch_content_detail);
        cxt=context;

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
                Intent intent = new Intent(getContext(),HomePage.class);
//                intent.putExtra("seal_no",PNumber);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                /*intent.putExtra("name",getCardHeader().getTitle());
                intent.putExtra("TransportId",getId());
                Log.e("transport id is ",getId());*/
                cxt.startActivity(intent);
            }
        });



    }

}

