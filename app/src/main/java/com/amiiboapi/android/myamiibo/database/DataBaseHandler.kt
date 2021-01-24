package com.amiiboapi.android.myamiibo.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.amiiboapi.android.myamiibo.model.Amiibo
import com.amiiboapi.android.myamiibo.model.AmiiboData
import com.amiiboapi.android.myamiibo.model.AmiiboRelease
import com.google.gson.Gson
import javax.inject.Inject

class DataBaseHandler @Inject constructor(
    var context: Context)
    : SQLiteOpenHelper(context, DATABASENAME, null, 2) {

    companion object {
        private val DATABASENAME = "AMIIBO DATABASE"
        private val TABLENAME = "amiibo_table"
        private val COL_ID = "id"
        private val COL_AMIIBO_SERIES = "amiiboSeries"
        private val COL_CHARATER = "character"
        private val COL_GAME_SERIES = "gameSeries"
        private val COL_HEAD = "head"
        private val COL_IMAGE_URI = "image"
        private val COL_NAME = "name"
        private val COL_RELEASE = "release"
        private val COL_TAIL = "tail"
        private val COL_TYPE = "type"
        private val COL_PURCHASE = "purchase"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_AMIIBO_SERIES + " VARCHAR(256)," +
                COL_CHARATER + " VARCHAR(256)," +
                COL_GAME_SERIES + " VARCHAR(256)," +
                COL_HEAD + " VARCHAR(256),"+
                COL_NAME + " VARCHAR(256)," +
                COL_IMAGE_URI + " VARCHAR(256)," +
                COL_RELEASE + " VARCHAR(256)," +
                COL_TAIL + " VARCHAR(256)," +
                COL_TYPE + " VARCHAR(256)," +
                COL_PURCHASE + " VARCHAR(256)" +
                ")"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun deleteAllData() {
        val db = this.writableDatabase
        db!!.execSQL("DROP TABLE IF EXISTS $TABLENAME")
        onCreate(db)
    }

    fun addAmiiboData(amiiboList: Amiibo) : Boolean {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        for (data in amiiboList.amiibo) {
            contentValues.put(COL_AMIIBO_SERIES, data.amiiboSeries)
            contentValues.put(COL_CHARATER, data.character)
            contentValues.put(COL_GAME_SERIES, data.gameSeries)
            contentValues.put(COL_HEAD, data.head)
            contentValues.put(COL_IMAGE_URI, data.image)
            contentValues.put(COL_NAME, data.name)
            contentValues.put(COL_TAIL, data.tail)
            contentValues.put(COL_PURCHASE, data.purchase)
            val result = database.insert(TABLENAME, null, contentValues)
            return when(result) {
                (0).toLong() -> false
                else -> true
            }
        }
        return false
    }

    //method to read data
    fun viewAmiiboList(): Amiibo? {
        val empList : ArrayList<AmiiboData> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLENAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return null
        }

        //database is empty
        if(cursor.getCount() == 0) return null


        var id: Int
        var amiiboSeries : String
        var character : String
        var gameSeries  : String
        var head : String
        var image : String
        var name: String
        var release : String
        var tail : String
        var type : String
        var purchase : String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                amiiboSeries = cursor.getString(cursor.getColumnIndex(COL_AMIIBO_SERIES))
                character = cursor.getString(cursor.getColumnIndex(COL_CHARATER))
                gameSeries = cursor.getString(cursor.getColumnIndex(COL_GAME_SERIES))
                head = cursor.getString(cursor.getColumnIndex(COL_HEAD))
                image = cursor.getString(cursor.getColumnIndex(COL_IMAGE_URI))
                name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                release = cursor.getString(cursor.getColumnIndex(COL_RELEASE))
                tail = cursor.getString(cursor.getColumnIndex(COL_TAIL))
                type = cursor.getString(cursor.getColumnIndex(COL_TYPE))
                purchase = cursor.getString(cursor.getColumnIndex(COL_PURCHASE))
                val amiibo = AmiiboData(
                    id = id,
                    amiiboSeries = amiiboSeries,
                    character = character,
                    gameSeries = gameSeries,
                    head = head,
                    image = image,
                    release = processRelease(release),
                    name = name,
                    tail = tail,
                    type = type,
                    purchase = parseInt(purchase))
                empList.add(amiibo)
            } while (cursor.moveToNext())
        }
        return Amiibo(amiibo = empList)
    }

    fun deleteEmployee(data: AmiiboData) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLENAME WHERE $COL_ID=${data.id}")
        db.close()
    }

    fun updatePurchase(data: AmiiboData) {
        val db = this.writableDatabase
        db.execSQL("UPDATE $TABLENAME SET $COL_PURCHASE=1 WHERE id = ${data.id}", null)
        db.close()
    }


    private fun processRelease(relaseData: String): AmiiboRelease {
        return Gson().fromJson(relaseData, AmiiboRelease::class.java)
    }

    private fun parseInt(parseStr: String): Int {
        return Integer.parseInt(parseStr)
    }

    fun getAmiiboItem(id: Int) : AmiiboData? {
        //method to read data
        val selectQuery = "SELECT  * FROM $TABLENAME WHERE $COL_ID=$id"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)


        //item not found
        if(cursor.getCount() == 0) return null

        val mId: Int
        val amiiboSeries : String
        val character : String
        val gameSeries  : String
        val head : String
        val image : String
        val name: String
        val release : String
        val tail : String
        val type : String
        val purchase : String

        cursor.moveToFirst()
        mId = cursor.getInt(cursor.getColumnIndex(COL_ID))
        amiiboSeries = cursor.getString(cursor.getColumnIndex(COL_AMIIBO_SERIES))
        character = cursor.getString(cursor.getColumnIndex(COL_CHARATER))
        gameSeries = cursor.getString(cursor.getColumnIndex(COL_GAME_SERIES))
        head = cursor.getString(cursor.getColumnIndex(COL_HEAD))
        image = cursor.getString(cursor.getColumnIndex(COL_IMAGE_URI))
        name = cursor.getString(cursor.getColumnIndex(COL_NAME))
        release = cursor.getString(cursor.getColumnIndex(COL_RELEASE))
        tail = cursor.getString(cursor.getColumnIndex(COL_TAIL))
        type = cursor.getString(cursor.getColumnIndex(COL_TYPE))
        purchase = cursor.getString(cursor.getColumnIndex(COL_PURCHASE))

        val amiibo = AmiiboData(
            id = mId,
            amiiboSeries = amiiboSeries,
            character = character,
            gameSeries = gameSeries,
            head = head,
            image = image,
            release = processRelease(release),
            name = name,
            tail = tail,
            type = type,
            purchase = parseInt(purchase))

        return amiibo

        }
}
