/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorCreationService;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorEventHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;

/**
 * Bundles all the parameters needed by the {@link FormDescriptionEditorEventProcessor}.
 *
 * @author pcdavid
 */
public record FormDescriptionEditorEventProcessorParameters(
        IEditingContext editingContext,
        IFormDescriptionEditorContext formDescriptionEditorContext,
        List<IFormDescriptionEditorEventHandler> formDescriptionEditorEventHandlers,
        ISubscriptionManager subscriptionManager,
        IFormDescriptionEditorCreationService formDescriptionEditorCreationService,
        IRepresentationDescriptionSearchService representationDescriptionSearchService,
        IRepresentationSearchService representationSearchService,
        IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {

    public FormDescriptionEditorEventProcessorParameters {
        Objects.requireNonNull(editingContext);
        Objects.requireNonNull(formDescriptionEditorContext);
        Objects.requireNonNull(formDescriptionEditorEventHandlers);
        Objects.requireNonNull(subscriptionManager);
        Objects.requireNonNull(formDescriptionEditorCreationService);
        Objects.requireNonNull(representationDescriptionSearchService);
        Objects.requireNonNull(representationSearchService);
        Objects.requireNonNull(representationRefreshPolicyRegistry);
    }

    public static Builder newFormDescriptionEditorEventProcessorParameters() {
        return new Builder();
    }

    /**
     * The builder used to create the parameters.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {

        private IEditingContext editingContext;

        private IFormDescriptionEditorContext formDescriptionEditorContext;

        private List<IFormDescriptionEditorEventHandler> formDescriptionEditorEventHandlers;

        private ISubscriptionManager subscriptionManager;

        private IFormDescriptionEditorCreationService formDescriptionEditorCreationService;

        private IRepresentationDescriptionSearchService representationDescriptionSearchService;

        private IRepresentationSearchService representationSearchService;

        private IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

        public Builder editingContext(IEditingContext editingContext) {
            this.editingContext = Objects.requireNonNull(editingContext);
            return this;
        }

        public Builder formDescriptionEditorContext(IFormDescriptionEditorContext formDescriptionEditorContext) {
            this.formDescriptionEditorContext = Objects.requireNonNull(formDescriptionEditorContext);
            return this;
        }

        public Builder formDescriptionEditorEventHandlers(List<IFormDescriptionEditorEventHandler> formDescriptionEditorEventHandlers) {
            this.formDescriptionEditorEventHandlers = Objects.requireNonNull(formDescriptionEditorEventHandlers);
            return this;
        }

        public Builder subscriptionManager(ISubscriptionManager subscriptionManager) {
            this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
            return this;
        }

        public Builder formDescriptionEditorCreationService(IFormDescriptionEditorCreationService formDescriptionEditorCreationService) {
            this.formDescriptionEditorCreationService = Objects.requireNonNull(formDescriptionEditorCreationService);
            return this;
        }

        public Builder representationDescriptionSearchService(IRepresentationDescriptionSearchService representationDescriptionSearchService) {
            this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
            return this;
        }

        public Builder representationSearchService(IRepresentationSearchService representationSearchService) {
            this.representationSearchService = Objects.requireNonNull(representationSearchService);
            return this;
        }

        public Builder representationRefreshPolicyRegistry(IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
            this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
            return this;
        }


        public FormDescriptionEditorEventProcessorParameters build() {
            return new FormDescriptionEditorEventProcessorParameters(
                    this.editingContext,
                    this.formDescriptionEditorContext,
                    this.formDescriptionEditorEventHandlers,
                    this.subscriptionManager,
                    this.formDescriptionEditorCreationService,
                    this.representationDescriptionSearchService,
                    this.representationSearchService,
                    this.representationRefreshPolicyRegistry);
        }
    }
}
