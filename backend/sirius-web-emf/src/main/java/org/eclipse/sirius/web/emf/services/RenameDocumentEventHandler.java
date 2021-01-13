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
package org.eclipse.sirius.web.emf.services;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.document.RenameDocumentInput;
import org.eclipse.sirius.web.services.api.document.RenameDocumentSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.IProjectInput;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Reducer used to rename a document.
 *
 * @author fbarbin
 */
@Service
public class RenameDocumentEventHandler implements IProjectEventHandler {

    private final IDocumentService documentService;

    private final IEMFMessageService messageService;

    private final Counter counter;

    public RenameDocumentEventHandler(IDocumentService documentService, IEMFMessageService messageService, MeterRegistry meterRegistry) {
        this.documentService = Objects.requireNonNull(documentService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IProjectInput projectInput) {
        return projectInput instanceof RenameDocumentInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IProjectInput projectInput) {
        this.counter.increment();

        // @formatter:off
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .map(IEditingContext::getDomain)
                .filter(AdapterFactoryEditingDomain.class::isInstance)
                .map(AdapterFactoryEditingDomain.class::cast);
        // @formatter:on

        if (projectInput instanceof RenameDocumentInput) {
            RenameDocumentInput input = (RenameDocumentInput) projectInput;
            UUID documentId = input.getDocumentId();
            String newName = input.getNewName();

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

                return new EventHandlerResponse(true, representation -> true, new RenameDocumentSuccessPayload(document));
            }
        }
        String message = this.messageService.invalidInput(projectInput.getClass().getSimpleName(), RenameDocumentInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }

}
