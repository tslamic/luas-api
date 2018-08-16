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
import me.tadej.luas.api.model.Destination
import org.junit.Test

class ForecastParserTest {
    @Test
    fun parse_withRanelaghResource() = withResource("ranelagh.xml") {
        val parser = ForecastParser()

        val forecast = parser.parse(it)
        assertThat(forecast.stop).isEqualTo("Ranelagh")
        assertThat(forecast.stopAbbreviation).isEqualTo("RAN")
        assertThat(forecast.created).isEqualTo("2018-08-11T09:25:55")
        assertThat(forecast.message).isEqualTo("Green Line services operating normally")

        val inbound = listOf(
            Destination("Parnell", 7),
            Destination("Broombridge", 13)
        )
        assertThat(forecast.inbound).containsExactlyElementsIn(inbound)

        val outbound = listOf(
            Destination("Bride's Glen", 0),
            Destination("Sandyford", 7),
            Destination("Bride's Glen", 15)
        )
        assertThat(forecast.outbound).containsExactlyElementsIn(outbound)
    }

    @Test
    fun parse_withBridesGlenResource() = withResource("bridesglen.xml") {
        val parser = ForecastParser()

        val forecast = parser.parse(it)
        assertThat(forecast.stop).isEqualTo("Bride's Glen")
        assertThat(forecast.stopAbbreviation).isEqualTo("BRI")
        assertThat(forecast.created).isEqualTo("2018-08-11T10:51:51")
        assertThat(forecast.message).isEqualTo("Green Line services operating normally")

        val inbound = listOf(
            Destination("Parnell", 6)
        )

        assertThat(forecast.inbound).containsExactlyElementsIn(inbound)
        assertThat(forecast.outbound).isEmpty()
    }
}
