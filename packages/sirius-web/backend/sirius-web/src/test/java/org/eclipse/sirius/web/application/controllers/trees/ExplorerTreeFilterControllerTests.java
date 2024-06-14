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
package org.eclipse.sirius.web.application.controllers.trees;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.trees.dto.TreeEventInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.trees.tests.graphql.TreeEventSubscriptionRunner;
import org.eclipse.sirius.components.trees.tests.graphql.TreeFiltersQueryRunner;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.studio.services.StudioExplorerTreeFilterProvider;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
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
    private TreeEventSubscriptionRunner treeEventSubscriptionRunner;

    @Autowired
    private TreeFiltersQueryRunner treeFiltersQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a tree id, when we request its tree filters, then the list is returned")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenTreeIdWhenWeRequestItsTreeFiltersThenTheListIsReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString(),
                "representationId", ExplorerDescriptionProvider.PREFIX
        );
        var result = this.treeFiltersQueryRunner.run(variables);

        List<String> treeFilterIds = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.filters[*].id");
        assertThat(treeFilterIds)
                .isNotEmpty()
                .anySatisfy(treeFilterId -> assertThat(treeFilterId).isEqualTo(StudioExplorerTreeFilterProvider.HIDE_STUDIO_COLOR_PALETTES_TREE_FILTER_ID));
    }

    @Test
    @DisplayName("Given a studio, when the filter to hide the default palette is active, then the default palette is hidden")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenStudioWhenFilterToHideDefaultPaletteIsActiveThenTheDefaultPaletteIsHidden() {
        var input = new TreeEventInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString(), ExplorerDescriptionProvider.PREFIX, List.of(), List.of(StudioExplorerTreeFilterProvider.HIDE_STUDIO_COLOR_PALETTES_TREE_FILTER_ID));
        var flux = this.treeEventSubscriptionRunner.run(input);

        Consumer<Object> projectContentConsumer = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(TreeRefreshedEventPayload.class::isInstance)
                .map(TreeRefreshedEventPayload.class::cast)
                .map(TreeRefreshedEventPayload::tree)
                .ifPresentOrElse(tree -> {
                    assertThat(tree.getChildren()).isEmpty();
                }, () -> fail("Missing tree"));

        StepVerifier.create(flux)
                .consumeNextWith(projectContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a studio, when the filter to show the default palette is inactive, then the default palette is visible")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenStudioWhenFilterToHideDefaultPaletteIsInactiveThenTheDefaultPaletteIsHidden() {
        var input = new TreeEventInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString(), ExplorerDescriptionProvider.PREFIX, List.of(), List.of());
        var flux = this.treeEventSubscriptionRunner.run(input);

        var defaultPaletteTreeItemId = ColorPaletteService.SIRIUS_STUDIO_COLOR_PALETTES_URI.substring((IEMFEditingContext.RESOURCE_SCHEME + ":///").length());

        Consumer<Object> projectContentConsumer = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(TreeRefreshedEventPayload.class::isInstance)
                .map(TreeRefreshedEventPayload.class::cast)
                .map(TreeRefreshedEventPayload::tree)
                .ifPresentOrElse(tree -> {
                    assertThat(tree.getChildren())
                            .hasSize(1)
                            .allSatisfy(treeItem -> assertThat(treeItem.getId()).isEqualTo(defaultPaletteTreeItemId));
                }, () -> fail("Missing tree"));

        StepVerifier.create(flux)
                .consumeNextWith(projectContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
