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
import io.ktor.serialization.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    install(FreeMarker){
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
    routing {
        static("dist"){
            resources("dist")
        }
        static("plugins"){
            resources("plugins")
        }
        get("/"){
            call.respond(FreeMarkerContent("index.ftl", null, ""))
        }
        get("/points.html"){
            call.respond(FreeMarkerContent("points.ftl", mapOf("points" to pointStorage), ""))
        }
        get("/{...}"){
            call.respond(FreeMarkerContent(call.request.uri.replace(".html", ".ftl"), null, ""))
        }
    }
}
