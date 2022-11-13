package com.example.mae_project.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mae_project.dataclasses.MajorDataClass
import com.example.mae_project.dataclasses.ScholarDataClass
import com.example.mae_project.dataclasses.UniversityDataClass

@Database(
    entities = [MajorDataClass::class, ScholarDataClass::class, UniversityDataClass::class],
    version = 1
)
abstract class DatabaseDao : RoomDatabase() {
    abstract fun scholars(): ScholarDao
    abstract fun majors(): MajorDao
    abstract fun universities(): UniversityDao
}