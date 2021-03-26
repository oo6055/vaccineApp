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
        /*
        String[] student = {Students.NAME,Students.CLASS,Students.ADDRESS,Students.PRIVATE_PHONE,Students.HOME_PHONE
                ,Students.MOTHER_NAME,Students.FATHER_NAME,Students.MOTHER_PHONE
                ,Students.FATHER_PHONE};
        String[] des = { "name", "class", "address" ,"personal Phone","home Phone","mother Name","father Name","mother Phone","father Phone"};


        if (thereIsSomeOne)
        {
            // create alert
            builder = new AlertDialog.Builder(UpdateStudent.this);
            builder.setTitle("enter " + des[findIndex(idies, view.getId())]);
            final EditText et = new EditText(this);

            // if it is phone number
            if (findIndex(idies, view.getId()) == 3 || findIndex(idies, view.getId()) == 4 || findIndex(idies, view.getId()) == 7 || findIndex(idies, view.getId()) == 8)
            {
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
            }

            builder.setView(et);


            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog,
                                    int which) {

                    // get the ID
                    String previousId = getId(nametv.getText().toString());

                    ContentValues values;
                    values = new ContentValues();

                    values.put(Students.ACTIVE, false);

                    db = hlp.getWritableDatabase();

                    // change the active to false in the old student
                    db.update(Students.TABLE_STUDENTS, values, "_id = ?", new String[]{getId(nametv.getText().toString())});
                    db.close();
                    textVies[findIndex(idies, view.getId())].setText(et.getText());

                    values = new ContentValues();
                    values.put(Students.NAME, nametv.getText().toString());
                    values.put(Students.ADDRESS, addresstv.getText().toString());
                    values.put(Students.PRIVATE_PHONE, personalPhonetv.getText().toString());
                    values.put(Students.HOME_PHONE, homePhonetv.getText().toString());
                    values.put(Students.MOTHER_NAME, motherNametv.getText().toString());
                    values.put(Students.FATHER_NAME, fatherNametv.getText().toString());
                    values.put(Students.MOTHER_PHONE, motherPhonetv.getText().toString());
                    values.put(Students.FATHER_PHONE, fatherPhonetv.getText().toString());
                    values.put(Students.CLASS, gradetv.getText().toString());
                    values.put(Students.ACTIVE, true);
                    db = hlp.getWritableDatabase();


                    db.insert(Students.TABLE_STUDENTS, null,values);

                    values = new ContentValues();
                    values.put(Grades.STUDENT,getId(nametv.getText().toString())); // the new ID
                    db.update(Grades.TABLE_GRADES, values, "Student = ?", new String[]{previousId});
                    db.close();

                    db.close();

                    // need to change thew graeds to the new ID and to update the system
                    dialog.cancel();
                }
            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();
        }
        */


        return true;
    }
}
