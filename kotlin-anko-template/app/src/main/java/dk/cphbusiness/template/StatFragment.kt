package dk.cphbusiness.template

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.stat_fragment.view.*
import org.jetbrains.anko.onClick

/**
 * Created by xboxm on 11-05-2017.
 */

class StatFragment(val activity: MainActivity) : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.stat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.statBackBtn.onClick { activity.hideStats() }
    }

}
