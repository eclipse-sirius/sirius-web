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
package org.eclipse.sirius.components.formdescriptioneditors;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.formdescriptioneditors.description.StyleProperty;

/**
 * Any widget contained in the form description editor flexbox container.
 *
 * @author arichard
 */
@Immutable
public final class FormDescriptionEditorFlexboxContainer extends AbstractFormDescriptionEditorWidget {

    private String flexDirection;

    private String flexWrap;

    private int flexGrow;

    private List<AbstractFormDescriptionEditorWidget> children;

    private FormDescriptionEditorFlexboxContainer() {
        // Prevent instantiation
    }

    public String getFlexDirection() {
        return this.flexDirection;
    }

    public String getFlexWrap() {
        return this.flexWrap;
    }

    public int getFlexGrow() {
        return this.flexGrow;
    }

    @Override
    public List<StyleProperty> getStyleProperties() {
        return List.of();
    }

    public List<AbstractFormDescriptionEditorWidget> getChildren() {
        return this.children;
    }

    public static Builder newFormDescriptionEditorFlexboxContainer(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, kind: {2}, label: {3}, flexDirection: {4}, flexWrap: {5}, flexGrow: {6}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.kind, this.label, this.flexDirection, this.flexWrap, this.flexGrow);
    }

    /**
     * The builder used to create the form description editor flexbox container.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String kind;

        private String label;

        private String flexDirection;

        private String flexWrap;

        private int flexGrow;

        private List<AbstractFormDescriptionEditorWidget> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder flexDirection(String flexDirection) {
            this.flexDirection = Objects.requireNonNull(flexDirection);
            return this;
        }

        public Builder flexWrap(String flexWrap) {
            this.flexWrap = Objects.requireNonNull(flexWrap);
            return this;
        }

        public Builder flexGrow(int flexGrow) {
            this.flexGrow = Objects.requireNonNull(flexGrow);
            return this;
        }

        public Builder children(List<AbstractFormDescriptionEditorWidget> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public FormDescriptionEditorFlexboxContainer build() {
            FormDescriptionEditorFlexboxContainer fdeFlexboxContainer = new FormDescriptionEditorFlexboxContainer();
            fdeFlexboxContainer.id = Objects.requireNonNull(this.id);
            fdeFlexboxContainer.kind = Objects.requireNonNull(this.kind);
            fdeFlexboxContainer.label = Objects.requireNonNull(this.label);
            fdeFlexboxContainer.flexDirection = Objects.requireNonNull(this.flexDirection);
            fdeFlexboxContainer.flexWrap = Objects.requireNonNull(this.flexWrap);
            fdeFlexboxContainer.flexGrow = Objects.requireNonNull(this.flexGrow);
            fdeFlexboxContainer.children = Objects.requireNonNull(this.children);
            return fdeFlexboxContainer;
        }
    }
}
