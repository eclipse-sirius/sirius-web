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
package org.eclipse.sirius.web.application.controllers.hierarchy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.charts.HierarchyRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.services.hierarchy.GivenCreatedHierarchySubscription;
import org.eclipse.sirius.web.services.hierarchy.HierarchyDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the hierarchy controller.
 *
 * @author pcdavid
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HierarchyLifecycleControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private GivenCreatedHierarchySubscription givenCreatedHierarchySubscription;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an arbitrary semantic element, then we can create a hierarchy on it")
    public void givenAnArbitrarySemanticElementThenWeCanCreateHierarchyOnIt() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), HierarchyDescriptionProvider.HIERARCHY_DESCRIPTION_ID, TestIdentifiers.EPACKAGE_OBJECT.toString(), "Sample Hierarchy");
        var flux = this.givenCreatedHierarchySubscription.createAndSubscribe(input);

        Consumer<Object> initialHierarchyContentConsumer = payload -> {
            Optional.of(payload)
                .filter(HierarchyRefreshedEventPayload.class::isInstance)
                .map(HierarchyRefreshedEventPayload.class::cast)
                .map(HierarchyRefreshedEventPayload::hierarchy)
                .ifPresentOrElse(hierarchy -> {
                    assertThat(hierarchy.getChildNodes()).isNotEmpty();
                }, () -> fail("Missing hierarchy"));
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialHierarchyContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
