/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties of the rich text element.
 *
 * @author pcdavid
 */
@Immutable
public final class RichTextElementProps implements IProps {

    public static final String TYPE = "RichText";

    private String id;

    private String label;

    private String iconURL;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private String value;

    private Function<String, IStatus> newValueHandler;

    private List<Element> children;

    private RichTextElementProps() {
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

    public String getValue() {
        return this.value;
    }

    public Function<String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newRichTextElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, value: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.value);
    }

    /**
     * The builder of the textarea element props.
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

        private String value;

        private Function<String, IStatus> newValueHandler;

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

        public Builder value(String value) {
            this.value = Objects.requireNonNull(value);
            return this;
        }

        public Builder newValueHandler(Function<String, IStatus> handler) {
            this.newValueHandler = Objects.requireNonNull(handler);
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

        public RichTextElementProps build() {
            RichTextElementProps textareaElementProps = new RichTextElementProps();
            textareaElementProps.id = Objects.requireNonNull(this.id);
            textareaElementProps.label = Objects.requireNonNull(this.label);
            textareaElementProps.iconURL = this.iconURL;
            textareaElementProps.readOnly = this.readOnly;
            textareaElementProps.value = Objects.requireNonNull(this.value);
            textareaElementProps.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            textareaElementProps.children = Objects.requireNonNull(this.children);
            textareaElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return textareaElementProps;
        }
    }
}
