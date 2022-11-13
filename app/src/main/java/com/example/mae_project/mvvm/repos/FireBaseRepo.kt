package com.example.mae_project.mvvm.repos

import android.content.Context
import com.example.mae_project.dataclasses.MajorDataClass
import com.example.mae_project.dataclasses.ScholarDataClass
import com.example.mae_project.dataclasses.UniversityDataClass
import com.example.mae_project.dataclasses.UserDataClass
import com.example.mae_project.utils.FireBaseRefrences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Job

class FireBaseRepo(val mAuth: FirebaseAuth, val context: Context, val mRef: FireBaseRefrences) {

    suspend fun loadUniversities(returnValue: (MutableList<UniversityDataClass>) -> Unit) {
        mRef.uniRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list: MutableList<UniversityDataClass> = mutableListOf()
                snapshot.children.forEach {
                    list.add(it.getValue(UniversityDataClass::class.java) as UniversityDataClass)
                }
                returnValue.invoke(list)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    suspend fun loadMajors(returnValue: (MutableList<MajorDataClass>) -> Unit) {
        mRef.majorRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list: MutableList<MajorDataClass> = mutableListOf()
                snapshot.children.forEach {
                    list.add(it.getValue(MajorDataClass::class.java) as MajorDataClass)
                }
                returnValue.invoke(list)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    suspend fun loadScholars(returnValue: (MutableList<ScholarDataClass>) -> Unit) {
        mRef.scholars.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list: MutableList<ScholarDataClass> = mutableListOf()
                snapshot.children.forEach {
                    list.add(it.getValue(ScholarDataClass::class.java) as ScholarDataClass)
                }
                returnValue.invoke(list)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    suspend fun createAccount(
        value: UserDataClass,
        password: String,
        onSuccess: () -> Job,
        onFail: (String) -> Job
    ) {
        val pushKey = mRef.userRef.push().key
        mAuth.createUserWithEmailAndPassword(value.email!!, password).addOnCompleteListener {
            if (it.isSuccessful) {
                value.pushId = pushKey
                value.uId = it.result.user?.uid
                mRef.userRef.child(pushKey!!)?.setValue(value)
                    ?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            onSuccess()
                        } else {
                            onFail.invoke("Fail to add data")
                        }
                    }

            } else {
                onFail.invoke("Fail to create account")
            }
        }.addOnFailureListener {
            onFail.invoke(it.message.toString())
        }
    }
}