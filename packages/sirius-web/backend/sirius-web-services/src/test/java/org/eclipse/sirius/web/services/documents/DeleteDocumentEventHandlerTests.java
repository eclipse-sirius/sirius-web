/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.dto.DeleteDocumentInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.projects.NoOpServicesMessageService;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Unit tests of the delete document event handler.
 *
 * @author sbegaudeau
 */
public class DeleteDocumentEventHandlerTests {
    @Test
    public void testDeleteDocument() {
        UUID projectId = UUID.randomUUID();
        Document document = new Document(UUID.randomUUID(), new Project(projectId, "", List.of()), "name", "content");

        IDocumentService documentService = new IDocumentService.NoOp() {
            @Override
            public Optional<Document> getDocument(UUID documentId) {
                return Optional.of(document);
            }
        };
        DeleteDocumentEventHandler handler = new DeleteDocumentEventHandler(documentService, new NoOpServicesMessageService(), new SimpleMeterRegistry());

        var input = new DeleteDocumentInput(UUID.randomUUID(), document.getId());

        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();

        Resource resource = new JSONResourceFactory().createResourceFromPath(document.getId().toString());
        editingDomain.getResourceSet().getResources().add(resource);
        var editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of(), List.of());

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        assertThat(handler.canHandle(editingContext, input)).isTrue();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        assertThat(editingDomain.getResourceSet().getResources().size()).isEqualTo(0);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

    }
}
