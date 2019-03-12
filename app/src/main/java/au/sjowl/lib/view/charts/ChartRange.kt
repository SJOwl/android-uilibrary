package au.sjowl.lib.view.charts

class ChartRange(
    var valueMin: Int = 0,
    var valueMax: Int = 0
) {
    var timeIndexStart = 0
    var timeIndexEnd = 0
    val interval get() = valueMax - valueMin
}