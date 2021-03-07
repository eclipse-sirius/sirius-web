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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.accounts.Profile;
import org.eclipse.sirius.web.services.api.document.DeleteDocumentInput;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.Visibility;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Unit tests of the delete document event handler.
 *
 * @author sbegaudeau
 */
public class DeleteDocumentEventHandlerTestCases {
    @Test
    public void testDeleteDocument() {
        UUID projectId = UUID.randomUUID();
        Document document = new Document(UUID.randomUUID(), new Project(projectId, "", new Profile(UUID.randomUUID(), "username"), Visibility.PUBLIC), "name", "content"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

        IDocumentService documentService = new NoOpDocumentService() {
            @Override
            public Optional<Document> getDocument(UUID documentId) {
                return Optional.of(document);
            }
        };
        DeleteDocumentEventHandler handler = new DeleteDocumentEventHandler(documentService, new NoOpEMFMessageService(), new SimpleMeterRegistry());

        var input = new DeleteDocumentInput(document.getId());
        var context = new Context(new UsernamePasswordAuthenticationToken(null, null));

        assertThat(handler.canHandle(input)).isTrue();

        EditingDomain editingDomain = new EditingDomainFactory().create();

        Resource resource = new SiriusWebJSONResourceFactoryImpl().createResource(URI.createURI(document.getId().toString()));
        editingDomain.getResourceSet().getResources().add(resource);

        IEditingContext editingContext = new IEditingContext() {

            @Override
            public UUID getProjectId() {
                return projectId;
            }

            @Override
            public Object getDomain() {
                return editingDomain;
            }
        };

        handler.handle(editingContext, input, context);
        assertThat(editingDomain.getResourceSet().getResources().size()).isEqualTo(0);
    }
}
