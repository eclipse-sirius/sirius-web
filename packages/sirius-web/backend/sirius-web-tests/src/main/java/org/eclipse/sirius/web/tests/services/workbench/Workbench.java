/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.tests.services.workbench;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.EditingContextEventInput;
import org.eclipse.sirius.components.collaborative.validation.dto.ValidationEventInput;
import org.eclipse.sirius.components.graphql.tests.EditingContextEventSubscriptionRunner;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.application.views.relatedelements.dto.RelatedElementsEventInput;
import org.eclipse.sirius.web.application.views.relatedviews.dto.RelatedViewsEventInput;
import org.eclipse.sirius.web.tests.graphql.DetailsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.graphql.RelatedElementsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.graphql.RelatedViewsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.graphql.ValidationEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;

import reactor.core.publisher.Flux;

/**
 * Utility class to interact with the workbench.
 *
 * @author sbegaudeau
 */
public final class Workbench {

    private String editingContextId;

    private List<String> selectedObjectIds;

    private EditingContextEventSubscriptionRunner editingContextEventSubscriptionRunner;

    private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

    private ValidationEventSubscriptionRunner validationEventSubscriptionRunner;

    private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

    private RelatedElementsEventSubscriptionRunner relatedElementsEventSubscriptionRunner;

    private RelatedViewsEventSubscriptionRunner relatedViewsEventSubscriptionRunner;

    private Workbench() {
        // Prevent instantiation
    }

    public Flux<Object> asFlux() {
        var editingContextEventFlux = this.editingContextEventSubscriptionRunner.run(new EditingContextEventInput(UUID.randomUUID(), this.editingContextId)).flux();

        var representationIdBuilder = new RepresentationIdBuilder();
        var explorerId = representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(), List.of());
        var explorerEventFlux = this.explorerEventSubscriptionRunner.run(new ExplorerEventInput(UUID.randomUUID(), this.editingContextId, explorerId)).flux();

        var validationEventFlux = this.validationEventSubscriptionRunner.run(new ValidationEventInput(UUID.randomUUID(), this.editingContextId, representationIdBuilder.buildValidationRepresentationId())).flux();

        var detailsId = representationIdBuilder.buildDetailsRepresentationId(this.selectedObjectIds);
        var detailsEventFlux = this.detailsEventSubscriptionRunner.run(new DetailsEventInput(UUID.randomUUID(), this.editingContextId, detailsId)).flux();

        var relatedElementId = representationIdBuilder.buildRelatedElementsRepresentationId(this.selectedObjectIds);
        var relatedElementsEventFlux = this.relatedElementsEventSubscriptionRunner.run(new RelatedElementsEventInput(UUID.randomUUID(), this.editingContextId, relatedElementId)).flux();

        var representationsId = representationIdBuilder.buildRepresentationViewRepresentationId(this.selectedObjectIds);
        var representationsEventFlux = this.relatedViewsEventSubscriptionRunner.run(new RelatedViewsEventInput(UUID.randomUUID(), this.editingContextId, representationsId)).flux();

        return Flux.merge(
                editingContextEventFlux,
                explorerEventFlux,
                validationEventFlux,
                detailsEventFlux,
                relatedElementsEventFlux,
                representationsEventFlux
        );
    }

    /**
     * Builder for the workbench class.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String editingContextId;

        private List<String> selectedObjectIds;

        private EditingContextEventSubscriptionRunner editingContextEventSubscriptionRunner;

        private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

        private ValidationEventSubscriptionRunner validationEventSubscriptionRunner;

        private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

        private RelatedElementsEventSubscriptionRunner relatedElementsEventSubscriptionRunner;

        private RelatedViewsEventSubscriptionRunner relatedViewsEventSubscriptionRunner;

        public Builder(String editingContextId) {
            this.editingContextId = Objects.requireNonNull(editingContextId);
        }

        public Builder withSelection(List<String> selectedObjectIds) {
            this.selectedObjectIds = Objects.requireNonNull(selectedObjectIds);
            return this;
        }


        public Builder editingContextEventSubscriptionRunner(EditingContextEventSubscriptionRunner editingContextEventSubscriptionRunner) {
            this.editingContextEventSubscriptionRunner = Objects.requireNonNull(editingContextEventSubscriptionRunner);
            return this;
        }

        public Builder explorerEventSubscriptionRunner(ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner) {
            this.explorerEventSubscriptionRunner = Objects.requireNonNull(explorerEventSubscriptionRunner);
            return this;
        }

        public Builder validationEventSubscriptionRunner(ValidationEventSubscriptionRunner validationEventSubscriptionRunner) {
            this.validationEventSubscriptionRunner = Objects.requireNonNull(validationEventSubscriptionRunner);
            return this;
        }

        public Builder detailsEventSubscriptionRunner(DetailsEventSubscriptionRunner detailsEventSubscriptionRunner) {
            this.detailsEventSubscriptionRunner = Objects.requireNonNull(detailsEventSubscriptionRunner);
            return this;
        }

        public Builder relatedElementsEventSubscriptionRunner(RelatedElementsEventSubscriptionRunner relatedElementsEventSubscriptionRunner) {
            this.relatedElementsEventSubscriptionRunner = Objects.requireNonNull(relatedElementsEventSubscriptionRunner);
            return this;
        }

        public Builder representationsEventSubscriptionRunner(RelatedViewsEventSubscriptionRunner relatedViewsEventSubscriptionRunner) {
            this.relatedViewsEventSubscriptionRunner = Objects.requireNonNull(relatedViewsEventSubscriptionRunner);
            return this;
        }

        public Workbench build() {
            var workbench = new Workbench();

            workbench.editingContextId = Objects.requireNonNull(this.editingContextId);
            workbench.selectedObjectIds = Objects.requireNonNull(this.selectedObjectIds);
            workbench.editingContextEventSubscriptionRunner = Objects.requireNonNull(this.editingContextEventSubscriptionRunner);
            workbench.explorerEventSubscriptionRunner = Objects.requireNonNull(this.explorerEventSubscriptionRunner);
            workbench.validationEventSubscriptionRunner = Objects.requireNonNull(this.validationEventSubscriptionRunner);
            workbench.detailsEventSubscriptionRunner = Objects.requireNonNull(this.detailsEventSubscriptionRunner);
            workbench.relatedElementsEventSubscriptionRunner = Objects.requireNonNull(this.relatedElementsEventSubscriptionRunner);
            workbench.relatedViewsEventSubscriptionRunner = Objects.requireNonNull(this.relatedViewsEventSubscriptionRunner);

            return workbench;
        }
    }
}
