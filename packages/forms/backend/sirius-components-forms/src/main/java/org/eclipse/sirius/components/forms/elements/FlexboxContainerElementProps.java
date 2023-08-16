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
import org.eclipse.sirius.components.forms.ContainerBorderStyle;
import org.eclipse.sirius.components.forms.FlexDirection;
import org.eclipse.sirius.components.forms.FlexboxAlignItems;
import org.eclipse.sirius.components.forms.FlexboxJustifyContent;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the flexbox container element.
 *
 * @author arichard
 */
@Immutable
public final class FlexboxContainerElementProps implements IProps {

    public static final String TYPE = "FlexboxContainer";

    private String id;

    private String label;

    private Supplier<String> helpTextProvider;

    private boolean readOnly;

    private FlexDirection flexDirection;

    private List<Element> children;

    private ContainerBorderStyle borderStyle;

    private String margin;

    private String padding;

    private String gap;

    private FlexboxJustifyContent justifyContent;

    private FlexboxAlignItems alignItems;

    private FlexboxContainerElementProps() {
        // Prevent instantiation
    }

    public static Builder newFlexboxContainerElementProps(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public Supplier<String> getHelpTextProvider() {
        return this.helpTextProvider;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public FlexDirection getFlexDirection() {
        return this.flexDirection;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public ContainerBorderStyle getBorderStyle() {
        return this.borderStyle;
    }

    public String getMargin() {
        return this.margin;
    }

    public String getPadding() {
        return this.padding;
    }

    public String getGap() {
        return this.gap;
    }

    public FlexboxJustifyContent getJustifyContent() {
        return this.justifyContent;
    }

    public FlexboxAlignItems getAlignItems() {
        return this.alignItems;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, flexDirection: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.flexDirection);
    }

    /**
     * The builder of the flexbox container element props.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private FlexDirection flexDirection;

        private List<Element> children;

        private ContainerBorderStyle borderStyle;

        private String margin;

        private String padding;

        private String gap;

        private FlexboxJustifyContent justifyContent;

        private FlexboxAlignItems alignItems;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder flexDirection(FlexDirection flexDirection) {
            this.flexDirection = Objects.requireNonNull(flexDirection);
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

        public Builder borderStyle(ContainerBorderStyle borderStyle) {
            this.borderStyle = Objects.requireNonNull(borderStyle);
            return this;
        }

        public Builder margin(String margin) {
            this.margin = Objects.requireNonNull(margin);
            return this;
        }

        public Builder padding(String padding) {
            this.padding = Objects.requireNonNull(padding);
            return this;
        }

        public Builder gap(String gap) {
            this.gap = Objects.requireNonNull(gap);
            return this;
        }

        public Builder justifyContent(FlexboxJustifyContent flexboxJustifyContent) {
            this.justifyContent = Objects.requireNonNull(flexboxJustifyContent);
            return this;
        }

        public Builder alignItems(FlexboxAlignItems flexboxAlignItems) {
            this.alignItems = Objects.requireNonNull(flexboxAlignItems);
            return this;
        }

        public FlexboxContainerElementProps build() {
            FlexboxContainerElementProps flexboxContainerElementProps = new FlexboxContainerElementProps();
            flexboxContainerElementProps.id = Objects.requireNonNull(this.id);
            flexboxContainerElementProps.label = Objects.requireNonNull(this.label);
            flexboxContainerElementProps.readOnly = this.readOnly;
            flexboxContainerElementProps.flexDirection = Objects.requireNonNull(this.flexDirection);
            flexboxContainerElementProps.children = Objects.requireNonNull(this.children);
            flexboxContainerElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            flexboxContainerElementProps.borderStyle = this.borderStyle; // Optional on purpose
            flexboxContainerElementProps.margin = this.margin; // Optional on purpose
            flexboxContainerElementProps.padding = this.padding; // Optional on purpose
            flexboxContainerElementProps.gap = this.gap; // Optional on purpose
            flexboxContainerElementProps.justifyContent = Objects.requireNonNull(this.justifyContent);
            flexboxContainerElementProps.alignItems = Objects.requireNonNull(this.alignItems);
            return flexboxContainerElementProps;
        }
    }

}
