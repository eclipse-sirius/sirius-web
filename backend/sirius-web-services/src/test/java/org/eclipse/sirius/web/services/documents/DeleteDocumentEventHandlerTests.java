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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.web.collaborative.api.dto.DeleteDocumentInput;
import org.eclipse.sirius.web.emf.services.EditingContext;
import org.eclipse.sirius.web.emf.services.SiriusWebJSONResourceFactoryImpl;
import org.eclipse.sirius.web.services.api.accounts.Profile;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.Visibility;
import org.eclipse.sirius.web.services.projects.NoOpServicesMessageService;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Unit tests of the delete document event handler.
 *
 * @author sbegaudeau
 */
public class DeleteDocumentEventHandlerTests {
    @Test
    public void testDeleteDocument() {
        UUID projectId = UUID.randomUUID();
        Document document = new Document(UUID.randomUUID(), new Project(projectId, "", new Profile(UUID.randomUUID(), "username"), Visibility.PUBLIC), "name", "content"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

        IDocumentService documentService = new IDocumentService.NoOp() {
            @Override
            public Optional<Document> getDocument(UUID documentId) {
                return Optional.of(document);
            }
        };
        DeleteDocumentEventHandler handler = new DeleteDocumentEventHandler(documentService, new NoOpServicesMessageService(), new SimpleMeterRegistry());

        var input = new DeleteDocumentInput(UUID.randomUUID(), document.getId());

        assertThat(handler.canHandle(input)).isTrue();

        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();

        Resource resource = new SiriusWebJSONResourceFactoryImpl().createResource(URI.createURI(document.getId().toString()));
        editingDomain.getResourceSet().getResources().add(resource);
        EditingContext editingContext = new EditingContext(UUID.randomUUID(), editingDomain);

        handler.handle(editingContext, input);
        assertThat(editingDomain.getResourceSet().getResources().size()).isEqualTo(0);
    }
}
