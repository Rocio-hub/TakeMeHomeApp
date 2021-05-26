package com.easv.takemehomeapp.Activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_lists.*


class ListsActivity : AppCompatActivity() {

    private var listName = " "
    private var myList: List<String>? = null
    private lateinit var lostUser: BELostUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)

        var extras: Bundle = intent.extras!! // We get the extras sent from the previous activity
        lostUser = extras.getSerializable("lostUser") as BELostUser
        listName = extras.getString("listName")!!

        when (listName) {
            "allergies" -> myList = lostUser.allergiesList.split(" ")
            "diseases" -> myList = lostUser.diseasesList.split(" ")
            "medication" -> myList = lostUser.medicationList.split(" ")
        }

        listView_items.adapter = ListAdapter(
            this,
            myList!!.toTypedArray()
        ) //We set the content of the adapter to be the list of items
    }
}

internal class ListAdapter(context: Context, private val list: Array<String>) :
    ArrayAdapter<String>(
        context,
        0,
        list
    ) { //Adapter that will create a new Extended Cell for each friend in the item list

    override fun getView(position: Int, v: View?, parent: ViewGroup): View {
        var v1: View? = v
        if (v1 == null) {
            val li = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                    as LayoutInflater
            v1 = li.inflate(R.layout.list_extended, null)
        }
        val resView: View = v1!!
        val l = list[position]
        val nameView = resView.findViewById<TextView>(R.id.textView_item)
        nameView.text = l
        return resView
    }
}