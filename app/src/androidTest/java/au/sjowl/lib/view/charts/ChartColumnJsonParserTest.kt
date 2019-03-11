package au.sjowl.lib.view.charts

import org.junit.Test

class ChartColumnJsonParserTest {
    @Test
    fun ParseJsonDataTest() {
        val json = ResourcesUtils.getResourceAsString("chart_data.json")

        val charts = ChartColumnJsonParser(json).parse()
        println(charts)
    }
}