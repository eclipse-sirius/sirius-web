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
import static org.eclipse.sirius.web.services.tree.TreeItemPaletteCustomizer.CUSTOMIZED_TREE_TOOL_ID;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.trees.tests.graphql.TreeItemPaletteExecutor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
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

import reactor.test.StepVerifier;

/**
 * Integration tests of the tree item palette customization.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class TreeItemPaletteCustomizationControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private TreeItemPaletteExecutor paletteExecutor;

    @Autowired
    private FetchTreeItemContextMenuEntryDataQueryRunner treeItemFetchContextActionDataQueryRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @Autowired
    private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

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

        Runnable getObjectPalette = () -> this.paletteExecutor.execute(
                        PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                        treeId.get(),
                        PapayaIdentifiers.PROJECT_OBJECT.toString())
                .hasQuickAccessTools(entries -> assertThat(entries).containsExactly(CUSTOMIZED_TREE_TOOL_ID));

        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
                .then(getObjectPalette)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
