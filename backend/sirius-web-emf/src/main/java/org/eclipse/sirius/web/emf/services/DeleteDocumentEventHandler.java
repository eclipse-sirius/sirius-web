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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.web.services.api.document.DeleteDocumentInput;
import org.eclipse.sirius.web.services.api.document.DeleteDocumentSuccessPayload;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Reducer used to delete a document.
 *
 * @author sbegaudeau
 */
@Service
public class DeleteDocumentEventHandler implements IProjectEventHandler {

    private final IDocumentService documentService;

    private final IEMFMessageService messageService;

    private final Counter counter;

    public DeleteDocumentEventHandler(IDocumentService documentService, IEMFMessageService messageService, MeterRegistry meterRegistry) {
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
        return input instanceof DeleteDocumentInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IInput input) {
        this.counter.increment();

        // @formatter:off
        var optionalEditingDomain = Optional.of(editingContext)
                .map(IEditingContext::getDomain)
                .filter(AdapterFactoryEditingDomain.class::isInstance)
                .map(AdapterFactoryEditingDomain.class::cast);
        // @formatter:on

        if (input instanceof DeleteDocumentInput) {
            DeleteDocumentInput deleteDocumentInput = (DeleteDocumentInput) input;
            var optionalDocument = this.documentService.getDocument(deleteDocumentInput.getDocumentId());

            if (optionalEditingDomain.isPresent() && optionalDocument.isPresent()) {
                AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
                Document document = optionalDocument.get();

                ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();
                URI uri = URI.createURI(document.getId().toString());

                // @formatter:off
                List<Resource> resourcesToDelete = resourceSet.getResources().stream()
                        .filter(resource -> resource.getURI().equals(uri))
                        .collect(Collectors.toUnmodifiableList());
                resourcesToDelete.stream().forEach(resourceSet.getResources()::remove);
                // @formatter:on

                this.documentService.delete(document.getId());

                return new EventHandlerResponse(true, representation -> true, new DeleteDocumentSuccessPayload(document.getProject()));
            }
        }

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), DeleteDocumentInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }

}
