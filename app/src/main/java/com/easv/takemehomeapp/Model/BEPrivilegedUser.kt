package com.easv.takemehomeapp.Model

import java.io.Serializable

class BEPrivilegedUser (
    var id: Int,
    var username: String,
    var password: String,
    var role: String
): Serializable