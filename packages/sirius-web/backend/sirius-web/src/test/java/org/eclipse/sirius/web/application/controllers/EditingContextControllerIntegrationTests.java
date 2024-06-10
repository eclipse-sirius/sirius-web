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
package org.eclipse.sirius.web.application.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.EditingContextEventInput;
import org.eclipse.sirius.components.collaborative.dto.InvokeEditingContextActionInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.graphql.tests.CurrentEditingContextQueryRunner;
import org.eclipse.sirius.components.graphql.tests.EditingContextActionsQueryRunner;
import org.eclipse.sirius.components.graphql.tests.EditingContextEventSubscriptionRunner;
import org.eclipse.sirius.components.graphql.tests.EditingContextQueryRunner;
import org.eclipse.sirius.components.graphql.tests.InvokeEditingContextActionMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.services.DefaultEditingContextActionProvider;
import org.eclipse.sirius.web.application.studio.services.StudioEditingContextActionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.data.TestIdentifiers;
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
 * Integration tests of the editing context controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EditingContextControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private EditingContextQueryRunner editingContextQueryRunner;

    @Autowired
    private CurrentEditingContextQueryRunner currentEditingContextQueryRunner;

    @Autowired
    private EditingContextActionsQueryRunner editingContextActionsQueryRunner;

    @Autowired
    private InvokeEditingContextActionMutationRunner invokeEditingContextActionMutationRunner;

    @Autowired
    private EditingContextEventSubscriptionRunner editingContextEventSubscriptionRunner;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @BeforeEach
    public void beforeEach() {
        this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                .map(IEditingContextEventProcessor::getEditingContextId)
                .forEach(this.editingContextEventProcessorRegistry::disposeEditingContextEventProcessor);
    }

    @Test
    @DisplayName("Given an editing context id, when a query is performed, then the editing context is returned")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenEditingContextIdWhenQueryIsPerformedThenTheEditingContextIsReturned() {
        Map<String, Object> variables = Map.of("editingContextId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString());
        var result = this.editingContextQueryRunner.run(variables);

        String editingContextId = JsonPath.read(result, "$.data.viewer.editingContext.id");
        assertThat(editingContextId).isEqualTo(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString());
    }

    @Test
    @DisplayName("Given a project, when a query is performed, then the editing context id is returned")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWhenQueryIsPerformedThenTheEditingContextIdIsReturned() {
        Map<String, Object> variables = Map.of("projectId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString());
        var result = this.currentEditingContextQueryRunner.run(variables);

        String editingContextId = JsonPath.read(result, "$.data.viewer.project.currentEditingContext.id");
        assertThat(editingContextId).isEqualTo(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString());
    }

    @Test
    @DisplayName("Given an editing context id, when a query is performed, then its actions are returned")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenEditingContextIdWhenQueryIsPerformedThenItsActionsAreReturned() {
        Map<String, Object> variables = Map.of("editingContextId", TestIdentifiers.ECORE_SAMPLE_PROJECT.toString());
        var result = this.editingContextActionsQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.editingContext.actions.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.editingContext.actions.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.editingContext.actions.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.editingContext.actions.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.editingContext.actions.pageInfo.count");
        assertThat(count).isGreaterThan(0);
    }

    @Test
    @DisplayName("Given a project, when an editing context action is invoked, then the editing context is modified")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWhenAnEditingContextActionIsInvokedThenTheEditingContextIsModified() {
        var editingContextEventInput = new EditingContextEventInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString());
        var flux = this.editingContextEventSubscriptionRunner.run(editingContextEventInput);

        Consumer<InvokeEditingContextActionInput> invokeEditingContextActionTask = (input) -> {
            var result = this.invokeEditingContextActionMutationRunner.run(input);

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();

            String typename = JsonPath.read(result, "$.data.invokeEditingContextAction.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        var createEmptyDomainInput = new InvokeEditingContextActionInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString(), StudioEditingContextActionProvider.EMPTY_DOMAIN_ID);
        var createEmptyViewInput = new InvokeEditingContextActionInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString(), StudioEditingContextActionProvider.EMPTY_VIEW_ID);
        var createEmptyDocumentInput = new InvokeEditingContextActionInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString(), DefaultEditingContextActionProvider.EMPTY_ACTION_ID);
        StepVerifier.create(flux)
                .then(() -> invokeEditingContextActionTask.accept(createEmptyDomainInput))
                .then(() -> invokeEditingContextActionTask.accept(createEmptyViewInput))
                .then(() -> invokeEditingContextActionTask.accept(createEmptyDocumentInput))
                .thenCancel()
                .verify(Duration.ofSeconds(5));
    }
}
