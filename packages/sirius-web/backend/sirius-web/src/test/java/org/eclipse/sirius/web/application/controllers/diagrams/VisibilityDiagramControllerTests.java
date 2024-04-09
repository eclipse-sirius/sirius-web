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
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.services.diagrams.VisibilityDiagramDescriptionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the hide / fade / reveal diagrams.
 *
 * @author gdaniel
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class VisibilityDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private VisibilityDiagramDescriptionProvider visibilityDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<DiagramRefreshedEventPayload> givenSubscriptionToVisibilityDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                this.visibilityDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.ROOT_OBJECT.toString(),
                "VisibilityDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a diagram with hidden and faded nodes by default, when it is opened, then some nodes are hidden and faded")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramWithHiddenAndFadedNodesByDefaultWhenItIsOpenedThenSomeNodesAreHiddenAndFaded() {
        var flux = this.givenSubscriptionToVisibilityDiagram();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {

                    var siriusWebDomainNode = new DiagramNavigator(diagram)
                            .nodeWithLabel("sirius-web-domain")
                            .getNode();
                    assertThat(siriusWebDomainNode).hasModifiers(Set.of(ViewModifier.Hidden));

                    var siriusWebApplicationNode = new DiagramNavigator(diagram)
                            .nodeWithLabel("sirius-web-application")
                            .getNode();
                    assertThat(siriusWebApplicationNode).hasModifiers(Set.of(ViewModifier.Faded));

                    var nonDomainNonApplicationNodes = diagram.getNodes().stream()
                            .filter(node -> !node.getInsideLabel().getText().endsWith("-domain")
                                        && !node.getInsideLabel().getText().endsWith("-application"));
                    assertThat(nonDomainNonApplicationNodes)
                        .isNotEmpty()
                        .allSatisfy(node -> assertThat(node).hasModifiers(Set.of()));
                }, () -> fail("Missing diagram"));

        StepVerifier.create(flux)
            .consumeNextWith(initialDiagramContentConsumer)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }
}
