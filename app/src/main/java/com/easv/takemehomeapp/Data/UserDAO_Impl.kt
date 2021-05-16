package com.easv.takemehomeapp.Data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.Model.BEPrivilegedUser

class UserDAO_Impl(context: Context) :
    SQLiteOpenHelper(context, "TakeMeHomeDB", null, 3), IUserDAO {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE PrivilegedUser (id INTEGER PRIMARY KEY, username TEXT, password TEXT, role TEXT)")
        db?.execSQL("CREATE TABLE LostUser (id INTEGER PRIMARY KEY, firstName TEXT, lastName TEXT, phone INTEGER UNIQUE, email TEXT UNIQUE)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + "LostUser")
        db!!.execSQL("DROP TABLE IF EXISTS " + "PrivilegedUser")
        onCreate(db)
    }

    //Methods for privileged users

    override fun insertPrivilegedUser(p: BEPrivilegedUser) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("username", p.username)
        cv.put("password", p.password)
        cv.put("role", p.role)
        db.insert("PrivilegedUser", null, cv)
    }

    override fun login(username: String, password: String): BEPrivilegedUser? {
        val db = this.readableDatabase
        var cursor = db.query(
            "PrivilegedUser",
            arrayOf("id", "username", "password", "role"),
            "username = '$username' AND password = '$password'",
            null,
            null,
            null,
            null
        )
        var myList : List<BEPrivilegedUser> = getByCursorPrivilegedUser(cursor)
        if (myList.isNotEmpty()) {
            return myList[0]
        } else {
            return null
        }
    }

    private fun getByCursorPrivilegedUser(cursor: Cursor): List<BEPrivilegedUser> {
        val myList = ArrayList<BEPrivilegedUser>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val username = cursor.getString(cursor.getColumnIndex("username"))
                val password = cursor.getString(cursor.getColumnIndex("password"))
                val role = cursor.getString(cursor.getColumnIndex("role"))
                myList.add(BEPrivilegedUser(id, username, password, role))
            } while (cursor.moveToNext())
        }
        return myList
    }

    //Methods for lost users

    override fun insertLostUser(l: BELostUser) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("firstName", l.firstName)
        cv.put("lastName", l.lastName)
        cv.put("phone", l.phone)
        cv.put("email", l.email)
        db.insert("LostUser", null, cv)
    }

    override fun getLostUserById(id: Int): BELostUser {
        val db = this.readableDatabase
        var cursor = db.query(
            "LostUser",
            arrayOf("id", "firstName", "lastName", "phone", "email"),
            "id LIKE '%$id%'",
            null,
            null,
            null,
            null
        )
        var myList = getByCursorLostUser(cursor)
        if (myList.isNotEmpty()) {
            return myList[0]
        } else {
            return BELostUser(0, "", "", 0, "")
        }
    }

    private fun getByCursorLostUser(cursor: Cursor): List<BELostUser> {
        val myList = ArrayList<BELostUser>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val firstName = cursor.getString(cursor.getColumnIndex("firstName"))
                val lastName = cursor.getString(cursor.getColumnIndex("lastName"))
                val phone = cursor.getInt(cursor.getColumnIndex("phone"))
                val email = cursor.getString(cursor.getColumnIndex("email"))
                myList.add(BELostUser(id, firstName, lastName, phone, email))
            } while (cursor.moveToNext())
        }
        return myList
    }

    //Methods for both
    override fun emptyDb() {
        val db = this.writableDatabase
        db!!.execSQL("DELETE FROM LostUser WHERE 1=1")
        db!!.execSQL("DELETE FROM PrivilegedUser WHERE 1=1")
    }
}