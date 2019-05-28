package au.sjowl.lib.ui.views.seek

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Paint
import au.sjowl.lib.ui.views.utils.colorCompat
import au.sjowl.lib.uxlibrary.R

class RangeSeekPaints(context: Context, dimens: RangeSeekDimensions) {
    val colorUnselected = context.colorCompat(R.color.seekUnselected)
    val colorSelected = context.colorCompat(R.color.seekSelected)
    val colorPointer = context.colorCompat(R.color.seekKnob)
    val colorShadow = context.colorCompat(R.color.seekShadow)

    val lineUnselected = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = dimens.width
        color = colorUnselected
    }

    val lineSelected = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = dimens.width
        color = colorSelected
    }

    val pointer = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = colorPointer
    }
    val pointerShadow = Paint().apply {
        style = Paint.Style.FILL
        color = colorShadow
        maskFilter = BlurMaskFilter(dimens.shadowRadius, BlurMaskFilter.Blur.NORMAL)
    }
}