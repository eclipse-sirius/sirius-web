/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import org.eclipse.sirius.components.forms.CheckboxStyle;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties of the checkbox element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class CheckboxElementProps implements IProps {

    public static final String TYPE = "Checkbox";

    private String id;

    private String label;

    private String iconURL;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private boolean value;

    private Function<Boolean, IStatus> newValueHandler;

    private CheckboxStyle style;

    private List<Element> children;

    private CheckboxElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    public Supplier<String> getHelpTextProvider() {
        return this.helpTextProvider;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public boolean isValue() {
        return this.value;
    }

    public Function<Boolean, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public CheckboxStyle getStyle() {
        return this.style;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newCheckboxElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, value: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.value);
    }

    /**
     * The builder of the checkbox element props.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private String iconURL;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private boolean value;

        private Function<Boolean, IStatus> newValueHandler;

        private CheckboxStyle style;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder value(boolean value) {
            this.value = Objects.requireNonNull(value);
            return this;
        }

        public Builder newValueHandler(Function<Boolean, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public Builder style(CheckboxStyle style) {
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

        public CheckboxElementProps build() {
            CheckboxElementProps checkboxElementProps = new CheckboxElementProps();
            checkboxElementProps.id = Objects.requireNonNull(this.id);
            checkboxElementProps.label = Objects.requireNonNull(this.label);
            checkboxElementProps.iconURL = this.iconURL;
            checkboxElementProps.readOnly = this.readOnly;
            checkboxElementProps.value = Objects.requireNonNull(this.value);
            checkboxElementProps.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            checkboxElementProps.style = this.style; // Optional on purpose
            checkboxElementProps.children = Objects.requireNonNull(this.children);
            checkboxElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return checkboxElementProps;
        }
    }

}
