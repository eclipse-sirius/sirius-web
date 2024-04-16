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
package org.eclipse.sirius.components.forms.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.DateTimeStyle;
import org.eclipse.sirius.components.forms.DateTimeType;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Custom dateTime widget description.
 *
 * @author lfasani
 */
@Immutable
public final class DateTimeDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private Function<VariableManager, String> stringValueProvider;

    private BiFunction<VariableManager, String, IStatus> newValueHandler;

    private Function<VariableManager, DateTimeStyle> styleProvider;

    private DateTimeType type;

    private DateTimeDescription() {
        // Prevent instantiation
    }

    public static Builder newDateTimeDescription(String id) {
        return new Builder(id);
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, List<String>> getIconURLProvider() {
        return this.iconURLProvider;
    }

    public Function<VariableManager, String> getStringValueProvider() {
        return this.stringValueProvider;
    }

    public DateTimeType getType() {
        return this.type;
    }

    public BiFunction<VariableManager, String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public Function<VariableManager, DateTimeStyle> getStyleProvider() {
        return this.styleProvider;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'idProvider: {1}, labelProvider: {2}, iconURLProvider: {3}, stringValueProvider: {4}, newValueHandler: {5}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getIdProvider(), this.getLabelProvider(), this.getIconURLProvider(), this.getStringValueProvider(), this.getNewValueHandler());
    }

    /**
     * Builder used to create the SliderDescription.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> List.of();

        private Function<VariableManager, String> helpTextProvider;

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private Function<VariableManager, String> stringValueProvider;

        private DateTimeType type;

        private BiFunction<VariableManager, String, IStatus> newValueHandler;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, DateTimeStyle> styleProvider = vm -> null;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder iconURLProvider(Function<VariableManager, List<String>> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
            return this;
        }

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder isReadOnlyProvider(Function<VariableManager, Boolean> isReadOnlyProvider) {
            this.isReadOnlyProvider = Objects.requireNonNull(isReadOnlyProvider);
            return this;
        }

        public Builder stringValueProvider(Function<VariableManager, String> stringValueProvider) {
            this.stringValueProvider = Objects.requireNonNull(stringValueProvider);
            return this;
        }

        public Builder type(DateTimeType type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public Builder newValueHandler(BiFunction<VariableManager, String, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public Builder diagnosticsProvider(Function<VariableManager, List<?>> diagnosticsProvider) {
            this.diagnosticsProvider = Objects.requireNonNull(diagnosticsProvider);
            return this;
        }

        public Builder kindProvider(Function<Object, String> kindProvider) {
            this.kindProvider = Objects.requireNonNull(kindProvider);
            return this;
        }

        public Builder messageProvider(Function<Object, String> messageProvider) {
            this.messageProvider = Objects.requireNonNull(messageProvider);
            return this;
        }

        public Builder styleProvider(Function<VariableManager, DateTimeStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public DateTimeDescription build() {
            DateTimeDescription dateTimeDescription = new DateTimeDescription();
            dateTimeDescription.id = Objects.requireNonNull(this.id);
            dateTimeDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            dateTimeDescription.idProvider = Objects.requireNonNull(this.idProvider);
            dateTimeDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            dateTimeDescription.iconURLProvider = this.iconURLProvider; // Optional on purpose
            dateTimeDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            dateTimeDescription.stringValueProvider = Objects.requireNonNull(this.stringValueProvider);
            dateTimeDescription.type = Objects.requireNonNull(this.type);
            dateTimeDescription.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            dateTimeDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            dateTimeDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            dateTimeDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            dateTimeDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            dateTimeDescription.styleProvider = this.styleProvider;
            return dateTimeDescription;
        }
    }

}
