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

import me.tadej.luas.api.model.Line
import me.tadej.luas.api.model.Stop
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.util.LinkedList
import kotlin.collections.ArrayList

internal class StopsParser : AbstractXmlParser<List<Line>>() {
    override fun convert(document: Document): List<Line> {
        val lines = ArrayList<Line>()

        document.getElementsByTagName(LINE).forEach {
            val lineName = it.getAttribute(NAME)
            val stops = LinkedList<Stop>()

            it.childNodes.forEach {
                val stop = collectStop(it)
                stops.add(stop)
            }

            val line = Line(lineName, stops.toList())
            lines.add(line)
        }

        return lines
    }

    private fun collectStop(element: Element): Stop {
        val abbreviation = element.getAttribute(ABBREVIATION)
        val isParkRide = element.getAttribute(IS_PARK_RIDE)
        val isCycleRide = element.getAttribute(IS_CYCLE_RIDE)
        val lat = element.getAttribute(LATITUDE)
        val lon = element.getAttribute(LONGITUDE)
        val pronunciation = element.getAttribute(PRONUNCIATION)
        val name = element.textContent

        return Stop(
            abbreviation,
            isParkRide == "1",
            isCycleRide == "1",
            lat.toDouble(),
            lon.toDouble(),
            pronunciation,
            name
        )
    }

    companion object {
        private const val LINE = "line"
        private const val NAME = "name"
        private const val ABBREVIATION = "abrev"
        private const val IS_PARK_RIDE = "isParkRide"
        private const val IS_CYCLE_RIDE = "isCycleRide"
        private const val LATITUDE = "lat"
        private const val LONGITUDE = "long"
        private const val PRONUNCIATION = "pronunciation"
    }
}
