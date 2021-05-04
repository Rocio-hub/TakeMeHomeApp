package com.easv.takemehomeapp.Model

class PrivilegedUsers {

    var listOfPolice = arrayListOf<BEPrivilegedUser>(
        BEPrivilegedUser(1, "AAA", "p1", "police"),
        BEPrivilegedUser(2, "B12345", "password2", "police"),
        BEPrivilegedUser(3, "C12345", "password3", "police"),
    )

    var listOfMedicalStaff = arrayListOf<BEPrivilegedUser>(
        BEPrivilegedUser(4, "BBB", "p4", "doctor"),
        BEPrivilegedUser(5, "doctor2", "password5", "doctor"),
        BEPrivilegedUser(6, "doctor3", "password6", "doctor"),
    )

    fun checkUserExists(username: String, password: String): BEPrivilegedUser? {
        var i = 0
        listOfPolice.forEach {
            when (username) {
                listOfPolice[i].username ->
                    when (password) {
                        listOfPolice[i].password ->
                            return listOfPolice[i]
                    }
            }
            when (username) {
                listOfMedicalStaff[i].username ->
                    when (password) {
                        listOfMedicalStaff[i].password ->
                            return listOfMedicalStaff[i]
                    }
            }
        }
        i++
        return null
    }
}
