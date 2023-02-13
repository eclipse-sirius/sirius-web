/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.web.services.documents;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.RenameDocumentInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.messages.IServicesMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Reducer used to rename a document.
 *
 * @author fbarbin
 */
@Service
public class RenameDocumentEventHandler implements IEditingContextEventHandler {

    private final IDocumentService documentService;

    private final IServicesMessageService messageService;

    private final Counter counter;

    public RenameDocumentEventHandler(IDocumentService documentService, IServicesMessageService messageService, MeterRegistry meterRegistry) {
        this.documentService = Objects.requireNonNull(documentService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof RenameDocumentInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), RenameDocumentInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        // @formatter:off
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain);
        // @formatter:on

        if (input instanceof RenameDocumentInput) {
            RenameDocumentInput renameDocumentInput = (RenameDocumentInput) input;
            UUID documentId = renameDocumentInput.documentId();
            String newName = renameDocumentInput.newName();

            Optional<Document> optionalDocument = this.documentService.rename(documentId, newName);
            if (optionalEditingDomain.isPresent() && optionalDocument.isPresent()) {
                Document document = optionalDocument.get();
                AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
                ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();

                // @formatter:off
                resourceSet.getResources().stream()
                        .filter(resource -> document.getId().toString().equals(resource.getURI().path().substring(1)))
                        .findFirst()
                        .ifPresent(resource -> {
                            resource.eAdapters().stream()
                                .filter(DocumentMetadataAdapter.class::isInstance)
                                .map(DocumentMetadataAdapter.class::cast)
                                .findFirst()
                                .ifPresent(adapter -> adapter.setName(newName));
                        });
                // @formatter:on

                payload = new SuccessPayload(input.id());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
