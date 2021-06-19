/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import org.eclipse.sirius.web.collaborative.api.dto.RenameDocumentInput;
import org.eclipse.sirius.web.collaborative.api.dto.RenameDocumentSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.services.ChangeDescription;
import org.eclipse.sirius.web.collaborative.api.services.ChangeKind;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IEditingContextEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.emf.services.EditingContext;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.messages.IServicesMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

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
    public boolean canHandle(IInput input) {
        return input instanceof RenameDocumentInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IInput input) {
        this.counter.increment();

        // @formatter:off
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain);
        // @formatter:on

        if (input instanceof RenameDocumentInput) {
            RenameDocumentInput renameDocumentInput = (RenameDocumentInput) input;
            UUID documentId = renameDocumentInput.getDocumentId();
            String newName = renameDocumentInput.getNewName();

            Optional<Document> optionalDocument = this.documentService.rename(documentId, newName);
            if (optionalEditingDomain.isPresent() && optionalDocument.isPresent()) {
                Document document = optionalDocument.get();
                AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
                ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();

                // @formatter:off
                resourceSet.getResources().stream()
                        .filter(resource -> document.getId().equals(UUID.fromString(resource.getURI().toString())))
                        .findFirst()
                        .ifPresent(resource -> {
                            resource.eAdapters().stream()
                                .filter(DocumentMetadataAdapter.class::isInstance)
                                .map(DocumentMetadataAdapter.class::cast)
                                .findFirst()
                                .ifPresent(adapter -> adapter.setName(newName));
                        });
                // @formatter:on

                return new EventHandlerResponse(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId()), new RenameDocumentSuccessPayload(input.getId()));
            }
        }
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), RenameDocumentInput.class.getSimpleName());
        return new EventHandlerResponse(new ChangeDescription(ChangeKind.NOTHING, editingContext.getId()), new ErrorPayload(input.getId(), message));
    }

}
