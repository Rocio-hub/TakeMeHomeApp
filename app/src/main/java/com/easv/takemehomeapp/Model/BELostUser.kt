package com.easv.takemehomeapp.Model

import java.io.Serializable

class BELostUser(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var phoneList: String,
    var email: String,
    var address: String,
    var CPR: Int,
    var medicationList: String,
    var allergiesList: String,
    var diseasesList: String,
    var picture: String
): Serializable
