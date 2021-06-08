package pl.lipov.laborki.data.repository

import io.reactivex.Single
import pl.lipov.laborki.data.repository.api.Api
import pl.lipov.laborki.data.repository.api.dto.UserDto

class LoginRepository(
    private val api: Api
) {
    fun getUsers(): Single<List<UserDto>> = api.getUsers()
}
