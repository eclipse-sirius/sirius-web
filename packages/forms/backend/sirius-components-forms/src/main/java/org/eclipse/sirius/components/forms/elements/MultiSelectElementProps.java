/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import org.eclipse.sirius.components.forms.MultiSelectStyle;
import org.eclipse.sirius.components.forms.SelectOption;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties of the multi-select element.
 *
 * @author pcdavid
 * @author arichard
 */
@Immutable
public final class MultiSelectElementProps implements IProps {

    public static final String TYPE = "MultiSelect";

    private String id;

    private String label;

    private List<String> iconURL;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private List<SelectOption> options;

    private List<String> values;

    private Function<List<String>, IStatus> newValuesHandler;

    private MultiSelectStyle style;

    private List<Element> children;

    private MultiSelectElementProps() {
        // Prevent instantiation
    }

    public static Builder newMultiSelectElementProps(String id) {
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

    public List<SelectOption> getOptions() {
        return this.options;
    }

    public List<String> getValues() {
        return this.values;
    }

    public Function<List<String>, IStatus> getNewValuesHandler() {
        return this.newValuesHandler;
    }

    public MultiSelectStyle getStyle() {
        return this.style;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, value: {3}, options:{4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.values, this.options);
    }

    /**
     * The builder of the select element props.
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

        private List<SelectOption> options;

        private List<String> values;

        private Function<List<String>, IStatus> newValuesHandler;

        private MultiSelectStyle style;

        private List<Element> children;

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

        public Builder options(List<SelectOption> options) {
            this.options = Objects.requireNonNull(options);
            return this;
        }

        public Builder values(List<String> values) {
            this.values = Objects.requireNonNull(values);
            return this;
        }

        public Builder newValuesHandler(Function<List<String>, IStatus> newValuesHandler) {
            this.newValuesHandler = Objects.requireNonNull(newValuesHandler);
            return this;
        }

        public Builder style(MultiSelectStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public MultiSelectElementProps build() {
            MultiSelectElementProps multiSelectElementProps = new MultiSelectElementProps();
            multiSelectElementProps.id = Objects.requireNonNull(this.id);
            multiSelectElementProps.label = Objects.requireNonNull(this.label);
            multiSelectElementProps.iconURL = this.iconURL;
            multiSelectElementProps.readOnly = this.readOnly;
            multiSelectElementProps.options = Objects.requireNonNull(this.options);
            multiSelectElementProps.values = List.copyOf(this.values);
            multiSelectElementProps.newValuesHandler = Objects.requireNonNull(this.newValuesHandler);
            multiSelectElementProps.style = this.style; // Optional on purpose
            multiSelectElementProps.children = Objects.requireNonNull(this.children);
            multiSelectElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return multiSelectElementProps;
        }
    }
}
