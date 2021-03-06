package com.easv.takemehomeapp.Data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import java.io.File

class UserDAO_Impl(context: Context) :
    SQLiteOpenHelper(context, "TakeMeHomeDB", null, 20), IUserDAO {

    //Methods for both

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE PrivilegedUser (id INTEGER PRIMARY KEY, username TEXT, password TEXT, firstName TEXT, lastName TEXT, CPR INTEGER, role TEXT, station TEXT, picture TEXT)")
        db?.execSQL("CREATE TABLE LostUser (id INTEGER PRIMARY KEY, fullName TEXT, phoneList INTEGER, email TEXT, address TEXT, CPR INTEGER UNIQUE, medicationList TEXT, allergiesList TEXT, diseasesList TEXT, picture String)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + "LostUser")
        db!!.execSQL("DROP TABLE IF EXISTS " + "PrivilegedUser")
        onCreate(db)
    }

    override fun emptyDb() {
        val db = this.writableDatabase
        db!!.execSQL("DELETE FROM LostUser WHERE 1=1")
        db!!.execSQL("DELETE FROM PrivilegedUser WHERE 1=1")
    }

    //Methods for privileged users

    override fun insertPrivilegedUser(p: BEPrivilegedUser) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("username", p.username)
        cv.put("password", p.password)
        cv.put("firstName", p.firstName)
        cv.put("lastName", p.lastName)
        cv.put("CPR", p.CPR)
        cv.put("role", p.role)
        cv.put("station", p.station)
        cv.put("picture", p.picture.toString())
        db.insert("PrivilegedUser", null, cv)
    }

    override fun createPrivilegedUser(p: BEPrivilegedUser) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("username", p.username)
        cv.put("password", p.password)
        cv.put("firstName", p.firstName)
        cv.put("lastName", p.lastName)
        cv.put("CPR", p.CPR)
        cv.put("role", p.role)
        cv.put("station", p.station)
        cv.put("picture", p.picture.toString())
        db.insert("PrivilegedUser", null, cv)
    }

    override fun login(username: String, password: String): BEPrivilegedUser? {
        val db = this.readableDatabase
        var cursor = db.query(
            "PrivilegedUser",
            arrayOf(
                "id",
                "username",
                "password",
                "firstName",
                "lastName",
                "CPR",
                "role",
                "station",
                "picture"
            ),
            "username LIKE '$username' AND password LIKE '$password'",
            null,
            null,
            null,
            null
        )
        var myList: List<BEPrivilegedUser> = getByCursorPrivilegedUser(cursor)
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
                val firstName = cursor.getString(cursor.getColumnIndex("firstName"))
                val lastName = cursor.getString(cursor.getColumnIndex("lastName"))
                val CPR = cursor.getInt(cursor.getColumnIndex("CPR"))
                val role = cursor.getString(cursor.getColumnIndex("role"))
                val station = cursor.getString(cursor.getColumnIndex("station"))
                val picture = cursor.getString(cursor.getColumnIndex("picture"))
                myList.add(
                    BEPrivilegedUser(
                        id,
                        username,
                        password,
                        firstName,
                        lastName,
                        CPR,
                        role,
                        station,
                        File(picture)
                    )
                )
            } while (cursor.moveToNext())
        }
        return myList
    }

    //Methods for lost users

    override fun insertLostUser(l: BELostUser) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("fullName", l.fullName)
        cv.put("phoneList", l.phoneList)
        cv.put("email", l.email)
        cv.put("address", l.address)
        cv.put("CPR", l.CPR)
        cv.put("medicationList", l.medicationList)
        cv.put("allergiesList", l.allergiesList)
        cv.put("diseasesList", l.diseasesList)
        cv.put("picture", l.picture.toString())
        db.insert("LostUser", null, cv)
    }

    override fun createLostUser(l: BELostUser) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("fullName", l.fullName)
        cv.put("phoneList", l.phoneList)
        cv.put("email", l.email)
        cv.put("address", l.address)
        cv.put("CPR", l.CPR)
        cv.put("medicationList", l.medicationList)
        cv.put("allergiesList", l.allergiesList)
        cv.put("diseasesList", l.diseasesList)
        cv.put("picture", l.picture.toString())
        db.insert("LostUser", null, cv)
    }

    override fun updateLostUser(l: BELostUser) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("fullName", l.fullName)
        cv.put("phoneList", l.phoneList)
        cv.put("email", l.email)
        cv.put("address", l.address)
        cv.put("CPR", l.CPR)
        cv.put("medicationList", l.medicationList)
        cv.put("allergiesList", l.allergiesList)
        cv.put("diseasesList", l.diseasesList)
        cv.put("picture", l.picture.toString())
        val whereClause = "id=?"
        val whereArgs = arrayOf((l.id).toString())
        db.update("LostUser", cv, whereClause, whereArgs)
    }

    override fun getLostUserById(id: Int): BELostUser {
        val db = this.readableDatabase
        var cursor = db.query(
            "LostUser",
            arrayOf(
                "id",
                "fullName",
                "phoneList",
                "email",
                "address",
                "CPR",
                "medicationList",
                "allergiesList",
                "diseasesList",
                "picture"
            ),
            "id LIKE '$id'",
            null,
            null,
            null,
            null
        )
        var myList = getByCursorLostUser(cursor)
        if (myList.isNotEmpty()) {
            return myList[0]
        } else {
            return BELostUser(0, "", "", "", "", 0, "", "", "", File(""))
        }
    }

    private fun getByCursorLostUser(cursor: Cursor): List<BELostUser> {
        val myList = ArrayList<BELostUser>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val fullName = cursor.getString(cursor.getColumnIndex("fullName"))
                val phoneList = cursor.getString(cursor.getColumnIndex("phoneList"))
                val email = cursor.getString(cursor.getColumnIndex("email"))
                val address = cursor.getString(cursor.getColumnIndex("address"))
                val CPR = cursor.getLong(cursor.getColumnIndex("CPR"))
                val medicationList = cursor.getString(cursor.getColumnIndex("medicationList"))
                val allergiesList = cursor.getString(cursor.getColumnIndex("allergiesList"))
                val diseasesList = cursor.getString(cursor.getColumnIndex("diseasesList"))
                val picture = cursor.getString(cursor.getColumnIndex("picture"))

                myList.add(
                    BELostUser(
                        id,
                        fullName,
                        phoneList,
                        email,
                        address,
                        CPR,
                        medicationList,
                        allergiesList,
                        diseasesList,
                        File(picture)
                    )
                )
            } while (cursor.moveToNext())
        }
        return myList
    }
}