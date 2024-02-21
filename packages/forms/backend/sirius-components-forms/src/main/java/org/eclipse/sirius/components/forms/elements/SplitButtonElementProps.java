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

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.text.MessageFormat;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the splitButton element.
 *
 * @author mcharfadi
 */
@Immutable
public final class SplitButtonElementProps implements IProps {

    public static final String TYPE = "SplitButton";

    private String id;

    private String label;

    private List<String> iconURL;

    private boolean readOnly;

    private Supplier<String> helpTextProvider;

    private List<Element> children;

    private SplitButtonElementProps() {
        // Prevent instantiation
    }

    public static Builder newSplitButtonElementProps(String id) {
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

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public Supplier<String> getHelpTextProvider() {
        return this.helpTextProvider;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder of the splitButton element props.
     *
     * @author mcharfadi
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private List<String> iconURL;

        private boolean readOnly;

        private Supplier<String> helpTextProvider;

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

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public SplitButtonElementProps build() {
            SplitButtonElementProps buttonElementProps = new SplitButtonElementProps();
            buttonElementProps.id = Objects.requireNonNull(this.id);
            buttonElementProps.label = Objects.requireNonNull(this.label);
            buttonElementProps.iconURL = this.iconURL;
            buttonElementProps.readOnly = this.readOnly;
            buttonElementProps.children = Objects.requireNonNull(this.children);
            buttonElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return buttonElementProps;
        }
    }
}
