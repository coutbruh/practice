package ci.nsu.mobile.main.data.api

import ci.nsu.mobile.main.data.datasource.local.TokenManager
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit 
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // For emulator to reach computer's localhost
    const val BASE_URL = "http://10.0.2.2:8080/" // "http://10.0.2.2:8080/"  "http://192.168.200.160:8080/"

    //настройки парсера
    private val json = Json {
        ignoreUnknownKeys = true //если лишние поля , то игнорим
        isLenient = true // разрешаем нестрогий формат json
        coerceInputValues = true // преобразуем значения
    }

    private fun getClient(tokenManager: TokenManager): OkHttpClient {
       // логирования всех запросов и ответов
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // логируем тело
        }

        val authInterceptor = AuthInterceptor(tokenManager)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) //добавляем логирование
            .addInterceptor(authInterceptor) // авторизацию
            .connectTimeout(30, TimeUnit.SECONDS) // таймауты поделюч
            .readTimeout(30, TimeUnit.SECONDS) // и чтения
            .build()
    }

    fun getApiService(tokenManager: TokenManager): ApiService {
        val client = getClient(tokenManager)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // адрес серва
            .client(client) // настроенный клиент http
            .addConverterFactory(KotlinxSerializationConverterFactory(json))
            .build() // преобраз. в json

        return retrofit.create(ApiService::class.java) // создаем реализацию api
    }
}
