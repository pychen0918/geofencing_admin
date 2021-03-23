package com.pychen0918.admin


import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
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
        post("/points"){
            val params = call.receiveParameters()
            val id = params["pointName"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val collection = params["pointCollection"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val lat = params["pointLat"]?.toDouble() ?: return@post call.respond(HttpStatusCode.BadRequest)
            val lng = params["pointLng"]?.toDouble() ?: return@post call.respond(HttpStatusCode.BadRequest)

            pointStorage.add(Point(id, collection, lat, lng))
            call.respond("OK")
        }
        get("/{...}"){
            call.respond(FreeMarkerContent(call.request.uri.replace(".html", ".ftl"), null, ""))
        }
    }
}
