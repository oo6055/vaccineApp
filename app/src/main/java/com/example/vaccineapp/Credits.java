package com.example.vaccineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * The Credits activity.
 *
 *  @author Ori Ofek <oriofek106@gmail.com> 17/04/2021
 *  @version 1.0
 *  @since 17/04/2021
 *  sort description:
 *  this is the activty the implement the exercise that my teacher gave and in this credits activity...
 */
public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }


    /**
     * onCreateContextMenu
     * Short description.
     * onCreateContextMenu listener use for the ContextMenu
     * <p>
     *     ContextMenu menu
     *     View v
     *     ContextMenu.ContextMenuInfo menuInfo
     *
     * @param  menu - the object,v - the item that selected ,menuInfo - the info
     * @return	true if it success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.generalmenu, menu);
        return true;
    }

    /**
     * onOptionsItemSelected
     * Short description.
     * what happen if an item was selected
     * <p>
     *     MenuItem item
     *
     * @param  item - the menuItem
     * @return	true if it success
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String whatClicked = (String) item.getTitle();
        Intent si;

        if(whatClicked.equals("enterData"))
        {
            si = new Intent(this,MainActivity.class);
            startActivity(si);
        }
        else if(whatClicked.equals("showData"))
        {
            si = new Intent(this,ShowData.class);
            startActivity(si);
        }

        return  true;
    }
}