/*******************************************************************************
 * Copyright (c) 2026 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.infrastructure.configuration.graphql;

import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.web.library.domain.LibraryId;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

/**
 * Used to support the LibraryId scalar.
 *
 * @author gdaniel
 */
public class LibraryIdCoercing implements Coercing<LibraryId, String> {

    @Override
    public String serialize(Object input) throws CoercingSerializeException {
        if (input instanceof LibraryId(String namespace, String name, String version)) {
            return namespace + ":" + name + "@" + version;
        }
        CoercingSerializeException coercingSerializeException = new CoercingSerializeException("Expected a LibraryId.");
        throw coercingSerializeException;
    }

    @Override
    public LibraryId parseValue(Object input) throws CoercingParseValueException {
        if (input instanceof String value) {
            Optional<LibraryId> optionalLibraryId = this.parse(value);
            if (optionalLibraryId.isPresent()) {
                return optionalLibraryId.get();
            }
        } else if (input instanceof Map<?, ?> value) {
            Optional<LibraryId> optionalLibraryId = this.parse(value);
            if (optionalLibraryId.isPresent()) {
                return optionalLibraryId.get();
            }
        }
        CoercingParseValueException coercingParseValueException = new CoercingParseValueException("Expected a library identifier formatted as namespace:name@version.");
        throw  coercingParseValueException;
    }

    @Override
    public LibraryId parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue stringValue) {
            Optional<LibraryId> optionalLibraryId = this.parse(stringValue.getValue());
            if (optionalLibraryId.isPresent()) {
                return optionalLibraryId.get();
            }
        } else if (input instanceof Map<?, ?> value) {
            Optional<LibraryId> optionalLibraryId = this.parse(value);
            if (optionalLibraryId.isPresent()) {
                return optionalLibraryId.get();
            }
        }
        CoercingParseValueException coercingParseValueException = new CoercingParseValueException("Expected a library identifier formatted as namespace:name@version.");
        throw  coercingParseValueException;
    }

    private Optional<LibraryId> parse(String value) {
        Optional<LibraryId> result = Optional.empty();
        int versionSeparator = value.lastIndexOf('@');
        int nameSeparator = value.indexOf(':');
        if (nameSeparator > 0 && versionSeparator > nameSeparator) {
            result = Optional.of(new LibraryId(value.substring(0, nameSeparator), value.substring(nameSeparator + 1, versionSeparator), value.substring(versionSeparator + 1)));
        }
        return result;
    }

    private Optional<LibraryId> parse(Map<?, ?> value) {
        Optional<LibraryId> result = Optional.empty();

        var rawNamespace = value.get("namespace");
        var rawName = value.get("name");
        var rawVersion = value.get("version");
        if (rawNamespace instanceof String namespace
                && rawName instanceof String name
                && rawVersion instanceof String version) {
            result = Optional.of(new LibraryId(namespace, name, version));
        }
        return result;
    }
}
