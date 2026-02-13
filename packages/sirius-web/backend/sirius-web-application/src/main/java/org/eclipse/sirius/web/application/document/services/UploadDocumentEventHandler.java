/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.application.document.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.document.dto.DocumentDTO;
import org.eclipse.sirius.web.application.document.dto.UploadDocumentInput;
import org.eclipse.sirius.web.application.document.dto.UploadDocumentSuccessPayload;
import org.eclipse.sirius.web.application.document.services.api.IUploadDocumentReportProvider;
import org.eclipse.sirius.web.application.document.services.api.IUploadFileLoader;
import org.eclipse.sirius.web.application.document.services.api.UploadedResource;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Event handler used to create a new document from a file upload.
 *
 * @author sbegaudeau
 */
@Service
public class UploadDocumentEventHandler implements IEditingContextEventHandler {

    private final IIdentityService identityService;

    private final IEditingContextSearchService editingContextSearchService;

    private final List<IUploadDocumentReportProvider> uploadDocumentReportProviders;

    private final IMessageService messageService;

    private final IUploadFileLoader uploadDocumentLoader;

    private final boolean reuseActiveResourceSet;

    private final Counter counter;

    public UploadDocumentEventHandler(IIdentityService identityService, IEditingContextSearchService editingContextSearchService, List<IUploadDocumentReportProvider> uploadDocumentReportProviders, IMessageService messageService,
                                      IUploadFileLoader uploadDocumentLoader, MeterRegistry meterRegistry, @Value("${sirius.web.upload.reuseActiveResourceSet:true}") boolean reuseActiveResourceSet) {
        this.identityService = Objects.requireNonNull(identityService);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.uploadDocumentReportProviders = Objects.requireNonNull(uploadDocumentReportProviders);
        this.messageService = Objects.requireNonNull(messageService);
        this.uploadDocumentLoader = Objects.requireNonNull(uploadDocumentLoader);
        this.reuseActiveResourceSet = reuseActiveResourceSet;
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof UploadDocumentInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        IPayload payload = new ErrorPayload(input.id(), this.messageService.unexpectedError());
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        Optional<ResourceSet> optionalResourceSet;
        if (this.reuseActiveResourceSet && editingContext instanceof IEMFEditingContext emfEditingContext) {
            optionalResourceSet = Optional.of(emfEditingContext.getDomain().getResourceSet());
        } else {
            optionalResourceSet = this.createResourceSet(editingContext.getId());
        }
        if (input instanceof UploadDocumentInput uploadDocumentInput && editingContext instanceof IEMFEditingContext emfEditingContext && optionalResourceSet.isPresent()) {
            var resourceSet = optionalResourceSet.get();

            IResult<UploadedResource> result = this.uploadDocumentLoader.load(resourceSet, emfEditingContext, uploadDocumentInput.file(), false, uploadDocumentInput.readOnly());
            if (result instanceof Success<UploadedResource> success) {
                var newResource = success.data().resource();

                var optionalId = new UUIDParser().parse(this.identityService.getId(newResource));

                var optionalName = newResource.eAdapters().stream()
                        .filter(ResourceMetadataAdapter.class::isInstance)
                        .map(ResourceMetadataAdapter.class::cast)
                        .findFirst()
                        .map(ResourceMetadataAdapter::getName);

                if (optionalId.isPresent() && optionalName.isPresent()) {
                    var id = optionalId.get();
                    var name = optionalName.get();

                    String report = this.getReport(success.data());
                    payload = new UploadDocumentSuccessPayload(input.id(), new DocumentDTO(id, name, ExplorerDescriptionProvider.DOCUMENT_KIND), report, success.data().idMapping());
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
                }

            } else if (result instanceof Failure<UploadedResource> failure) {
                payload = new ErrorPayload(input.id(), failure.message());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<ResourceSet> createResourceSet(String editingContextId) {
        return this.editingContextSearchService.findById(editingContextId)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(AdapterFactoryEditingDomain::getResourceSet);
    }

    private String getReport(UploadedResource uploadedResource) {
        return this.uploadDocumentReportProviders.stream()
                .filter(provider -> provider.canHandle(uploadedResource))
                .map(provider -> provider.createReport(uploadedResource))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
