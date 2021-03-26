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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.vaccineapp.FBref.refStudents;

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
    AutoCompleteTextView students;
    ArrayList<String> tbl = new ArrayList<>();
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

            // if it is phone number
            if (findIndex(idies, view.getId()) == 3 || findIndex(idies, view.getId()) == 4 || findIndex(idies, view.getId()) == 7 || findIndex(idies, view.getId()) == 8)
            {
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            else
            {
                builder.setView(et);
            }



            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog,
                                    int which) {
                    textVies[findIndex(idies, view.getId())].setText(et.getText());

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
