package com.easv.takemehomeapp.Model

class Users {

    var listOfUsers = arrayListOf(
        BEUser(1, "firstName1", "lastName1", 123, "a@b"),
        BEUser(2, "firstName2", "lastName2", 123, "a@b"),
        BEUser(3, "firstName3", "lastName3", 123, "a@b"),
        BEUser(123, "Peter", "Parker", 12121212, "a@b"),
    )

    fun getLostUser(id: Int): BEUser {
        for (i in listOfUsers) {
            when (id) {
                i.id ->
                    return i
            }
        }
        return BEUser(0, "", "", 0, "")
    }
}