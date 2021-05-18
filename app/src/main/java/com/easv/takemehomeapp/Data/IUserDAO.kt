package com.easv.takemehomeapp.Data

import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.Model.BEPrivilegedUser

interface IUserDAO {

    //Privileged users
    fun login (username: String, password: String): BEPrivilegedUser?
    fun insertPrivilegedUser(p: BEPrivilegedUser) //add mock data
    fun createPrivilegedUser(p: BEPrivilegedUser) //add real user

    //Lost users
    fun getLostUserById(id: Int): BELostUser
    fun insertLostUser(l: BELostUser) //add mock data

    //Both
    fun emptyDb()
}