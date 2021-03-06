package com.easv.takemehomeapp.Model

import java.io.File
import java.io.Serializable

class BELostUser(
    var id: Int,
    var fullName: String,
    var phoneList: String,
    var email: String,
    var address: String,
    var CPR: Long,
    var medicationList: String,
    var allergiesList: String,
    var diseasesList: String,
    var picture: File
) : Serializable
