/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.documents;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionSuccessPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.document.dto.CreateDocumentInput;
import org.eclipse.sirius.web.application.document.dto.CreateDocumentSuccessPayload;
import org.eclipse.sirius.web.application.document.dto.UploadDocumentInput;
import org.eclipse.sirius.web.application.document.dto.UploadDocumentSuccessPayload;
import org.eclipse.sirius.web.application.studio.services.StudioStereotypeProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.CreateDocumentMutationRunner;
import org.eclipse.sirius.web.tests.graphql.StereotypesQueryRunner;
import org.eclipse.sirius.web.tests.graphql.UploadDocumentMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the document controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private StereotypesQueryRunner stereotypesQueryRunner;

    @Autowired
    private CreateDocumentMutationRunner createDocumentMutationRunner;

    @Autowired
    private UploadDocumentMutationRunner uploadDocumentMutationRunner;

    @Autowired
    private ExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the stereotypes are requested, then the studio stereotypes are available")
    public void givenStudioWhenStereotypesAreRequestedThenTheStudioStereotypesAreAvailable() {
        Map<String, Object> variables = Map.of("editingContextId", StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString());
        var result = this.stereotypesQueryRunner.run(variables);

        List<String> stereotypeIds = JsonPath.read(result.data(), "$.data.viewer.editingContext.stereotypes.edges[*].node.id");
        assertThat(stereotypeIds).containsAll(List.of(
                StudioStereotypeProvider.DOMAIN_STEREOTYPE,
                StudioStereotypeProvider.VIEW_STEREOTYPE
        ));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the creation of a new view document is requested, then the view is created")
    public void givenStudioWhenTheCreationOfViewDocumentIsRequestedThenTheViewIsCreated() {
        this.createDocument(StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), StudioStereotypeProvider.DOMAIN_STEREOTYPE, "Domain");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the creation of a new domain document is requested, then the domain is created")
    public void givenStudioWhenTheCreationOfDomainDocumentIsRequestedThenTheDomainIsCreated() {
        this.createDocument(StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), StudioStereotypeProvider.VIEW_STEREOTYPE, "View");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the upload of a new domain document is performed, then the domain is created")
    public void givenStudioWhenTheUploadOfDomainDocumentIsPerformedThenTheDomainIsCreated() {
        var content = """
                {
                  "json": {
                    "version": "1.0",
                    "encoding": "utf-8"
                  },
                  "ns": {
                    "domain":"http://www.eclipse.org/sirius-web/domain"
                  },
                  "content": [
                    {
                      "id":"b213a94d-fa13-4f76-bc5b-fd1125a8aaaf",
                      "eClass":"domain:Domain",
                      "data": {
                        "name":"test",
                        "types": [
                          {"id":"1fb6fd00-5800-4ec5-abb8-76e95309ba55","eClass":"domain:Entity","data":{"name":"NewEntity1"}},
                          {"id":"8b9e2835-2a53-42ad-8b1f-f07a9fbdda10","eClass":"domain:Entity","data":{"name":"NewEntity2","superTypes":["1fb6fd00-5800-4ec5-abb8-76e95309ba55"]}}
                        ]
                      }
                    }
                  ]
                }
                """;
        this.uploadDocument(StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), "test", content, false, this::isExpectedDomain);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the upload of a new domain XMI document is performed, then the domain is created")
    public void givenStudioWhenTheUploadOfDomainXMIDocumentIsPerformedThenTheDomainIsCreated() {
        var content = """
                <?xml version="1.0" encoding="utf-8"?>
                <domain:Domain xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:domain="http://www.eclipse.org/sirius-web/domain" name="test">
                  <types name="NewEntity1"/>
                  <types name="NewEntity2" superTypes="//@types.0"/>
                </domain:Domain>
                """;
        this.uploadDocument(StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), "test", content, false, this::isExpectedDomain);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the upload of a new read-only domain XMI document is performed, then the domain is created")
    public void givenStudioWhenTheUploadOfReadOnlyDomainXMIDocumentIsPerformedThenTheDomainIsCreated() {
        var content = """
                <?xml version="1.0" encoding="utf-8"?>
                <domain:Domain xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:domain="http://www.eclipse.org/sirius-web/domain" name="test">
                  <types name="NewEntity1"/>
                  <types name="NewEntity2" superTypes="//@types.0"/>
                </domain:Domain>
                """;
        this.uploadDocument(StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), "test-xmi-read-only", content, true, resource -> this.isExpectedDomain(resource) && this.isReadOnly(resource));
    }

    private boolean isExpectedDomain(Resource resource) {
        Domain domain = (Domain) resource.getContents().get(0);
        if (domain.getTypes().size() == 2) {
            Entity entity1 = domain.getTypes().get(0);
            Entity entity2 = domain.getTypes().get(1);
            return entity2.getSuperTypes().get(0) == entity1;
        } else {
            return false;
        }
    }

    private boolean isReadOnly(Resource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst()
                .map(ResourceMetadataAdapter::isReadOnly)
                .orElse(false);
    }


    private void createDocument(String editingContextId, String stereotypeId, String name) {
        var input = new CreateDocumentInput(UUID.randomUUID(), editingContextId, stereotypeId, name);
        var result = this.createDocumentMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result.data(), "$.data.createDocument.__typename");
        assertThat(typename).isEqualTo(CreateDocumentSuccessPayload.class.getSimpleName());

        BiFunction<IEditingContext, IInput, IPayload> function = (editingContext, executeEditingContextFunctionInput) -> {
            var resourceFound = Optional.of(editingContext)
                    .filter(IEMFEditingContext.class::isInstance)
                    .map(IEMFEditingContext.class::cast)
                    .map(emfEditingContext -> emfEditingContext.getDomain().getResourceSet().getResources().stream()
                            .anyMatch(resource -> this.isDocumentNamed(resource, name)))
                    .orElse(false);
            return new ExecuteEditingContextFunctionSuccessPayload(executeEditingContextFunctionInput.id(), resourceFound);
        };

        var mono = this.executeEditingContextFunctionRunner.execute(new ExecuteEditingContextFunctionInput(UUID.randomUUID(), editingContextId, function));

        Predicate<IPayload> predicate = payload -> Optional.of(payload)
                .filter(ExecuteEditingContextFunctionSuccessPayload.class::isInstance)
                .map(ExecuteEditingContextFunctionSuccessPayload.class::cast)
                .map(ExecuteEditingContextFunctionSuccessPayload::result)
                .filter(Boolean.class::isInstance)
                .map(Boolean.class::cast)
                .orElse(false);

        StepVerifier.create(mono)
                .expectNextMatches(predicate)
                .thenCancel()
                .verify();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the upload of a new domain XMI document is performed without a name, then an error is returned")
    public void givenStudioWhenTheUploadOfDomainXMIDocumentIsPerformedWithoutNameThenAnErrorIsReturned() {
        this.createInvalidDocument(StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), StudioStereotypeProvider.DOMAIN_STEREOTYPE, "");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the upload of a new domain XMI document is performed with an invalid stereotype, then an error is returned")
    public void givenStudioWhenTheUploadOfDomainXMIDocumentIsPerformedWithInvalidStereotypeThenAnErrorIsReturned() {
        this.createInvalidDocument(StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), "INVALID", "Domain");
    }

    private void createInvalidDocument(String editingContextId, String stereotypeId, String name) {
        var input = new CreateDocumentInput(UUID.randomUUID(), editingContextId, stereotypeId, name);
        var result = this.createDocumentMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result.data(), "$.data.createDocument.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
    }

    private void uploadDocument(String editingContextId, String name, String content, boolean readOnly, Predicate<Resource> check) {
        var file = new UploadFile(name, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
        var input = new UploadDocumentInput(UUID.randomUUID(), editingContextId, file, readOnly);
        var result = this.uploadDocumentMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result.data(), "$.data.uploadDocument.__typename");
        assertThat(typename).isEqualTo(UploadDocumentSuccessPayload.class.getSimpleName());

        String report = JsonPath.read(result.data(), "$.data.uploadDocument.report");
        assertThat(report).isEqualTo("This is a test report");

        this.validateDocument(editingContextId, name, check);
    }

    private void validateDocument(String editingContextId, String name, Predicate<Resource> check) {
        BiFunction<IEditingContext, IInput, IPayload> function = (editingContext, executeEditingContextFunctionInput) -> {
            boolean documentFoundAndValid = Optional.of(editingContext)
                    .filter(IEMFEditingContext.class::isInstance)
                    .map(IEMFEditingContext.class::cast)
                    .flatMap(emfEditingContext -> this.findDocumentByName(emfEditingContext, name))
                    .filter(check)
                    .isPresent();
            return new ExecuteEditingContextFunctionSuccessPayload(executeEditingContextFunctionInput.id(), documentFoundAndValid);
        };
        var mono = this.executeEditingContextFunctionRunner.execute(new ExecuteEditingContextFunctionInput(UUID.randomUUID(), editingContextId, function));
        Predicate<IPayload> predicate = payload -> Optional.of(payload)
                .filter(ExecuteEditingContextFunctionSuccessPayload.class::isInstance)
                .map(ExecuteEditingContextFunctionSuccessPayload.class::cast)
                .map(ExecuteEditingContextFunctionSuccessPayload::result)
                .filter(Boolean.class::isInstance)
                .map(Boolean.class::cast)
                .orElse(false);

        StepVerifier.create(mono)
                .expectNextMatches(predicate)
                .thenCancel()
                .verify();
    }

    private Optional<Resource> findDocumentByName(IEMFEditingContext emfEditingContext, String name) {
        return emfEditingContext.getDomain().getResourceSet().getResources().stream()
                .filter(resource -> this.isDocumentNamed(resource, name))
                .findFirst();
    }

    private boolean isDocumentNamed(Resource resource, String name) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .map(ResourceMetadataAdapter::getName)
                .anyMatch(name::equals);
    }
}