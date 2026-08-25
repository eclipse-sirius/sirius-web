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

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.trees.tests.graphql.TreeItemPaletteExecutor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.studio.services.representations.DomainViewTreeDescriptionProvider;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerTreeItemContextMenuEntryProvider;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ExplorerDescriptionsQueryRunner;
import org.eclipse.sirius.web.tests.graphql.FetchTreeItemContextMenuEntryDataQueryRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import reactor.test.StepVerifier;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.trees.tests.TreeEventPayloadConsumer.assertRefreshedTreeThat;
import static org.eclipse.sirius.web.data.StudioIdentifiers.DOMAIN_OBJECT;
import static org.eclipse.sirius.web.data.StudioIdentifiers.DOMAIN_DOCUMENT;
import static org.eclipse.sirius.web.data.StudioIdentifiers.ROOT_ENTITY_OBJECT;
import static org.eclipse.sirius.web.data.StudioIdentifiers.HUMAN_ENTITY_OBJECT;

/**
 * Integration tests of the tree item palette controllers.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class TreeItemPaletteControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private TreeItemPaletteExecutor paletteExecutor;

    @Autowired
    private FetchTreeItemContextMenuEntryDataQueryRunner treeItemFetchContextActionDataQueryRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @Autowired
    private DomainViewTreeDescriptionProvider domainViewTreeDescriptionProvider;

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
    @DisplayName("Given a Papaya project, when the palette is requested on an object, then the correct tools are returned")
    public void givenPapayaProjectWhenThePaletteAreRequestedOnAnObjectThenTheCorrectToolsAreReturned() {
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

        Runnable getDocumentPalette = () -> this.paletteExecutor.execute(
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                treeId.get(),
                PapayaIdentifiers.PAPAYA_SIRIUS_WEB_ARCHITECTURE_DOCUMENT.toString())
                .hasPaletteEntriesIds(entries -> assertThat(entries).containsExactly(ExplorerTreeItemContextMenuEntryProvider.NEW_ROOT_OBJECT, ExplorerTreeItemContextMenuEntryProvider.DOWNLOAD_DOCUMENT, ExplorerTreeItemContextMenuEntryProvider.EXPAND_ALL));

        Runnable getObjectPalette = () -> this.paletteExecutor.execute(
                        PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                        treeId.get(),
                        PapayaIdentifiers.PROJECT_OBJECT.toString())
                .hasPaletteEntriesIds(entries -> assertThat(entries).containsExactly(ExplorerTreeItemContextMenuEntryProvider.NEW_OBJECT, ExplorerTreeItemContextMenuEntryProvider.NEW_REPRESENTATION, ExplorerTreeItemContextMenuEntryProvider.DUPLICATE_OBJECT, ExplorerTreeItemContextMenuEntryProvider.EXPAND_ALL));

        Runnable getRepresentationPalette = () -> this.paletteExecutor.execute(
                        PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                        treeId.get(),
                        PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString())
                .hasPaletteEntriesIds(entries -> assertThat(entries).containsExactly(ExplorerTreeItemContextMenuEntryProvider.DUPLICATE_REPRESENTATION));

        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
                .then(getDocumentPalette)
                .then(getObjectPalette)
                .then(getRepresentationPalette)
                .thenCancel()
                .verify(Duration.ofSeconds(30));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the palette is requested on an Entity, then the correct tools are returned")
    public void givenAStudioWhenThePaletteIsRequestedOnAnEntityThenTheCorrectToolsAreReturned() {
        // 1- retrieve the tree description id of the DSL Domain explorer example
        Map<String, Object> explorerVariables = Map.of("editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID);
        var explorerResult = this.explorerDescriptionsQueryRunner.run(explorerVariables);
        List<String> explorerIds = JsonPath.read(explorerResult.data(), "$.data.viewer.editingContext.explorerDescriptions[*].id");
        assertThat(explorerIds).isNotEmpty().hasSize(2);
        assertThat(explorerIds.get(0)).isEqualTo(ExplorerDescriptionProvider.DESCRIPTION_ID);
        assertThat(explorerIds.get(1)).startsWith("siriusComponents://representationDescription?kind=treeDescription");

        var explorerRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(explorerIds.get(1), List.of(StudioIdentifiers.DOMAIN_DOCUMENT.toString(), StudioIdentifiers.DOMAIN_OBJECT.toString(), StudioIdentifiers.ROOT_ENTITY_OBJECT.toString()), List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, explorerRepresentationId);
        var flux = this.explorerEventSubscriptionRunner.run(input).flux();

        // 2- Retrieve the representation id (the id of DSL Domain explorer example tree)
        var treeId = new AtomicReference<String>();

        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> treeId.set(tree.getId()));
        var helpId = new AtomicReference<String>();
        var toggleAbstractAction = new AtomicReference<String>();

        // 3- retrieve all context menu actions defined for an Entity tree item
        Runnable getPalette = () -> this.paletteExecutor.execute(
                        StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                        treeId.get(),
                        StudioIdentifiers.ROOT_ENTITY_OBJECT.toString())
                .hasPaletteEntriesIds(toolIds -> assertThat(toolIds)
                        .isNotEmpty()
                        .hasSizeGreaterThanOrEqualTo(3)
                        .anyMatch(ExplorerTreeItemContextMenuEntryProvider.EXPAND_ALL::equals))
                .hasFetchTreeItemTool("Help", (id, keyBindings) -> {
                    assertThat(keyBindings)
                            .hasSize(2)
                            .anyMatch(keyBinding -> !keyBinding.isAlt() && !keyBinding.isMeta() && keyBinding.isCtrl() && keyBinding.key().equals("m"))
                            .anyMatch(keyBinding -> !keyBinding.isAlt() && !keyBinding.isCtrl() && keyBinding.isMeta() && keyBinding.key().equals("m"));
                    helpId.set(id);
                })
                .hasSingleClickTreeItemTool("Toggle abstract", (id, keyBindings) -> {
                    assertThat(keyBindings)
                            .hasSize(2)
                            .anyMatch(keyBinding -> !keyBinding.isAlt() && !keyBinding.isMeta() && keyBinding.isCtrl() && keyBinding.key().equals("b"))
                            .anyMatch(keyBinding -> !keyBinding.isAlt() && !keyBinding.isCtrl() && keyBinding.isMeta() && keyBinding.key().equals("b"));
                    toggleAbstractAction.set(id);
                });

        // 4- invoke fetch action data query to retrieve the fetch action data
        Runnable getFetchActionData = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    "representationId", treeId.get(),
                    "treeItemId", StudioIdentifiers.ROOT_ENTITY_OBJECT,
                    "menuEntryId", helpId.get()
            );
            var result = this.treeItemFetchContextActionDataQueryRunner.run(variables);

            Object document = Configuration.defaultConfiguration().jsonProvider().parse(result.data());
            String urlToFetch = JsonPath.read(document, "$.data.viewer.editingContext.representation.description.fetchTreeItemContextMenuEntryData.urlToFetch");
            String fetchKind = JsonPath.read(document, "$.data.viewer.editingContext.representation.description.fetchTreeItemContextMenuEntryData.fetchKind");

            assertThat(urlToFetch).isEqualTo("https://eclipse.dev/sirius/sirius-web.html");
            assertThat(fetchKind).isEqualTo("OPEN");
        };

        StepVerifier.create(flux)
            .consumeNextWith(initialTreeContentConsumer)
            .then(getPalette)
            .then(getFetchActionData)
            .thenCancel()
            .verify(Duration.ofSeconds(30));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with dependencies, when the palette is requested on an imported resource, then the update and remove tool are returned")
    public void givenProjectWithDependenciesWhenPaletteIsRequestedOnImportedResourceThenUpdateAndRemoveToolsAreReturned() {
        var explorerRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(), List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), explorerRepresentationId);
        var flux = this.explorerEventSubscriptionRunner.run(input).flux();

        var treeId = new AtomicReference<String>();

        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> treeId.set(tree.getId()));

        Runnable getPalette = () -> this.paletteExecutor.execute(
                        PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                        treeId.get(),
                        PapayaIdentifiers.PAPAYA_SIRIUS_WEB_TESTS_DATA_DOCUMENT.toString())
                .hasPaletteEntriesIds(entries -> assertThat(entries).containsExactly(ExplorerTreeItemContextMenuEntryProvider.DOWNLOAD_DOCUMENT, ExplorerTreeItemContextMenuEntryProvider.UPDATE_LIBRARY, ExplorerTreeItemContextMenuEntryProvider.REMOVE_LIBRARY, ExplorerTreeItemContextMenuEntryProvider.EXPAND_ALL))
                .hasPaletteEntriesLabels(entries -> assertThat(entries).containsExactly("Download", "Update library", "Remove library", "Expand all"));

        StepVerifier.create(flux)
            .consumeNextWith(initialTreeContentConsumer)
            .then(getPalette)
            .thenCancel()
            .verify(Duration.ofSeconds(5));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a tree with a view description, when the palette is requested on an object, then the correct tools are returned")
    public void givenPapayaProjectWhenThePaletteAreRequestedOnAnObjectThenTheCorrectToolsAreReturned2() {
        List<String> expandedItemIds = List.of(
                DOMAIN_DOCUMENT.toString(),
                DOMAIN_OBJECT.toString(),
                ROOT_ENTITY_OBJECT.toString(),
                HUMAN_ENTITY_OBJECT.toString()
        );
        var explorerRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(this.domainViewTreeDescriptionProvider.getRepresentationDescriptionId(), expandedItemIds, List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, explorerRepresentationId);
        var flux = this.explorerEventSubscriptionRunner.run(input).flux();

        var treeId = new AtomicReference<String>();
        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> treeId.set(tree.getId()));

        Runnable getPalette = () -> this.paletteExecutor.execute(
                        StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                        treeId.get(),
                        HUMAN_ENTITY_OBJECT.toString())
                .hasPaletteEntriesIds(entries -> assertThat(entries).containsExactly(ExplorerTreeItemContextMenuEntryProvider.EXPAND_ALL, this.domainViewTreeDescriptionProvider.getHelpMenuEntryId(), this.domainViewTreeDescriptionProvider.getToggleAbstractMenuEntryId()));

        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
                .then(getPalette)
                .thenCancel()
                .verify(Duration.ofSeconds(30));
    }
}
