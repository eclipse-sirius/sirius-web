/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.SelectOption;
import org.eclipse.sirius.components.forms.SelectStyle;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties of the select element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class SelectElementProps implements IProps {
    public static final String TYPE = "Select";

    private String id;

    private String label;

    private String iconURL;

    private List<SelectOption> options;

    private String value;

    private Function<String, IStatus> newValueHandler;

    private SelectStyle style;

    private List<Element> children;

    private SelectElementProps() {
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

    public List<SelectOption> getOptions() {
        return this.options;
    }

    public String getValue() {
        return this.value;
    }

    public Function<String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public SelectStyle getStyle() {
        return this.style;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newSelectElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, value: {3}, options:{4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.value, this.options);
    }

    /**
     * The builder of the select element props.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private String iconURL;

        private List<SelectOption> options;

        private String value;

        private Function<String, IStatus> newValueHandler;

        private SelectStyle style;

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

        public Builder options(List<SelectOption> options) {
            this.options = Objects.requireNonNull(options);
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder newValueHandler(Function<String, IStatus> handler) {
            this.newValueHandler = Objects.requireNonNull(handler);
            return this;
        }

        public Builder style(SelectStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public SelectElementProps build() {
            SelectElementProps selectElementProps = new SelectElementProps();
            selectElementProps.id = Objects.requireNonNull(this.id);
            selectElementProps.label = Objects.requireNonNull(this.label);
            selectElementProps.iconURL = this.iconURL;
            selectElementProps.options = Objects.requireNonNull(this.options);
            selectElementProps.value = this.value;
            selectElementProps.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            selectElementProps.style = this.style; // Optional on purpose
            selectElementProps.children = Objects.requireNonNull(this.children);
            return selectElementProps;
        }
    }
}
