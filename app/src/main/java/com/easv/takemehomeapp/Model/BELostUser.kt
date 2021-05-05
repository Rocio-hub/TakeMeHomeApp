package com.easv.takemehomeapp.Model

import java.io.Serializable

class BELostUser(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var phone: Int,
    var email: String,
): Serializable