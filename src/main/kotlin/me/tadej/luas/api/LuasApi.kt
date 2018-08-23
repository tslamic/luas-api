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
package me.tadej.luas.api

import io.reactivex.Single
import me.tadej.luas.api.model.Forecast
import me.tadej.luas.api.model.Line
import me.tadej.luas.api.network.Request
import me.tadej.luas.api.xml.ForecastParser
import me.tadej.luas.api.xml.StopsParser
import me.tadej.luas.api.xml.XmlParser

class LuasApi(
    private val forecastParser: XmlParser<Forecast> = ForecastParser(),
    private val stopsParser: XmlParser<List<Line>> = StopsParser(),
    private val timeout: Int = 10_000,
    private val useCache: Boolean = false
) {
    /**
     * Returns the list of all available lines with its corresponding stops.
     *
     * @see Line
     */
    fun lines(): Single<List<Line>> =
        Request(
            URL_STOPS,
            stopsParser,
            timeout,
            useCache
        ).get()

    /**
     * Returns the forecast for a specific stop.
     * @param stopAbbreviation the abbreviated stop name. For example, use "RAN" instead of Ranelagh.
     *
     * @see Forecast
     */
    fun forecast(stopAbbreviation: String): Single<Forecast> {
        if (stopAbbreviation.isInvalid()) {
            throw IllegalArgumentException("invalid stop abbreviation")
        }
        val stop = stopAbbreviation.toLowerCase()
        return Request(
            URL_FORECAST.format(stop),
            forecastParser,
            timeout,
            useCache
        ).get()
    }

    private fun String.isInvalid() = length != ABBREVIATION_LENGTH || isBlank()

    companion object {
        private const val ABBREVIATION_LENGTH = 3
        private const val URL_FORECAST =
            "http://luasforecasts.rpa.ie/xml/get.ashx?action=forecast&stop=%s&encrypt=false"
        private const val URL_STOPS =
            "http://luasforecasts.rpa.ie/xml/get.ashx?action=stops&encrypt=false"
    }
}
