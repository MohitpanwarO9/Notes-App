package com.example.mynotes

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.hardware.camera2.DngCreator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var listNotes=ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadQuery("%")
    }

    override fun onResume() {
        super.onResume()
        loadQuery("%")
    }


    private fun loadQuery(title:String) {
        var dbManager=DbManager(this)
        val projection= arrayOf("ID","Title","Description")
        val selectionArg= arrayOf(title)
        val cursor=dbManager.Query(projection,"Title like ?",selectionArg,"Title")

        if(cursor.moveToFirst()){
            listNotes.clear()
            do{
                 val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val title=cursor.getString(cursor.getColumnIndex("Title"))
                val des=cursor.getString(cursor.getColumnIndex("Description"))
                listNotes.add(Notes(ID,title,des))

            }while (cursor.moveToNext())
        }
        var myNotesAdapter=MyNotesAdapter(this,listNotes)
        notes_list.adapter=myNotesAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val sv=menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val sm=getSystemService(Context.SEARCH_SERVICE) as SearchManager
            sv.setSearchableInfo(sm.getSearchableInfo(componentName))
            sv.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    loadQuery("%"+ query +"%")
                    Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.addNotes->{
                    val intent=Intent(this,AddNotes::class.java)
                        startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    inner class MyNotesAdapter:BaseAdapter{
            var listNotesAdapter=ArrayList<Notes>()
            var context:Context?=null
        constructor(context: Context,listNotesAdapter:ArrayList<Notes>):super(){
            this.listNotesAdapter=listNotesAdapter
            this.context=context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView=layoutInflater.inflate(R.layout.ticket,null)
            var myNotes=listNotesAdapter[position]

            //inisiliazing the listView with ticket
            myView.ticket_title.text=myNotes.noteTitle
            myView.ticket_des.text=myNotes.noteDes

            myView.ticket_delete.setOnClickListener(View.OnClickListener {
                var dbManager=DbManager(this.context!!)
                val selectionArg= arrayOf(myNotes.noteId.toString())
                dbManager.Delete("ID=?",selectionArg)
                loadQuery("%")
            })
            myView.ticket_edit.setOnClickListener(View.OnClickListener {
                goToUpadate(myNotes)
            })
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

    fun goToUpadate(note:Notes){
        val intent=Intent(this,AddNotes::class.java)
        intent.putExtra("ID",note.noteId)
        intent.putExtra("Title",note.noteTitle)
        intent.putExtra("Des",note.noteDes)
        startActivity(intent)
    }

}