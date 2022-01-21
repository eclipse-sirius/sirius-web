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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;

/**
 * Root concept of the form representation.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Immutable
public final class Form implements IRepresentation, ISemanticRepresentation {

    public static final String KIND = IRepresentation.KIND_PREFIX + "?type=Form"; //$NON-NLS-1$

    private String id;

    private String kind;

    private String label;

    private String targetObjectId;

    private UUID descriptionId;

    private List<Page> pages;

    private Form() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Override
    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    public List<Page> getPages() {
        return this.pages;
    }

    public static Builder newForm(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, targetObjectId: {3}, descriptionId: {4} pageCount: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.targetObjectId, this.descriptionId, this.pages.size());
    }

    /**
     * The builder used to create the form.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String kind = KIND;

        private String label;

        private String targetObjectId;

        private UUID descriptionId;

        private List<Page> pages;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder pages(List<Page> pages) {
            this.pages = pages;
            return this;
        }

        public Form build() {
            Form form = new Form();
            form.id = Objects.requireNonNull(this.id);
            form.kind = Objects.requireNonNull(this.kind);
            form.label = Objects.requireNonNull(this.label);
            form.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            form.descriptionId = Objects.requireNonNull(this.descriptionId);
            form.pages = Objects.requireNonNull(this.pages);
            return form;
        }
    }
}
