package com.example.vaccineapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.vaccineapp.FBref.refStudents;

/**
 * The changeData activity.
 *
 *  @author Ori Ofek <oriofek106@gmail.com> 17/04/2021
 *  @version 1.0
 *  @since 17/04/2021
 *  sort description:
 *  this is the activty the implement the exercise that my teacher gave and in this activity I change the students...
 */
public class ChangeData extends AppCompatActivity implements View.OnLongClickListener{

    TextView firstNametv;
    TextView gradetv;
    TextView secondNametv;
    TextView classtv;
    TextView canBetv;
    TextView v1Loctv;
    TextView v1Datetv;
    TextView v2Loctv;
    TextView v2Datetv;
    String key;
    Student stud;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);

        firstNametv = (TextView) findViewById(R.id.firstnameOfStudent);
        gradetv = (TextView) findViewById(R.id.grade);
        secondNametv = (TextView) findViewById(R.id.secondnameOfStudent);
        classtv = (TextView) findViewById(R.id.clas);
        canBetv = (TextView) findViewById(R.id.canBe);
        v1Loctv = (TextView) findViewById(R.id.v1loc);
        v1Datetv = (TextView) findViewById(R.id.v1date);
        v2Loctv = (TextView) findViewById(R.id.v2loc);
        v2Datetv = (TextView) findViewById(R.id.v2date);

        TextView[] idies= {firstNametv,gradetv,secondNametv,classtv,canBetv,v1Loctv,
                v1Datetv,v2Loctv,v2Datetv};

        for (int i = 0; i < idies.length; i++)
        {
            idies[i].setOnLongClickListener(this);
        }

        // if we got a command from other activity
        if(getIntent().getBooleanExtra("toDo",false))
        {
           key = getIntent().getStringExtra("name");
           show(key);
        }
    }

    private void show(String key) {
        Query q = refStudents.orderByKey().equalTo(key);

        // there is only one student with a special key
        ValueEventListener stuListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dS) {
                for (DataSnapshot data : dS.getChildren()) {

                    // get the stud
                    stud = data.getValue(Student.class);

                    // set the tvs
                    if (!stud.getCanBeVaccinated())
                    {
                        stud.getVaccine1().setData("");
                        stud.getVaccine2().setData("");
                        stud.getVaccine1().setPlace("");
                        stud.getVaccine2().setPlace("");
                    }

                    firstNametv.setText(stud.getFirstName());
                    secondNametv.setText(stud.getSecondName());
                    gradetv.setText(stud.getGrade());
                    classtv.setText(stud.getClassStud());
                    v1Datetv.setText(stud.getVaccine1().getData());
                    v2Datetv.setText(stud.getVaccine2().getData());
                    v1Loctv.setText(stud.getVaccine1().getPlace());
                    v2Loctv.setText(stud.getVaccine2().getPlace());
                    canBetv.setText(String.valueOf(stud.getCanBeVaccinated()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        q.addListenerForSingleValueEvent(stuListener);
    }

    /**
     * onLongClick.
     * short dec: take teh data and change the db
     *
     * <p>
     *      View view
     * @param	view - see which tv pressed
     * @return	none
     */
    @Override
    public boolean onLongClick(View view) {
        TextView[] textVies= {firstNametv,gradetv,secondNametv,classtv,canBetv,v1Loctv,
                v1Datetv,v2Loctv,v2Datetv};
        int[] idies= {(R.id.firstnameOfStudent),(R.id.grade),(R.id.secondnameOfStudent),(R.id.clas),(R.id.canBe),(R.id.v1loc),
                (R.id.v1date),(R.id.v2loc),(R.id.v2date)};
        String[] des = { "first name", "grade", "second name" ,"class","status","first vaccine location","first vaccine date","second vaccine location","second vaccine date"};


        // create alert
        builder = new AlertDialog.Builder(this);
        builder.setTitle("enter " + des[findIndex(idies, view.getId())]);
        final EditText et = new EditText(this);
        final ToggleButton t = new ToggleButton(this);
        final DatePicker d = new DatePicker(this);

        // if it is phone number
        if (findIndex(idies, view.getId()) == 3)
        {
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(et);
        }
        else if(findIndex(idies, view.getId()) == 6 || findIndex(idies, view.getId()) == 8 ) // date
            {
                d.setCalendarViewShown(false);

                builder.setView(d);
            }
            else if(findIndex(idies, view.getId()) == 4 ) // toggle
            {
                t.setTextOff("can't be");
                t.setTextOn("can be");
                t.setChecked(true);
                builder.setView(t);
            }
            else // reguler
            {
                builder.setView(et);
            }

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog,
                                    int which) {

                    switch (findIndex(idies, view.getId()))
                    {
                        case 0: // the first name
                            stud.setFirstName(et.getText().toString());
                            textVies[findIndex(idies, view.getId())].setText(et.getText());
                            break;
                        case 1: // the grade
                            stud.setGrade(et.getText().toString());
                            textVies[findIndex(idies, view.getId())].setText(et.getText());
                            break;
                        case 2: // the second name
                            stud.setSecondName(et.getText().toString());
                            textVies[findIndex(idies, view.getId())].setText(et.getText());
                            break;
                        case 3: // the class
                            stud.setClassStud(et.getText().toString());
                            textVies[findIndex(idies, view.getId())].setText(et.getText());
                            break;
                        case 4: // the allergic
                            stud.setCanBeVaccinated(t.isChecked());
                            textVies[findIndex(idies, view.getId())].setText(String.valueOf(t.isChecked()));
                            break;
                        case 5: // the first vaccine place
                            if(!stud.getCanBeVaccinated())
                            {
                                Toast.makeText(ChangeData.this, "this student can be vancianted!", Toast.LENGTH_SHORT).show();
                            }
                            else if (stud.getVaccine1().getData().equals("NOT TAKEN"))
                            {
                                Toast.makeText(ChangeData.this, "please update the date first!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                stud.getVaccine1().setPlace(et.getText().toString());
                                textVies[findIndex(idies, view.getId())].setText(et.getText());
                            }
                            break;
                        case 6: // the first vaccine date
                            if(!stud.getCanBeVaccinated())
                            {
                                Toast.makeText(ChangeData.this, "this student can be vancianted!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                stud.getVaccine1().setData(String.valueOf(d.getDayOfMonth()) + ":" + String.valueOf(d.getMonth() + 1) + ":" + d.getYear());
                                textVies[findIndex(idies, view.getId())].setText(String.valueOf(d.getDayOfMonth()) + ":" + String.valueOf(d.getMonth() + 1) + ":" + d.getYear());
                            }

                            break;
                        case 7: // v2 loc
                            if(!stud.getCanBeVaccinated())
                            {
                                Toast.makeText(ChangeData.this, "this student can be vancianted!", Toast.LENGTH_SHORT).show();
                            }
                            else if (stud.getVaccine1().getData().equals("NOT TAKEN"))
                            {
                                Toast.makeText(ChangeData.this, "please update the first vaccine!", Toast.LENGTH_SHORT).show();
                            }
                            else if(stud.getVaccine2().getData().equals("NOT TAKEN"))
                            {
                                Toast.makeText(ChangeData.this, "please update the date first!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                stud.getVaccine2().setPlace(et.getText().toString());
                                textVies[findIndex(idies, view.getId())].setText(et.getText());
                            }

                            break;
                        case 8: // v2 date
                            if(!stud.getCanBeVaccinated())
                            {
                                Toast.makeText(ChangeData.this, "this student can be vancianted!", Toast.LENGTH_SHORT).show();
                            }
                            else if (stud.getVaccine1().getData().equals("NOT TAKEN"))
                            {
                                Toast.makeText(ChangeData.this, "please update the first vaccine!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                stud.getVaccine2().setData(String.valueOf(d.getDayOfMonth()) + ":" + String.valueOf(d.getMonth() + 1) + ":" + d.getYear());
                                textVies[findIndex(idies, view.getId())].setText(String.valueOf(d.getDayOfMonth()) + ":" + String.valueOf(d.getMonth() + 1) + ":" + d.getYear());
                            }

                            break;
                    }

                    refStudents.child(key).removeValue();
                    key = stud.getGrade() + stud.getClassStud() + stud.getFirstName() + stud.getSecondName() + String.valueOf(stud.getCanBeVaccinated()) +
                            stud.getVaccine1().getData() +stud.getVaccine1().getPlace() + stud.getVaccine2().getData() + stud.getVaccine2().getPlace();
                    refStudents.child(key).setValue(stud);


                    // need to change thew graeds to the new ID and to update the system
                    dialog.cancel();
                }
            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();
            // Show the Alert Dialog box
        alertDialog.show();

        return true;
    }

    /**
     * findIndex.
     * short dec: return the index of the id (-1 not found)
     *
     * <p>
     *      int[] idies
     *      int id
     * @param	id - the id that we wanna find, idies - the arr of the idies
     * @return	none
     */
    private int findIndex(int[] idies, int id) {
        for (int i = 0; i < idies.length; i++)
        {
            if(id == idies[i])
            {
                return (i);
            }
        }
        return -1;
    }
}
