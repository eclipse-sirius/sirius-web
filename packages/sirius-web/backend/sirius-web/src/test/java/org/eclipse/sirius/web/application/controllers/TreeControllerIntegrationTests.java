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
package org.eclipse.sirius.web.application.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.trees.api.TreeConfiguration;
import org.eclipse.sirius.components.collaborative.trees.dto.DeleteTreeItemInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeEventInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.TestIdentifiers;
import org.eclipse.sirius.web.services.api.IGraphQLRequestor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.test.StepVerifier;

/**
 * Integration tests of the tree controllers.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TreeControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String GET_TREE_EVENT_SUBSCRIPTION = """
            subscription treeEvent($input: TreeEventInput!) {
              treeEvent(input: $input) {
                __typename
              }
            }
            """;

    private static final String DELETE_TREE_ITEM_MUTATION = """
            mutation deleteTreeItem($input: DeleteTreeItemInput!) {
              deleteTreeItem(input: $input) {
                __typename
              }
            }
            """;

    /**
     * Record used to contain both a way to find a tree item and some predicate to validate on said tree item in order to simplify the design of some advanced tests.
     *
     * @author sbegaudeau
     */
    private record TreeItemMatcher(Function<Tree, TreeItem> treeItemFinder, Predicate<TreeItem> treeItemPredicate) { }

    private final TreeItemMatcher rootDocumentIsNamedEcore = new TreeItemMatcher(
            tree -> tree.getChildren().get(0),
            treeItem -> treeItem.getLabel().equals("Ecore")
    );

    private final TreeItemMatcher ePackageIsNamedSample = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.getLabel().equals("Sample")
    );

    private final TreeItemMatcher representationIsAPortal = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.getLabel().equals("Portal")
    );

    private final TreeItemMatcher ePackageHasNoRepresentation = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem -> !treeItem.isHasChildren()
    );

    private final TreeItemMatcher documentHasNoObject = new TreeItemMatcher(
            tree -> tree.getChildren().get(0),
            treeItem -> !treeItem.isHasChildren()
    );

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IIdentityService identityService;

    @Test
    @DisplayName("Given a project, when we subscribe to the tree events of its explorer, then the tree of the explorer is sent")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWhenWeSubscribeToTreeEventsOfItsExplorerThenTheTreeOfTheExplorerIsSent() {
        var input = new TreeEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), "explorer://", List.of());
        var flux = this.graphQLRequestor.subscribe(GET_TREE_EVENT_SUBSCRIPTION, input);

        Predicate<Object> projectContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(TreeRefreshedEventPayload.class::isInstance)
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(projectContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given the explorer of a project, when we delete tree items, then the tree is refreshed")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenExplorerOfProjectWhenWeDeleteTreeItemsThenTheTreeIsRefreshed() {
        var expandedIds = this.getAllTreeItemIdsForEcoreSampleProject();
        var input = new TreeEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), "explorer://", expandedIds);
        var flux = this.graphQLRequestor.subscribe(GET_TREE_EVENT_SUBSCRIPTION, input);

        var hasProjectContent = this.getTreeRefreshedEventPayloadMatcher(List.of(rootDocumentIsNamedEcore, ePackageIsNamedSample, representationIsAPortal));
        var hasNoMoreRepresentation = this.getTreeRefreshedEventPayloadMatcher(List.of(ePackageHasNoRepresentation));
        var hasNoMoreObject = this.getTreeRefreshedEventPayloadMatcher(List.of(documentHasNoObject));

        Predicate<Object> hasNoMoreDocument = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(TreeRefreshedEventPayload.class::isInstance)
                .map(TreeRefreshedEventPayload.class::cast)
                .map(TreeRefreshedEventPayload::tree)
                .filter(tree -> tree.getChildren().isEmpty())
                .isPresent();

        var treeId = new TreeConfiguration(input.editingContextId(), input.treeId(), input.expanded()).getId();

        StepVerifier.create(flux)
                .expectNextMatches(hasProjectContent)
                .then(this.deleteTreeItem(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeId, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION))
                .expectNextMatches(hasNoMoreRepresentation)
                .then(this.deleteTreeItem(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeId, TestIdentifiers.EPACKAGE_OBJECT))
                .expectNextMatches(hasNoMoreObject)
                .then(this.deleteTreeItem(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeId, TestIdentifiers.ECORE_SAMPLE_DOCUMENT))
                .expectNextMatches(hasNoMoreDocument)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private List<String> getAllTreeItemIdsForEcoreSampleProject() {
        var optionalEditingContext = this.editingContextSearchService.findById(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString())
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast);
        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        var expandedIds = new ArrayList<String>();
        editingContext.getDomain().getResourceSet().getAllContents().forEachRemaining(notifier -> {
            if (notifier instanceof Resource resource) {
                expandedIds.add(resource.getURI().path().substring(1));
            } else if (notifier instanceof EObject eObject) {
                expandedIds.add(this.identityService.getId(eObject));
            }
        });
        return expandedIds;
    }

    private Predicate<Object> getTreeRefreshedEventPayloadMatcher(List<TreeItemMatcher> treeItemMatchers) {
        Predicate<Tree> treeMatcher = tree -> treeItemMatchers.stream().allMatch(treeItemMatcher -> {
            var treeItem = treeItemMatcher.treeItemFinder.apply(tree);
            return treeItemMatcher.treeItemPredicate.test(treeItem);
        });

        return object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(TreeRefreshedEventPayload.class::isInstance)
                .map(TreeRefreshedEventPayload.class::cast)
                .map(TreeRefreshedEventPayload::tree)
                .filter(treeMatcher)
                .isPresent();
    }

    private Runnable deleteTreeItem(String editingContextId, String treeId, UUID treeItemId) {
        return () -> {
            var input = new DeleteTreeItemInput(UUID.randomUUID(), editingContextId, treeId, treeItemId);
            var result = this.graphQLRequestor.execute(DELETE_TREE_ITEM_MUTATION, input);

            String typename = JsonPath.read(result, "$.data.deleteTreeItem.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        };
    }
}
