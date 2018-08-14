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
package me.tadej.luas.api.xml

import me.tadej.luas.api.model.Destination
import me.tadej.luas.api.model.Forecast
import org.w3c.dom.Document
import org.w3c.dom.NodeList

internal class ForecastParser : AbstractXmlParser<Forecast>() {
    override fun convert(document: Document): Forecast {
        val stopInfo = document.getElementByTagName(STOP_INFO)
        val created = stopInfo.getAttribute(CREATED)
        val stop = stopInfo.getAttribute(STOP)
        val abbreviation = stopInfo.getAttribute(STOP_ABBREVIATION)

        val message = stopInfo.getElementByTagName(MESSAGE).textContent

        val directions = stopInfo.getElementsByTagName(DIRECTION)
        val inbound = ArrayList<Destination>()
        val outbound = ArrayList<Destination>()
        collectDestinations(directions, inbound, outbound)

        return Forecast(
            created,
            stop,
            abbreviation,
            message,
            inbound.toList(),
            outbound.toList()
        )
    }

    private fun collectDestinations(
        list: NodeList,
        inbound: ArrayList<Destination>,
        outbound: ArrayList<Destination>
    ) {
        if (list.length != 2) {
            return
        }
        val first = list.element(0)
        if (first.getAttribute(NAME) == INBOUND) {
            collectDestinations(list.children(0), inbound)
            collectDestinations(list.children(1), outbound)
        } else {
            collectDestinations(list.children(1), inbound)
            collectDestinations(list.children(0), outbound)
        }
    }

    private fun collectDestinations(trams: NodeList, list: ArrayList<Destination>) {
        trams.forEach {
            val due = it.getAttribute(DUE)
            val destination = it.getAttribute(DESTINATION)

            if (destination != NO_TRAMS) {
                val minutes = if (due == IS_DUE) {
                    0
                } else {
                    due.toInt()
                }
                list.add(Destination(destination, minutes))
            }
        }
    }

    companion object {
        private const val STOP_INFO = "stopInfo"
        private const val CREATED = "created"
        private const val STOP = "stop"
        private const val STOP_ABBREVIATION = "stopAbv"
        private const val MESSAGE = "message"
        private const val DIRECTION = "direction"
        private const val NAME = "name"
        private const val INBOUND = "Inbound"
        private const val DUE = "dueMins"
        private const val DESTINATION = "destination"
        private const val NO_TRAMS = "No trams forecast"
        private const val IS_DUE = "DUE"
    }
}
