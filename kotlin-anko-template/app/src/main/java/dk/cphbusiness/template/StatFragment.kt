package dk.cphbusiness.template

import android.app.AlertDialog
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
        updateStats(view)
        view.resetBtn.onClick { promptWarning() }
    }

    fun promptWarning() {
        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage("Are you sure?")
        builder1.setCancelable(true)

        builder1.setPositiveButton(
                "Confirm"
        ) {
            dialog, id -> run {
            activity.startOver()
            updateStats(this.view)
            dialog.cancel()
        }
        }

        builder1.setNegativeButton(
                "Cancel"
        ) { dialog, id -> dialog.cancel() }

        val alert11 = builder1.create()
        alert11.show()
    }

    fun updateStats(view : View?) {
        view!!.mWalkedText.text = "${activity.user.totalMWalked}m"
        view!!.bestSprintText.text = "${activity.user.bestSprint}m"
        view!!.markersDiscoveredText.text = "${activity.user.markersDiscovered} markers"
    }

}
