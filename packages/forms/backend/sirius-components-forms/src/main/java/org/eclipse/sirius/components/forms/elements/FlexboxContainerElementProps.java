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
import org.eclipse.sirius.components.forms.FlexDirection;
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

    private FlexboxContainerElementProps() {
        // Prevent instantiation
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

    public static Builder newFlexboxContainerElementProps(String id) {
        return new Builder(id);
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

        private String id;

        private String label;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private FlexDirection flexDirection;

        private List<Element> children;

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

        public FlexboxContainerElementProps build() {
            FlexboxContainerElementProps flexboxContainerElementProps = new FlexboxContainerElementProps();
            flexboxContainerElementProps.id = Objects.requireNonNull(this.id);
            flexboxContainerElementProps.label = Objects.requireNonNull(this.label);
            flexboxContainerElementProps.readOnly = this.readOnly;
            flexboxContainerElementProps.flexDirection = Objects.requireNonNull(this.flexDirection);
            flexboxContainerElementProps.children = Objects.requireNonNull(this.children);
            flexboxContainerElementProps.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return flexboxContainerElementProps;
        }
    }

}
