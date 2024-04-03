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
package org.eclipse.sirius.web.application.controllers.hierarchy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.charts.HierarchyRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.services.hierarchy.GivenCreatedHierarchySubscription;
import org.eclipse.sirius.web.services.hierarchy.HierarchyDescriptionProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
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
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @Autowired
    private GivenCreatedHierarchySubscription givenCreatedHierarchySubscription;

    @BeforeEach
    public void beforeEach() {
        this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                .map(IEditingContextEventProcessor::getEditingContextId)
                .forEach(this.editingContextEventProcessorRegistry::disposeEditingContextEventProcessor);
    }

    @Test
    @DisplayName("Given an arbitrary semantic element, then we can create a hierarchy on it")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnArbitrarySemanticElementThenWeCanCreateHierarchyOnIt() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), HierarchyDescriptionProvider.HIERARCHY_DESCRIPTION_ID, TestIdentifiers.EPACKAGE_OBJECT.toString(), "Sample Hierarchy");
        var flux = this.givenCreatedHierarchySubscription.createAndSubscribe(input);

        Consumer<HierarchyRefreshedEventPayload> initialHierarchyContentConsumer = payload -> {
            Optional.of(payload)
                .map(HierarchyRefreshedEventPayload::hierarchy)
                .ifPresentOrElse(hierarchy -> {
                    assertThat(hierarchy.getLabel()).isEqualTo("Sample Hierarchy");
                    assertThat(hierarchy.getChildNodes()).isEmpty();
                }, () -> fail("Missing hierarchy"));
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialHierarchyContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
