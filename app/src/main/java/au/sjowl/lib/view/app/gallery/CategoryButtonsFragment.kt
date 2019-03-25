package au.sjowl.lib.view.app.gallery

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.BaseFragment
import kotlinx.android.synthetic.main.fr_cat_buttons.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.sdk27.coroutines.onClick

class CategoryButtonsFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_cat_buttons

    var animDuration = 200L

    val progressStep = 0.01f

    private var processJob: Job? = null

    private val delayStep = 10L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSubmitButton()
    }

    private fun setupSubmitButton() {
        submitButton.onSubmitClick {
            println("click submit")
            processJob?.cancel()
            processJob = progress()
        }
        submitButton.onCancelClick { println("click cancel") }
        submitButton.onDoneClick {
            println("click done")
            submitButton.showSubmit()
        }
        submitButton.onRetryClick {
            println("click retry")
            processJob?.cancel()
            processJob = progress()
        }

        stateDisabled.onClick {
            submitButton.isEnabled = false
        }
        stateReady.onClick {
            submitButton.isEnabled = true
        }
        stateProgress.onClick {
            processJob?.cancel()
            processJob = progress()
        }
        stateError.onClick {
            processJob?.cancel()
            processJob = progressWithError()
        }

        submitButton.animationDuration = animDuration
        submitButton.isEnabled = true
        val animDMax = 2000L
        val unit = animDMax / 100
        durationSeek.progress = (submitButton.animationDuration / unit).toInt()
        durationSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                animDuration = seekBar.progress * unit
                submitButton.animationDuration = seekBar.progress * unit
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun progress() = GlobalScope.launch(Dispatchers.Main) {
        var progress = 0f
        submitButton.progress = 0f
        while (progress < 1) {
            delay(delayStep)
            progress += progressStep
            submitButton.progress = progress
        }
    }

    private fun progressWithError() = GlobalScope.launch(Dispatchers.Main) {
        var progress = 0f
        submitButton.progress = 0f
        while (progress < 0.9f) {
            delay(delayStep)
            progress += progressStep
            submitButton.progress = progress
        }
        submitButton.showError()
    }
}