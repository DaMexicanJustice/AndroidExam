package dk.cphbusiness.template

/**
 * Created by xboxm on 18-05-2017.
 */

open class Objective(val objName : String, val objGoal : String, var isCompleted : Boolean = false) {
    // Constructor
    init {

    }
}

class DiscoverObjective(val latitude : Double, val longitude : Double, objName : String, objGoal : String, isCompleted : Boolean = false) : Objective(objName, objGoal, isCompleted) {

    // Constructor
    init {

    }

}

class SprintObjective(val timeLimit : Long, objName : String, objGoal : String, isCompleted : Boolean = false) : Objective(objName, objGoal, isCompleted) {

    // Constructor
    init {

    }

}
