package com.easv.takemehomeapp.Model

import java.io.Serializable

class BEPrivilegedUser (
    var id: Int,
    var username: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var CPR: Int,
    var role: String,
    var station: String,
    var picture: Int,
): Serializable