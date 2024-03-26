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
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.EditSelectInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormEventInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.PropertiesEventInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.RichText;
import org.eclipse.sirius.components.forms.Select;
import org.eclipse.sirius.components.forms.TreeWidget;
import org.eclipse.sirius.components.forms.tests.graphql.EditSelectMutationRunner;
import org.eclipse.sirius.components.forms.tests.graphql.FormEventSubscriptionRunner;
import org.eclipse.sirius.components.forms.tests.graphql.PropertiesEventSubscriptionRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.graphql.tests.CreateRepresentationMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.TestIdentifiers;
import org.eclipse.sirius.web.services.FormVariableViewPreEditingContextProcessor;
import org.eclipse.sirius.web.services.MasterDetailsFormDescriptionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.test.StepVerifier;

/**
 * Integration tests of the form controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FormControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private PropertiesEventSubscriptionRunner propertiesEventSubscriptionRunner;

    @Autowired
    private FormEventSubscriptionRunner formEventSubscriptionRunner;

    @Autowired
    private CreateRepresentationMutationRunner createRepresentationMutationRunner;

    @Autowired
    private EditSelectMutationRunner editSelectMutationRunner;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @BeforeEach
    public void beforeEach() {
        this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                .map(IEditingContextEventProcessor::getEditingContextId)
                .forEach(this.editingContextEventProcessorRegistry::disposeEditingContextEventProcessor);
    }

    @Test
    @DisplayName("Given a semantic object, when we subscribe to a form representation, then the form is sent")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSemanticObjectWhenSubscribeToFormRepresentationThenFormIsSent() {
        this.commitInitializeStateBeforeThreadSwitching();

        var input = new FormEventInput(UUID.randomUUID(), TestIdentifiers.SAMPLE_STUDIO_INSTANCE_PROJECT.toString(), TestIdentifiers.HUMAN_FORM_REPRESENTATION.toString());
        var flux = this.formEventSubscriptionRunner.run(input);

        Consumer<Object> formContentConsumer = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresent(form -> {
                    assertThat(form.getLabel()).isEqualTo("Human Form");
                });

        StepVerifier.create(flux)
                .consumeNextWith(formContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Given a semantic object, when we subscribe to its properties events, then the form is sent")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSemanticObjectWhenWeSubscribeToItsPropertiesEventsThenTheFormIsSent() {
        var input = new PropertiesEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), List.of(TestIdentifiers.EPACKAGE_OBJECT.toString()));
        var flux = this.propertiesEventSubscriptionRunner.run(input);

        Predicate<Object> formContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(formContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a master / details based form representation, when we edit the master part, then the details part is updated")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenMasterDetailsBasedFormRepresentationWhenWeEditTheMasterPartThenTheDetailsPartIsUpdated() {
        this.commitInitializeStateBeforeThreadSwitching();

        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                MasterDetailsFormDescriptionProvider.DESCRIPTION_ID,
                TestIdentifiers.EPACKAGE_OBJECT.toString(),
                "Master Details Form"
        );
        var result = this.createRepresentationMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        String typename = JsonPath.read(result, "$.data.createRepresentation.__typename");
        assertThat(typename).isEqualTo(CreateRepresentationSuccessPayload.class.getSimpleName());

        String representationId = JsonPath.read(result, "$.data.createRepresentation.representation.id");
        assertThat(representationId).isNotNull();

        var formEventInput = new FormEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), representationId);
        var flux = this.formEventSubscriptionRunner.run(formEventInput);

        AtomicReference<String> selectId = new AtomicReference<>("");

        Predicate<Object> initialFormContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .filter(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var richText = groupNavigator.findWidget("RichText", RichText.class);
                    assertThat(richText).hasValue("first");

                    var select = groupNavigator.findWidget("Select", Select.class);
                    selectId.set(select.getId());

                    return true;
                })
                .isPresent();

        Runnable changeMasterValue = () -> {
            var editSelectInput = new EditSelectInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), representationId, selectId.get(), "second");

            var editSelectResult = this.editSelectMutationRunner.run(editSelectInput);
            String editSelectResultTypename = JsonPath.read(editSelectResult, "$.data.editSelect.__typename");
            assertThat(editSelectResultTypename).isEqualTo(SuccessPayload.class.getSimpleName());

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        };

        Predicate<Object> updatedFormContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .filter(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var richText = groupNavigator.findWidget("RichText", RichText.class);
                    assertThat(richText).hasValue("second");

                    return true;
                })
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(initialFormContentMatcher)
                .then(changeMasterValue)
                .expectNextMatches(updatedFormContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a view based form description, when form variables are initialized, then widgets can read their value")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenViewBasedFormDescriptionWhenFormVariablesAreInitializedThenWidgetsCanReadTheirValue() {
        this.commitInitializeStateBeforeThreadSwitching();

        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                FormVariableViewPreEditingContextProcessor.REPRESENTATION_DESCRIPTION_ID,
                TestIdentifiers.DOMAIN_OBJECT.toString(),
                "Shared Variables Form"
        );
        var result = this.createRepresentationMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        String typename = JsonPath.read(result, "$.data.createRepresentation.__typename");
        assertThat(typename).isEqualTo(CreateRepresentationSuccessPayload.class.getSimpleName());

        String representationId = JsonPath.read(result, "$.data.createRepresentation.representation.id");
        assertThat(representationId).isNotNull();

        var formEventInput = new FormEventInput(UUID.randomUUID(), TestIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), representationId);
        var flux = this.formEventSubscriptionRunner.run(formEventInput);

        Predicate<Object> initialFormContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .filter(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var listWidget = groupNavigator.findWidget("List", org.eclipse.sirius.components.forms.List.class);
                    assertThat(listWidget.getItems()).hasSize(1);

                    var treeWidget = groupNavigator.findWidget("Tree", TreeWidget.class);
                    assertThat(treeWidget).hasCheckedTreeItemsSize(1);
                    return true;
                })
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(initialFormContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    /**
     * Used to commit the state of the transaction after its initialization by the @Sql annotation
     * in order to make the state persisted in the database. Without this, the initialized state
     * will not be visible by the various repositories when the test will switch threads to use
     * the thread of the editing context.
     *
     * This should not be used every single time but only in the couple integrations tests that are
     * required to interact with repositories while inside an editing context event handler or a
     * representation event handler for example.
     */
    private void commitInitializeStateBeforeThreadSwitching() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }
}
