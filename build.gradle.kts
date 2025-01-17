import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}



fun translate(endpoint: String, file1: Path, file2: Path) {
    val boundary = "Boundary-${System.currentTimeMillis()}"
    HttpClient.newHttpClient().send(
        HttpRequest.newBuilder().uri(URI.create(endpoint))
            .header("Content-Type", "multipart/form-data; boundary=$boundary")
            .header("Authorization", "Bearer ${System.getenv("API_TOKEN")}")
            .POST(
                HttpRequest.BodyPublishers.ofString(
                    "--$boundary\r\n" +
                            "Content-Disposition: form-data; name=\"source\"; filename=\"${file1.fileName}\"\r\n" +
                            "Content-Type: application/octet-stream\r\n" +
                            "\r\n" +
                            "${Files.readString(file1)}\r\n" +
                            "--$boundary\r\n" +
                            "Content-Disposition: form-data; name=\"target\"; filename=\"${file2.fileName}\"\r\n" +
                            "Content-Type: application/octet-stream\r\n" +
                            "\r\n" +
                            "${Files.readString(file2)}\r\n" +
                            "--$boundary--\r\n"
                )
            )
            .build(), HttpResponse.BodyHandlers.ofFile(file2)
    );
}

tasks.register("doloc-de") {
    doLast {
        translate("https://api.doloc.io?targetLang=de", project.file("app/src/main/res/values/strings.xml").toPath(), project.file("app/src/main/res/values-de/strings.xml").toPath())
    }
}

tasks.register("doloc-fr") {
    doLast {
        translate("https://api.doloc.io?targetLang=fr", project.file("app/src/main/res/values/strings.xml").toPath(), project.file("app/src/main/res/values-fr/strings.xml").toPath())
    }
}

tasks.register("update-i18n") {
    dependsOn("doloc-de", "doloc-fr")
}