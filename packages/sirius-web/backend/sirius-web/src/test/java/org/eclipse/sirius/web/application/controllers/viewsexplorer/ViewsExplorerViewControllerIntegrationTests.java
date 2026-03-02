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

package org.eclipse.sirius.web.application.controllers.viewsexplorer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.trees.tests.TreeEventPayloadConsumer.assertRefreshedTreeThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.trees.dto.DeleteTreeItemInput;
import org.eclipse.sirius.components.collaborative.trees.dto.RenameTreeItemInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.trees.tests.graphql.DeleteTreeItemMutationRunner;
import org.eclipse.sirius.components.trees.tests.graphql.ExpandAllTreePathQueryRunner;
import org.eclipse.sirius.components.trees.tests.graphql.RenameTreeItemMutationRunner;
import org.eclipse.sirius.components.trees.tests.graphql.TreePathQueryRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.viewsexplorer.ViewsExplorerEventInput;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.ViewsExplorerTreeDescriptionProvider;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ViewsExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the views explorer view.
 *
 * @author tgiraudet
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewsExplorerViewControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ViewsExplorerEventSubscriptionRunner viewsExplorerEventSubscriptionRunner;

    @Autowired
    private DeleteTreeItemMutationRunner deleteTreeItemMutationRunner;

    @Autowired
    private RenameTreeItemMutationRunner renameTreeItemMutationRunner;

    @Autowired
    private TreePathQueryRunner treePathQueryRunner;

    @Autowired
    private ExpandAllTreePathQueryRunner expandAllTreePathQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context with portal representations, when we subscribe to views events, then the tree only contains one root element")
    public void givenEditingContextWithRepresentationsWhenWeSubscribeToViewsEventsThenTreeOnlyContainsOneRootElement() {
        var representationId = new RepresentationIdBuilder().buildViewsExplorerViewRepresentationId(List.of());
        var defaultExplorerInput = new ViewsExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, representationId);
        var defaultFlux = this.viewsExplorerEventSubscriptionRunner.run(defaultExplorerInput).flux();

        Consumer<Object> initialDefaultViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsExplorerTreeDescriptionProvider.DESCRIPTION_ID);
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren()).allSatisfy(treeItem -> assertThat(treeItem.getChildren()).isEmpty());
        });

        StepVerifier.create(defaultFlux)
                .consumeNextWith(initialDefaultViewsContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context with portal representations, when we subscribe to views events, and the root element and contained type are expanded, then the tree contains two leaves")
    public void givenEditingContextWithRepresentationsWhenWeSubscribeToViewsEventsAndRootElementAndContainedTypeAreExpandedThenTreeContainsTwoLeaves() {
        var defaultExplorerInput = buildViewInput();
        var defaultFlux = this.viewsExplorerEventSubscriptionRunner.run(defaultExplorerInput).flux();

        Consumer<Object> initialDefaultViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsExplorerTreeDescriptionProvider.DESCRIPTION_ID);
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren().get(0).getChildren()).hasSize(2);
        });

        StepVerifier.create(defaultFlux)
                .consumeNextWith(initialDefaultViewsContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context with portal representations, when we subscribe to views events and delete one representation, then the tree contains one leaf")
    public void givenEditingContextWithRepresentationsWhenWeSubscribeToViewsEventsAndDeleteOneRepresentationThenTreeContainsOneLeaf() {
        var defaultExplorerInput = buildViewInput();
        var defaultFlux = this.viewsExplorerEventSubscriptionRunner.run(defaultExplorerInput).flux();

        var treeId = new AtomicReference<String>();

        Consumer<Object> initialDefaultViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsExplorerTreeDescriptionProvider.DESCRIPTION_ID);
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren().get(0).getChildren()).hasSize(2);
            treeId.set(tree.getId());
        });

        Runnable deleteTreeItem = () -> {
            var input = new DeleteTreeItemInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, treeId.get(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
            var result = this.deleteTreeItemMutationRunner.run(input);

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();

            String typename = JsonPath.read(result.data(), "$.data.deleteTreeItem.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> afterDeleteViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsExplorerTreeDescriptionProvider.DESCRIPTION_ID);
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren().get(0).getChildren()).hasSize(1);
        });

        StepVerifier.create(defaultFlux)
                .consumeNextWith(initialDefaultViewsContentConsumer)
                .then(deleteTreeItem)
                .consumeNextWith(afterDeleteViewsContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context with portal representations, when we subscribe to views events and rename one representation, then the tree contains a renamed leaf")
    public void givenEditingContextWithRepresentationsWhenWeSubscribeToViewsEventsAndRenameOneRepresentationThenTreeContainsRenamedLeaf() {
        var defaultExplorerInput = buildViewInput();
        var defaultFlux = this.viewsExplorerEventSubscriptionRunner.run(defaultExplorerInput).flux();

        var treeId = new AtomicReference<String>();

        Consumer<Object> initialDefaultViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsExplorerTreeDescriptionProvider.DESCRIPTION_ID);
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren().get(0).getChildren()).hasSize(2);
            treeId.set(tree.getId());
        });

        Runnable renameTreeItem = () -> {
            var input = new RenameTreeItemInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, treeId.get(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION, "Foo");
            var result = this.renameTreeItemMutationRunner.run(input);

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();

            String typename = JsonPath.read(result.data(), "$.data.renameTreeItem.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> afterRenameViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsExplorerTreeDescriptionProvider.DESCRIPTION_ID);
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren().get(0).getChildren()).hasSize(2);
            assertThat(tree.getChildren().get(0).getChildren().get(0).getChildren().get(1).getLabel().toString()).isEqualTo("Foo");
        });

        StepVerifier.create(defaultFlux)
            .consumeNextWith(initialDefaultViewsContentConsumer)
            .then(renameTreeItem)
            .consumeNextWith(afterRenameViewsContentConsumer)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context with portal representations, when we ask for the tree path of a representation, then its ancestor ids in the views tree are returned")
    public void givenEditingContextWithRepresentationsWhenWeAskForTreePathThenAncestorIdsAreReturned() {
        var defaultExplorerInput = buildViewInput();
        var defaultFlux = this.viewsExplorerEventSubscriptionRunner.run(defaultExplorerInput).flux();

        var treeId = new AtomicReference<String>();

        Consumer<Object> initialDefaultViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            treeId.set(tree.getId());
        });

        Runnable getTreePath = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID,
                    "treeId", treeId.get(),
                    "selectionEntryIds", List.of(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString())
            );
            var result = this.treePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result.data(), "$.data.viewer.editingContext.treePath.treeItemIdsToExpand");
            assertThat(treeItemIdsToExpand).isNotEmpty();
        };

        StepVerifier.create(defaultFlux)
                .consumeNextWith(initialDefaultViewsContentConsumer)
                .then(getTreePath)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context with portal representations, when we ask to expand all from a representation kind, then tree item ids to expand are returned")
    public void givenEditingContextWithRepresentationsWhenWeAskToExpandAllFromRepresentationKindThenTreeItemIdsToExpandAreReturned() {
        var representationId = new RepresentationIdBuilder().buildViewsExplorerViewRepresentationId(List.of());
        var defaultExplorerInput = new ViewsExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, representationId);
        var defaultFlux = this.viewsExplorerEventSubscriptionRunner.run(defaultExplorerInput).flux();

        var treeId = new AtomicReference<String>();
        var rootTreeItemId = new AtomicReference<String>();

        Consumer<Object> initialDefaultViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(1);
            treeId.set(tree.getId());
            rootTreeItemId.set(tree.getChildren().get(0).getId());
        });

        Runnable getExpandAllTreePath = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID,
                    "treeId", treeId.get(),
                    "treeItemId", rootTreeItemId.get()
            );
            var result = this.expandAllTreePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result.data(), "$.data.viewer.editingContext.expandAllTreePath.treeItemIdsToExpand");
            assertThat(treeItemIdsToExpand).isNotEmpty();
        };

        StepVerifier.create(defaultFlux)
                .consumeNextWith(initialDefaultViewsContentConsumer)
                .then(getExpandAllTreePath)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private ViewsExplorerEventInput buildViewInput() {
        var representationId = new RepresentationIdBuilder().buildViewsExplorerViewRepresentationId(
            List.of(Portal.KIND.split("=")[1], TestIdentifiers.PORTAL_DESCRIPTION_ID.toString()));
        return new ViewsExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, representationId);
    }
}
