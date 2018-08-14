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
@file:JvmName("DomUtils")

package me.tadej.luas.api.xml

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList

fun Document.getElementByTagName(tag: String, index: Int = 0): Element =
    getElementsByTagName(tag).item(index) as Element

fun Element.getElementByTagName(tag: String, index: Int = 0): Element =
    getElementsByTagName(tag).item(index) as Element

fun NodeList.element(index: Int = 0): Element = item(index) as Element

fun NodeList.children(index: Int): NodeList = item(index).childNodes

fun NodeList.forEach(action: (Element) -> Unit) {
    for (i in 0 until length) {
        val item = item(i)
        if (item is Element) {
            action(item)
        }
    }
}
