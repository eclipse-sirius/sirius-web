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
package org.eclipse.sirius.web.application.controllers.selection;

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
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionEventInput;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.tests.graphql.EditTextfieldMutationRunner;
import org.eclipse.sirius.components.forms.tests.graphql.PropertiesEventSubscriptionRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.selection.SelectionDescriptionProvider;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SelectionControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String GET_SELECTION_EVENT_SUBSCRIPTION = """
            subscription selectionEvent($input: SelectionEventInput!) {
              selectionEvent(input: $input) {
                __typename
                ... on SelectionRefreshedEventPayload {
                  selection {
                    objects {
                      id
                      label
                      iconURL
                    }
                  }
                }
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
    @DisplayName("Given a semantic object, when we subscribe to its selection events, then the selection is sent")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSemanticObjectWhenWeSubscribeToItsSelectionEventsThenTheSelectionIsSent() {
        var input = new SelectionEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), SelectionDescriptionProvider.REPRESENTATION_DESCRIPTION_ID, PapayaIdentifiers.PROJECT_OBJECT.toString());
        var flux = this.graphQLRequestor.subscribe(GET_SELECTION_EVENT_SUBSCRIPTION, input)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(SelectionRefreshedEventPayload.class::isInstance)
                .map(SelectionRefreshedEventPayload.class::cast);

        Consumer<SelectionRefreshedEventPayload> selectionContentConsumer = payload -> Optional.of(payload)
                .map(SelectionRefreshedEventPayload::selection)
                .ifPresentOrElse(selection -> {
                    assertThat(selection.getObjects()).hasSizeGreaterThanOrEqualTo(5);
                }, () -> fail("Missing selection"));

        StepVerifier.create(flux)
                .consumeNextWith(selectionContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a selection representation, when we edit the details of an object, then the selection representation is updated")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSelectionRepresentationWhenWeEditTheDetailsOfAnObjectThenTheSelectionRepresentationIsUpdated() {
        var selectionEventInput = new SelectionEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), SelectionDescriptionProvider.REPRESENTATION_DESCRIPTION_ID, PapayaIdentifiers.PROJECT_OBJECT.toString());
        var selectionFlux = this.graphQLRequestor.subscribe(GET_SELECTION_EVENT_SUBSCRIPTION, selectionEventInput);

        var detailsEventInput = new PropertiesEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), List.of(PapayaIdentifiers.SIRIUS_WEB_DOMAIN_OBJECT.toString()));
        var detailsFlux = this.propertiesEventSubscriptionRunner.run(detailsEventInput);

        var formId = new AtomicReference<String>();
        var textareaId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = payload -> Optional.of(payload)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    formId.set(form.getId());

                    var groupNavigator = new FormNavigator(form).page("Component sirius-web-domain").group("Core Properties");
                    var textarea = groupNavigator.findWidget("Name", Textarea.class);

                    textareaId.set(textarea.getId());
                }, () -> fail("Missing form"));

        Consumer<Object> selectionContentConsumer = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(SelectionRefreshedEventPayload.class::isInstance)
                .map(SelectionRefreshedEventPayload.class::cast)
                .map(SelectionRefreshedEventPayload::selection)
                .ifPresentOrElse(selection -> {
                    assertThat(selection.getObjects())
                            .isNotEmpty()
                            .anyMatch(selectionObject -> selectionObject.getLabel().contains("sirius-web-domain"));
                }, () -> fail("Missing selection"));

        Runnable editTextfield = () -> {
            var input = new EditTextfieldInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), formId.get(), textareaId.get(), "sirius-web-domain-EDITED");
            var result = this.editTextfieldMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editTextfield.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedFormContentConsumer = payload -> Optional.of(payload)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Component sirius-web-domain-EDITED").group("Core Properties");
                    var textarea = groupNavigator.findWidget("Name", Textarea.class);

                    assertThat(textarea).hasValue("sirius-web-domain-EDITED");
                }, () -> fail("Missing form"));

        Consumer<Object> updatedSelectionContentConsumer = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(SelectionRefreshedEventPayload.class::isInstance)
                .map(SelectionRefreshedEventPayload.class::cast)
                .map(SelectionRefreshedEventPayload::selection)
                .ifPresentOrElse(selection -> {
                    assertThat(selection.getObjects())
                            .isNotEmpty()
                            .anyMatch(selectionObject -> selectionObject.getLabel().contains("sirius-web-domain-EDITED"));
                }, () -> fail("Missing selection"));

        StepVerifier.create(Flux.merge(selectionFlux, detailsFlux))
                .consumeNextWith(selectionContentConsumer)
                .consumeNextWith(initialFormContentConsumer)
                .then(editTextfield)
                .consumeNextWith(updatedFormContentConsumer)
                .consumeNextWith(updatedSelectionContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a semantic object, when we subscribe to its selection events, then the URL of its objects is valid")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSemanticObjectWhenWeSubscribeToItsSelectionEventsThenTheURLOfItsObjectsIsValid() {
        var input = new SelectionEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), SelectionDescriptionProvider.REPRESENTATION_DESCRIPTION_ID, PapayaIdentifiers.PROJECT_OBJECT.toString());
        var flux = this.graphQLRequestor.subscribeToSpecification(GET_SELECTION_EVENT_SUBSCRIPTION, input);

        Consumer<String> selectionContentConsumer = payload -> Optional.of(payload)
                .ifPresentOrElse(body -> {
                    String typename = JsonPath.read(body, "$.data.selectionEvent.__typename");
                    assertThat(typename).isEqualTo(SelectionRefreshedEventPayload.class.getSimpleName());

                    List<List<String>> objectIconURLs = JsonPath.read(body, "$.data.selectionEvent.selection.objects[*].iconURL");
                    assertThat(objectIconURLs)
                            .isNotEmpty()
                            .allSatisfy(iconURLs -> {
                                assertThat(iconURLs)
                                        .isNotEmpty()
                                        .hasSize(1)
                                        .allSatisfy(iconURL -> assertThat(iconURL).startsWith(URLConstants.IMAGE_BASE_PATH));
                            });
                }, () -> fail("Missing selection"));

        StepVerifier.create(flux)
                .consumeNextWith(selectionContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
