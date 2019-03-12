package au.sjowl.lib.view.charts

class ChartRange {
    var valueMin: Int = 0
    var valueMax: Int = 0
    var timeIndexStart = 0
    var timeIndexEnd = 0
    val interval get() = valueMax - valueMin
    var chartData = ChartData()
    val timeStart get() = chartData.x.values[timeIndexStart]
    val timeEnd get() = chartData.x.values[timeIndexEnd]
    val timeInterval get() = timeEnd - timeStart
    val timeIntervalIndexes get() = timeIndexEnd - timeIndexStart
    var pointerTimeIndex = 0
    var pointerTimeX = 0f
}