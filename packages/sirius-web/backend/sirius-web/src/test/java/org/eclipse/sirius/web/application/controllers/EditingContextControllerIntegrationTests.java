/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.EditingContextEventInput;
import org.eclipse.sirius.components.collaborative.dto.InvokeEditingContextActionInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.migration.MigrationService;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.graphql.tests.CurrentEditingContextQueryRunner;
import org.eclipse.sirius.components.graphql.tests.EditingContextActionsQueryRunner;
import org.eclipse.sirius.components.graphql.tests.EditingContextEventSubscriptionRunner;
import org.eclipse.sirius.components.graphql.tests.EditingContextQueryRunner;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionSuccessPayload;
import org.eclipse.sirius.components.graphql.tests.InvokeEditingContextActionMutationRunner;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.services.DefaultEditingContextActionProvider;
import org.eclipse.sirius.web.application.studio.services.StudioEditingContextActionProvider;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.LibraryQueryRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the editing context controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EditingContextControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private EditingContextQueryRunner editingContextQueryRunner;

    @Autowired
    private CurrentEditingContextQueryRunner currentEditingContextQueryRunner;

    @Autowired
    private LibraryQueryRunner libraryQueryRunner;

    @Autowired
    private EditingContextActionsQueryRunner editingContextActionsQueryRunner;

    @Autowired
    private InvokeEditingContextActionMutationRunner invokeEditingContextActionMutationRunner;

    @Autowired
    private ExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @Autowired
    private EditingContextEventSubscriptionRunner editingContextEventSubscriptionRunner;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @Autowired
    private List<IMigrationParticipant> migrationParticipants;

    @BeforeEach
    public void beforeEach() {
        this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                .map(IEditingContextEventProcessor::getEditingContextId)
                .forEach(this.editingContextEventProcessorRegistry::disposeEditingContextEventProcessor);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context id, when a query is performed, then the editing context is returned")
    public void givenEditingContextIdWhenQueryIsPerformedThenTheEditingContextIsReturned() {
        Map<String, Object> variables = Map.of("editingContextId", TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID);
        var result = this.editingContextQueryRunner.run(variables);

        String editingContextId = JsonPath.read(result.data(), "$.data.viewer.editingContext.id");
        assertThat(editingContextId).isEqualTo(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when a query is performed, then the editing context id is returned")
    public void givenProjectWhenQueryIsPerformedThenTheEditingContextIdIsReturned() {
        Map<String, Object> variables = Map.of("projectId", TestIdentifiers.ECORE_SAMPLE_PROJECT);
        var result = this.currentEditingContextQueryRunner.run(variables);

        String editingContextId = JsonPath.read(result.data(), "$.data.viewer.project.currentEditingContext.id");
        assertThat(editingContextId).isEqualTo(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a library, when a query to retrieve the editing context ID is performed, then it is returned")
    public void givenLibraryWhenAQueryToRetrieveEditingContextIdIsPerformedThenItIsReturned() {
        var namespace = "papaya";
        var name = "java";
        var version = "1.0.0";

        Map<String, Object> variables = Map.of("namespace", namespace, "name", name, "version", version);
        var result = this.libraryQueryRunner.run(variables);

        String editingContextId = JsonPath.read(result.data(), "$.data.viewer.library.currentEditingContext.id");
        assertThat(editingContextId).isEqualTo(PapayaIdentifiers.PAPAYA_LIBRARY_EDITING_CONTEXT_ID.toString());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context id, when a query is performed, then its actions are returned")
    public void givenEditingContextIdWhenQueryIsPerformedThenItsActionsAreReturned() {
        Map<String, Object> variables = Map.of("editingContextId", StudioIdentifiers.INSTANCE_EDITING_CONTEXT_ID);
        var result = this.editingContextActionsQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result.data(), "$.data.viewer.editingContext.actions.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result.data(), "$.data.viewer.editingContext.actions.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result.data(), "$.data.viewer.editingContext.actions.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result.data(), "$.data.viewer.editingContext.actions.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result.data(), "$.data.viewer.editingContext.actions.pageInfo.count");
        assertThat(count).isGreaterThan(0);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when an editing context action is invoked, then the editing context is modified")
    public void givenProjectWhenAnEditingContextActionIsInvokedThenTheEditingContextIsModified() {
        var editingContextEventInput = new EditingContextEventInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString());
        var flux = this.editingContextEventSubscriptionRunner.run(editingContextEventInput).flux();

        Consumer<InvokeEditingContextActionInput> invokeEditingContextActionTask = (input) -> {
            var result = this.invokeEditingContextActionMutationRunner.run(input);

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();

            String typename = JsonPath.read(result.data(), "$.data.invokeEditingContextAction.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        var createEmptyDomainInput = new InvokeEditingContextActionInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), StudioEditingContextActionProvider.EMPTY_DOMAIN_ID);
        var createEmptyViewInput = new InvokeEditingContextActionInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), StudioEditingContextActionProvider.EMPTY_VIEW_ID);
        var createEmptyDocumentInput = new InvokeEditingContextActionInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), DefaultEditingContextActionProvider.EMPTY_ACTION_ID);
        StepVerifier.create(flux)
                .then(() -> invokeEditingContextActionTask.accept(createEmptyDomainInput))
                .then(() -> invokeEditingContextActionTask.accept(createEmptyViewInput))
                .then(() -> invokeEditingContextActionTask.accept(createEmptyDocumentInput))
                .thenCancel()
                .verify(Duration.ofSeconds(5));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a blank studio, when the create empty view action is invoked, then the created view has migration data")
    public void givenBlankStudioWhenCreateEmptyViewActionIsInvokedThenCreatedViewHasMigrationData() {
        var editingContextId = StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString();
        var editingContextEventInput = new EditingContextEventInput(UUID.randomUUID(), editingContextId);
        var flux = this.editingContextEventSubscriptionRunner.run(editingContextEventInput).flux();

        var existingViews = new HashSet<View>();
        var recordExistingViewsInput = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), editingContextId, (editingContext, input) -> {
            existingViews.addAll(this.getStudioViews((IEMFEditingContext) editingContext));
            return new SuccessPayload(input.id());
        });
        Runnable recordExistingViews = () -> StepVerifier.create(this.executeEditingContextFunctionRunner.execute(recordExistingViewsInput))
                .expectNextMatches(SuccessPayload.class::isInstance)
                .expectComplete()
                .verify(Duration.ofSeconds(5));

        var checkMigrationDataInput = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), editingContextId, (editingContext, input) ->
                new ExecuteEditingContextFunctionSuccessPayload(input.id(), this.getStudioViews((IEMFEditingContext) editingContext)));
        var expectedMigrationData = new MigrationService(this.migrationParticipants).getMostRecentParticipantMigrationData();

        Runnable invokeEditingContextAction = () -> {
            var input = new InvokeEditingContextActionInput(UUID.randomUUID(), editingContextId, StudioEditingContextActionProvider.EMPTY_VIEW_ID);
            var result = this.invokeEditingContextActionMutationRunner.run(input);

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();

            String typename = JsonPath.read(result.data(), "$.data.invokeEditingContextAction.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Runnable checkMigrationData = () -> StepVerifier.create(this.executeEditingContextFunctionRunner.execute(checkMigrationDataInput))
                .assertNext(payload -> {
                    assertThat(payload).isInstanceOf(ExecuteEditingContextFunctionSuccessPayload.class);
                    var result = ((ExecuteEditingContextFunctionSuccessPayload) payload).result();
                    assertThat(result).isInstanceOf(List.class);
                    assertThat((List<?>) result).hasSize(existingViews.size() + 1);
                    assertThat((List<?>) result).filteredOn(view -> !existingViews.contains(view))
                            .singleElement().isInstanceOfSatisfying(View.class, view ->
                                    assertThat(view.eResource().eAdapters()).filteredOn(ResourceMetadataAdapter.class::isInstance)
                                            .singleElement().isInstanceOfSatisfying(ResourceMetadataAdapter.class, resourceMetadataAdapter ->
                                                    assertThat(resourceMetadataAdapter.getLastMigrationData()).isEqualTo(expectedMigrationData)));
                })
                .expectComplete()
                .verify(Duration.ofSeconds(5));

        StepVerifier.create(flux)
                .then(recordExistingViews)
                .then(invokeEditingContextAction)
                .then(checkMigrationData)
                .thenCancel()
                .verify(Duration.ofSeconds(5));
    }

    private List<View> getStudioViews(IEMFEditingContext editingContext) {
        return editingContext.getDomain().getResourceSet().getResources().stream()
                .filter(resource -> IEMFEditingContext.RESOURCE_SCHEME.equals(resource.getURI().scheme()))
                .flatMap(resource -> resource.getContents().stream())
                .filter(View.class::isInstance)
                .map(View.class::cast)
                .toList();
    }
}
