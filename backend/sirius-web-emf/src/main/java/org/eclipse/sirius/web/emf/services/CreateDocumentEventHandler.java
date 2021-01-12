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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.api.configuration.StereotypeDescription;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.document.CreateDocumentInput;
import org.eclipse.sirius.web.services.api.document.CreateDocumentSuccessPayload;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.projects.IProjectInput;
import org.eclipse.sirius.web.services.api.stereotypes.IStereotypeDescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Event handler used to create a new document from a stereotype.
 *
 * @author sbegaudeau
 */
@Service
public class CreateDocumentEventHandler implements IProjectEventHandler {

    private final Logger logger = LoggerFactory.getLogger(CreateDocumentEventHandler.class);

    private final IDocumentService documentService;

    private final IStereotypeDescriptionService stereotypeDescriptionService;

    private final IEMFMessageService messageService;

    private final Counter counter;

    public CreateDocumentEventHandler(IDocumentService documentService, IStereotypeDescriptionService stereotypeDescriptionService, IEMFMessageService messageService, MeterRegistry meterRegistry) {
        this.documentService = Objects.requireNonNull(documentService);
        this.stereotypeDescriptionService = Objects.requireNonNull(stereotypeDescriptionService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IProjectInput projectInput) {
        return projectInput instanceof CreateDocumentInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IProjectInput projectInput, Context context) {
        this.counter.increment();

        EventHandlerResponse response = new EventHandlerResponse(false, representation -> false, new ErrorPayload(this.messageService.unexpectedError()));

        if (projectInput instanceof CreateDocumentInput) {
            CreateDocumentInput input = (CreateDocumentInput) projectInput;

            String name = input.getName().trim();
            UUID projectId = input.getProjectId();
            String stereotypeDescriptionId = input.getStereotypeDescriptionId();

            Optional<StereotypeDescription> optionalStereotypeDescription = this.stereotypeDescriptionService.getStereotypeDescriptionById(stereotypeDescriptionId);

            if (name.isBlank()) {
                IPayload payload = new ErrorPayload(this.messageService.invalidDocumentName(name));
                response = new EventHandlerResponse(false, representation -> false, payload);
            } else if (optionalStereotypeDescription.isEmpty()) {
                IPayload payload = new ErrorPayload(this.messageService.stereotypeDescriptionNotFound(stereotypeDescriptionId));
                response = new EventHandlerResponse(false, representation -> false, payload);
            } else if (optionalStereotypeDescription.isPresent()) {
                StereotypeDescription stereotypeDescription = optionalStereotypeDescription.get();
                response = this.createDocument(editingContext, projectId, name, stereotypeDescription);
            }
        }

        return response;
    }

    private EventHandlerResponse createDocument(IEditingContext editingContext, UUID projectId, String name, StereotypeDescription stereotypeDescription) {
        // @formatter:off
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .map(IEditingContext::getDomain)
                .filter(AdapterFactoryEditingDomain.class::isInstance)
                .map(AdapterFactoryEditingDomain.class::cast);
        // @formatter:on

        if (optionalEditingDomain.isPresent()) {
            AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
            ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();

            var optionalDocument = this.documentService.createDocument(projectId, name, stereotypeDescription.getContent());
            if (optionalDocument.isPresent()) {
                Document document = optionalDocument.get();
                URI uri = URI.createURI(document.getId().toString());

                JsonResource resource = new SiriusWebJSONResourceFactoryImpl().createResource(uri);
                try (var inputStream = new ByteArrayInputStream(document.getContent().getBytes())) {
                    resource.load(inputStream, null);
                } catch (IOException exception) {
                    this.logger.error(exception.getMessage(), exception);
                }

                resource.eAdapters().add(new DocumentMetadataAdapter(name));

                resourceSet.getResources().add(resource);

                return new EventHandlerResponse(true, representation -> true, new CreateDocumentSuccessPayload(document));
            }
        }

        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(this.messageService.unexpectedError()));
    }

}
