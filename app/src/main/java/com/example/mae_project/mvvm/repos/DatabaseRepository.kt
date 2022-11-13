package com.example.mae_project.mvvm.repos

import android.content.Context
import androidx.room.Room
import com.example.mae_project.dataclasses.MajorDataClass
import com.example.mae_project.dataclasses.ScholarDataClass
import com.example.mae_project.dataclasses.UniversityDataClass
import com.example.mae_project.roomdatabase.DatabaseDao

open class DatabaseRepository(val context: Context) {
    private var mDatabase: DatabaseDao? = null
    var universities: Universities? = null
    var majors: Majors? = null
     var scholarShip: ScholarShip? = null

    init {
        mDatabase = Room.databaseBuilder(
            context.applicationContext,
            DatabaseDao::class.java, "_database"
        )
            .build()
        universities = Universities(mDatabase)
        majors = Majors(mDatabase)
        scholarShip = ScholarShip(mDatabase)
    }


    class Universities(private val mDatabaseDao: DatabaseDao?) {
        suspend fun insertData(value: UniversityDataClass) {
            mDatabaseDao?.universities()?.insertUniversity(value)
        }

        suspend fun getData(): List<UniversityDataClass>? {
            return mDatabaseDao?.universities()?.getUniversity()
        }

        suspend fun deleteData(id: Int) {
            mDatabaseDao?.universities()?.deleteUniversity(id)
        }
    }

    class Majors(private val mDatabaseDao: DatabaseDao?) {
        suspend fun insertData(value: MajorDataClass) {
            mDatabaseDao?.majors()?.insertMajor(value)
        }

        suspend fun getData(): List<MajorDataClass>? {
            return mDatabaseDao?.majors()?.getMajors()
        }

        suspend fun deleteData(id: Int) {
            mDatabaseDao?.majors()?.deleteMajor(id)
        }
    }

    class ScholarShip(private val mDatabaseDao: DatabaseDao?) {
        suspend fun insertData(value: ScholarDataClass) {
            mDatabaseDao?.scholars()?.insertScholar(value)
        }

        suspend fun getData(): List<ScholarDataClass>? {
            return mDatabaseDao?.scholars()?.getScholars()
        }

        suspend fun deleteData(id: Int) {
            mDatabaseDao?.scholars()?.deleteScholar(id)
        }
    }
}