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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * A dateTime widget to edit a date and a time.
 *
 * @author lfasani
 */
@Immutable
public final class DateTime extends AbstractWidget {

    private String stringValue;

    private DateTimeType type;

    private Function<String, IStatus> newValueHandler;

    private DateTimeStyle style;

    private DateTime() {
        // Prevent instantiation
    }

    public static Builder newDateTime(String id) {
        return new Builder(id);
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public DateTimeType getType() {
        return this.type;
    }

    public Function<String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public DateTimeStyle getStyle() {
        return this.style;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'stringValue: {1}, newValueHandler: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getStringValue(), this.getNewValueHandler());
    }

    /**
     * Builder used to create the DateTime.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL = List.of();

        private List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private String stringValue;

        private DateTimeType type = DateTimeType.DATE_TIME;

        private Function<String, IStatus> newValueHandler;

        private DateTimeStyle style;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder stringValue(String stringValue) {
            this.stringValue = Objects.requireNonNull(stringValue);
            return this;
        }

        public Builder type(DateTimeType type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public Builder newValueHandler(Function<String, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder style(DateTimeStyle dateTimeStyle) {
            this.style = Objects.requireNonNull(dateTimeStyle);
            return this;
        }

        public DateTime build() {
            DateTime dateTime = new DateTime();
            dateTime.id = Objects.requireNonNull(this.id);
            dateTime.label = Objects.requireNonNull(this.label);
            dateTime.iconURL = Objects.requireNonNull(this.iconURL);
            dateTime.readOnly = this.readOnly;
            dateTime.stringValue = Objects.requireNonNull(this.stringValue);
            dateTime.type = Objects.requireNonNull(this.type);
            dateTime.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            dateTime.diagnostics = Objects.requireNonNull(this.diagnostics);
            dateTime.helpTextProvider = this.helpTextProvider; // Optional on purpose
            dateTime.style = this.style; // Optional on purpose
            return dateTime;
        }
    }

}
