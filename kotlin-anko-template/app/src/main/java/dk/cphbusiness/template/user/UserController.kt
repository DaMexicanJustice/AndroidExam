package dk.cphbusiness.template.user

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*



/**
 * Created by xboxm on 26-05-2017.
 */

class UserController(var context: Context = App.instance) : ManagedSQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    lateinit var db: SQLiteDatabase

    companion object {
        val DB_NAME = "UserDB"
        val DB_VERSION = 1
        val instance by lazy { UserController() }
    }

    fun setup() {

        val dbExists = context.getDatabasePath("UserDB").exists()

        db = writableDatabase

        db.createTable(
                UserTable.tableName, true,
                UserTable.userID to INTEGER + PRIMARY_KEY,
                UserTable.totalMwalked to INTEGER,
                UserTable.bestSprint to INTEGER,
                UserTable.markersDiscovered to INTEGER
        )

        db.close()
    }

    fun updateMarkersDiscovered(markers : Int) {
        db = writableDatabase

        val cv = ContentValues()
        cv.put("markersDiscovered", markers) //These Fields should be your String values of actual column names
        // Database always only have 1 user, so id is 1
        db.update("UserTable", cv, "_id=1", null)

        db.close()
    }

    fun updateBestSprint(sprint : Int) {
        db = writableDatabase

        val cv = ContentValues()
        cv.put("bestSprint", sprint) //These Fields should be your String values of actual column names
        // Database always only have 1 user, so id is 1
        db.update("UserTable", cv, "_id=1", null)

        db.close()
    }

    fun updateMWalked(mWalked : Int) {
        db = writableDatabase

        val cv = ContentValues()
        cv.put("totalMWalked", mWalked) //These Fields should be your String values of actual column names
        // Database always only have 1 user, so id is 1
        db.update("UserTable", cv, "_id=1", null)

        db.close()
    }

    /*fun addUser(totalMWalked : Int, bestSprint : Int, markersDiscovered : Int) {

        db = writableDatabase

        db.insert(
                UserTable.tableName,
                UserTable.totalMwalked to totalMWalked,
                UserTable.bestSprint to bestSprint,
                UserTable.markersDiscovered to markersDiscovered
        )

        db.close()
    } */

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}