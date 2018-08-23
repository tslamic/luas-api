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

import me.tadej.luas.api.LuasApi
import org.junit.Test

class LuasApiTest {
    @Test(expected = IllegalArgumentException::class)
    fun forecast_withEmptyStop_throws() {
        LuasApi().forecast("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun forecast_withBlankStop_throws() {
        LuasApi().forecast(" \t")
    }

    @Test(expected = IllegalArgumentException::class)
    fun forecast_withLongStopName_throws() {
        LuasApi().forecast("ILLEGAL")
    }

    @Test(expected = IllegalArgumentException::class)
    fun forecast_withShortStopName_throws() {
        LuasApi().forecast("IL")
    }
}