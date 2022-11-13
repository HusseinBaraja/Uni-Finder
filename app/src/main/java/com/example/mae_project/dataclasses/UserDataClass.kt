package com.example.mae_project.dataclasses

data class UserDataClass(var uId: String? = null) : java.io.Serializable {
    var pushId: String? = null
    var name: String? = null
    var email: String? = null
    var phoneNo: String? = null
    var imageUrl: String? = null
    var city: String? = null
    var province: String? = null
    var country: String? = null
    var address: String? = null
    var gender: String? = null
    var degree: String? = null
    var highestQualification: String? = null
    var specialocation: String? = null
    var currentStatus: String? = null
    var occupation: String? = null
    var occupationGroup: String? = null
    var attemp: String? = null
    var fromwhere: String? = null
    var appear: String? = null
}