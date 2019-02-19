package anko

import android.app.Activity
import android.os.Bundle
import au.sjowl.lib.twolinestextview.R
import org.jetbrains.anko.dip

/**
 * Generate with Plugin
 * @plugin Kotlin Anko Converter For Xml
 * @version 1.3.4
 */
class TestActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        constraintLayout {
            id = R.id.root
            imageView {
                id = R.id.icon
                imageResource = R.drawable.ic_bookmark_filled
                //app:layout_constraintEnd_toEndOf = parent //not support attribute
                //app:layout_constraintStart_toStartOf = parent //not support attribute
                //app:layout_constraintTop_toTopOf = parent //not support attribute
            }.lparams(width = dip(70), height = dip(70))
            imageView {
                id = R.id.searchOne
                imageResource = R.drawable.search_anim
                //app:layout_constraintEnd_toEndOf = parent //not support attribute
                //app:layout_constraintStart_toStartOf = parent //not support attribute
                //app:layout_constraintTop_toBottomOf = @id/icon //not support attribute
            }.lparams(width = dip(70), height = dip(70))
            imageView {
                id = R.id.searchReverse
                imageResource = R.drawable.search_reverse
                //app:layout_constraintEnd_toEndOf = parent //not support attribute
                //app:layout_constraintStart_toStartOf = parent //not support attribute
                //app:layout_constraintTop_toBottomOf = @id/searchOne //not support attribute
            }.lparams(width = dip(70), height = dip(70))
        }
    }
}
