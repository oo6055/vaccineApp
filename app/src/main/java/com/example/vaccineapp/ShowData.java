package com.example.vaccineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.vaccineapp.FBref.refStudents;

public class ShowData extends AppCompatActivity implements View.OnCreateContextMenuListener {

    ListView ls;
    ArrayList<Student> dataArr;
    ArrayList<String> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        dataArr = new ArrayList<Student>();
        ids = new ArrayList<String>();
        ls = (ListView) findViewById(R.id.ls);
        ls.setOnCreateContextMenuListener(this);


    }


    public void showByGrades(View view) {

        Query q = refStudents.orderByChild("grade");
        q.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dS) {
                dataArr.clear();
                ids.clear();
                for (DataSnapshot data : dS.getChildren()) {
                    Student stuTmp = data.getValue(Student.class);
                    dataArr.add(stuTmp);
                    ids.add(data.getKey());
                }
                CustomAdapter adp = new CustomAdapter(getApplicationContext(), dataArr);
                ls.setAdapter(adp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void showByOneGrade(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText et = new EditText(this);

        builder.setView(et);
        builder.setTitle("enter the grade that U wanne take:");

        builder.setPositiveButton("submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Query q = refStudents.orderByChild("grade").startAt(et.getText().toString()).endAt(et.getText().toString());
                        q.addListenerForSingleValueEvent( new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dS) {
                                dataArr.clear();
                                ids.clear();
                                for (DataSnapshot data : dS.getChildren()) {
                                    Student stuTmp = data.getValue(Student.class);
                                    dataArr.add(stuTmp);
                                    ids.add(data.getKey());
                                }
                                CustomAdapter adp = new CustomAdapter(getApplicationContext(), dataArr);
                                ls.setAdapter(adp);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

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

    public void bySpecificClass(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText clas = new EditText(this);
        final EditText grade = new EditText(this);

        clas.setHint("class");
        grade.setHint("grade");
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(clas);
        ll.addView(grade);
        builder.setView(ll);
        builder.setTitle("enter the specific class that U wanne take:");

        builder.setPositiveButton("submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Query q = refStudents.orderByChild("classFormat").startAt(grade.getText().toString() + ":" + clas.getText().toString()).endAt(grade.getText().toString() + ":" + clas.getText().toString());
                        q.addListenerForSingleValueEvent( new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dS) {
                                dataArr.clear();
                                ids.clear();
                                for (DataSnapshot data : dS.getChildren()) {
                                    Student stuTmp = data.getValue(Student.class);
                                    dataArr.add(stuTmp);
                                    ids.add(data.getKey());
                                }
                                CustomAdapter adp = new CustomAdapter(getApplicationContext(), dataArr);
                                ls.setAdapter(adp);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

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
     * @return	none
     */
    //@Overrid
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("options");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
    }

    /**
     * onContextItemSelected
     * Short description.
     * onContextItemSelected listener use for the ContextMenu
     * <p>
     *     MenuItem item
     *
     * @param  item - the item that selected
     * @return	true if it worked
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String op = item.getTitle().toString();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int i = info.position;
        Intent si;

        if (op.equals("change"))
        {
            si = new Intent(this,ChangeData.class);
            si.putExtra("name",ids.get(i));
            si.putExtra("toDo",true);
            startActivity(si);
        }
        else if (op.equals("delete"))
        {

            refStudents.child(ids.get(i)).removeValue();

            ls.setAdapter(null);
        }
        return true;
    }

    public void notCanBeVancied(View view) {
        Query q = refStudents.orderByChild("canBeVaccinated").equalTo(false);
        q.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dS) {
                dataArr.clear();
                ids.clear();
                for (DataSnapshot data : dS.getChildren()) {
                    Student stuTmp = data.getValue(Student.class);
                    dataArr.add(stuTmp);
                    ids.add(data.getKey());
                }
                CustomAdapter adp = new CustomAdapter(getApplicationContext(), dataArr);
                ls.setAdapter(adp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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

        return  true;
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
        grade.setText(stud.getGrade() + "'" + stud.getClassStud());

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