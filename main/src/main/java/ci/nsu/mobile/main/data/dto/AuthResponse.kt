package ci.nsu.mobile.main.data.dto

import kotlinx.serialization.Serializable

//описывает какие данные сервер присылает после входа/регистрации
@Serializable
data class AuthResponse(
    val token: String
)
