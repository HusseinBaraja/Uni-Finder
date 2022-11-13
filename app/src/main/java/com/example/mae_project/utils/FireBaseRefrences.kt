package com.example.mae_project.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FireBaseRefrences {
    val userRef: DatabaseReference= FirebaseDatabase.getInstance().getReference("users")

    val uniRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("universities")
    val majorRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("majors")
    val scholars: DatabaseReference = FirebaseDatabase.getInstance().getReference("scholarship")
}