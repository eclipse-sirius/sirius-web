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
import org.eclipse.sirius.components.collaborative.dto.RenameDocumentInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
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
 * Unit tests of the rename document event handler.
 *
 * @author fbarbin
 */
public class RenameDocumentEventHandlerTests {
    private static final String OLD_NAME = "oldName";

    private static final String NEW_NAME = "newName";

    @Test
    public void testRenameDocument() {
        IDocumentService noOpDocumentService = new IDocumentService.NoOp() {
            @Override
            public Optional<Document> rename(UUID documentId, String newName) {
                return Optional.of(new Document(documentId, new Project(UUID.randomUUID(), "", List.of()), newName, "noContent"));
            }
        };
        RenameDocumentEventHandler handler = new RenameDocumentEventHandler(noOpDocumentService, new NoOpServicesMessageService(), new SimpleMeterRegistry());

        UUID documentId = UUID.randomUUID();
        IInput input = new RenameDocumentInput(UUID.randomUUID(), documentId, NEW_NAME);

        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();

        ResourceMetadataAdapter adapter = new ResourceMetadataAdapter(OLD_NAME);
        Resource resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        resource.eAdapters().add(adapter);
        editingDomain.getResourceSet().getResources().add(resource);

        assertThat(editingDomain.getResourceSet().getResources().size()).isEqualTo(1);
        assertThat(adapter.getName()).isEqualTo(OLD_NAME);

        IEditingContext editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of(), List.of());

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        assertThat(handler.canHandle(editingContext, input)).isTrue();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        assertThat(editingDomain.getResourceSet().getResources().size()).isEqualTo(1);
        assertThat(adapter.getName()).isEqualTo(NEW_NAME);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);
    }
}
