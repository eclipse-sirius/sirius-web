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

package org.eclipse.sirius.web.application.controllers.viewsview;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.trees.tests.TreeEventPayloadConsumer.assertRefreshedTreeThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.trees.dto.DeleteTreeItemInput;
import org.eclipse.sirius.components.collaborative.trees.dto.RenameTreeItemInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.trees.tests.graphql.DeleteTreeItemMutationRunner;
import org.eclipse.sirius.components.trees.tests.graphql.RenameTreeItemMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.views.ViewsEventInput;
import org.eclipse.sirius.web.application.views.views.services.ViewsTreeDescriptionProvider;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ViewsEventSubscriptionRunner;
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
 * Integration tests of the views view.
 *
 * @author tgiraudet
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewsViewControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ViewsEventSubscriptionRunner viewsEventSubscriptionRunner;

    @Autowired
    private DeleteTreeItemMutationRunner deleteTreeItemMutationRunner;

    @Autowired
    private RenameTreeItemMutationRunner renameTreeItemMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context with portal representations, when we subscribe to views events, then the tree only contains one root element")
    public void givenEditingContextWithRepresentationsWhenWeSubscribeToViewsEventsThenTreeOnlyContainsOneRootElement() {
        var representationId = new RepresentationIdBuilder().buildViewsViewRepresentationId(ViewsTreeDescriptionProvider.DESCRIPTION_ID, List.of());
        var defaultExplorerInput = new ViewsEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, representationId);
        var defaultFlux = this.viewsEventSubscriptionRunner.run(defaultExplorerInput).flux();

        Consumer<Object> initialDefaultViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsTreeDescriptionProvider.DESCRIPTION_ID);
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
    @DisplayName("Given an editing context with portal representations, when we subscribe to views events and the root element and contained type are expanded, then the tree contains two leaves")
    public void givenEditingContextWithRepresentationsWhenWeSubscribeToViewsEventsAndRootElementAndContainedTypeAreExpandedThenTreeContainsTwoLeaves() {
        var defaultExplorerInput = buildViewInput();
        var defaultFlux = this.viewsEventSubscriptionRunner.run(defaultExplorerInput).flux();

        Consumer<Object> initialDefaultViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsTreeDescriptionProvider.DESCRIPTION_ID);
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
        var defaultFlux = this.viewsEventSubscriptionRunner.run(defaultExplorerInput).flux();

        var treeId = new AtomicReference<String>();

        Consumer<Object> initialDefaultViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsTreeDescriptionProvider.DESCRIPTION_ID);
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
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsTreeDescriptionProvider.DESCRIPTION_ID);
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
        var defaultFlux = this.viewsEventSubscriptionRunner.run(defaultExplorerInput).flux();

        var treeId = new AtomicReference<String>();

        Consumer<Object> initialDefaultViewsContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsTreeDescriptionProvider.DESCRIPTION_ID);
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
            assertThat(tree.getDescriptionId()).isEqualTo(ViewsTreeDescriptionProvider.DESCRIPTION_ID);
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
    
    private ViewsEventInput buildViewInput() {
        var representationId = new RepresentationIdBuilder().buildViewsViewRepresentationId(ViewsTreeDescriptionProvider.DESCRIPTION_ID, List.of(Portal.KIND.split("=")[1], TestIdentifiers.PORTAL_DESCRIPTION_ID.toString()));
        return new ViewsEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, representationId);
    }
}
