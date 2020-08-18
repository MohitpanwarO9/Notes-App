package com.example.mynotes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import android.widget.Toast
import kotlin.coroutines.coroutineContext

class DbManager {

    val dbName="MyNotes"
    val dbTable="Notes"
    val colId="ID"
    val colTitle="Title"
    val colDes="Description"
    val dbVersion=1
    val sqlCreateTable="CREATE TABLE "+ dbTable +" ("+ colId +" INTEGER PRIMARY KEY AUTOINCREMENT," + colTitle +" TEXT," + colDes +" TEXT);"
    var sqlDB:SQLiteDatabase?=null
    constructor(context: Context){
        var db=DatabaseHelperNotes(context)
            sqlDB=db.writableDatabase

    }


    inner class DatabaseHelperNotes:SQLiteOpenHelper{
            var context:Context?=null
        constructor(context: Context):super(context,dbName,null,dbVersion){
            this.context=context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context, "Database Created", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop Table IF EXISTS " + dbTable)
        }


    }

    fun Insert(values:ContentValues):Long{
        val ID=sqlDB!!.insert(dbTable,null,values)
        Log.d("congo added","NOW HERE THIS BITCH")
        return ID
    }

    fun Query(projection:Array<String>,selection:String,selectionArg:Array<String>,sorOrder:String):Cursor{
            val qb=SQLiteQueryBuilder()
            qb.tables=dbTable
            val cursor=qb.query(sqlDB,projection,selection,selectionArg,null,null,sorOrder)
        return cursor
    }

    fun Delete(selection:String,selectionArg:Array<String>):Int{
        val count=sqlDB!!.delete(dbTable,selection,selectionArg)
        return count
    }

    fun Update(values:ContentValues,selection:String,selectionArg:Array<String>):Int{
        val count=sqlDB!!.update(dbTable,values,selection,selectionArg)
        return count
    }

}