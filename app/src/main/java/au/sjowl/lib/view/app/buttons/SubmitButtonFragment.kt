package au.sjowl.lib.view.app.buttons

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.app.BaseFragment
import au.sjowl.lib.view.buttons.SubmitButton
import kotlinx.android.synthetic.main.fr_button_submit.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.sdk27.coroutines.onClick

class SubmitButtonFragment : BaseFragment() {

    override val layoutId: Int get() = R.layout.fr_button_submit

    var animDuration = 200L

    val progressStep = 0.01f

    private var processJob: Job? = null

    private val delay = 10L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.onClick {
            println("button clicked!")
        }

        stateDisabled.onClick {
            button.isEnabled = false
        }
        stateReady.onClick {
            button.isEnabled = true
        }
        stateProgress.onClick {
            processJob?.cancel()
            processJob = progress()
        }
        stateError.onClick {
            processJob?.cancel()
            processJob = progressWithError()
        }

        button.animDuration = 1200L
        val animDMax = 2000L
        val unit = animDMax / 100
        durationSeek.progress = (button.animDuration / unit).toInt()
        durationSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                animDuration = seekBar.progress * unit
                button.animDuration = seekBar.progress * unit
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun progress() = GlobalScope.launch(Dispatchers.Main) {
        button.setState(SubmitButton.STATE_PROGRESS)
        var progress = 0f
        button.progress = 0f
        while (progress < 1) {
            delay(delay)
            progress += progressStep
            button.progress = progress
        }
    }

    private fun progressWithError() = GlobalScope.launch(Dispatchers.Main) {
        button.setState(SubmitButton.STATE_PROGRESS)
        var progress = 0f
        button.progress = 0f
        while (progress < 0.9f) {
            delay(delay)
            progress += progressStep
            button.progress = progress
        }
        button.setState(SubmitButton.STATE_ERROR)
    }
}