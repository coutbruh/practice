// data/api/KotlinxSerializationConverter.kt
package ci.nsu.mobile.main.data.api

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
//  Конвертер , преобразовывает kotlin обьекты в json и обратно при отправке данных черех API
@OptIn(ExperimentalSerializationApi::class)
// фабрика конвертеов для Retrofit
class KotlinxSerializationConverterFactory(
    private val json: Json = Json { ignoreUnknownKeys = true }
) : Converter.Factory() {
// преобразование запроса kotlin-json
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val serializer = getSerializer(type) ?: return null
        return Converter<Any, RequestBody> { value ->
            RequestBody.create(
                "application/json".toOkHttpMediaType(),
                json.encodeToString(serializer, value)
            )
        }
    }
// json-kotlin
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val deserializer = getDeserializer(type) ?: return null
        return Converter<ResponseBody, Any> { body ->
            json.decodeFromString(deserializer, body.string())
        }
    }
//сериализация
    private fun getSerializer(type: Type): SerializationStrategy<Any>? {
        return try {
            @Suppress("UNCHECKED_CAST")
            serializer(type) as SerializationStrategy<Any>
        } catch (e: Exception) {
            null
        }
    }
//десереализация
    private fun getDeserializer(type: Type): DeserializationStrategy<Any>? {
        return try {
            @Suppress("UNCHECKED_CAST")
            serializer(type) as DeserializationStrategy<Any>
        } catch (e: Exception) {
            null
        }
    }
}

private fun String.toOkHttpMediaType() = toMediaTypeOrNull()
