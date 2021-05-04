package com.easv.takemehomeapp.Model

class Users {

    var listOfUsers = arrayListOf<BEUser>(
        BEUser(1, "firstName1", "lastName1", 123),
        BEUser(2, "firstName2", "lastName2", 123),
        BEUser(3, "firstName3", "lastName3", 123),
        BEUser(123, "Lelota", "Yui", 12121212),
    )

    fun getLostUser(id: Int): BEUser {
        for (i in listOfUsers) {
            when (id) {
                i.id ->
                    return i
            }
        }
        return BEUser(0, "", "", 0)
    }
}