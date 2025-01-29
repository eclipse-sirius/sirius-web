/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.web.application.document.services.api.IDocumentDownloadResourceSearchService;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a document download resource search service for tests.
 *
 * @author frouene
 */
@Service
@Conditional(OnStudioTests.class)
public class PapayaDocumentDownloadResourceSearchService implements IDocumentDownloadResourceSearchService {

    @Override
    public Optional<Resource> findResource(String editingContextId, String documentId) {
        if (PapayaIdentifiers.PROJECT_OBJECT.toString().equals(documentId)) {
            return Optional.ofNullable(new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString()));
        }
        return Optional.empty();
    }
}
