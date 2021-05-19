package com.easv.takemehomeapp.Model

import java.io.Serializable

class BELostUser(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var phone: Int,
    var email: String,
    var address: String,
    var CPR: Int,
    var currentMedication: String,
    var allergies: String,
    var diseases: String,
    var picture: String
): Serializable
