package com.nokhyun.timer

import retrofit2.http.GET

interface MyApi {

    @GET("/users/1")
    @Authenticated
    suspend fun getUser()

    @GET("/posts/1")
    @Authenticated
    suspend fun getPost()
}

@Target(AnnotationTarget.FUNCTION)
annotation class Authenticated