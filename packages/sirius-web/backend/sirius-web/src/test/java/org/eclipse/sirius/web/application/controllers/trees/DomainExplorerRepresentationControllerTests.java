/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.trees;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.trees.tests.TreeEventPayloadConsumer.assertRefreshedTreeThat;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.tree.DomainExplorerRepresentationDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.test.StepVerifier;

/**
 * Integration tests of the explorer representation for Domain model.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DomainExplorerRepresentationControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an domain explorer representation, when we subscribe to its event, then the representation data are received")
    public void givenAnDomainExplorerRepresentationWhenWeSubscribeToItsEventThenTheRepresentationDataAreReceived() {
        var representationId = new RepresentationIdBuilder().buildExplorerRepresentationId(DomainExplorerRepresentationDescriptionProvider.DESCRIPTION_ID, List.of(), List.of());
        var defaultExplorerInput = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, representationId);
        var defaultFlux = this.explorerEventSubscriptionRunner.run(defaultExplorerInput).flux();
        var defaultTreeId = new AtomicReference<String>();

        Consumer<Object> initialDefaultExplorerContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(DomainExplorerRepresentationDescriptionProvider.DESCRIPTION_ID);
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren()).allSatisfy(treeItem -> assertThat(treeItem.getChildren()).isEmpty());

            defaultTreeId.set(tree.getId());
        });

        StepVerifier.create(defaultFlux)
                .consumeNextWith(initialDefaultExplorerContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

    }

}
