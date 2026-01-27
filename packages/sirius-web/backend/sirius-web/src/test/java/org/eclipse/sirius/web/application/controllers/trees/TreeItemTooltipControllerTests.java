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
package org.eclipse.sirius.web.application.controllers.trees;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.trees.tests.TreeEventPayloadConsumer.assertRefreshedTreeThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ExplorerDescriptionsQueryRunner;
import org.eclipse.sirius.web.tests.graphql.TreeItemTooltipQueryRunner;
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
 * Integration tests of the tree item tooltip controllers.
 *
 * @author gdaniel
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class TreeItemTooltipControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @Autowired
    private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

    @Autowired
    private TreeItemTooltipQueryRunner treeItemTooltipQueryRunner;

    @Autowired
    private ExplorerDescriptionsQueryRunner explorerDescriptionsQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a Papaya project, when the tooltip is requested on an object, then the tooltip is returned")
    public void givenAPapayaProjectWhenTooltipIsRequestedOnObjectThenTooltipIsReturned() {
        var expandedItemIds = List.of(
                PapayaIdentifiers.PAPAYA_SIRIUS_WEB_ARCHITECTURE_DOCUMENT.toString(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_OBJECT.toString(),
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_PACKAGE.toString()
        );
        var explorerRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, expandedItemIds, List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), explorerRepresentationId);
        var flux = this.explorerEventSubscriptionRunner.run(input).flux();

        var treeId = new AtomicReference<String>();
        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> treeId.set(tree.getId()));

        Runnable getObjectTooltip = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", treeId.get(),
                    "treeItemId", PapayaIdentifiers.PROJECT_OBJECT.toString()
            );
            var result = this.treeItemTooltipQueryRunner.run(variables);

            String tooltip = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.treeItemTooltip");
            assertThat(tooltip).isEqualTo("papaya::Project");
        };

        Runnable getRepresentationTooltip = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", treeId.get(),
                    "treeItemId", PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION
            );
            var result = this.treeItemTooltipQueryRunner.run(variables);

            String tooltip = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.treeItemTooltip");
            assertThat(tooltip).isEqualTo("Table");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
                .then(getObjectTooltip)
                .then(getRepresentationTooltip)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio with a domain explorer, when the tooltip is requested on an object, then the tooltip is returned")
    public void givenAStudioWithADomainExplorerWhenTooltipIsRequestedOnObjectThenTooltipIsReturned() {
        var explorerDescriptionId = new AtomicReference<String>();

        Map<String, Object> explorerVariables = Map.of(
                "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID
        );
        var explorerResult = this.explorerDescriptionsQueryRunner.run(explorerVariables);
        List<String> explorerIds = JsonPath.read(explorerResult.data(), "$.data.viewer.editingContext.explorerDescriptions[*].id");
        assertThat(explorerIds).isNotEmpty().hasSize(2);
        assertThat(explorerIds.get(0)).isEqualTo(ExplorerDescriptionProvider.DESCRIPTION_ID);
        assertThat(explorerIds.get(1)).startsWith("siriusComponents://representationDescription?kind=treeDescription");
        explorerDescriptionId.set(explorerIds.get(1));

        var explorerRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(explorerDescriptionId.get(), List.of(StudioIdentifiers.DOMAIN_DOCUMENT.toString()), List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, explorerRepresentationId);
        var flux = this.explorerEventSubscriptionRunner.run(input).flux();

        var treeId = new AtomicReference<String>();
        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> treeId.set(tree.getId()));

        Runnable getObjectTooltip = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    "representationId", treeId.get(),
                    "treeItemId", StudioIdentifiers.DOMAIN_OBJECT.toString()
            );
            var result = this.treeItemTooltipQueryRunner.run(variables);

            String tooltip = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.treeItemTooltip");
            assertThat(tooltip).isEqualTo("domain::Domain");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
                .then(getObjectTooltip)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
