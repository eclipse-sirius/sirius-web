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
        if (input instanceof LibraryId libraryId) {
            return libraryId.namespace() + ":" + libraryId.name() + "@" + libraryId.version();
        }
        throw new CoercingSerializeException("Expected a LibraryId.");
    }

    @Override
    public LibraryId parseValue(Object input) throws CoercingParseValueException {
        if (input instanceof String value) {
            try {
                return this.parse(value);
            } catch (IllegalArgumentException exception) {
                throw new CoercingParseValueException(exception);
            }
        }
        throw new CoercingParseValueException("Expected a library identifier formatted as namespace:name@version.");
    }

    @Override
    public LibraryId parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue value) {
            try {
                return this.parse(value.getValue());
            } catch (IllegalArgumentException exception) {
                throw new CoercingParseLiteralException(exception);
            }
        }
        throw new CoercingParseLiteralException("Expected a library identifier formatted as namespace:name@version.");
    }

    private LibraryId parse(String value) {
        int versionSeparator = value.lastIndexOf('@');
        int nameSeparator = value.indexOf(':');
        if (nameSeparator <= 0 || nameSeparator + 1 >= versionSeparator || versionSeparator + 1 >= value.length()) {
            throw new IllegalArgumentException("Expected a library identifier formatted as namespace:name@version.");
        }
        return new LibraryId(value.substring(0, nameSeparator), value.substring(nameSeparator + 1, versionSeparator), value.substring(versionSeparator + 1));
    }
}
