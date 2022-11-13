package com.example.mae_project.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "major_table")
data class MajorDataClass(
    @ColumnInfo(name = "department")
    var department: String? = null,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "majImage")
    var majImage: String? = null,
    @ColumnInfo(name = "name")
    var name: String? = null
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}