/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import org.eclipse.sirius.components.collaborative.dto.CreateDocumentInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.core.configuration.StereotypeDescription;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.web.services.api.accounts.Profile;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.Visibility;
import org.eclipse.sirius.web.services.api.stereotypes.IStereotypeDescriptionService;
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

    // @formatter:off
    private static final String CONTENT = "{" + System.lineSeparator()
        + "    \"json\": {" + System.lineSeparator()
        + "      \"version\": \"1.0\"," + System.lineSeparator()
        + "    \"encoding\": \"utf-8\"" + System.lineSeparator()
        + "  }," + System.lineSeparator()
        + "  \"ns\": {" + System.lineSeparator()
        + "      \"ecore\": \"http://www.eclipse.org/emf/2002/Ecore\"" + System.lineSeparator()
        + "  }," + System.lineSeparator()
        + "  \"content\": [" + System.lineSeparator()
        + "      {" + System.lineSeparator()
        + "        \"eClass\": \"ecore:EPackage\"," + System.lineSeparator()
        + "      \"data\": {" + System.lineSeparator()
        + "          \"name\": \"ecore\"," + System.lineSeparator()
        + "        \"nsURI\": \"http://www.eclipse.org/emf/2002/Ecore\"," + System.lineSeparator()
        + "        \"nsPrefix\": \"ecore\"," + System.lineSeparator()
        + "        \"eClassifiers\": [" + System.lineSeparator()
        + "            {" + System.lineSeparator()
        + "              \"eClass\": \"ecore:EClass\"," + System.lineSeparator()
        + "            \"data\": {" + System.lineSeparator()
        + "                \"name\": \"AClass\"" + System.lineSeparator()
        + "            }" + System.lineSeparator()
        + "          }" + System.lineSeparator()
        + "        ]" + System.lineSeparator()
        + "      }" + System.lineSeparator()
        + "    }" + System.lineSeparator()
        + "  ]" + System.lineSeparator()
        + "}" + System.lineSeparator();
    // @formatter:on

    private static final String DOCUMENT_NAME = "name";

    private static final UUID STEREOTYPE_DESCRIPTION_ID = UUID.nameUUIDFromBytes("stereotypeDescriptionId".getBytes());

    @Test
    public void testCreateDocument() {
        IDocumentService documentService = new IDocumentService.NoOp() {
            @Override
            public Optional<Document> createDocument(String projectId, String name, String content) {
                return Optional.of(new Document(UUID.randomUUID(), new Project(UUID.fromString(projectId), "", new Profile(UUID.randomUUID(), "username"), Visibility.PUBLIC), name, content));
            }
        };
        IStereotypeDescriptionService stereotypeDescriptionService = new IStereotypeDescriptionService.NoOp() {
            @Override
            public Optional<StereotypeDescription> getStereotypeDescriptionById(String editingContextId, UUID stereotypeId) {
                StereotypeDescription stereotypeDescription = new StereotypeDescription(stereotypeId, "label", () -> CONTENT);
                return Optional.of(stereotypeDescription);
            }
        };
        IServicesMessageService messageService = new NoOpServicesMessageService();

        CreateDocumentEventHandler handler = new CreateDocumentEventHandler(documentService, stereotypeDescriptionService, messageService, new SimpleMeterRegistry());
        var input = new CreateDocumentInput(UUID.randomUUID(), UUID.randomUUID().toString(), DOCUMENT_NAME, STEREOTYPE_DESCRIPTION_ID);

        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();
        EditingContext editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of());

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        assertThat(handler.canHandle(editingContext, input)).isTrue();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, input, List.of());

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

        assertThat(editingDomain.getResourceSet().getResources().size()).isEqualTo(1);
        Condition<Object> condition = new Condition<>(adapter -> adapter instanceof DocumentMetadataAdapter, "has an DocumentMetadataAdapter");
        assertThat(editingDomain.getResourceSet().getResources().get(0).eAdapters()).areAtLeastOne(condition);
    }

    @Test
    public void testCreateTwoDocumentWithSameName() {
        IDocumentService documentService = new IDocumentService.NoOp() {
            @Override
            public Optional<Document> createDocument(String projectId, String name, String content) {
                return Optional.of(new Document(UUID.randomUUID(), new Project(UUID.fromString(projectId), "", new Profile(UUID.randomUUID(), "username"), Visibility.PUBLIC), name, content));
            }
        };
        IStereotypeDescriptionService stereotypeDescriptionService = new IStereotypeDescriptionService.NoOp() {
            @Override
            public Optional<StereotypeDescription> getStereotypeDescriptionById(String editingContextId, UUID stereotypeId) {
                StereotypeDescription stereotypeDescription = new StereotypeDescription(stereotypeId, "label", () -> CONTENT);
                return Optional.of(stereotypeDescription);
            }
        };
        IServicesMessageService messageService = new NoOpServicesMessageService();
        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();
        EditingContext editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of());

        CreateDocumentEventHandler handler = new CreateDocumentEventHandler(documentService, stereotypeDescriptionService, messageService, new SimpleMeterRegistry());
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        changeDescriptionSink.asFlux().subscribe(changeDescription -> {
            assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);
        });

        var firstCreateInput = new CreateDocumentInput(UUID.randomUUID(), editingContext.getId(), DOCUMENT_NAME, STEREOTYPE_DESCRIPTION_ID);
        assertThat(handler.canHandle(editingContext, firstCreateInput)).isTrue();
        One<IPayload> firstPayloadSink = Sinks.one();
        handler.handle(firstPayloadSink, changeDescriptionSink, editingContext, firstCreateInput, List.of());

        IPayload firstPayload = firstPayloadSink.asMono().block();
        assertThat(firstPayload).isInstanceOf(SuccessPayload.class);

        var secondCreatedInput = new CreateDocumentInput(UUID.randomUUID(), editingContext.getId(), DOCUMENT_NAME, STEREOTYPE_DESCRIPTION_ID);
        assertThat(handler.canHandle(editingContext, secondCreatedInput)).isTrue();
        One<IPayload> secondPayloadSink = Sinks.one();
        handler.handle(secondPayloadSink, changeDescriptionSink, editingContext, secondCreatedInput, List.of());

        IPayload secondPayload = firstPayloadSink.asMono().block();
        assertThat(secondPayload).isInstanceOf(SuccessPayload.class);
    }
}
