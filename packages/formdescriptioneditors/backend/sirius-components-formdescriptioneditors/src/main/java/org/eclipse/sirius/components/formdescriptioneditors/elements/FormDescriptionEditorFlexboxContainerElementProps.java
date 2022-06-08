/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.formdescriptioneditors.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.view.FlexDirection;

/**
 * The properties of the form description editor flexbox container element.
 *
 * @author arichard
 */
@Immutable
public final class FormDescriptionEditorFlexboxContainerElementProps implements IProps {
    public static final String TYPE = "FormDescriptionEditorFlexboxContainer"; //$NON-NLS-1$

    private String id;

    private String label;

    private String kind;

    private FlexDirection flexDirection;

    private List<Element> children;

    private FormDescriptionEditorFlexboxContainerElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getKind() {
        return this.kind;
    }

    public FlexDirection getFlexDirection() {
        return this.flexDirection;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newFormDescriptionEditorFlexboxContainerElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, kind: {3}, flexDirection: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.kind, this.flexDirection);
    }

    /**
     * The builder of the form description editor flexbox container element props.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String kind;

        private FlexDirection flexDirection;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
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

        public FormDescriptionEditorFlexboxContainerElementProps build() {
            FormDescriptionEditorFlexboxContainerElementProps fdeFlexboxContainerElementProps = new FormDescriptionEditorFlexboxContainerElementProps();
            fdeFlexboxContainerElementProps.id = Objects.requireNonNull(this.id);
            fdeFlexboxContainerElementProps.label = Objects.requireNonNull(this.label);
            fdeFlexboxContainerElementProps.kind = Objects.requireNonNull(this.kind);
            fdeFlexboxContainerElementProps.flexDirection = Objects.requireNonNull(this.flexDirection);
            fdeFlexboxContainerElementProps.children = Objects.requireNonNull(this.children);
            return fdeFlexboxContainerElementProps;
        }
    }
}
