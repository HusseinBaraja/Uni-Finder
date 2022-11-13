package com.example.mae_project.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mae_project.dataclasses.MajorDataClass
import com.example.mae_project.dataclasses.ScholarDataClass
import com.example.mae_project.dataclasses.UniversityDataClass

@Dao
interface UniversityDao {
    @Insert
    suspend fun insertUniversity(value: UniversityDataClass)

    @Query("Select * from university_table")
    suspend fun getUniversity(): List<UniversityDataClass>

    @Query("Delete from university_table where id = :id")
    suspend fun deleteUniversity(id: Int)
}