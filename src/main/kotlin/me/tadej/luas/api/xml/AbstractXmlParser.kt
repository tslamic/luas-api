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

import org.w3c.dom.Document
import org.xml.sax.ErrorHandler
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

abstract class AbstractXmlParser<T>(
    private val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance(),
    private val errorHandler: ErrorHandler = ThrowableErrorHandler
) : XmlParser<T> {

    final override fun parse(stream: InputStream): T {
        val builder = factory.newDocumentBuilder()
        builder.setErrorHandler(errorHandler)

        val document = builder.parse(stream)
        document.documentElement.normalize()

        return convert(document)
    }

    protected abstract fun convert(document: Document): T
}
