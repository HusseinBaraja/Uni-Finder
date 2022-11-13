package com.example.mae_project.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scholar_table")
data class ScholarDataClass(
    @ColumnInfo(name = "content")
    var content: String? = null,
    @ColumnInfo(name = "title")
    val title: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int =0
}