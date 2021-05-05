package pl.lipov.laborki.data.repository.api

import io.reactivex.Single
import pl.lipov.laborki.data.Api
import pl.lipov.laborki.data.repository.api.dto.GalleryDto
import pl.lipov.laborki.data.repository.api.dto.UnlockKeyDto
import pl.lipov.laborki.data.repository.api.dto.UserDto

class LoginRepository(
    private val loginApi: Api
) {
    lateinit var unlockPassword: UnlockKeyDto
    fun getUsers(): Single<List<UserDto>> = loginApi.getUsers()
    fun getGalleries(): Single<List<GalleryDto>> = loginApi.getGalleries()
}
