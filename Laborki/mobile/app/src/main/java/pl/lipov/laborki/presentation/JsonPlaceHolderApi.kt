package pl.lipov.laborki.presentation

import io.reactivex.Single
import pl.lipov.laborki.data.model.Gallery
import pl.lipov.laborki.data.repository.api.dto.UserDto
import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceHolderApi {

    @GET("/posts")
    fun getPosts(): Call<List<Post>>

}