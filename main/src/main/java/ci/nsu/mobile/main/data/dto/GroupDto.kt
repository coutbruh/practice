package ci.nsu.mobile.main.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
//описание как выглядит одна группа
//описывает какие данные приходят с сервера и как хранить
@Serializable
data class GroupDto(
    @JsonNames("groupId")
    val id: Int,
    @JsonNames("groupName")
    val name: String
)
