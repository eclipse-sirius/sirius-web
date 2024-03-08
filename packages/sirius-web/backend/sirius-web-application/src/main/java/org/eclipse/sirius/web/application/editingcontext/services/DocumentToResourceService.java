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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.web.application.editingcontext.services.api.IDocumentToResourceService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to load documents as resources.
 *
 * @author sbegaudeau
 */
@Service
public class DocumentToResourceService implements IDocumentToResourceService {

    private final Logger logger = LoggerFactory.getLogger(DocumentToResourceService.class);

    @Override
    public Optional<Resource> toResource(ResourceSet resourceSet, Document document) {
        Optional<Resource> optionalResource = Optional.empty();

        var resource = new JSONResourceFactory().createResourceFromPath(document.getId().toString());
        try (var inputStream = new ByteArrayInputStream(document.getContent().getBytes())) {
            resourceSet.getResources().add(resource);
            resource.load(inputStream, null);

            resource.eAdapters().add(new ResourceMetadataAdapter(document.getName()));

            optionalResource = Optional.of(resource);
        } catch (IOException | IllegalArgumentException exception) {
            this.logger.warn("An error occured while loading document {}: {}.", document.getId(), exception.getMessage());
            resourceSet.getResources().remove(resource);
        }

        return optionalResource;
    }
}
