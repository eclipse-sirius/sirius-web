/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.collaborative.diagrams.api;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;

/**
 * This class is used because creating a diagram requires sending at once multiple parameters. Since this list of
 * parameter will only grow over time, it will because too cumbersome to maintain and send such a list of parameters
 * without a common "container". This class uses the builder pattern because we know that it will grow bigger and it
 * does not need to be modified.
 *
 * @author sbegaudeau
 */
@Immutable
public final class DiagramCreationParameters {

    private UUID id;

    private String label;

    private Object object;

    private DiagramDescription diagramDescription;

    private IEditingContext editingContext;

    private DiagramCreationParameters() {
        // Prevent instantiation
    }

    public UUID getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public Object getObject() {
        return this.object;
    }

    public DiagramDescription getDiagramDescription() {
        return this.diagramDescription;
    }

    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    public static Builder newDiagramCreationParameters(UUID id) {
        return new Builder(id);
    }

    public static Builder newDiagramCreationParameters(DiagramCreationParameters diagramCreationParameters) {
        // @formatter:off
        return new Builder(diagramCreationParameters.getId())
                .diagramDescription(diagramCreationParameters.getDiagramDescription())
                .editingContext(diagramCreationParameters.getEditingContext())
                .label(diagramCreationParameters.getLabel())
                .object(diagramCreationParameters.getObject());
        // @formatter:on
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, diagramDescriptionId: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.diagramDescription.getId());
    }

    /**
     * The builder of the diagram creation parameters.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private String label;

        private Object object;

        private DiagramDescription diagramDescription;

        private IEditingContext editingContext;

        private Builder(UUID id) {
            this.id = id;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder object(Object object) {
            this.object = Objects.requireNonNull(object);
            return this;
        }

        public Builder diagramDescription(DiagramDescription diagramDescription) {
            this.diagramDescription = Objects.requireNonNull(diagramDescription);
            return this;
        }

        public Builder editingContext(IEditingContext editingContext) {
            this.editingContext = Objects.requireNonNull(editingContext);
            return this;
        }

        public DiagramCreationParameters build() {
            DiagramCreationParameters diagramCreationParameters = new DiagramCreationParameters();
            diagramCreationParameters.id = this.id;
            diagramCreationParameters.label = Objects.requireNonNull(this.label);
            diagramCreationParameters.object = Objects.requireNonNull(this.object);
            diagramCreationParameters.diagramDescription = Objects.requireNonNull(this.diagramDescription);
            diagramCreationParameters.editingContext = Objects.requireNonNull(this.editingContext);
            return diagramCreationParameters;
        }
    }
}
