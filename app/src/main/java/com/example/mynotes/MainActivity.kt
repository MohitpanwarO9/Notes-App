package com.example.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var listNotes=ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // this is dummy data

        listNotes.add(Notes(1,"Aim","Today i have to do the coding practice and android notes project"))
        listNotes.add(Notes(2,"complete","the goal of toady is to master sql lite for android and practice android skills"))
        listNotes.add(Notes(3,"Aim","Today i have to do the coding practice and android notes project"))

        var myNotesAdapter=MyNotesAdapter(listNotes)
            notes_list.adapter=myNotesAdapter
    }


    inner class MyNotesAdapter:BaseAdapter{
            var listNotesAdapter=ArrayList<Notes>()
        constructor(listNotesAdapter:ArrayList<Notes>):super(){
            this.listNotesAdapter=listNotesAdapter
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView=layoutInflater.inflate(R.layout.ticket,null)
            var myNotes=listNotesAdapter[position]

            //inisiliazing the listView with ticket
            myView.ticket_title.text=myNotes.noteTitle
            myView.ticket_des.text=myNotes.noteDes

            return myView
        }

        override fun getItem(position: Int): Any {
           return listNotesAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNotesAdapter.size
        }


    }

}