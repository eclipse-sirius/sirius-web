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
public final class ToolbarActionElementProps implements IProps {
    public static final String TYPE = "ToolbarAction";

    private String id;

    private String label;

    private String iconURL;

    private Supplier<String> helpTextProvider;

    private String toolbarActionLabel;

    private String imageURL;

    private Supplier<IStatus> pushButtonHandler;

    private ButtonStyle style;

    private List<Element> children;

    private ToolbarActionElementProps() {
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

    public String getToolbarActionLabel() {
        return this.toolbarActionLabel;
    }

    public String getImageURL() {
        return this.imageURL;
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

    public static Builder newToolbarActionElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, toolbarActionLabel: {3}, imageURL: {4},'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.toolbarActionLabel, this.imageURL);
    }

    /**
     * The builder of the toolbar action element props.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String iconURL;

        private Supplier<String> helpTextProvider;

        private String toolbarActionLabel;

        private String imageURL;

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

        public Builder toolbarActionLabel(String toolbarActionLabel) {
            this.toolbarActionLabel = Objects.requireNonNull(toolbarActionLabel);
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

        public ToolbarActionElementProps build() {
            ToolbarActionElementProps buttonElementProps = new ToolbarActionElementProps();
            buttonElementProps.id = Objects.requireNonNull(this.id);
            buttonElementProps.label = Objects.requireNonNull(this.label);
            buttonElementProps.toolbarActionLabel = this.toolbarActionLabel;
            buttonElementProps.iconURL = this.iconURL;
            buttonElementProps.imageURL = this.imageURL;
            buttonElementProps.pushButtonHandler = Objects.requireNonNull(this.pushButtonHandler);
            buttonElementProps.style = this.style; // Optional on purpose
            buttonElementProps.children = Objects.requireNonNull(this.children);
            buttonElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return buttonElementProps;
        }
    }
}
