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
package org.eclipse.sirius.web.application.document.controllers;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.document.services.api.IDocumentExporter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The entry point of the HTTP API to download documents.
 * <p>
 * This endpoint will be available on the API base path prefix with download segment and followed by the document Id
 * used as a suffix. As such, users will be able to send document download request to the following URL:
 * </p>
 *
 * <pre>
 * PROTOCOL://DOMAIN.TLD(:PORT)/API_BASE_PATH/editingcontexts/EDITING_CONTEXT_ID/documents/DOCUMENT_ID
 * </pre>
 *
 * @author smonnier
 */
@Controller
@RequestMapping("/api/editingcontexts/{editingContextId}/documents")
public class DocumentDownloadController {

    private final IEditingContextSearchService editingContextSearchService;

    private final List<IDocumentExporter> documentExporters;

    public DocumentDownloadController(IEditingContextSearchService editingContextSearchService, List<IDocumentExporter> documentExporters) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.documentExporters = Objects.requireNonNull(documentExporters);
    }

    @ResponseBody
    @GetMapping(path = "/{documentId}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable String editingContextId, @PathVariable String documentId, @RequestHeader HttpHeaders requestHeaders) {
        var optionalResource = this.getResource(editingContextId, documentId);
        if (optionalResource.isPresent()) {
            var resource = optionalResource.get();

            Optional<MediaType> optionalAcceptHeaderValue = Optional.empty();
            Optional<IDocumentExporter> optionalDocumentExporter = Optional.empty();

            var iterator = requestHeaders.getAccept().iterator();
            while (iterator.hasNext() && optionalAcceptHeaderValue.isEmpty() && optionalDocumentExporter.isEmpty()) {
                var acceptHeaderValue = iterator.next();

                optionalAcceptHeaderValue = Optional.of(acceptHeaderValue);
                optionalDocumentExporter = this.documentExporters.stream()
                        .filter(documentExporter -> documentExporter.canHandle(resource, acceptHeaderValue.toString()))
                        .findFirst();
            }

            if (optionalDocumentExporter.isPresent() && optionalAcceptHeaderValue.isPresent()) {
                var documentExporter = optionalDocumentExporter.get();
                var acceptHeaderValue = optionalAcceptHeaderValue.get();

                var optionalContent = documentExporter.getBytes(resource, acceptHeaderValue.toString());
                if (optionalContent.isPresent()) {
                    var content = optionalContent.get();

                    var name = resource.eAdapters().stream()
                            .filter(ResourceMetadataAdapter.class::isInstance)
                            .map(ResourceMetadataAdapter.class::cast)
                            .findFirst()
                            .map(ResourceMetadataAdapter::getName)
                            .orElse("resource");
                    ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                            .filename(name)
                            .build();

                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.setContentDisposition(contentDisposition);
                    responseHeaders.setContentType(acceptHeaderValue);
                    responseHeaders.setContentLength(content.length);

                    InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(content));
                    return new ResponseEntity<>(inputStreamResource, responseHeaders, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    private Optional<org.eclipse.emf.ecore.resource.Resource> getResource(String editingContextId, String documentId) {
        return this.editingContextSearchService.findById(editingContextId)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .flatMap(editingContext -> {
                    var uri = new JSONResourceFactory().createResourceURI(documentId);
                    return editingContext.getDomain().getResourceSet().getResources().stream()
                            .filter(resource -> resource.getURI().equals(uri))
                            .findFirst();
                });
    }

}
