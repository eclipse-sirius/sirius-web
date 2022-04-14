/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and others.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * The input for the "Invoke single click on diagram element tool" mutation.
 *
 * @author pcdavid
 */
@Immutable
public final class InvokeSingleClickOnDiagramElementToolInput implements IDiagramInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private String diagramElementId;

    private String toolId;

    private double startingPositionX;

    private double startingPositionY;

    private String selectedObjectId;

    public InvokeSingleClickOnDiagramElementToolInput() {
        // Used by Jackson
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    @Override
    public String getRepresentationId() {
        return this.representationId;
    }

    public String getDiagramElementId() {
        return this.diagramElementId;
    }

    public String getToolId() {
        return this.toolId;
    }

    public double getStartingPositionX() {
        return this.startingPositionX;
    }

    public double getStartingPositionY() {
        return this.startingPositionY;
    }

    public String getSelectedObjectId() {
        return this.selectedObjectId;
    }

    public static Builder newInvokeSingleClickOnDiagramElementToolInput(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, diagramElementId: {4}, toolId: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.diagramElementId, this.toolId);
    }

    /**
     * The builder used to create a new input.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private String editingContextId;

        private String representationId;

        private String diagramElementId;

        private String toolId;

        private double startingPositionX;

        private double startingPositionY;

        private String selectedObjectId;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder editingContextId(String editingContextId) {
            this.editingContextId = Objects.requireNonNull(editingContextId);
            return this;
        }

        public Builder representationId(String representationId) {
            this.representationId = Objects.requireNonNull(representationId);
            return this;
        }

        public Builder diagramElementId(String diagramElementId) {
            this.diagramElementId = Objects.requireNonNull(diagramElementId);
            return this;
        }

        public Builder toolId(String toolId) {
            this.toolId = Objects.requireNonNull(toolId);
            return this;
        }

        public Builder startingPositionX(double startingPositionX) {
            this.startingPositionX = startingPositionX;
            return this;
        }

        public Builder startingPositionY(double startingPositionY) {
            this.startingPositionY = startingPositionY;
            return this;
        }

        public Builder selectedObjectId(String selectedObjectId) {
            this.selectedObjectId = Objects.requireNonNull(selectedObjectId);
            return this;
        }

        public InvokeSingleClickOnDiagramElementToolInput build() {
            var input = new InvokeSingleClickOnDiagramElementToolInput();
            input.id = Objects.requireNonNull(this.id);
            input.editingContextId = Objects.requireNonNull(this.editingContextId);
            input.representationId = Objects.requireNonNull(this.representationId);
            input.diagramElementId = Objects.requireNonNull(this.diagramElementId);
            input.toolId = Objects.requireNonNull(this.toolId);
            input.startingPositionX = this.startingPositionX;
            input.startingPositionY = this.startingPositionY;
            input.selectedObjectId = Objects.requireNonNull(this.selectedObjectId);
            return input;
        }
    }

}
