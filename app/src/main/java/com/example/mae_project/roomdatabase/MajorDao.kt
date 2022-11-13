package com.example.mae_project.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mae_project.dataclasses.MajorDataClass

@Dao
interface MajorDao {
    @Insert
    suspend fun insertMajor(value: MajorDataClass)

    @Query("Select * from major_table")
    suspend fun getMajors(): List<MajorDataClass>

    @Query("Delete from major_table where id = :id")
    suspend fun deleteMajor(id: Int)
}