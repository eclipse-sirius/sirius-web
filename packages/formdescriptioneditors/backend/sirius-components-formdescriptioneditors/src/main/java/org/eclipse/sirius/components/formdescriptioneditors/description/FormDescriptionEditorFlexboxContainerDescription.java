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
package org.eclipse.sirius.components.formdescriptioneditors.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.view.FlexDirection;

/**
 * The root concept of the description of a form description editor flexbox container widget.
 *
 * @author arichard
 */
@Immutable
public final class FormDescriptionEditorFlexboxContainerDescription extends AbstractFormDescriptionEditorWidgetDescription {

    private FlexDirection flexDirection;

    private List<AbstractFormDescriptionEditorWidgetDescription> children;

    private FormDescriptionEditorFlexboxContainerDescription() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    public FlexDirection getFlexDirection() {
        return this.flexDirection;
    }

    public List<AbstractFormDescriptionEditorWidgetDescription> getChildren() {
        return this.children;
    }

    public static Builder newFormDescriptionEditorFlexboxContainerDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, kind: {3}, flexDirection: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.kind, this.flexDirection);
    }

    /**
     * Builder used to create the form description editor flexbox widget description.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String kind;

        private FlexDirection flexDirection;

        private List<AbstractFormDescriptionEditorWidgetDescription> children;

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

        public Builder children(List<AbstractFormDescriptionEditorWidgetDescription> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public FormDescriptionEditorFlexboxContainerDescription build() {
            FormDescriptionEditorFlexboxContainerDescription fdeFlexboxContainerDescription = new FormDescriptionEditorFlexboxContainerDescription();
            fdeFlexboxContainerDescription.id = Objects.requireNonNull(this.id);
            fdeFlexboxContainerDescription.label = Objects.requireNonNull(this.label);
            fdeFlexboxContainerDescription.kind = Objects.requireNonNull(this.kind);
            fdeFlexboxContainerDescription.flexDirection = Objects.requireNonNull(this.flexDirection);
            fdeFlexboxContainerDescription.children = Objects.requireNonNull(this.children);
            return fdeFlexboxContainerDescription;
        }
    }
}
