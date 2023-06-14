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
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The properties of the button element.
 *
 * @author arichard
 */
@Immutable
public final class ButtonElementProps implements IProps {

    public static final String TYPE = "Button";

    private String id;

    private String label;

    private String iconURL;

    private boolean readOnly;

    private String buttonLabel;

    private String imageURL;

    private Supplier<String> helpTextProvider;

    private Supplier<IStatus> pushButtonHandler;

    private ButtonStyle style;

    private List<Element> children;

    private ButtonElementProps() {
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

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public String getButtonLabel() {
        return this.buttonLabel;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public Supplier<String> getHelpTextProvider() {
        return this.helpTextProvider;
    }

    public Supplier<IStatus> getPushButtonHandler() {
        return this.pushButtonHandler;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public ButtonStyle getStyle() {
        return this.style;
    }

    public static Builder newButtonElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, buttonLabel: {3}, imageURL: {4},'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.buttonLabel, this.imageURL);
    }

    /**
     * The builder of the textfield element props.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private String iconURL;

        private boolean readOnly;

        private String buttonLabel;

        private String imageURL;

        private Supplier<String> helpTextProvider;

        private Supplier<IStatus> pushButtonHandler;

        private ButtonStyle style;

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

        public Builder buttonLabel(String buttonLabel) {
            this.buttonLabel = Objects.requireNonNull(buttonLabel);
            return this;
        }

        public Builder imageURL(String imageURL) {
            this.imageURL = Objects.requireNonNull(imageURL);
            return this;
        }

        public Builder pushButtonHandler(Supplier<IStatus> pushButtonHandler) {
            this.pushButtonHandler = Objects.requireNonNull(pushButtonHandler);
            return this;
        }

        public Builder style(ButtonStyle style) {
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

        public ButtonElementProps build() {
            ButtonElementProps buttonElementProps = new ButtonElementProps();
            buttonElementProps.id = Objects.requireNonNull(this.id);
            buttonElementProps.label = Objects.requireNonNull(this.label);
            buttonElementProps.buttonLabel = this.buttonLabel;
            buttonElementProps.iconURL = this.iconURL;
            buttonElementProps.readOnly = this.readOnly;
            buttonElementProps.imageURL = this.imageURL;
            buttonElementProps.pushButtonHandler = Objects.requireNonNull(this.pushButtonHandler);
            buttonElementProps.style = this.style; // Optional on purpose
            buttonElementProps.children = Objects.requireNonNull(this.children);
            buttonElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return buttonElementProps;
        }
    }
}
