package com.example.mynotes

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*

class AddNotes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

    }
    fun buAdd(view:View){
        var dbManager=DbManager(this)

        var values=ContentValues()
        values.put("Title",addNotes_title.text.toString())
        values.put("Description",addNotes_des.text.toString())

        val Id=dbManager.Insert(values)

        if(Id>0){
            Toast.makeText(this, "Added to Daily list", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Cannot Added", Toast.LENGTH_SHORT).show()
        }

        addNotes_title.text.clear()
        addNotes_des.text.clear()
    }

}