package dk.cphbusiness.template.user

/**
 * Created by xboxm on 26-05-2017.
 */

import java.io.Serializable

data class User(
        val userID: Int,
        //var userName: String,
        var totalMWalked: Int,
        var bestSprint : Int,
        var markersDiscovered : Int
) : Serializable


object UserTable {
    val tableName = "User"
    val userID = "userID"
    val totalMwalked = "totalMWalked"
    val bestSprint = "bestSprint"
    val markersDiscovered = "markersDiscovered"
}
