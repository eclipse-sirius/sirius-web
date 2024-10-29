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
import static org.junit.jupiter.api.Named.named;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalEventInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.trees.dto.DeleteTreeItemInput;
import org.eclipse.sirius.components.collaborative.trees.dto.RenameTreeItemInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.portals.tests.graphql.PortalEventSubscriptionRunner;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.tests.graphql.DeleteTreeItemMutationRunner;
import org.eclipse.sirius.components.trees.tests.graphql.InitialDirectEditTreeItemLabelQueryRunner;
import org.eclipse.sirius.components.trees.tests.graphql.RenameTreeItemMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.core.publisher.Flux;
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

    private static final TreeItemMatcher ROOT_DOCUMENT_IS_NAMED_ECORE_RENAMED = new TreeItemMatcher(
            tree -> tree.getChildren().get(0),
            treeItem -> treeItem.getLabel().toString().equals("EcoreRenamed")
    );

    private static final TreeItemMatcher EPACKAGE_IS_NAMED_SAMPLE_RENAMED = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.getLabel().toString().equals("SampleRenamed")
    );

    private static final TreeItemMatcher EPACKAGE_STILL_HAS_ONE_REPRESENTATION = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.isHasChildren() && treeItem.getChildren().size() == 2 && treeItem.getChildren().get(1).getKind().startsWith(SemanticKindConstants.PREFIX)
    );

    private static final TreeItemMatcher DOCUMENT_HAS_NO_OBJECT = new TreeItemMatcher(
            tree -> tree.getChildren().get(0),
            treeItem -> !treeItem.isHasChildren()
    );

    private static RenameTreeItemMutationRunner renameTreeItemMutationRunner;

    private static DeleteTreeItemMutationRunner deleteTreeItemMutationRunner;

    /**
     * Record used to contain both a way to find a tree item and some predicate to validate on said tree item in order to simplify the design of some advanced tests.
     *
     * @author sbegaudeau
     */
    private record TreeItemMatcher(Function<Tree, TreeItem> treeItemFinder, Predicate<TreeItem> treeItemPredicate) { }

    private final TreeItemMatcher rootDocumentIsNamedEcore = new TreeItemMatcher(
            tree -> tree.getChildren().get(0),
            treeItem -> treeItem.getLabel().toString().equals("Ecore")
    );

    private final TreeItemMatcher ePackageIsNamedSample = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.getLabel().toString().equals("Sample")
    );

    private final TreeItemMatcher sampleHasThreeChildren = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.getChildren().size() == 3
    );

    private final TreeItemMatcher hasTwoPortals = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem ->
                "Portal".equals(treeItem.getChildren().get(0).getLabel().toString()) &&
                "Portal".equals(treeItem.getChildren().get(1).getLabel().toString())

    );

    private final TreeItemMatcher ePackageTreeItemIsStyled = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.getLabel().styledStringFragments().size() == 1 &&
                    treeItem.getLabel().styledStringFragments().get(0).styledStringFragmentStyle().isStruckOut() &&
                    treeItem.getLabel().styledStringFragments().get(0).styledStringFragmentStyle().getBackgroundColor().equals("red")
    );

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ExplorerEventSubscriptionRunner treeEventSubscriptionRunner;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private PortalEventSubscriptionRunner portalEventSubscriptionRunner;

    @Autowired
    private InitialDirectEditTreeItemLabelQueryRunner initialDirectEditTreeItemLabelQueryRunner;

    @Autowired
    private IIdentityService identityService;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeAll
    public static void init(@Autowired RenameTreeItemMutationRunner renameRunner, @Autowired DeleteTreeItemMutationRunner deleteRunner) {
        TreeControllerIntegrationTests.renameTreeItemMutationRunner = Objects.requireNonNull(renameRunner);
        TreeControllerIntegrationTests.deleteTreeItemMutationRunner = Objects.requireNonNull(deleteRunner);
    }

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a project, when we subscribe to the tree events of its explorer, then the tree of the explorer is sent")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWhenWeSubscribeToTreeEventsOfItsExplorerThenTheTreeOfTheExplorerIsSent() {
        var treeRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(), List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeRepresentationId);
        var flux = this.treeEventSubscriptionRunner.run(input);

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

    private static Stream<Arguments> provideDeleteTreeItemThenAndExpected() {
        return Stream.of(
                Arguments.of(named("Delete Portal Representation", (Function<String, Runnable>) (treeRepresentationId) -> TreeControllerIntegrationTests.deleteTreeItem(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeRepresentationId, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION)), TreeControllerIntegrationTests.getTreeRefreshedEventPayloadMatcher(List.of(TreeControllerIntegrationTests.EPACKAGE_STILL_HAS_ONE_REPRESENTATION))),
                Arguments.of(named("Delete EPackage Object", (Function<String, Runnable>) (treeRepresentationId) -> TreeControllerIntegrationTests.deleteTreeItem(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeRepresentationId, TestIdentifiers.EPACKAGE_OBJECT)), TreeControllerIntegrationTests.getTreeRefreshedEventPayloadMatcher(List.of(TreeControllerIntegrationTests.DOCUMENT_HAS_NO_OBJECT))),
                Arguments.of(named("Delete Document", (Function<String, Runnable>) (treeRepresentationId) -> deleteTreeItem(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeRepresentationId, TestIdentifiers.ECORE_SAMPLE_DOCUMENT)), (Predicate<Object>) object -> Optional.of(object)
                        .filter(DataFetcherResult.class::isInstance)
                        .map(DataFetcherResult.class::cast)
                        .map(DataFetcherResult::getData)
                        .filter(TreeRefreshedEventPayload.class::isInstance)
                        .map(TreeRefreshedEventPayload.class::cast)
                        .map(TreeRefreshedEventPayload::tree)
                        .filter(tree -> tree.getChildren().isEmpty())
                        .isPresent())
        );
    }

    @ParameterizedTest(name = "{argumentsWithNames}")
    @MethodSource("provideDeleteTreeItemThenAndExpected")
    @DisplayName("Given the explorer of a project, when we delete tree items, then the tree is refreshed")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenExplorerOfProjectWhenWeDeleteTreeItemsThenTheTreeIsRefreshed(Function<String, Runnable> deleteTreeItem, Predicate<Object> expectedNextMatch) {
        this.givenCommittedTransaction.commit();

        var expandedIds = this.getAllTreeItemIdsForEcoreSampleProject();
        var treeRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, expandedIds, List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeRepresentationId);
        var flux = this.treeEventSubscriptionRunner.run(input);

        var hasProjectContent = TreeControllerIntegrationTests.getTreeRefreshedEventPayloadMatcher(List.of(
                this.rootDocumentIsNamedEcore, this.ePackageIsNamedSample, this.ePackageTreeItemIsStyled, this.sampleHasThreeChildren, this.hasTwoPortals));

        StepVerifier.create(flux)
                .expectNextMatches(hasProjectContent)
                .then(deleteTreeItem.apply(treeRepresentationId))
                .expectNextMatches(expectedNextMatch)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given the explorer of a project, when we trigger a tree item rename, then the initial direct edit label is executed")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenExplorerOfProjectWhenWeTriggerATreeItemRenameThenTheInitialDirectEditTreeItemLabelIsExecuted() {
        var expandedIds = this.getAllTreeItemIdsForEcoreSampleProject();
        var treeRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, expandedIds, List.of());

        var treeEventInput = new ExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeRepresentationId);
        var treeFlux = this.treeEventSubscriptionRunner.run(treeEventInput);

        var hasProjectContent = TreeControllerIntegrationTests.getTreeRefreshedEventPayloadMatcher(List.of(this.rootDocumentIsNamedEcore, this.ePackageIsNamedSample, this.ePackageTreeItemIsStyled));

        StepVerifier.create(treeFlux)
                .expectNextMatches(hasProjectContent)
                .then(this.triggerDirectEditTreeItemLabel(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeRepresentationId, TestIdentifiers.EPACKAGE_OBJECT, "Sample"))
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private static Stream<Arguments> provideRenameTreeItemThenAndExpected() {
        return Stream.of(
                Arguments.of(named("Rename EPackage Object", (Function<String, Runnable>) (treeRepresentationId) -> TreeControllerIntegrationTests.renameTreeItem(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeRepresentationId, TestIdentifiers.EPACKAGE_OBJECT, "SampleRenamed")), TreeControllerIntegrationTests.getTreeRefreshedEventPayloadMatcher(List.of(TreeControllerIntegrationTests.EPACKAGE_IS_NAMED_SAMPLE_RENAMED))),
                Arguments.of(named("Rename Document", (Function<String, Runnable>) (treeRepresentationId) -> TreeControllerIntegrationTests.renameTreeItem(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeRepresentationId, TestIdentifiers.ECORE_SAMPLE_DOCUMENT, "EcoreRenamed")), TreeControllerIntegrationTests.getTreeRefreshedEventPayloadMatcher(List.of(TreeControllerIntegrationTests.ROOT_DOCUMENT_IS_NAMED_ECORE_RENAMED))),
                Arguments.of(named("Rename Portal Representation", (Function<String, Runnable>) (treeRepresentationId) -> TreeControllerIntegrationTests.renameTreeItem(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeRepresentationId, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION, "PortalRenamed")), (Predicate<Object>) object -> {
                    return Optional.of(object)
                            .filter(DataFetcherResult.class::isInstance)
                            .map(DataFetcherResult.class::cast)
                            .map(DataFetcherResult::getData)
                            .filter(TreeRefreshedEventPayload.class::isInstance)
                            .isPresent();
                })
        );
    }

    @ParameterizedTest(name = "{argumentsWithNames}")
    @MethodSource("provideRenameTreeItemThenAndExpected")
    @DisplayName("Given the explorer of a project, when we rename a tree item, then the tree is refreshed")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenExplorerOfProjectWhenWeRenameTreeItemThenTheTreeIsRefreshed(Function<String, Runnable> renameTreeItem, Predicate<Object> expectedNextMatch) {
        this.givenCommittedTransaction.commit();

        var portalEventInput = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var portalFlux = this.portalEventSubscriptionRunner.run(portalEventInput);

        Predicate<Object> portalRefreshedEventPayloadMatcher = object -> {
            return Optional.of(object)
                    .filter(DataFetcherResult.class::isInstance)
                    .map(DataFetcherResult.class::cast)
                    .map(DataFetcherResult::getData)
                    .filter(PortalRefreshedEventPayload.class::isInstance)
                    .isPresent();
        };
        var expandedIds = this.getAllTreeItemIdsForEcoreSampleProject();
        var treeRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, expandedIds, List.of());

        var treeEventInput = new ExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), treeRepresentationId);
        var treeFlux = this.treeEventSubscriptionRunner.run(treeEventInput);

        var hasProjectContent = TreeControllerIntegrationTests.getTreeRefreshedEventPayloadMatcher(List.of(this.rootDocumentIsNamedEcore, this.ePackageIsNamedSample, this.ePackageTreeItemIsStyled, this.sampleHasThreeChildren, this.hasTwoPortals));

        StepVerifier.create(Flux.merge(portalFlux, treeFlux))
                .expectNextMatches(portalRefreshedEventPayloadMatcher)
                .expectNextMatches(hasProjectContent)
                .then(renameTreeItem.apply(treeRepresentationId))
                .expectNextMatches(expectedNextMatch)
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

    private static Predicate<Object> getTreeRefreshedEventPayloadMatcher(List<TreeItemMatcher> treeItemMatchers) {
        Predicate<Tree> treeMatcher = tree -> treeItemMatchers.stream().allMatch(treeItemMatcher -> {
            var treeItem = treeItemMatcher.treeItemFinder.apply(tree);
            return treeItemMatcher.treeItemPredicate.test(treeItem);
        });

        return object -> {
            return Optional.of(object)
                    .filter(DataFetcherResult.class::isInstance)
                    .map(DataFetcherResult.class::cast)
                    .map(DataFetcherResult::getData)
                    .filter(TreeRefreshedEventPayload.class::isInstance)
                    .map(TreeRefreshedEventPayload.class::cast)
                    .map(TreeRefreshedEventPayload::tree)
                    .filter(treeMatcher)
                    .isPresent();
        };
    }

    private static Runnable deleteTreeItem(String editingContextId, String treeId, UUID treeItemId) {
        return () -> {
            var input = new DeleteTreeItemInput(UUID.randomUUID(), editingContextId, treeId, treeItemId);
            var result = TreeControllerIntegrationTests.deleteTreeItemMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.deleteTreeItem.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        };
    }

    private static Runnable renameTreeItem(String editingContextId, String treeId, UUID treeItemId, String newLabel) {
        return () -> {
            var input = new RenameTreeItemInput(UUID.randomUUID(), editingContextId, treeId, treeItemId, newLabel);
            var result = TreeControllerIntegrationTests.renameTreeItemMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.renameTreeItem.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        };
    }

    private Runnable triggerDirectEditTreeItemLabel(String editingContextId, String treeId, UUID treeItemId, String expectedLabel) {
        return () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", editingContextId,
                    "representationId", treeId,
                    "treeItemId", treeItemId
            );
            var result = this.initialDirectEditTreeItemLabelQueryRunner.run(variables);

            String initialDirectEditLabel = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.initialDirectEditTreeItemLabel");
            assertThat(initialDirectEditLabel).isEqualTo(expectedLabel);
        };
    }

}
