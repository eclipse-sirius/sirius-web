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
package org.eclipse.sirius.web.application.controllers.forms;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.tests.assertions.FormAssertions;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.DetailsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.test.StepVerifier;

/**
 * Integration tests of the label style details view.
 *
 * @author frouene
 */

@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LabelStyleDetailsViewControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an InsideLabelStyleDescription, when we subscribe to its properties events, then the form is sent")
    public void givenInsideLabelStyleDescriptionWhenWeSubscribeToItsPropertiesEventsThenTheFormIsSent() {
        var detailRepresentationId = representationIdBuilder.buildDetailsRepresentationId(List.of(StudioIdentifiers.HUMAN_INSIDE_LABEL_STYLE_OBJECT.toString()));
        var input = new DetailsEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), detailRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(input);

        Predicate<Form> formPredicate = form -> {
            var groupNavigator = new FormNavigator(form).page("").group("Core Properties");

            var borderSizeTextField = groupNavigator.findWidget("Border Size", Textfield.class);
            FormAssertions.assertThat(borderSizeTextField).isNotNull();

            var backgroundReferenceWidget = groupNavigator.findWidget("Background", ReferenceWidget.class);
            FormAssertions.assertThat(backgroundReferenceWidget).isNotNull();

            var borderColorReferenceWidget = groupNavigator.findWidget("Border Color", ReferenceWidget.class);
            FormAssertions.assertThat(borderColorReferenceWidget).isNotNull();

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

}

