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

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ExplorerDescriptionsQueryRunner;
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
 * Integration tests of the tree item label description controllers.
 *
 * @author Jerome Gout
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class TreeItemLabelDescriptionControllerTests extends AbstractIntegrationTests {

    private static final String ROOT_ENTITY_ID = "c341bf91-d315-4264-9787-c51b121a6375";

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @Autowired
    private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

    @Autowired
    private ExplorerDescriptionsQueryRunner explorerDescriptionsQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the tree item labels are requested, then the correct styles are returned")
    public void givenAStudioWhenTheTreeItemLabelsAreRequestedThenTheCorrectStylesAreReturned() {
        // 1- retrieve the tree description id of the DSL Domain explorer example
        var explorerDescriptionId = new AtomicReference<String>();

        Map<String, Object> explorerVariables = Map.of(
                "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString()
        );
        var explorerResult = this.explorerDescriptionsQueryRunner.run(explorerVariables);
        List<String> explorerIds = JsonPath.read(explorerResult.data(), "$.data.viewer.editingContext.explorerDescriptions[*].id");
        assertThat(explorerIds).isNotEmpty()
                .hasSize(2);
        assertThat(explorerIds.get(0)).isEqualTo(ExplorerDescriptionProvider.DESCRIPTION_ID);
        assertThat(explorerIds.get(1)).startsWith("siriusComponents://representationDescription?kind=treeDescription");
        explorerDescriptionId.set(explorerIds.get(1));

        var explorerRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(explorerDescriptionId.get(),
                List.of(StudioIdentifiers.DOMAIN_DOCUMENT.toString(), StudioIdentifiers.DOMAIN_OBJECT.toString(), ROOT_ENTITY_ID), List.of());

        // 2- check that styles are properly applied
        //      - check that the ROOT entity has not the abstract style applied.
        //      - check that the NamedElement entity has the abstract style (if style)
        //      - check that the Human entity has 3 attributes (for style)
        var inputStyle = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), explorerRepresentationId);
        var fluxStyle = this.explorerEventSubscriptionRunner.run(inputStyle).flux();

        Consumer<Object> styleTreeContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            List<TreeItem> domainChildren = tree.getChildren()
                    .get(0)
                    .getChildren()
                    .get(0)
                    .getChildren();
            var rootLabels = domainChildren.get(0).getLabel().styledStringFragments();
            var namedElementLabels = domainChildren.get(1).getLabel().styledStringFragments();
            var humanLabels = domainChildren.get(2).getLabel().styledStringFragments();

            assertThat(rootLabels.get(rootLabels.size() - 1).text()).doesNotContain("[abstract]");
            assertThat(namedElementLabels.get(namedElementLabels.size() - 1).text()).contains("[abstract]");
            assertThat(humanLabels).hasSize(9);
            assertThat(humanLabels.get(3).text()).isEqualTo("description");
            assertThat(humanLabels.get(5).text()).isEqualTo("promoted");
            assertThat(humanLabels.get(7).text()).isEqualTo("birthDate");
        });

        StepVerifier.create(fluxStyle)
                .consumeNextWith(styleTreeContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(30));
    }
}
