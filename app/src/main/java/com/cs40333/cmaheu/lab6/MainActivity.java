package com.cs40333.cmaheu.lab6;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.design.widget.CoordinatorLayout;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String[]> teamstrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ND Athletics");

        MyCsvFileReader reader = new MyCsvFileReader(this);
        teamstrings = reader.readCsvFile(R.raw.schedule);
        final ArrayList<Team> teams = new ArrayList<>();

        for (int i = 0; i < teamstrings.size(); i++) {
            String[] istring = teamstrings.get(i);
            try {
                teams.add(new Team(istring[0], istring[1], istring[2], istring[3],
                        istring[4], istring[5], istring[6], istring[7], istring[8]));
            } catch (ArrayIndexOutOfBoundsException e) {
                for (int j = 0; j < istring.length; j++) {
                    Log.d("array out of bounds", Integer.toString(j).concat(istring[j]).concat("\n"));
                }
            }
        }


        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this, teams);

        ListView scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        scheduleListView.setAdapter(scheduleAdapter);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("team", teams.get(position)); //where al is your ArrayList holding team information.
                startActivity(intent);
            }
        };
        scheduleListView.setOnItemClickListener(clickListener);
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();

        if (res_id == R.id.share) {
// code for sharing the schedule
            Intent shareIntent = new Intent();
            shareIntent.setAction(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "BasketBall Matches");
            shareIntent.putExtra(Intent.EXTRA_TEXT, gameSchedule());
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        } else if (res_id == R.id.sync) {
// Snackbar with Try Again action
            final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Sync is not yet implemented", Snackbar.LENGTH_LONG);
            snackbar.setAction("Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(coordinatorLayout, "Wait for the next few labs. Thank you for your patience", Snackbar.LENGTH_LONG).show();
                }
            });
            snackbar.show();
        } else if (res_id == R.id.settings) {
            // Floating Contextual Menu with options
            registerForContextMenu(findViewById(R.id.toolbar));
            this.openContextMenu(findViewById(R.id.toolbar));
        }
        return true;
    }

    public String gameSchedule ()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < teamstrings.size(); i++) {
            String[] istring = teamstrings.get(i);
            try {
                sb.append(istring[0]);
                sb.append("\t");
                sb.append(istring[5]);
                sb.append("\t");
                sb.append(istring[2]);
                sb.append("\n");
            } catch (ArrayIndexOutOfBoundsException e) {
                for (int j = 0; j < istring.length; j++) {
                    Log.d("array out of bounds", Integer.toString(j).concat(istring[j]).concat("\n"));
                }
            }
        }
        return sb.toString();
    }

    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.floating_contextual_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int item_id = item.getItemId();
        if (item_id == R.id.women) {
            // to be implemented later
        } else if (item_id == R.id.men) {
            // to be implemented later
        } else if (item_id == R.id.oncampus) {
            // to be implemented later
        } else if (item_id == R.id.offcampus) {
            // to be implemented later
        }

        return false;

    }
}