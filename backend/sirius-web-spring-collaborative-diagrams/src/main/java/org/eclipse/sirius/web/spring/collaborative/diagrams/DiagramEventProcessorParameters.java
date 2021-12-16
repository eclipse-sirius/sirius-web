/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.web.spring.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramEventHandler;

/**
 * Encapsulates the parameters needed to create a {@link DiagramEventProcessor}, which are too numerous to be passed
 * directly in a readable manner.
 *
 * @author pcdavid
 */
@Immutable
public final class DiagramEventProcessorParameters {
    private IEditingContext editingContext;

    private IDiagramContext diagramContext;

    private ISemanticRepresentationMetadata diagramMetadata;

    private List<IDiagramEventHandler> diagramEventHandlers;

    private ISubscriptionManager subscriptionManager;

    private IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private IDiagramCreationService diagramCreationService;

    private DiagramEventProcessorParameters() {
        // Prevent uncontrolled instanciation.
    }

    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    public IDiagramContext getDiagramContext() {
        return this.diagramContext;
    }

    public ISemanticRepresentationMetadata getDiagramMetadata() {
        return this.diagramMetadata;
    }

    public List<IDiagramEventHandler> getDiagramEventHandlers() {
        return this.diagramEventHandlers;
    }

    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    public IRepresentationDescriptionSearchService getRepresentationDescriptionSearchService() {
        return this.representationDescriptionSearchService;
    }

    public IRepresentationRefreshPolicyRegistry getRepresentationRefreshPolicyRegistry() {
        return this.representationRefreshPolicyRegistry;
    }

    public IDiagramCreationService getDiagramCreationService() {
        return this.diagramCreationService;
    }

    public static Builder newDiagramEventProcessorParameters() {
        return new Builder();
    }

    /**
     * The builder of the diagram event processor creation parameters.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private IEditingContext editingContext;

        private IDiagramContext diagramContext;

        private ISemanticRepresentationMetadata diagramMetadata;

        private List<IDiagramEventHandler> diagramEventHandlers;

        private ISubscriptionManager subscriptionManager;

        private IRepresentationDescriptionSearchService representationDescriptionSearchService;

        private IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

        private IDiagramCreationService diagramCreationService;

        public Builder editingContext(IEditingContext editingContext) {
            this.editingContext = Objects.requireNonNull(editingContext);
            return this;
        }

        public Builder diagramContext(IDiagramContext diagramContext) {
            this.diagramContext = Objects.requireNonNull(diagramContext);
            return this;
        }

        public Builder diagramMetadata(ISemanticRepresentationMetadata diagramMetadata) {
            this.diagramMetadata = Objects.requireNonNull(diagramMetadata);
            return this;
        }

        public Builder diagramEventHandlers(List<IDiagramEventHandler> diagramEventHandlers) {
            this.diagramEventHandlers = List.copyOf(Objects.requireNonNull(diagramEventHandlers));
            return this;
        }

        public Builder subscriptionManager(ISubscriptionManager subscriptionManager) {
            this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
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

        public Builder diagramCreationService(IDiagramCreationService diagramCreationService) {
            this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
            return this;
        }

        public DiagramEventProcessorParameters build() {
            DiagramEventProcessorParameters parameters = new DiagramEventProcessorParameters();
            parameters.editingContext = Objects.requireNonNull(this.editingContext);
            parameters.diagramContext = Objects.requireNonNull(this.diagramContext);
            parameters.diagramMetadata = Objects.requireNonNull(this.diagramMetadata);
            parameters.diagramEventHandlers = Objects.requireNonNull(this.diagramEventHandlers);
            parameters.subscriptionManager = Objects.requireNonNull(this.subscriptionManager);
            parameters.representationDescriptionSearchService = Objects.requireNonNull(this.representationDescriptionSearchService);
            parameters.representationRefreshPolicyRegistry = Objects.requireNonNull(this.representationRefreshPolicyRegistry);
            parameters.diagramCreationService = Objects.requireNonNull(this.diagramCreationService);
            return parameters;
        }

    }

}
