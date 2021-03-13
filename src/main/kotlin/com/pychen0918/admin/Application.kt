package com.pychen0918.admin


import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.routing.get

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(FreeMarker){
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "temp")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
    routing {
        static("dist"){
            resources("temp/dist")
        }
        static("plugins"){
            resources("temp/plugins")
        }
        get("/"){
            call.respond(FreeMarkerContent("index.html", null, ""))
        }
        get("/{...}"){
            call.respond(FreeMarkerContent(call.request.uri, null, ""))
        }
    }
}
