package dk.cphbusiness.template

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.obj_fragment.*
import java.util.concurrent.TimeUnit

class ObjFragment(val activity: MapActivity) : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.obj_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    fun updateObjective() {
        objName.text = "Ready to set objective!"
        val curObj = activity.currentObjective
        if (curObj != null) {
            when (curObj) {
                is SprintObjective -> {
                    val sprint = curObj as SprintObjective
                    objName.text = sprint.objName
                    objGoal.text = sprint.objGoal
                    objLimit.text = "${TimeUnit.MILLISECONDS.toMinutes(sprint.timeLimit)} minutes"
                }
                is DiscoverObjective -> {
                    val discover = curObj as DiscoverObjective
                    objName.text = curObj.objName
                    objGoal.text = curObj.objGoal
                    objLimit.text = "∞"
                }
            }
        }
    }
}