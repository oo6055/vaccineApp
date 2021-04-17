package com.example.vaccineapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The FBRef activity.
 *
 *  @author Ori Ofek <oriofek106@gmail.com> 17/04/2021
 *  @version 1.0
 *  @since 17/04/2021
 *  sort description:
 *  this is the activty the implement the exercise that my teacher gave and in this activity there is a connection to the fireBase
 */
public class FBref {

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    public static DatabaseReference refStudents=FBDB.getReference("Students");
}
