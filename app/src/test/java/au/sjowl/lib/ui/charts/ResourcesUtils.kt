package au.sjowl.lib.ui.charts

object ResourcesUtils {
    fun getResourceAsString(resourceId: String): String {
        return this::class.java.classLoader
            .getResourceAsStream(resourceId)
            .bufferedReader(Charsets.UTF_8).use {
                it.readText()
            }
    }
}