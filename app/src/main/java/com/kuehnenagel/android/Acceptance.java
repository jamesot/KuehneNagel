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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Acceptance extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String seal_no, one, two, three, four, five;
    @Bind(R.id.temp1)
    EditText temp1;
    @Bind(R.id.temp2)
    EditText temp2;
    @Bind(R.id.temp3)
    EditText temp3;
    @Bind(R.id.temp4)
    EditText temp4;
    @Bind(R.id.temp5)
    EditText temp5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        setTitle(getIntent().getStringExtra("name"));

        Button button = (Button) findViewById(R.id.next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                one = temp1.getText().toString();
                two = temp2.getText().toString();
                three = temp3.getText().toString();
                four = temp4.getText().toString();
                five = temp5.getText().toString();

                if (!one.isEmpty() && !two.isEmpty() && !three.isEmpty() && !four.isEmpty() && !five.isEmpty()) {

                    if (temp1.getText().toString().equals("10") || temp2.getText().toString().equals("10")
                            || temp3.getText().toString().equals("10") || temp4.getText().toString().equals("10")
                            || temp5.getText().toString().equals("10") || Integer.parseInt(one) > 10 ||
                            Integer.parseInt(two) > 10 || Integer.parseInt(three) > 10 || Integer.parseInt(four) > 10 ||
                            Integer.parseInt(five) > 10) {
                        showDialog();


                    } else {
                        Intent intent = new Intent(getBaseContext(), ConsigneeInput.class);
                        intent.putExtra("name", getIntent().getStringExtra("name"));
                        intent.putExtra("seal_no",getIntent().getStringExtra("seal_no"));
                        intent.putExtra("TransportId", getIntent().getStringExtra("TransportId"));
                        intent.putExtra("ShipperId",getIntent().getStringExtra("ShipperId"));
                        startActivity(intent);
                    }

                } else {
//                    MyShortcuts.showToast("Kindly fill all the temperatures!",getBaseContext());
                    Toast.makeText(getBaseContext(), "Kindly fill all the temperatures!", Toast.LENGTH_LONG);
                }

            }
        });

//        TODO testing morphing button
        final MorphingButton btnMorph1 = (MorphingButton) findViewById(R.id.btnMorph1);
        final MorphingButton btnMorph2 = (MorphingButton) findViewById(R.id.btnMorph2);
        final MorphingButton btnMorph3 = (MorphingButton) findViewById(R.id.btnMorph3);
        final MorphingButton btnMorph4 = (MorphingButton) findViewById(R.id.btnMorph4);
        final MorphingButton btnMorph5 = (MorphingButton) findViewById(R.id.btnMorph5);

        temp1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {
                    String t1 = temp1.getText().toString();
                    if (!t1.isEmpty()) {
                        if (t1.equals("0") || t1.equals("1") || t1.equals("2") || t1.equals("3") || t1.equals("4")) {
                            morphToSuccess(btnMorph1);
                        } else if (t1.equals("5") || t1.equals("6")) {
                            morphToFair(btnMorph1);
                        } else {
                            morphToFailure(btnMorph1, 20);
                        }
                    } else {
                        temp1.setError("Empty");
                    }
                }

            }
        });


        temp2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {
                    String t1 = temp2.getText().toString();
                    if (!t1.isEmpty()) {
                        if (t1.equals("0") || t1.equals("1") || t1.equals("2") || t1.equals("3") || t1.equals("4")) {
                            morphToSuccess(btnMorph2);
                        } else if (t1.equals("5") || t1.equals("6")) {
                            morphToFair(btnMorph2);
                        } else {
                            morphToFailure(btnMorph2, 20);
                        }
                    } else {
                        temp2.setError("Empty");
                    }
                }

            }
        });


        temp3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {
                    String t1 = temp3.getText().toString();
                    if (!t1.isEmpty()) {
                        if (t1.equals("0") || t1.equals("1") || t1.equals("2") || t1.equals("3") || t1.equals("4")) {
                            morphToSuccess(btnMorph3);
                        } else if (t1.equals("5") || t1.equals("6")) {
                            morphToFair(btnMorph3);
                        } else {
                            morphToFailure(btnMorph3, 20);
                        }
                    } else {
                        temp3.setError("Empty");
                    }
                }

            }
        });


        temp4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {
                    String t1 = temp4.getText().toString();
                    if (!t1.isEmpty()) {
                        if (t1.equals("0") || t1.equals("1") || t1.equals("2") || t1.equals("3") || t1.equals("4")) {
                            morphToSuccess(btnMorph4);
                        } else if (t1.equals("5") || t1.equals("6")) {
                            morphToFair(btnMorph4);
                        } else {
                            morphToFailure(btnMorph4, 20);
                        }
                    } else {
                        temp4.setError("Empty");
                    }
                }

            }
        });

        temp5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus) {
                    String t1 = temp5.getText().toString();
                    if (!t1.isEmpty()) {
                        if (t1.equals("0") || t1.equals("1") || t1.equals("2") || t1.equals("3") || t1.equals("4")) {
                            morphToSuccess(btnMorph5);
                        } else if (t1.equals("5") || t1.equals("6")) {
                            morphToFair(btnMorph5);
                        } else {
                            morphToFailure(btnMorph5, 20);
                        }
                    } else {
                        temp5.setError("Empty");
                    }
                }

            }
        });


        btnMorph1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morphToFailure(btnMorph1, 20);
                morphToSuccess(btnMorph2);
                morphToFair(btnMorph3);
                morphToSuccess(btnMorph4);
                morphToSuccess(btnMorph5);
            }
        });


        seal_no = getIntent().getStringExtra("seal_no");
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Undo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
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

        } else if (id == R.id.dispatch) {
            Intent intent = new Intent(getBaseContext(), Dispatch.class);
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void morphToSquare(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
                .width(dimen(R.dimen.mb_width_200))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_blue))
                .colorPressed(color(R.color.mb_blue_dark))
                .text(getString(R.string.mb_button));
        btnMorph.morph(square);
    }

    private void morphToSuccess(final MorphingButton btnMorph) {
        btnMorph.setVisibility(View.VISIBLE);
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(integer(R.integer.mb_animation))
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_green))
                .colorPressed(color(R.color.mb_green_dark))
                .icon(R.drawable.ic_done);
        btnMorph.morph(circle);
    }

    private void morphToFair(final MorphingButton btnMorph) {
        btnMorph.setVisibility(View.VISIBLE);
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(integer(R.integer.mb_animation))
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.holo_orange_light))
                .colorPressed(color(R.color.holo_orange_light))
                .icon(R.drawable.ic_done);
        btnMorph.morph(circle);
    }

    private void morphToFailure(final MorphingButton btnMorph, int duration) {
        btnMorph.setVisibility(View.VISIBLE);
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(integer(R.integer.mb_animation))
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_red))
                .colorPressed(color(R.color.mb_red_dark))
                .icon(R.drawable.ic_x);
        btnMorph.morph(circle);
    }


    protected void showDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Acceptance.this);

        alertDialogBuilder.setTitle("Prompt!");
        alertDialogBuilder.setMessage("Vacuum cooling required. Do you want to proceed?");

       /* LinearLayout layout = new LinearLayout(getBaseContext());
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

        alertDialogBuilder.setView(layout);*/

        alertDialogBuilder.setPositiveButton("Yes Proceed", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                WeightDialog("0.0", "0.0", "0.0");
                Intent intent = new Intent(getBaseContext(), ConsigneeInput.class);
                intent.putExtra("name", getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("No don't", new DialogInterface.OnClickListener() {

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
