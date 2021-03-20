package com.example.vaccineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.vaccineapp.FBref.refStudents;

public class ShowData extends AppCompatActivity {

    ListView ls;
    ValueEventListener stuListener;
    ArrayList<Student> dataArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        dataArr = new ArrayList<Student>();
        ls = (ListView)findViewById(R.id.ls);

        readData();
    }

    private void readData() {
        ValueEventListener stuListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dS) {
                dataArr.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    Student stuTmp = data.getValue(Student.class);
                    dataArr.add(stuTmp);
                }
                CustomAdapter adp = new CustomAdapter(getApplicationContext(),dataArr);
                ls.setAdapter(adp);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        refStudents.addListenerForSingleValueEvent(stuListener);

    }
    public void showByClasses(View view) {

        Query q = refStudents.orderByChild("grade");
        ValueEventListener stuListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dS) {
                dataArr.clear();
                for(DataSnapshot data : dS.getChildren()) {
                    Student stuTmp = data.getValue(Student.class);
                    dataArr.add(stuTmp);
                }
                CustomAdapter adp = new CustomAdapter(getApplicationContext(),dataArr);
                ls.setAdapter(adp);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        q.addListenerForSingleValueEvent(stuListener);
    }
}


class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Student> data;
    LayoutInflater inflter;

    /**
     * CustomAdapter
     * Short description.
     * the constuctor
     *
     * <p>
     *     Context applicationContext
     *     ArrayList<String> data
     * @param  data - the data in this format subject:grade:samster or name:grade:samster, applicationContext - the app contance
     * @return	true if it success
     */
    public CustomAdapter(Context applicationContext, ArrayList<Student> data) {
        this.context = context;
        this.data = data;
        inflter = (LayoutInflater.from(applicationContext));
    }

    /**
     * getCount
     * Short description.
     * get the number of the grades
     *
     * @return the number of the elements
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * getItem
     * Short description.
     * I need to do it
     * <p>
     *    int i
     *
     * @param  i - the number of the object
     * @return null
     */
    @Override
    public Object getItem(int i) {
        return null;
    }

    /**
     * getItemId
     * Short description.
     * I need to do it
     * <p>
     *    int i
     *
     * @param  i - the number of the object
     * @return null
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * getView
     * Short description.
     * in oder to create the view
     * <p>
     *    int i
     *    View view
     *    ViewGroup viewGroup
     *
     * @param  i - the number of the object,view - the view , viewGroup - the viewGroup
     * @return null
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Student stud = data.get(i);
        view = inflter.inflate(R.layout.custom_lv_layout, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        ImageView vac1 = (ImageView) view.findViewById(R.id.vac1);
        ImageView vac2 = (ImageView) view.findViewById(R.id.vac2);
        ImageView canBe = (ImageView) view.findViewById(R.id.canBecl);
        TextView grade = (TextView) view.findViewById(R.id.gradeCl);

        name.setTextColor(Color.BLACK);
        grade.setTextColor(Color.BLACK);

        name.setText(stud.getFirstName() + " " + stud.getSecondName());
        grade.setText(stud.getGrade().replace(":","'"));

        if (stud.getCanBeVaccinated())
        {
            canBe.setImageResource(R.drawable.canbe);

            if (stud.getVaccine1().getPlace().equals("NOT TAKEN"))
            {
                vac1.setImageResource(android.R.drawable.checkbox_off_background);
            }
            else
            {
                vac1.setImageResource(android.R.drawable.checkbox_on_background);
            }

            if (stud.getVaccine2().getPlace().equals("NOT TAKEN"))
            {
                vac2.setImageResource(android.R.drawable.checkbox_off_background);
            }
            else
            {
                vac2.setImageResource(android.R.drawable.checkbox_on_background);
            }
        }
        else
        {
            canBe.setImageResource(R.drawable.notpossible);
            vac2.setImageResource(android.R.drawable.checkbox_off_background);
            vac1.setImageResource(android.R.drawable.checkbox_off_background);
        }

        return view;
    }
}