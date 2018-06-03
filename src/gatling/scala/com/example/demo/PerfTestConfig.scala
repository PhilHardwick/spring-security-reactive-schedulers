package com.example.demo

object PerfTestConfig {

    val holdFor: Int = Integer.getInteger("duration", 2).toInt
    val usersPerSecond: Int = Integer.getInteger("usersPerSecond", 80).toInt
    val appBaseURL: String = Option(System.getProperty("appBaseURL")) getOrElse """http://localhost:8080"""
    val username: String = Option(System.getProperty("username")) getOrElse "admin"
    val password: String = Option(System.getProperty("password")) getOrElse "admin"

}
