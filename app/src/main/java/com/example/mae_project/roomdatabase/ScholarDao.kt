package com.example.mae_project.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mae_project.dataclasses.MajorDataClass
import com.example.mae_project.dataclasses.ScholarDataClass

@Dao
interface ScholarDao {
    @Insert
    suspend fun insertScholar(value: ScholarDataClass)

    @Query("Select * from scholar_table")
    suspend fun getScholars(): List<ScholarDataClass>

    @Query("Delete from scholar_table where id = :id")
    suspend fun deleteScholar(id: Int)
}