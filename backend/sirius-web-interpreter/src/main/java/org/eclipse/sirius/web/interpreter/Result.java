/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.interpreter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The evaluation result.
 *
 * @author sbegaudeau
 */
public class Result {

    private Logger logger = LoggerFactory.getLogger(Result.class);

    private final Optional<Object> rawValue;

    private final Status status;

    public Result(Optional<Object> rawValue, Status status) {
        this.rawValue = Objects.requireNonNull(rawValue);
        this.status = status;
    }

    /**
     * The status computed during the evaluation of the expression.
     *
     * @return The status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Coerces the value as an {@link Object} if possible.
     *
     * @return an {@link Object} representing the result of the evaluation, which will be empty if the raw value can not
     *         be coerced meaningfully.
     */
    public Optional<Object> asObject() {
        return this.rawValue;
    }

    /**
     * Coerces the value as a collection of Object if possible.
     *
     * @return a collection of {@link Object}, which will be empty if the raw value could not be coerced.
     */
    public Optional<List<Object>> asObjects() {
        return this.rawValue.map(value -> {
            List<Object> list = new ArrayList<>();
            if (value instanceof Collection<?>) {
                list = List.copyOf((Collection<?>) value);
            } else if (value instanceof Object) {
                list = List.of(value);
            } else if (value != null && value.getClass().isArray()) {
                list = Arrays.asList((Object[]) value);
            }
            return list;
        });
    }

    /**
     * Coerces the value as a string.
     *
     * @return a string representation of the value, which will be empty if the raw value could not be coerced
     *         meaningfully.
     */
    public Optional<String> asString() {
        return this.rawValue.map(value -> String.valueOf(value));
    }

    /**
     * Coerces the value as an int if possible.
     *
     * @return an int representing the result of the evaluation, which will be empty if the raw value can not be coerced
     *         meaningfully.
     */
    public OptionalInt asInt() {
        OptionalInt result = OptionalInt.empty();
        if (this.rawValue.isPresent()) {
            Object object = this.rawValue.get();
            if (object instanceof Integer) {
                result = OptionalInt.of(((Integer) object).intValue());
            } else {
                try {
                    result = OptionalInt.of(Integer.parseInt(String.valueOf(object)));
                } catch (NumberFormatException exception) {
                    this.logger.error(exception.getMessage(), exception);
                }
            }
        }
        return result;
    }

    /**
     * Coerces the value as a boolean if possible.
     *
     * @return a boolean representing the result of the evaluation, which will be empty if the raw value can not be
     *         coerced meaningfully.
     */
    public Optional<Boolean> asBoolean() {
        return this.rawValue.map(value -> {
            final boolean result;
            if (value == null) {
                result = false;
            } else if (value instanceof Boolean) {
                result = ((Boolean) value).booleanValue();
            } else {
                String toString = value.toString();
                if ("true".equalsIgnoreCase(toString)) { //$NON-NLS-1$
                    result = true;
                } else if ("false".equalsIgnoreCase(toString)) { //$NON-NLS-1$
                    result = false;
                } else {
                    /*
                     * raw is != null and its toString is neither true or false, this happens when the user expect the
                     * condition to check that a value is existing, then we consider any non null value returns true and
                     * null returns false.
                     */
                    result = true;
                }
            }
            return result;
        });
    }

    @Override
    public String toString() {
        return MessageFormat.format("Result { status: {0}, rawValue: {1}}", this.status, this.rawValue); //$NON-NLS-1$
    }
}
