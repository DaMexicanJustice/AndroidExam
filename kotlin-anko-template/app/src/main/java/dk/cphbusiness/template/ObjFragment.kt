package dk.cphbusiness.template

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ObjFragment(val activity: MapActivity) : Fragment() {

    var curObj : Any? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        curObj = activity.currentObjective
        return inflater.inflate(R.layout.obj_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}