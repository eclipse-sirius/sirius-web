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

import org.assertj.core.api.Condition;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.services.api.document.Stereotype;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.api.document.CreateDocumentInput;
import org.eclipse.sirius.web.services.api.document.CreateDocumentSuccessPayload;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.stereotypes.IStereotypeService;
import org.eclipse.sirius.web.services.messages.IServicesMessageService;
import org.eclipse.sirius.web.services.projects.NoOpServicesMessageService;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Unit tests of the create document from stereotype event handler.
 *
 * @author sbegaudeau
 */
public class CreateDocumentEventHandlerTests {
    private static final String CONTENT = """
            {
              "json": {
                "version": "1.0",
                "encoding": "utf-8"
              },
              "ns": {
                "ecore": "http://www.eclipse.org/emf/2002/Ecore"
              },
              "content": [
                {
                  "eClass": "ecore::EPackage",
                  "data": {
                    "name": "ecore",
                    "nsURI": "http://www.eclipse.org/emf/2002/Ecore",
                    "nsPrefix": "ecore",
                    "eClassifiers": [
                      {
                        "eClass": "ecore::EClass",
                        "data": {
                          "name": "AClass"
                        }
                      }
                    ]
                  }
                }
              ]
            }
            """;

    private static final String DOCUMENT_NAME = "name";

    private static final UUID STEREOTYPE_ID = UUID.nameUUIDFromBytes("stereotypeId".getBytes());

    @Test
    public void testCreateDocument() {
        IDocumentService documentService = new IDocumentService.NoOp() {
            @Override
            public Optional<Document> createDocument(String projectId, String name, String content) {
                return Optional.of(new Document(UUID.randomUUID(), new Project(UUID.fromString(projectId), "", List.of()), name, content));
            }
        };
        IStereotypeService stereotypeService = new IStereotypeService.NoOp() {
            @Override
            public Optional<Stereotype> getStereotypeById(String editingContextId, UUID stereotypeId) {
                Stereotype stereotype = new Stereotype(stereotypeId, "label", () -> CONTENT);
                return Optional.of(stereotype);
            }
        };
        IServicesMessageService messageService = new NoOpServicesMessageService();

        CreateDocumentEventHandler handler = new CreateDocumentEventHandler(documentService, stereotypeService, messageService, new SimpleMeterRegistry());
        var input = new CreateDocumentInput(UUID.randomUUID(), UUID.randomUUID().toString(), DOCUMENT_NAME, STEREOTYPE_ID);

        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();
        EditingContext editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of(), List.of());

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        assertThat(handler.canHandle(editingContext, input)).isTrue();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(CreateDocumentSuccessPayload.class);

        assertThat(editingDomain.getResourceSet().getResources().size()).isEqualTo(1);
        Condition<Object> condition = new Condition<>(adapter -> adapter instanceof ResourceMetadataAdapter, "has an DocumentMetadataAdapter");
        assertThat(editingDomain.getResourceSet().getResources().get(0).eAdapters()).areAtLeastOne(condition);
    }

    @Test
    public void testCreateTwoDocumentWithSameName() {
        IDocumentService documentService = new IDocumentService.NoOp() {
            @Override
            public Optional<Document> createDocument(String projectId, String name, String content) {
                return Optional.of(new Document(UUID.randomUUID(), new Project(UUID.fromString(projectId), "", List.of()), name, content));
            }
        };
        IStereotypeService stereotypeService = new IStereotypeService.NoOp() {
            @Override
            public Optional<Stereotype> getStereotypeById(String editingContextId, UUID stereotypeId) {
                Stereotype stereotype = new Stereotype(stereotypeId, "label", () -> CONTENT);
                return Optional.of(stereotype);
            }
        };
        IServicesMessageService messageService = new NoOpServicesMessageService();
        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();
        EditingContext editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of(), List.of());

        CreateDocumentEventHandler handler = new CreateDocumentEventHandler(documentService, stereotypeService, messageService, new SimpleMeterRegistry());
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        changeDescriptionSink.asFlux().subscribe(changeDescription -> {
            assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);
        });

        var firstCreateInput = new CreateDocumentInput(UUID.randomUUID(), editingContext.getId(), DOCUMENT_NAME, STEREOTYPE_ID);
        assertThat(handler.canHandle(editingContext, firstCreateInput)).isTrue();
        One<IPayload> firstPayloadSink = Sinks.one();
        handler.handle(firstPayloadSink, changeDescriptionSink, editingContext, firstCreateInput);

        IPayload firstPayload = firstPayloadSink.asMono().block();
        assertThat(firstPayload).isInstanceOf(CreateDocumentSuccessPayload.class);

        var secondCreatedInput = new CreateDocumentInput(UUID.randomUUID(), editingContext.getId(), DOCUMENT_NAME, STEREOTYPE_ID);
        assertThat(handler.canHandle(editingContext, secondCreatedInput)).isTrue();
        One<IPayload> secondPayloadSink = Sinks.one();
        handler.handle(secondPayloadSink, changeDescriptionSink, editingContext, secondCreatedInput);

        IPayload secondPayload = firstPayloadSink.asMono().block();
        assertThat(secondPayload).isInstanceOf(CreateDocumentSuccessPayload.class);
    }
}
