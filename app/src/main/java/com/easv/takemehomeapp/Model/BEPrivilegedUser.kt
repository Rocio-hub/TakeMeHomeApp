package com.easv.takemehomeapp.Model

import java.io.Serializable

class BEPrivilegedUser (
    var id: Int,
    var username: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var role: String,
    var picture: String,
): Serializable