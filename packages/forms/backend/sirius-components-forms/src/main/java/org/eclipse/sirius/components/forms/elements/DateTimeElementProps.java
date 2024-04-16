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
package org.eclipse.sirius.components.forms.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.DateTimeStyle;
import org.eclipse.sirius.components.forms.DateTimeType;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties for the DateTime element.
 *
 * @author lfasani
 */
@Immutable
public final class DateTimeElementProps implements IProps {

    public static final String TYPE = "DateTime";

    private String id;

    private String label;

    private List<String> iconURL;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private String stringValue;

    private DateTimeType type;

    private Function<String, IStatus> newValueHandler;

    private DateTimeStyle style;

    private DateTimeElementProps() {
        // Prevent instantiation
    }

    public static Builder newDateTimeElementProps(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    public Supplier<String> getHelpTextProvider() {
        return this.helpTextProvider;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public DateTimeType getType() {
        return this.type;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public Function<String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public DateTimeStyle getStyle() {
        return this.style;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, stringValue: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.stringValue);
    }

    /**
     * The builder of the dateTime element props.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private String stringValue;

        private DateTimeType type;

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

        public Builder style(DateTimeStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public DateTimeElementProps build() {
            DateTimeElementProps dateTimeElementProps = new DateTimeElementProps();
            dateTimeElementProps.id = Objects.requireNonNull(this.id);
            dateTimeElementProps.label = Objects.requireNonNull(this.label);
            dateTimeElementProps.iconURL = Objects.requireNonNull(this.iconURL);
            dateTimeElementProps.readOnly = this.readOnly;
            dateTimeElementProps.stringValue = Objects.requireNonNull(this.stringValue);
            dateTimeElementProps.type = Objects.requireNonNull(this.type);
            dateTimeElementProps.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            dateTimeElementProps.style = this.style; // Optional on purpose
            dateTimeElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return dateTimeElementProps;
        }
    }
}
