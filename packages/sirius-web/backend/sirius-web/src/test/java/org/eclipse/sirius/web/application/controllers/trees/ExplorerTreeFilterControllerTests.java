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
package org.eclipse.sirius.web.application.controllers.trees;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.trees.tests.TreeEventPayloadConsumer.assertRefreshedTreeThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.trees.tests.graphql.TreeFiltersQueryRunner;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.studio.services.StudioExplorerTreeFilterProvider;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
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
 * Integration tests of the tree controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExplorerTreeFilterControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ExplorerEventSubscriptionRunner treeEventSubscriptionRunner;

    @Autowired
    private TreeFiltersQueryRunner treeFiltersQueryRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a tree id, when we request its tree filters, then the list is returned")
    public void givenTreeIdWhenWeRequestItsTreeFiltersThenTheListIsReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(),
                "representationId", ExplorerDescriptionProvider.PREFIX
        );
        var result = this.treeFiltersQueryRunner.run(variables);

        List<String> treeFilterIds = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.filters[*].id");
        assertThat(treeFilterIds)
                .isNotEmpty()
                .anySatisfy(treeFilterId -> assertThat(treeFilterId).isEqualTo(StudioExplorerTreeFilterProvider.HIDE_STUDIO_COLOR_PALETTES_TREE_FILTER_ID));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the filter to hide the default color palette is active, then the default color palette is hidden")
    public void givenStudioWhenFilterToHideDefaultColorPaletteIsActiveThenTheDefaultColorPaletteIsHidden() {
        var treeRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(), List.of(StudioExplorerTreeFilterProvider.HIDE_STUDIO_COLOR_PALETTES_TREE_FILTER_ID));
        var input = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), treeRepresentationId);
        var flux = this.treeEventSubscriptionRunner.run(input);

        Consumer<Object> projectContentConsumer = assertRefreshedTreeThat(tree -> assertThat(tree.getChildren()).isEmpty());

        StepVerifier.create(flux)
                .consumeNextWith(projectContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the filter to show the default color palette is inactive, then the default color palette is visible")
    public void givenStudioWhenFilterToHideDefaultColorPaletteIsInactiveThenTheDefaultColorPaletteIsHidden() {
        var treeRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(), List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), treeRepresentationId);
        var flux = this.treeEventSubscriptionRunner.run(input);

        var defaultPaletteTreeItemId = ColorPaletteService.SIRIUS_STUDIO_COLOR_PALETTES_URI.substring((IEMFEditingContext.RESOURCE_SCHEME + ":///").length());

        Consumer<Object> projectContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree.getChildren())
                    .hasSize(1)
                    .allSatisfy(treeItem -> assertThat(treeItem.getId()).isEqualTo(defaultPaletteTreeItemId));
        });

        StepVerifier.create(flux)
                .consumeNextWith(projectContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
