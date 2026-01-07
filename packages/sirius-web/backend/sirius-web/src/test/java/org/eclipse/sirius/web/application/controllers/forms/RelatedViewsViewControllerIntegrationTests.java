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
package org.eclipse.sirius.web.application.controllers.forms;

import static org.eclipse.sirius.components.forms.tests.FormEventPayloadConsumer.assertRefreshedFormThat;
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.TreeWidget;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.relatedviews.dto.RelatedViewsEventInput;
import org.eclipse.sirius.web.application.views.relatedviews.services.RelatedViewsFormDescriptionProvider;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.RelatedViewsEventSubscriptionRunner;
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
 * Integration tests of the representations view.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RelatedViewsViewControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private RelatedViewsEventSubscriptionRunner relatedViewsEventSubscriptionRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a semantic object associated with representations, when we subscribe to its related views events, then the form sent contains the list widget listing the associated representations")
    public void givenSemanticObjectAssociatedWithRepresentationsWhenWeSubscribeToItsRelatedViewsEventsThenTheFormSentContainsTheListWidgetListingTheAssociatedRepresentations() {
        var representationId = this.representationIdBuilder.buildRepresentationViewRepresentationId(List.of(TestIdentifiers.EPACKAGE_OBJECT.toString()));
        var input = new RelatedViewsEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, representationId);
        var flux = this.relatedViewsEventSubscriptionRunner.run(input)
                .flux()
                .filter(FormRefreshedEventPayload.class::isInstance);

        Consumer<Object> initialformContentConsumer = assertRefreshedFormThat(form -> {
            var formNavigator = new FormNavigator(form);
            var listWidget = formNavigator.page(RelatedViewsFormDescriptionProvider.PAGE_LABEL)
                    .group(RelatedViewsFormDescriptionProvider.GROUP_LABEL)
                    .findWidget("Related Views", org.eclipse.sirius.components.forms.List.class);
            assertThat(listWidget).hasListItemWithLabel("Portal");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialformContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a portal containing another portal, when we subscribe to its related views events, then the form sent contains the tree widget containing at least the portal and the other portal as a child")
    public void givenPortalContainingAnotherPortalWhenWeSubscribeToItsRelatedViewsEventsThenTheFormSentContainsTheTreeWidgetContainingAtLeastThePortalAndTheOtherPortalAsAChild() {
        var representationId = this.representationIdBuilder.buildRepresentationViewRepresentationId(List.of(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString()));
        var input = new RelatedViewsEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, representationId);
        var flux = this.relatedViewsEventSubscriptionRunner.run(input)
                .flux()
                .filter(FormRefreshedEventPayload.class::isInstance);

        Consumer<Object> initialformContentConsumer = assertRefreshedFormThat(form -> {
            var formNavigator = new FormNavigator(form);
            var treeWidget = formNavigator.page(RelatedViewsFormDescriptionProvider.PAGE_LABEL)
                    .group(RelatedViewsFormDescriptionProvider.GROUP_LABEL)
                    .findWidget("Portal contents", TreeWidget.class);
            assertThat(treeWidget).hasTreeItemWithLabel("Portal");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialformContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
