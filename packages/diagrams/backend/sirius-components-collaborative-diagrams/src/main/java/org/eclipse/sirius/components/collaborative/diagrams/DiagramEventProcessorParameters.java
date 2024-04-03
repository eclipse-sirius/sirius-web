/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInputReferencePositionProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;

/**
 * Parameters of the diagram event processor.
 *
 * @author sbegaudeau
 */
public record DiagramEventProcessorParameters(
        IEditingContext editingContext,
        IDiagramContext diagramContext,
        List<IDiagramEventHandler> diagramEventHandlers,
        ISubscriptionManager subscriptionManager,
        IDiagramCreationService diagramCreationService,
        IRepresentationDescriptionSearchService representationDescriptionSearchService,
        IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry,
        IRepresentationPersistenceService representationPersistenceService,
        IRepresentationSearchService representationSearchService,
        List<IDiagramInputReferencePositionProvider> diagramInputReferencePositionProviders
) {

    public DiagramEventProcessorParameters {
        Objects.requireNonNull(editingContext);
        Objects.requireNonNull(diagramContext);
        Objects.requireNonNull(diagramEventHandlers);
        Objects.requireNonNull(subscriptionManager);
        Objects.requireNonNull(diagramCreationService);
        Objects.requireNonNull(representationDescriptionSearchService);
        Objects.requireNonNull(representationRefreshPolicyRegistry);
        Objects.requireNonNull(representationPersistenceService);
        Objects.requireNonNull(representationSearchService);
        Objects.requireNonNull(diagramInputReferencePositionProviders);
    }

    public static Builder newDiagramEventProcessorParameters() {
        return new Builder();
    }

    /**
     * The builder used to create the parameters.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {

        private IEditingContext editingContext;

        private IDiagramContext diagramContext;

        private List<IDiagramEventHandler> diagramEventHandlers;

        private ISubscriptionManager subscriptionManager;

        private IDiagramCreationService diagramCreationService;

        private IRepresentationDescriptionSearchService representationDescriptionSearchService;

        private IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

        private IRepresentationPersistenceService representationPersistenceService;

        private IRepresentationSearchService representationSearchService;

        private List<IDiagramInputReferencePositionProvider> diagramInputReferencePositionProviders;

        private Builder() {
            // Prevent instantiation
        }

        public Builder editingContext(IEditingContext editingContext) {
            this.editingContext = Objects.requireNonNull(editingContext);
            return this;
        }

        public Builder diagramContext(IDiagramContext diagramContext) {
            this.diagramContext = Objects.requireNonNull(diagramContext);
            return this;
        }

        public Builder diagramEventHandlers(List<IDiagramEventHandler> diagramEventHandlers) {
            this.diagramEventHandlers = Objects.requireNonNull(diagramEventHandlers);
            return this;
        }

        public Builder subscriptionManager(ISubscriptionManager subscriptionManager) {
            this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
            return this;
        }

        public Builder diagramCreationService(IDiagramCreationService diagramCreationService) {
            this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
            return this;
        }

        public Builder representationDescriptionSearchService(IRepresentationDescriptionSearchService representationDescriptionSearchService) {
            this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
            return this;
        }

        public Builder representationRefreshPolicyRegistry(IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
            this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
            return this;
        }

        public Builder representationPersistenceService(IRepresentationPersistenceService representationPersistenceService) {
            this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
            return this;
        }

        public Builder representationSearchService(IRepresentationSearchService representationSearchService) {
            this.representationSearchService = Objects.requireNonNull(representationSearchService);
            return this;
        }

        public Builder diagramInputReferencePositionProviders(List<IDiagramInputReferencePositionProvider> diagramInputReferencePositionProviders) {
            this.diagramInputReferencePositionProviders = Objects.requireNonNull(diagramInputReferencePositionProviders);
            return this;
        }

        public DiagramEventProcessorParameters build() {
            return new DiagramEventProcessorParameters(
                    this.editingContext,
                    this.diagramContext,
                    this.diagramEventHandlers,
                    this.subscriptionManager,
                    this.diagramCreationService,
                    this.representationDescriptionSearchService,
                    this.representationRefreshPolicyRegistry,
                    this.representationPersistenceService,
                    this.representationSearchService,
                    this.diagramInputReferencePositionProviders
            );
        }
    }
}
