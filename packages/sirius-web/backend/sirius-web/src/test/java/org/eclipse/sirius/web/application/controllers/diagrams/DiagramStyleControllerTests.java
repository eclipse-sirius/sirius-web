/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import static org.eclipse.sirius.components.forms.tests.FormEventPayloadConsumer.assertRefreshedFormThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.components.collaborative.dto.CreateChildSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.eclipse.sirius.components.widget.reference.tests.assertions.ReferenceWidgetAssertions;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.CreateChildMutationRunner;
import org.eclipse.sirius.web.tests.graphql.DetailsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram style, when we subscribe to its properties events, then the form contains background property")
    public void givenDiagramStyleObjectWhenWeSubscribeToItsPropertiesEventsThenTheFormContainsBackgroundProperty() {
        var detailRepresentationId = this.representationIdBuilder.buildDetailsRepresentationId(List.of(StudioIdentifiers.DIAGRAM_STYLE_DESCRIPTION_OBJECT.toString()));
        var input = new DetailsEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, detailRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(input)
                .flux()
                .filter(FormRefreshedEventPayload.class::isInstance);

        Consumer<Object> formContentMatcher = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("DiagramStyleDescription").group("Core Properties");

            var backgroundReferenceWidget = groupNavigator.findWidget("Background", ReferenceWidget.class);
            ReferenceWidgetAssertions.assertThat(backgroundReferenceWidget).hasValueWithLabel("White");
        });

        StepVerifier.create(flux)
                .consumeNextWith(formContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a view based diagram, when diagram is using conditional style, then the style is computed properly")
    public void givenViewBasedDiagramWhenDiagramIsUsingConditionalStyleThenTheStyleIsComputedProperly() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.INSTANCE_EDITING_CONTEXT_ID, StudioIdentifiers.DIAGRAM_DESCRIPTION_ID, StudioIdentifiers.ROOT_OBJECT.toString(), "");
        var flux = this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();

        Consumer<Object> initialDiagramStyleConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> assertThat(diagram.getStyle())
                        .isNotNull()
                        .matches(style -> style.getBackground().equals("#FFFFFF")), () -> fail("Missing style background"));

        Consumer<Object> conditonnalDiagramStyleConsumer = payload -> Optional.of(payload)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> assertThat(diagram.getStyle())
                        .isNotNull()
                        .matches(style -> style.getBackground().equals("#000000")), () -> fail("Missing style background"));

        Runnable runnable = () -> {
            var createChildInput = new CreateChildInput(
                    UUID.randomUUID(),
                    StudioIdentifiers.INSTANCE_EDITING_CONTEXT_ID,
                    StudioIdentifiers.ROOT_OBJECT.toString(),
                    "humans-Human"
            );
            var result = this.createChildMutationRunner.run(createChildInput);
            String typename = JsonPath.read(result.data(), "$.data.createChild.__typename");
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
