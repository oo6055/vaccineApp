package com.example.vaccineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.vaccineapp.FBref.refStudents;

public class MainActivity extends AppCompatActivity {

    String v1DataStr;
    String v2DataStr;
    TextView v1Data;
    TextView v2Data;
    ImageButton v1;
    ImageButton v2;
    EditText v1Place;
    EditText v2Place;
    EditText firstName;
    EditText secondName;
    EditText grade;
    EditText clas;
    boolean cond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v1 = (ImageButton) findViewById(R.id.v1);
        v2 = (ImageButton) findViewById(R.id.v2);
        v1Data = (TextView) findViewById(R.id.v1Data);
        v2Data = (TextView) findViewById(R.id.v2Data);
        v1Place = (EditText) findViewById(R.id.placeV1);
        v2Place = (EditText) findViewById(R.id.v2Place);

        firstName = (EditText) findViewById(R.id.firstName);
        secondName = (EditText) findViewById(R.id.secondName);
        grade = (EditText) findViewById(R.id.grade);
        clas = (EditText) findViewById(R.id.classes);

        cond = true;
        v1DataStr = "NOT TAKEN";
        v2DataStr = "NOT TAKEN";


    }

    public void getDataOfVaccine(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DatePicker picker = new DatePicker(this);
        picker.setCalendarViewShown(false);

        builder.setView(picker);
        builder.setPositiveButton("set",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(view.getId() == v1.getId())
                        {
                            v1DataStr = String.valueOf(picker.getDayOfMonth()) + ":" + String.valueOf(picker.getMonth() + 1) + ":" + picker.getYear(); // why it is strange
                            v1Data.setText(v1DataStr);
                            v2.setVisibility(View.VISIBLE);
                            v2Data.setVisibility(View.VISIBLE);
                            v2Place.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            v2DataStr = String.valueOf(picker.getDayOfMonth()) + ":" + String.valueOf(picker.getMonth() + 1) + ":" + picker.getYear(); // why it is strange
                            v2Data.setText(v2DataStr);
                        }

                    }
                }
        );
        builder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                }
        );

        builder.show();
    }

    public void changeCond(View view) {
        cond = !cond;
        if(cond)
        {
            v1Data.setVisibility(View.VISIBLE);
            v1.setVisibility(View.VISIBLE);
            v1Place.setVisibility(View.VISIBLE);
            if(!v1DataStr.equals("NOT TAKEN"))
            {
                v2.setVisibility(View.VISIBLE);
                v2Data.setVisibility(View.VISIBLE);
                v2Place.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            v1Data.setVisibility(View.INVISIBLE);
            v2Data.setVisibility(View.INVISIBLE);
            v1.setVisibility(View.INVISIBLE);
            v2.setVisibility(View.INVISIBLE);
            v1Place.setVisibility(View.INVISIBLE);
            v2Place.setVisibility(View.INVISIBLE);
        }
    }

    public void submit(View view) {
        Student student=new Student(firstName.getText().toString(),secondName.getText().toString(),grade.getText().toString(),clas.getText().toString()
        ,cond, new Vaccine(v1Place.getText().toString(),v1DataStr),new Vaccine(v2Place.getText().toString(),v2DataStr));
        refStudents.child(grade.getText().toString() + clas.getText().toString() + firstName.getText().toString() + secondName.getText().toString() + String.valueOf(cond) +
                v1DataStr + v1Place.getText().toString() + v2DataStr + v2Place.getText().toString() ).setValue(student);

        v1Data.setText("NOT TAKEN");
        v2Data.setText("NOT TAKEN");
        v1Place.setText("");
        v2Place.setText("");
        firstName.setText("");
        secondName.setText("");
        grade.setText("");
        clas.setText("");
        v1DataStr = "NOT TAKEN";
        v2DataStr = "NOT TAKEN";

        // clear the screen
        v2.setVisibility(View.GONE);
        v2Data.setVisibility(View.GONE);
        v2Place.setVisibility(View.GONE);
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

        if(whatClicked.equals("showData"))
        {
            si = new Intent(this,ShowData.class);
            startActivity(si);
        }

        return  true;
    }
}



