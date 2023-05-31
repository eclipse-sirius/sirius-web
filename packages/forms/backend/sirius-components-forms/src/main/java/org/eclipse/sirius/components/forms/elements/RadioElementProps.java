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
import org.eclipse.sirius.components.forms.RadioOption;
import org.eclipse.sirius.components.forms.RadioStyle;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties of the radio element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class RadioElementProps implements IProps {
    public static final String TYPE = "Radio";

    private String id;

    private String label;

    private String iconURL;

    private Supplier<String> helpTextProvider;

    private List<RadioOption> options;

    private Function<String, IStatus> newValueHandler;

    private RadioStyle style;

    private List<Element> children;

    private RadioElementProps() {
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

    public List<RadioOption> getOptions() {
        return this.options;
    }

    public Function<String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public RadioStyle getStyle() {
        return this.style;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newRadioElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, options: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.options);
    }

    /**
     * The builder of the radio element props.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String iconURL;

        private Supplier<String> helpTextProvider;

        private List<RadioOption> options;

        private Function<String, IStatus> newValueHandler;

        private RadioStyle style;

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

        public Builder options(List<RadioOption> options) {
            this.options = Objects.requireNonNull(options);
            return this;
        }

        public Builder newValueHandler(Function<String, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public Builder style(RadioStyle style) {
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

        public RadioElementProps build() {
            RadioElementProps radioElementProps = new RadioElementProps();
            radioElementProps.id = Objects.requireNonNull(this.id);
            radioElementProps.label = Objects.requireNonNull(this.label);
            radioElementProps.iconURL = this.iconURL;
            radioElementProps.options = Objects.requireNonNull(this.options);
            radioElementProps.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            radioElementProps.style = this.style; // Optional on purpose
            radioElementProps.children = Objects.requireNonNull(this.children);
            radioElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return radioElementProps;
        }
    }
}
