package au.sjowl.lib.view.buttons.submit

import android.graphics.Paint
import android.graphics.Rect
import androidx.core.content.ContextCompat
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.animations.ViewStateAnimator
import au.sjowl.lib.view.utils.colorCompat
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

class DrawHelper(
    val view: SubmitButton
) {

    val context = view.context

    val r = context.dip(40) * 1f

    val mainColor = context.colorCompat(R.color.submit_main)
    val colorError = context.colorCompat(R.color.submit_error)

    val paintBackground = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    val paintFrame = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    val paintText = Paint().apply {
        textSize = context.sp(20) * 1f
        isAntiAlias = true
    }

    val rect = Rect()

    val rectText = Rect()

    val doneSvg = ContextCompat.getDrawable(context, R.drawable.ic_ok)
        ?: throw IllegalStateException("")

    val animator = ViewStateAnimator(1800L)
}