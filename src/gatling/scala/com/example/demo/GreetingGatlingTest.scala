package com.example.demo

import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.core.json.JsonParsers
import io.gatling.http.Predef._

import scala.concurrent.duration._
import org.slf4j.{Logger, LoggerFactory}
import io.gatling.http.request.builder.HttpRequestBuilder

class GreetingGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val httpConf = http
        .baseURL(PerfTestConfig.appBaseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")


    val headers_http = Map(
        "Accept" -> """application/json""",
        "Content-Type" -> """application/json"""
    )
    val token_headers_http = Map(
        "Accept" -> """application/json""",
        "Content-Type" -> """application/x-www-form-urlencoded"""
    )

    val scn = scenario("Test")
        .exec(http("Greeting")
            .get("/greeting")
            .basicAuth(PerfTestConfig.username, PerfTestConfig.password)
            .headers(headers_http)
            .check(status.is(200))).exitHereIfFailed

    val userRegistrationScenario = scenario("Test").exec(scn)

    setUp(
        userRegistrationScenario.inject(
            constantUsersPerSec(PerfTestConfig.usersPerSecond) during (PerfTestConfig.holdFor minutes)
        )
    ).protocols(httpConf)
}

