package com.example.mae_project.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "university_table")
data class UniversityDataClass(

    var pushId: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "uniName")

    var uniName: String? = null

    @ColumnInfo(name = "fee")

    var fee: String? = null

    @ColumnInfo(name = "uniImage")

    var uniImage: String? = null

    @ColumnInfo(name = "uniLocation")

    var uniLocation: String? = null

    @ColumnInfo(name = "uniCountry")

    var uniCountry: String? = null

    @ColumnInfo(name = "contactNo")

    var contactNo: String? = null

    @ColumnInfo(name = "emailAddress")

    var emailAddress: String? = null
}