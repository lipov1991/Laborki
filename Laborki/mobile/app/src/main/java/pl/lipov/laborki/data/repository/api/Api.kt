package pl.lipov.laborki.data.repository.api

import io.reactivex.Single
import pl.lipov.laborki.data.model.Gallery
import pl.lipov.laborki.data.repository.api.dto.UserDto
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("/users.json")
    fun getUsers(): Single<List<UserDto>>

    @GET("/galeries.json")
    fun getGalleries(): Single<List<Gallery>>

}