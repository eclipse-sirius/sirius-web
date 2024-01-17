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
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.trees.dto.TreeEventInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.tests.graphql.ExpandAllTreePathQueryRunner;
import org.eclipse.sirius.components.trees.tests.graphql.TreeEventSubscriptionRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
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
 * Integration tests of a non semantic element in the explorer.
 *
 * @author Jerome Gout
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExplorerNonSemanticElementControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private TreeEventSubscriptionRunner treeEventSubscriptionRunner;

    @Autowired
    private ExpandAllTreePathQueryRunner expandAllTreePathQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a sample studio, when we expand all the domain elements, then underneath entities have a superTypes child")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenStudioWhenWeAskForTheTreePathOfPapayaViewObjectThenItsPathInTheExplorerIsReturned() {
        var input = new TreeEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), ExplorerDescriptionProvider.PREFIX, List.of(), List.of());
        var flux = this.treeEventSubscriptionRunner.run(input);

        var treeId = new AtomicReference<String>();
        var domainDocumentId = new AtomicReference<String>();

        Consumer<Object> initialTreeContentConsumer = this.getTreeSubscriptionConsumer(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(5);
            assertThat(tree.getChildren()).allSatisfy(treeItem -> assertThat(treeItem.getChildren()).isEmpty());

            treeId.set(tree.getId());
            domainDocumentId.set(tree.getChildren().get(1).getId()); // Domain document is the second child of the tree
        });

        // Expand the part of the tree corresponding to the Domain document
        var treeItemIds = new AtomicReference<List<String>>();

        Runnable getTreePath = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                    "treeId", treeId.get(),
                    "treeItemId", domainDocumentId.get()
            );
            var result = this.expandAllTreePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result, "$.data.viewer.editingContext.expandAllTreePath.treeItemIdsToExpand");
            assertThat(treeItemIdsToExpand).isNotEmpty();
            // there are 3 entities in the Domain document, so should get 3 superType treeItems
            assertThat(treeItemIdsToExpand.stream()
                    .filter(id -> id.startsWith(ExplorerDescriptionProvider.SETTING))
                    .toList()
                    ).hasSize(3);
            treeItemIds.set(treeItemIdsToExpand);
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
                .then(getTreePath)
                .thenCancel()
                .verify(Duration.ofSeconds(10));


        var expandedTreeInput = new TreeEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), ExplorerDescriptionProvider.PREFIX, treeItemIds.get(), List.of());
        var expandedTreeFlux = this.treeEventSubscriptionRunner.run(expandedTreeInput);

        Consumer<Object> initialExpandedTreeContentConsumer = this.getTreeSubscriptionConsumer(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).isNotEmpty();
            assertThat(tree.getChildren()).hasSize(5);
            var domainDocument = tree.getChildren().get(1);
            assertThat(domainDocument).isNotNull();
            assertThat(domainDocument.getChildren()).isNotEmpty();
            assertThat(domainDocument.getChildren()).hasSize(1);
            assertThat(domainDocument.getChildren().get(0)).isNotNull();
            List<TreeItem> domainElements = domainDocument.getChildren().get(0).getChildren();
            assertThat(domainElements).hasSize(3);
            assertThat(domainElements.get(0).getLabel().toString()).isEqualTo("Root");
            assertThat(domainElements.get(0).getChildren()).isNotEmpty();
            assertThat(domainElements.get(0).getChildren()).hasSize(3);
            assertThat(domainElements.get(0).getChildren().get(0).getLabel().toString()).isEqualTo("superTypes");
        });

        StepVerifier.create(expandedTreeFlux)
                .consumeNextWith(initialExpandedTreeContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private Consumer<Object> getTreeSubscriptionConsumer(Consumer<Tree> treeConsumer) {
        return object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(TreeRefreshedEventPayload.class::isInstance)
                .map(TreeRefreshedEventPayload.class::cast)
                .map(TreeRefreshedEventPayload::tree)
                .ifPresentOrElse(treeConsumer, () -> fail("Missing tree"));
    }
}
