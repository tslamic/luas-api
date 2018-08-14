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

import com.google.common.truth.Truth.assertThat
import me.tadej.luas.api.model.Line
import me.tadej.luas.api.model.Stop
import org.junit.Test

class StopsParserTest {
    @Test
    fun parse_withStopsResource() = parseResource("stops.xml") {
        val parser = StopsParser()

        val lines = parser.parse(it)
        assertThat(lines).hasSize(2)

        val redLine = lines.getLineWithName("Luas Red Line")
        assertThat(redLine.stops).hasSize(32)

        val greenLine = lines.getLineWithName("Luas Green Line")
        assertThat(greenLine.stops).hasSize(35)
    }

    @Test
    fun parse_withStopsResource_hasRedStop() {
        val stop = Stop(
            "KYL",
            false,
            false,
            53.3266555555556,
            -6.34344444444444,
            "Kylemore",
            "Kylemore"
        )
        parse_withStopsResource_hasStop("Luas Red Line", stop)
    }

    @Test
    fun parse_withStopsResource_hasGreenStop() {
        val stop = Stop(
            "SAN",
            true,
            true,
            53.2776027777778,
            -6.20467777777778,
            "Sandyford",
            "Sandyford"
        )
        parse_withStopsResource_hasStop("Luas Green Line", stop)
    }

    private fun parse_withStopsResource_hasStop(lineName: String, vararg stops: Stop) {
        parseResource("stops.xml") {
            val parser = StopsParser()
            val lines = parser.parse(it)
            val line = lines.getLineWithName(lineName)

            assertThat(line.stops).containsAllIn(stops)
        }
    }

    private fun List<Line>.getLineWithName(name: String): Line {
        forEach {
            if (it.name == name) {
                return it
            }
        }
        throw AssertionError("no line with name $name")
    }
}
