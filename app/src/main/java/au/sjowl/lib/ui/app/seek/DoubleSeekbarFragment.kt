package au.sjowl.lib.ui.app.seek

import android.os.Bundle
import android.view.View
import au.sjowl.lib.ui.app.base.BaseFragment
import au.sjowl.lib.ui.app.utils.prefs.getProperty
import au.sjowl.lib.ui.app.utils.prefs.setProperty
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.fr_double_seekbar.*

class DoubleSeekbarFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_double_seekbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rangeSeekBar.totalStars = 5
        rangeSeekBar.selectRange(context!!.getProperty(KEY_STARS_MIN, 2), context!!.getProperty(KEY_STARS_MAX, 3))

        startTextView.text = rangeSeekBar.min.toString()
        endTextView.text = rangeSeekBar.max.toString()

        rangeSeekBar.onRangeChanged { start, end ->
            println("$start - $end")
            context!!.setProperty(KEY_STARS_MIN, start)
            context!!.setProperty(KEY_STARS_MAX, end)
            startTextView.text = start.toString()
            endTextView.text = end.toString()
        }
    }

    companion object {
        const val KEY_STARS_MIN = "stars_min"
        const val KEY_STARS_MAX = "stars_max"
    }
}