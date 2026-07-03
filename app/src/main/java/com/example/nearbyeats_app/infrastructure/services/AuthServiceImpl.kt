package com.example.nearbyeats_app.infrastructure.services

import android.content.Context
import com.example.nearbyeats_app.domain.model.AuthSession
import com.example.nearbyeats_app.domain.service.AuthService
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthServiceImpl(context: Context) : AuthService {
    private val appContext = context.applicationContext
    private val preferences = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val sslContext = createUnsafeSslContext()

    override suspend fun signIn(username: String, password: String): AuthSession = withContext(Dispatchers.IO) {
        val response = executePost("/api/v1/Authentication/sign-in", username, password)
        val json = JSONObject(response)
        val session = AuthSession(
            id = json.getInt("id"),
            username = json.getString("username"),
            token = json.getString("token")
        )
        saveSession(session)
        session
    }

    override suspend fun signUp(username: String, password: String): String = withContext(Dispatchers.IO) {
        val response = executePost("/api/v1/Authentication/sign-up", username, password)
        val json = JSONObject(response)
        json.optString("message", "User created successfully")
    }

    override fun logout() {
        preferences.edit()
            .remove(KEY_ID)
            .remove(KEY_USERNAME)
            .remove(KEY_TOKEN)
            .apply()
    }

    override fun isLoggedIn(): Boolean = preferences.contains(KEY_TOKEN)

    override fun currentSession(): AuthSession? {
        if (!isLoggedIn()) return null

        return AuthSession(
            id = preferences.getInt(KEY_ID, 0),
            username = preferences.getString(KEY_USERNAME, "").orEmpty(),
            token = preferences.getString(KEY_TOKEN, "").orEmpty()
        )
    }

    private fun saveSession(session: AuthSession) {
        preferences.edit()
            .putInt(KEY_ID, session.id)
            .putString(KEY_USERNAME, session.username)
            .putString(KEY_TOKEN, session.token)
            .apply()
    }

    private fun executePost(path: String, username: String, password: String): String {
        val connection = openConnection(path)
        val payload = JSONObject()
            .put("username", username)
            .put("password", password)
            .toString()

        try {
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("Content-Type", "application/json")

            connection.outputStream.use { output ->
                output.write(payload.toByteArray(Charsets.UTF_8))
            }

            val responseCode = connection.responseCode
            val body = readBody(connection, responseCode)

            if (responseCode !in 200..299) {
                throw IllegalStateException(body.ifBlank { connection.responseMessage })
            }

            return body
        } finally {
            connection.disconnect()
        }
    }

    private fun openConnection(path: String): HttpURLConnection {
        val connection = URL("$BASE_URL$path").openConnection()

        if (connection is HttpsURLConnection) {
            connection.sslSocketFactory = sslContext.socketFactory
            connection.hostnameVerifier = HostnameVerifier { _, _ -> true }
        }

        return connection as HttpURLConnection
    }

    private fun readBody(connection: HttpURLConnection, responseCode: Int): String {
        val stream = if (responseCode in 200..299) connection.inputStream else connection.errorStream
        if (stream == null) return ""

        return BufferedReader(InputStreamReader(stream)).use { reader ->
            reader.readText()
        }
    }

    private fun createUnsafeSslContext(): SSLContext {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) = Unit
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) = Unit
            override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
        })

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, SecureRandom())
        return sslContext
    }

    private companion object {
        const val BASE_URL = "https://10.0.2.2:5261"
        const val PREFS_NAME = "nearbyeats_auth"
        const val KEY_ID = "auth_id"
        const val KEY_USERNAME = "auth_username"
        const val KEY_TOKEN = "auth_token"
    }
}
