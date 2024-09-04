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
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.components.collaborative.dto.CreateChildSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.tests.assertions.FormAssertions;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.graphql.CreateChildMutationRunner;
import org.eclipse.sirius.web.tests.graphql.DetailsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
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
import reactor.test.StepVerifier;

/**
 * Integration tests of the "Diagram Style" view.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DiagramStyleControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private CreateChildMutationRunner createChildMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a diagram style, when we subscribe to its properties events, then the form contains background property")
    @Sql(scripts = { "/scripts/studio.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramStyleObjectWhenWeSubscribeToItsPropertiesEventsThenTheFormContainsBackgroundProperty() {
        var input = new DetailsEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), List.of(StudioIdentifiers.DIAGRAM_STYLE_DESCRIPTION_OBJECT.toString()));
        var flux = this.detailsEventSubscriptionRunner.run(input);

        Predicate<Form> formPredicate = form -> {
            var groupNavigator = new FormNavigator(form).page("").group("Core Properties");

            var backgroundReferenceWidget = groupNavigator.findWidget("Background", ReferenceWidget.class);
            FormAssertions.assertThat(backgroundReferenceWidget).hasValueWithLabel("White");

            return true;
        };

        Predicate<Object> formContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .filter(formPredicate)
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(formContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a view based diagram, when diagram is using conditional style, then the style is computed properly")
    @Sql(scripts = { "/scripts/studio.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenViewBasedDiagramWhenDiagramIsUsingConditionalStyleThenTheStyleIsComputedProperly() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.INSTANCE_PROJECT.toString(), StudioIdentifiers.DIAGRAM_DESCRIPTION_ID, StudioIdentifiers.ROOT_OBJECT.toString(), "");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input);

        Consumer<Object> initialDiagramStyleConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getStyle())
                            .isNotNull()
                            .matches(style -> style.getBackground().equals("#FFFFFF"));
                }, () -> fail("Missing style background"));

        Consumer<Object> conditonnalDiagramStyleConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    assertThat(diagram.getStyle())
                            .isNotNull()
                            .matches(style -> style.getBackground().equals("#000000"));
                }, () -> fail("Missing style background"));

        Runnable runnable = () -> {
            var createChildInput = new CreateChildInput(
                    UUID.randomUUID(),
                    StudioIdentifiers.INSTANCE_PROJECT.toString(),
                    StudioIdentifiers.ROOT_OBJECT.toString(),
                    "humans-Human"
            );
            var result = this.createChildMutationRunner.run(createChildInput);
            String typename = JsonPath.read(result, "$.data.createChild.__typename");
            assertThat(typename).isEqualTo(CreateChildSuccessPayload.class.getSimpleName());
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramStyleConsumer)
                .then(runnable)
                .consumeNextWith(conditonnalDiagramStyleConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
