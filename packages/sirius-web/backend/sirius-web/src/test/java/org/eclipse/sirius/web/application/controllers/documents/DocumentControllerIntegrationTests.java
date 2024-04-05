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
package org.eclipse.sirius.web.application.controllers.documents;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionSuccessPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.document.dto.CreateDocumentInput;
import org.eclipse.sirius.web.application.document.dto.CreateDocumentSuccessPayload;
import org.eclipse.sirius.web.application.studio.services.StudioStereotypeProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.graphql.CreateDocumentMutationRunner;
import org.eclipse.sirius.web.tests.graphql.StereotypesQueryRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
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
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private StereotypesQueryRunner stereotypesQueryRunner;

    @Autowired
    private CreateDocumentMutationRunner createDocumentMutationRunner;

    @Autowired
    private ExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a studio, when the stereotypes are requested, then the studio stereotypes are available")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenStudioWhenStereotypesAreRequestedThenTheStudioStereotypesAreAvailable() {
        this.givenCommittedTransaction.commit();

        Map<String, Object> variables = Map.of("editingContextId", StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString());
        var result = this.stereotypesQueryRunner.run(variables);

        List<String> stereotypeIds = JsonPath.read(result, "$.data.viewer.editingContext.stereotypes.edges[*].node.id");
        assertThat(stereotypeIds).containsAll(List.of(
                StudioStereotypeProvider.DOMAIN_STEREOTYPE,
                StudioStereotypeProvider.VIEW_STEREOTYPE
        ));
    }

    @Test
    @DisplayName("Given a studio, when the creation of a new view document is requested, then the view is created")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenStudioWhenTheCreationOfViewDocumentIsRequestedThenTheViewIsCreated() {
        this.createDocument(StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString(), StudioStereotypeProvider.DOMAIN_STEREOTYPE, "Domain");
    }

    @Test
    @DisplayName("Given a studio, when the creation of a new domain document is requested, then the domain is created")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenStudioWhenTheCreationOfDomainDocumentIsRequestedThenTheDomainIsCreated() {
        this.createDocument(StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString(), StudioStereotypeProvider.VIEW_STEREOTYPE, "View");
    }

    private void createDocument(String editingContextId, String stereotypeId, String name) {
        this.givenCommittedTransaction.commit();

        var input = new CreateDocumentInput(UUID.randomUUID(), editingContextId, stereotypeId, name);
        var result = this.createDocumentMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result, "$.data.createDocument.__typename");
        assertThat(typename).isEqualTo(CreateDocumentSuccessPayload.class.getSimpleName());

        Function<IEditingContext, Object> function = editingContext -> Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(emfEditingContext -> emfEditingContext.getDomain().getResourceSet().getResources().stream()
                        .anyMatch(resource -> resource.eAdapters().stream()
                                .filter(ResourceMetadataAdapter.class::isInstance)
                                .map(ResourceMetadataAdapter.class::cast)
                                .map(ResourceMetadataAdapter::getName)
                                .anyMatch(name::equals)))
                .orElse(false);
        var mono = this.executeEditingContextFunctionRunner.execute(new ExecuteEditingContextFunctionInput(UUID.randomUUID(), editingContextId, function));

        Predicate<ExecuteEditingContextFunctionSuccessPayload> predicate = payload -> Optional.of(payload)
                .map(ExecuteEditingContextFunctionSuccessPayload::result)
                .filter(Boolean.class::isInstance)
                .map(Boolean.class::cast)
                .orElse(false);

        StepVerifier.create(mono)
                .expectNextMatches(predicate)
                .thenCancel()
                .verify();
    }
}
