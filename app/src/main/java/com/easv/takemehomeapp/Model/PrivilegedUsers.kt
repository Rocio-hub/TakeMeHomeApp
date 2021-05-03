package com.easv.takemehomeapp.Model

class PrivilegedUsers {

    var listOfPolice = arrayListOf<BEPrivilegedUser>(
        BEPrivilegedUser(1,	"A12345", "password1", "police"),
        BEPrivilegedUser(2,	"B12345", "password2", "police"),
        BEPrivilegedUser(3,	"C12345", "password3", "police"),
    )

    var listOfMedicalStaff = arrayListOf<BEPrivilegedUser>(
        BEPrivilegedUser(4,	"doctor1", "password4", "doctor"),
        BEPrivilegedUser(5,	"doctor2", "password5", "doctor"),
        BEPrivilegedUser(6,	"doctor3", "password6", "doctor"),
    )

}