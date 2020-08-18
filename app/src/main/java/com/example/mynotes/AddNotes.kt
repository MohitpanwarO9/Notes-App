package com.example.mynotes

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import java.lang.Exception

class AddNotes : AppCompatActivity() {

    private var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        try {
            var bundle: Bundle? = intent.extras
            id = bundle!!.getInt("ID", 0)
            if (id != 0) {
                addNotes_title.setText(bundle.getString("Title").toString())
                addNotes_des.setText(bundle.getString("Des").toString())
            }
        }catch (ex:Exception){ }

    }
    fun buAdd(view: View){
        var dbManager=DbManager(this)

        var values=ContentValues()
        values.put("Title",addNotes_title.text.toString())
        values.put("Description",addNotes_des.text.toString())

        if(id==0) {

            val Id = dbManager.Insert(values)
            if (Id > 0) {
                Toast.makeText(this, "Added to Daily list", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Cannot Added", Toast.LENGTH_SHORT).show()
            }

        }else{

            var selectionArg= arrayOf(id.toString())
            val Id = dbManager.Update(values,"ID=?",selectionArg)
            if (Id > 0) {
                Toast.makeText(this, "UPDATED", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "FAIL TO UPDATE", Toast.LENGTH_SHORT).show()
            }

        }

        addNotes_title.text.clear()
        addNotes_des.text.clear()
        finish()
    }

}