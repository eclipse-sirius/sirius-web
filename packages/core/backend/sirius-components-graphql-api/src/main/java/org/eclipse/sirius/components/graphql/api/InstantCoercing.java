/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.graphql.api;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

/**
 * Used to support the Instant scalar.
 *
 * @author lfasani
 */
public class InstantCoercing implements Coercing<Instant, String> {
    @Override
    public String serialize(Object input) throws CoercingSerializeException {
        if (input instanceof Instant) {
            Instant instant = (Instant) input;
            try {
                return DateTimeFormatter.ISO_INSTANT.format(instant);
            } catch (DateTimeException e) {
                CoercingSerializeException exception = new CoercingSerializeException();
                throw exception;
            }
        }
        CoercingSerializeException exception = new CoercingSerializeException();
        throw exception;
    }

    @Override
    public Instant parseValue(Object input) throws CoercingParseValueException {
        Instant value = null;
        if (input instanceof Instant) {
            value = (Instant) input;
        } else if (input instanceof String) {
            try {
                value = Instant.parse((String) input);
            } catch (DateTimeParseException e) {
                CoercingParseValueException exception = new CoercingParseValueException(e);
                throw exception;
            }
        }
        return value;
    }

    @Override
    public Instant parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            StringValue stringValue = (StringValue) input;
            try {
                return Instant.parse(stringValue.getValue());
            } catch (DateTimeParseException e) {
                CoercingParseLiteralException exception = new CoercingParseLiteralException(e);
                throw exception;
            }
        }
        CoercingParseLiteralException exception = new CoercingParseLiteralException();
        throw exception;
    }
}