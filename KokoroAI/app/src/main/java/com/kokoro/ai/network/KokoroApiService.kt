package com.kokoro.ai.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface KokoroApiService {

    // Chat with AI (Gemini)
    @POST("api/Chat")
    suspend fun sendChatMessage(@Body request: ChatRequestDto): Response<ChatResponseDto>

    // Dashboard
    @GET("api/Dashboard")
    suspend fun getDashboard(): Response<DashboardResponseDto>

    @POST("api/Dashboard/action")
    suspend fun executeQuickAction(@Body request: QuickActionRequestDto): Response<QuickActionResultDto>

    // Wardrobe & VIP
    @GET("api/Wardrobe")
    suspend fun getWardrobe(): Response<WardrobeResponseDto>

    @POST("api/Wardrobe/equip/{outfitId}")
    suspend fun equipOutfit(@Path("outfitId") outfitId: String): Response<EquipOutfitResponseDto>

    @POST("api/Wardrobe/subscribe-trial")
    suspend fun subscribeVipTrial(): Response<SubscribeVipResponseDto>

    // Personality
    @GET("api/Personality")
    suspend fun getPersonality(): Response<PersonalitySettingsDto>

    @PUT("api/Personality")
    suspend fun updatePersonality(@Body request: UpdatePersonalityRequestDto): Response<PersonalitySettingsDto>
}

object NetworkClient {
    const val BASE_URL = "https://pc4gr3cx-5123.brs.devtunnels.ms/"

    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                // Bypass Microsoft Dev Tunnels anti-abuse interstitial landing page
                val request = original.newBuilder()
                    .header("X-Tunnel-Skip-Anti-Abuse-Page", "true")
                    .header("Accept", "application/json")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .callTimeout(150, TimeUnit.SECONDS)
            .build()
    }

    val apiService: KokoroApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KokoroApiService::class.java)
    }
}
