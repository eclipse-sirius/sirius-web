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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorCreationService;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorEventHandler;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorEventProcessor;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.RenameFormDescriptionEditorInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Reacts to the input that target the property sheet of a specific object and publishes updated versions of the
 * {@link FormDescriptionEditor} to interested subscribers.
 *
 * @author arichard
 */
public class FormDescriptionEditorEventProcessor implements IFormDescriptionEditorEventProcessor {
    private final Logger logger = LoggerFactory.getLogger(FormDescriptionEditorEventProcessor.class);

    private final IEditingContext editingContext;

    private final IFormDescriptionEditorContext formDescriptionEditorContext;

    private final List<IFormDescriptionEditorEventHandler> formDescriptionEditorEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IFormDescriptionEditorCreationService formDescriptionEditorCreationService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final FormDescriptionEditorEventFlux formDescriptionEditorEventFlux;

    public FormDescriptionEditorEventProcessor(IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext,
            List<IFormDescriptionEditorEventHandler> formDescriptionEditorEventHandlers, ISubscriptionManager subscriptionManager,
            IFormDescriptionEditorCreationService formDescriptionEditorCreationService, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.logger.trace("Creating the form description editor event processor {}", formDescriptionEditorContext.getFormDescriptionEditor().getId()); //$NON-NLS-1$
        this.editingContext = Objects.requireNonNull(editingContext);
        this.formDescriptionEditorContext = Objects.requireNonNull(formDescriptionEditorContext);
        this.formDescriptionEditorEventHandlers = Objects.requireNonNull(formDescriptionEditorEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.formDescriptionEditorCreationService = Objects.requireNonNull(formDescriptionEditorCreationService);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);

        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database. This is quite similar to the auto-refresh on loading in Sirius.
        FormDescriptionEditor formDescriptionEditor = this.formDescriptionEditorCreationService.refresh(editingContext, formDescriptionEditorContext);
        formDescriptionEditorContext.update(formDescriptionEditor);
        this.formDescriptionEditorEventFlux = new FormDescriptionEditorEventFlux(formDescriptionEditor);

        if (formDescriptionEditor != null) {
            this.logger.trace("FormDescriptionEditor refreshed: {})", formDescriptionEditor.getId()); //$NON-NLS-1$
        }
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.formDescriptionEditorContext.getFormDescriptionEditor();
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        IRepresentationInput effectiveInput = representationInput;
        if (representationInput instanceof RenameRepresentationInput) {
            RenameRepresentationInput renameRepresentationInput = (RenameRepresentationInput) representationInput;
            effectiveInput = new RenameFormDescriptionEditorInput(renameRepresentationInput.getId(), renameRepresentationInput.getEditingContextId(), renameRepresentationInput.getRepresentationId(),
                    renameRepresentationInput.getNewLabel());
        }
        if (effectiveInput instanceof IFormDescriptionEditorInput) {
            IFormDescriptionEditorInput formDescriptionEditorInput = (IFormDescriptionEditorInput) effectiveInput;

            Optional<IFormDescriptionEditorEventHandler> optionalFormEventHandler = this.formDescriptionEditorEventHandlers.stream().filter(handler -> handler.canHandle(formDescriptionEditorInput))
                    .findFirst();

            if (optionalFormEventHandler.isPresent()) {
                IFormDescriptionEditorEventHandler formDescriptionEditorEventHandler = optionalFormEventHandler.get();
                formDescriptionEditorEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.formDescriptionEditorContext, formDescriptionEditorInput);
            } else {
                this.logger.warn("No handler found for event: {}", formDescriptionEditorInput); //$NON-NLS-1$
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            FormDescriptionEditor refreshedFormDescriptionEditor = this.formDescriptionEditorCreationService.refresh(this.editingContext, this.formDescriptionEditorContext);
            if (refreshedFormDescriptionEditor != null) {
                this.logger.trace("FormDescriptionEditor refreshed: {}", refreshedFormDescriptionEditor.getId()); //$NON-NLS-1$
                this.formDescriptionEditorContext.update(refreshedFormDescriptionEditor);
                this.formDescriptionEditorEventFlux.formDescriptionEditorRefreshed(changeDescription.getInput(), refreshedFormDescriptionEditor);
            }
        }
    }

    private boolean shouldRefresh(ChangeDescription changeDescription) {
        FormDescriptionEditor formDescriptionEditor = this.formDescriptionEditorContext.getFormDescriptionEditor();
        // @formatter:off
        var optionalFormDescriptionEditorDescription = this.representationDescriptionSearchService.findById(this.editingContext, formDescriptionEditor.getDescriptionId())
                .filter(FormDescriptionEditorDescription.class::isInstance)
                .map(FormDescriptionEditorDescription.class::cast);
        // @formatter:on

        // @formatter:off
        return optionalFormDescriptionEditorDescription.flatMap(this.representationRefreshPolicyRegistry::getRepresentationRefreshPolicy)
                .orElseGet(this::getDefaultRefreshPolicy)
                .shouldRefresh(changeDescription);
        // @formatter:on

    }

    private IRepresentationRefreshPolicy getDefaultRefreshPolicy() {
        return (changeDescription) -> ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        // @formatter:off
        return Flux.merge(
            this.formDescriptionEditorEventFlux.getFlux(input),
            this.subscriptionManager.getFlux(input)
        );
        // @formatter:on
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the form description editor event processor {}", this.formDescriptionEditorContext.getFormDescriptionEditor().getId()); //$NON-NLS-1$

        this.subscriptionManager.dispose();

        this.formDescriptionEditorEventFlux.dispose();
    }
}
