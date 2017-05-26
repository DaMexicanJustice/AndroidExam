package dk.cphbusiness.template.system

import android.content.Context
import dk.cphbusiness.template.user.User
import java.io.ObjectInputStream
import java.io.ObjectOutputStream




/**
 * Created by xboxm on 26-05-2017.
 */

class SaveLoad() {

    init {

    }

    fun save(fileName : String, user : User, context : Context) {
        val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(user)
        os.close()
        fos.close()
    }

    fun load(fileName : String, context : Context) : User {
        if (fileExists(context, fileName)) {
            val fis = context.openFileInput(fileName)
            val `is` = ObjectInputStream(fis)
            val user = `is`.readObject() as User
            `is`.close()
            fis.close()
            return user
        } else {
            return User(1, 0f, 0f, 0)
        }
    }

    fun fileExists(context: Context, filename: String): Boolean {
        val file = context.getFileStreamPath(filename)
        if (file == null || !file.exists()) {
            return false
        }
        return true
    }

}
