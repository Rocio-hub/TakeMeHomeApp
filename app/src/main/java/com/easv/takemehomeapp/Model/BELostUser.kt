package com.easv.takemehomeapp.Model

import java.io.Serializable

class BELostUser(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var phoneList: List<String>,
    var email: String,
    var address: String,
    var CPR: Long,
    var medicationList: List<String>,
    var allergiesList: List<String>,
    var diseasesList: List<String>,
    var picture: Int
): Serializable
