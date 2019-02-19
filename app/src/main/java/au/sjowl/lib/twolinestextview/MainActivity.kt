package au.sjowl.lib.twolinestextview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        runTest()
    }

    fun withInflater(): Long {
        times++
        container.removeAllViews()

        val t = measureTimeMillis {
            container.addView(LayoutInflater.from(this).inflate(R.layout.layout_test, null))
        }
        return t
    }

    fun withCode(): Long {
        times++

        container.removeAllViews()
        container.invalidate()

        val t = measureTimeMillis {
            container.addView(getCodedView())
        }
        return t
    }

    fun getCodedView(): ConstraintLayout {

        return ConstraintLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
            id = R.id.root
            val cs = ConstraintSet()
            addView(ImageView(context).apply {
                id = R.id.icon
                layoutParams = ViewGroup.LayoutParams(context.dip(70), context.dip(70))
                setImageResource(R.drawable.ic_bookmark_filled)
                cs.connectDefaults(R.id.icon, R.id.root)
            })
            addView(ImageView(context).apply {
                id = R.id.searchOne
                layoutParams = ViewGroup.LayoutParams(context.dip(70), context.dip(70))
                setImageResource(R.drawable.search_anim)
                cs.connectHorizonts(id, R.id.root)
                cs.connectTopToBottom(id, R.id.icon)
            })
//            addView(ImageView(context).apply {
//                id = R.id.searchReverse
//                layoutParams = ViewGroup.LayoutParams(context.dip(70), context.dip(70))
//                setImageResource(R.drawable.search_reverse)
//                cs.connectHorizonts(id)
//                cs.connectTopToBottom(id, R.id.searchOne)
//            })
            cs.applyTo(this)
        }
    }

    fun ConstraintSet.connectDefaults(id: Int, idTo: Int) {
        connect(id, ConstraintSet.START, idTo, ConstraintSet.START)
        connect(id, ConstraintSet.TOP, idTo, ConstraintSet.TOP)
        connect(id, ConstraintSet.BOTTOM, idTo, ConstraintSet.BOTTOM)
        connect(id, ConstraintSet.END, idTo, ConstraintSet.END)
    }

    fun ConstraintSet.connectHorizonts(id: Int, idTo: Int) {
        connect(id, ConstraintSet.START, idTo, ConstraintSet.START)
        connect(id, ConstraintSet.BOTTOM, idTo, ConstraintSet.BOTTOM)
    }

    fun ConstraintSet.connectTopToBottom(id: Int, idTo: Int) {
        connect(id, ConstraintSet.TOP, idTo, ConstraintSet.BOTTOM)
    }

    var times = 0

    fun runTest() {
        var totalTime = 0L
        val count = 10
        totalTime += withInflater()

        val inflaterResult = totalTime / count
        println("inflate withInflater = ${inflaterResult}")

        totalTime = 0L
        totalTime += withCode()
        val codeResult = totalTime / count
        println("inflate withCode = ${totalTime / count}")
        println("inflate times total = $times")
        println("inflate ratio = ${inflaterResult * 1f / codeResult}")
    }
}