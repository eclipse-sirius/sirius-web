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
package org.eclipse.sirius.web.application.controllers.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.forms.dto.EditTextfieldInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.PropertiesEventInput;
import org.eclipse.sirius.components.collaborative.validation.dto.ValidationEventInput;
import org.eclipse.sirius.components.collaborative.validation.dto.ValidationRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.tests.graphql.EditTextfieldMutationRunner;
import org.eclipse.sirius.components.forms.tests.graphql.PropertiesEventSubscriptionRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the selection controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.enabled=validation" })
public class ValidationControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String GET_VALIDATION_EVENT_SUBSCRIPTION = """
            subscription validationEvent($input: ValidationEventInput!) {
              validationEvent(input: $input) {
                __typename
              }
            }
            """;

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private PropertiesEventSubscriptionRunner propertiesEventSubscriptionRunner;

    @Autowired
    private EditTextfieldMutationRunner editTextfieldMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given an editing context, when we subscribe to its validation events, then the validation data are sent")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnEditingContextWhenWeSubscribeToItsValidationEventsThenTheValidationDataAreSent() {
        var input = new ValidationEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString());
        var flux = this.graphQLRequestor.subscribe(GET_VALIDATION_EVENT_SUBSCRIPTION, input)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(ValidationRefreshedEventPayload.class::isInstance)
                .map(ValidationRefreshedEventPayload.class::cast);

        Consumer<ValidationRefreshedEventPayload> validationContentConsumer = payload -> Optional.of(payload)
                .map(ValidationRefreshedEventPayload::validation)
                .ifPresentOrElse(validation -> {
                    assertThat(validation).isNotNull();
                }, () -> fail("Missing validation"));

        StepVerifier.create(flux)
                .consumeNextWith(validationContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a validation representation, when we edit the details of an object, then its validation status is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenValidationRepresentationWhenWeEditTheDetailsOfAnObjectThenItsValidationStatusIsUpdated() {
        var validationEventInput = new ValidationEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString());
        var validationFlux = this.graphQLRequestor.subscribe(GET_VALIDATION_EVENT_SUBSCRIPTION, validationEventInput);

        var detailsEventInput = new PropertiesEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), List.of(StudioIdentifiers.DIAGRAM_DESCRIPTION_OBJECT.toString()));
        var detailsFlux = this.propertiesEventSubscriptionRunner.run(detailsEventInput);

        var formId = new AtomicReference<String>();
        var textareaId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    formId.set(form.getId());

                    var groupNavigator = new FormNavigator(form).page("Root Diagram").group("Core Properties");
                    var textarea = groupNavigator.findWidget("Domain Type", Textarea.class);

                    textareaId.set(textarea.getId());
                }, () -> fail("Missing form"));

        Consumer<Object> noDiagramDescriptionErrorValidationContentConsumer = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(ValidationRefreshedEventPayload.class::isInstance)
                .map(ValidationRefreshedEventPayload.class::cast)
                .map(ValidationRefreshedEventPayload::validation)
                .ifPresentOrElse(validation -> {
                    assertThat(validation.getDiagnostics())
                            .isNotEmpty()
                            .noneMatch(diagnostic -> diagnostic.getMessage().startsWith("The diagram description") && diagnostic.getMessage().endsWith("does not have a valid domain class"));
                }, () -> fail("Missing validation"));

        Runnable editTextfield = () -> {
            var input = new EditTextfieldInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), formId.get(), textareaId.get(), "");
            var result = this.editTextfieldMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editTextfield.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedFormContentConsumer = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Root Diagram").group("Core Properties");
                    var textarea = groupNavigator.findWidget("Domain Type", Textarea.class);

                    assertThat(textarea).hasValue("");
                }, () -> fail("Missing form"));

        Consumer<Object> diagramDescriptionErrorValidationContentConsumer = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(ValidationRefreshedEventPayload.class::isInstance)
                .map(ValidationRefreshedEventPayload.class::cast)
                .map(ValidationRefreshedEventPayload::validation)
                .ifPresentOrElse(validation -> {
                    assertThat(validation.getDiagnostics())
                            .isNotEmpty()
                            .anyMatch(diagnostic -> diagnostic.getMessage().startsWith("The diagram description") && diagnostic.getMessage().endsWith("does not have a valid domain class"));
                }, () -> fail("Missing validation"));

        StepVerifier.create(Flux.merge(validationFlux, detailsFlux))
                .consumeNextWith(noDiagramDescriptionErrorValidationContentConsumer)
                .consumeNextWith(initialFormContentConsumer)
                .then(editTextfield)
                .consumeNextWith(updatedFormContentConsumer)
                .consumeNextWith(diagramDescriptionErrorValidationContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
