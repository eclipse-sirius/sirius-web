/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.selection;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;

/**
 * The root concept of the selection representation.
 *
 * @author arichard
 */
@Immutable
public final class Selection implements IRepresentation, ISemanticRepresentation {

    public static final String KIND = IRepresentation.KIND_PREFIX + "?type=Selection";

    public static final String PREFIX = "selection://";

    public static final String TITLE = "Selection";

    private String id;

    private String kind;

    private String descriptionId;

    private String label;

    private String targetObjectId;

    private String message;

    private List<SelectionObject> objects;

    private Selection() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
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

    public String getMessage() {
        return this.message;
    }

    public List<SelectionObject> getObjects() {
        return this.objects;
    }

    public static Builder newSelection(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, targetObjectId: {3}, label: {4}, message: {5}, objectsCount: {6}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.targetObjectId, this.label, this.message, this.objects.size());
    }

    /**
     * The builder used to create the selection.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String kind = KIND;

        private String descriptionId;

        private String label;

        private String targetObjectId;

        private String message;

        private List<SelectionObject> objects;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder objects(List<SelectionObject> objects) {
            this.objects = Objects.requireNonNull(objects);
            return this;
        }

        public Selection build() {
            Selection selection = new Selection();
            selection.id = Objects.requireNonNull(this.id);
            selection.kind = Objects.requireNonNull(this.kind);
            selection.descriptionId = Objects.requireNonNull(this.descriptionId);
            selection.label = Objects.requireNonNull(this.label);
            selection.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            selection.message = this.message;
            selection.objects = Objects.requireNonNull(this.objects);
            return selection;
        }
    }
}
