package com.pychen0918.admin


import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.routing.get

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(FreeMarker){
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
    routing {
        static("css"){
            resources("static/css")
        }
        static("js"){
            resources("static/js")
        }
        static("assets"){
            resources("static/assets")
        }
        get("/"){
            call.respond(FreeMarkerContent("index.html", null, ""))
        }
        get("/{page}"){
            call.respond(FreeMarkerContent("${call.parameters["page"]}", null, ""))
        }
    }
}
