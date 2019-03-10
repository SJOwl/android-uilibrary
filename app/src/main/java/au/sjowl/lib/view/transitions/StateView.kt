package au.sjowl.lib.view.transitions

interface StateView {
    val heightMin: Int
    val heightMax: Int
    val heightRange: Range

    fun onSetStateToEdgeValues()
    fun increaseSize(deltaX: Float, deltaY: Float)
}