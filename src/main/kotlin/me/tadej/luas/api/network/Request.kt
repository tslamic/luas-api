/*
 * Copyright 2018 Tadej Slamic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.tadej.luas.api.network

import io.reactivex.Single
import me.tadej.luas.api.xml.XmlParser
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

internal class Request<T>(
    private val url: String,
    private val parser: XmlParser<T>,
    private val timeout: Int,
    private val useCache: Boolean
) {
    fun get(): Single<T> = Single.just(performGetRequest())

    @Throws(IOException::class)
    private fun performGetRequest(): T {
        var connection: HttpURLConnection? = null
        return try {
            connection = openConnection("GET")
            val responseCode = connection.responseCode
            when {
                responseCode == -1 -> throw IOException("invalid responseCode")
                responseCode != 200 -> throw IOException("$responseCode")
                else -> connection.inputStream.use {
                    parser.parse(it)
                }
            }
        } finally {
            connection?.disconnect()
        }
    }

    @Throws(IOException::class)
    private fun openConnection(method: String): HttpURLConnection {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = method
        connection.instanceFollowRedirects = HttpURLConnection.getFollowRedirects()
        connection.connectTimeout = timeout
        connection.readTimeout = timeout
        connection.useCaches = useCache
        connection.setRequestProperty("Accept", "application/xml")
        return connection
    }
}
