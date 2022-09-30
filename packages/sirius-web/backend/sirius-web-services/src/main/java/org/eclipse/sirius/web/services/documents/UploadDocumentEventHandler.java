/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.SiriusWebJSONResourceFactoryImpl;
import org.eclipse.sirius.components.emf.utils.EMFResourceUtils;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.document.UploadDocumentInput;
import org.eclipse.sirius.web.services.api.document.UploadDocumentSuccessPayload;
import org.eclipse.sirius.web.services.messages.IServicesMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Event handler used to create a new document from a file upload.
 *
 * @author sbegaudeau
 */
@Service
public class UploadDocumentEventHandler implements IEditingContextEventHandler {

    private final Logger logger = LoggerFactory.getLogger(UploadDocumentEventHandler.class);

    private final IDocumentService documentService;

    private final IServicesMessageService messageService;

    private final Counter counter;

    public UploadDocumentEventHandler(IDocumentService documentService, IServicesMessageService messageService, MeterRegistry meterRegistry) {
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
        return input instanceof UploadDocumentInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        IPayload payload = new ErrorPayload(input.getId(), this.messageService.unexpectedError());
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof UploadDocumentInput) {

            UploadDocumentInput uploadDocumentInput = (UploadDocumentInput) input;
            String projectId = uploadDocumentInput.getEditingContextId();
            UploadFile file = uploadDocumentInput.getFile();

        // @formatter:off
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain);
        // @formatter:on

            String name = file.getName().trim();
            if (optionalEditingDomain.isPresent()) {
                AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();

                String content = this.getContent(adapterFactoryEditingDomain.getResourceSet().getPackageRegistry(), file);
                var optionalDocument = this.documentService.createDocument(projectId, name, content);

                if (optionalDocument.isPresent()) {
                    Document document = optionalDocument.get();
                    ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();
                    URI uri = URI.createURI(document.getId().toString());

                    if (resourceSet.getResource(uri, false) == null) {

                        ResourceSet loadingResourceSet = new ResourceSetImpl();
                        loadingResourceSet.setPackageRegistry(resourceSet.getPackageRegistry());

                        JsonResource resource = new SiriusWebJSONResourceFactoryImpl().createResource(uri);
                        loadingResourceSet.getResources().add(resource);
                        try (var inputStream = new ByteArrayInputStream(document.getContent().getBytes())) {
                            resource.load(inputStream, null);
                        } catch (IOException exception) {
                            this.logger.warn(exception.getMessage(), exception);
                        }

                        resource.eAdapters().add(new DocumentMetadataAdapter(name));
                        resourceSet.getResources().add(resource);

                        payload = new UploadDocumentSuccessPayload(input.getId(), document);
                        changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
                    }
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private String getContent(EPackage.Registry registry, UploadFile file) {
        String uri = file.getName();
        String content = ""; //$NON-NLS-1$
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.setPackageRegistry(registry);
        try (var inputStream = file.getInputStream()) {
            URI resourceURI = URI.createURI(uri);
            Optional<Resource> optionalInputResource = this.getResource(inputStream, resourceURI, resourceSet);
            if (optionalInputResource.isPresent()) {
                Resource inputResource = optionalInputResource.get();
                JsonResource ouputResource = new SiriusWebJSONResourceFactoryImpl().createResource(URI.createURI(uri));
                resourceSet.getResources().add(ouputResource);
                ouputResource.getContents().addAll(inputResource.getContents());

                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    Map<String, Object> saveOptions = new HashMap<>();
                    saveOptions.put(JsonResource.OPTION_ENCODING, JsonResource.ENCODING_UTF_8);
                    saveOptions.put(JsonResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
                    saveOptions.put(JsonResource.OPTION_ID_MANAGER, new EObjectRandomIDManager());

                    ouputResource.save(outputStream, saveOptions);

                    content = outputStream.toString();
                }
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return content;
    }

    /**
     * Returns the {@link Resource} with the given {@link URI} or {@link Optional#empty()} regarding to the content of
     * the first line of the given {@link InputStream}.
     *
     * <p>
     * Returns a {@link JsonResourceImpl} if the first line contains a '{', a {@link XMIResourceImpl} if the first line
     * contains '<', {@link Optional#empty()} otherwise.
     * </p>
     *
     * @param inputStream
     *            The {@link InputStream} used to determine which {@link Resource} to create
     * @param resourceURI
     *            The {@link URI} to use to create the {@link Resource}
     * @param resourceSet
     *            The {@link ResourceSet} used to store the loaded resource
     * @return a {@link JsonResourceImpl}, a {@link XMIResourceImpl} or {@link Optional#empty()}
     */
    private Optional<Resource> getResource(InputStream inputStream, URI resourceURI, ResourceSet resourceSet) {
        Resource resource = null;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(Integer.MAX_VALUE);
        try (var reader = new BufferedReader(new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            Map<String, Object> options = new HashMap<>();
            if (line != null) {
                if (line.contains("{")) { //$NON-NLS-1$
                    resource = new SiriusWebJSONResourceFactoryImpl().createResource(resourceURI);
                } else if (line.contains("<")) { //$NON-NLS-1$
                    resource = new XMIResourceImpl(resourceURI);
                    options = new EMFResourceUtils().getXMILoadOptions();
                }
            }
            bufferedInputStream.reset();
            if (resource != null) {
                resourceSet.getResources().add(resource);
                resource.load(bufferedInputStream, options);
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return Optional.ofNullable(resource);
    }

}
