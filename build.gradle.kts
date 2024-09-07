import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
}
ext {

    fun getLocalProperty(key: String, project: Project): String {
        val properties = Properties()
        val localPropertiesFile = File(project.rootDir, "local.properties")
        if (localPropertiesFile.exists()) {
            properties.load(FileInputStream(localPropertiesFile))
        }
        return properties.getProperty(key) ?: ""
    }
    extra["API_KEY"] = getLocalProperty("API_KEY", project)
}