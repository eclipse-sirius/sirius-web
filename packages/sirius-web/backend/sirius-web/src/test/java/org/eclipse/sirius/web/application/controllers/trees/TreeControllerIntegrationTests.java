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
import static org.eclipse.sirius.components.portals.tests.PortalEventPayloadConsumer.assertRefreshedPortalThat;
import static org.eclipse.sirius.components.trees.tests.TreeEventPayloadConsumer.assertRefreshedTreeThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalEventInput;
import org.eclipse.sirius.components.collaborative.trees.dto.DeleteTreeItemInput;
import org.eclipse.sirius.components.collaborative.trees.dto.RenameTreeItemInput;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IIdentityService;
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
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
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

    private final TreeItemMatcher rootDocumentIsNamedEcoreRenamed = new TreeItemMatcher(
            tree -> tree.getChildren().get(0),
            treeItem -> treeItem.getLabel().toString().equals("EcoreRenamed")
    );

    private final TreeItemMatcher ePackageIsNamedSample = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.getLabel().toString().equals("Sample")
    );

    private final TreeItemMatcher ePackageIsNamedSampleRenamed = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.getLabel().toString().equals("SampleRenamed")
    );

    private final TreeItemMatcher representationIsAPortal = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.getLabel().toString().equals("EPackage Portal")
    );

    private final TreeItemMatcher ePackageHasNoRepresentation = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.isHasChildren() && treeItem.getChildren().stream().noneMatch(childTreeItem -> childTreeItem.getId().equals(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString()))
    );

    private final TreeItemMatcher documentHasNoObject = new TreeItemMatcher(
            tree -> tree.getChildren().get(0),
            treeItem -> !treeItem.isHasChildren()
    );

    private final TreeItemMatcher ePackageTreeItemIsStyled = new TreeItemMatcher(
            tree -> tree.getChildren().get(0).getChildren().get(0),
            treeItem -> treeItem.getLabel().styledStringFragments().size() == 1 &&
                    treeItem.getLabel().styledStringFragments().get(0).styledStringFragmentStyle().isStruckOut() &&
                    treeItem.getLabel().styledStringFragments().get(0).styledStringFragmentStyle().getBackgroundColor().equals("red")
    );

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ExplorerEventSubscriptionRunner treeEventSubscriptionRunner;

    @Autowired
    private DeleteTreeItemMutationRunner deleteTreeItemMutationRunner;

    @Autowired
    private RenameTreeItemMutationRunner renameTreeItemMutationRunner;

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

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we subscribe to the tree events of its explorer, then the tree of the explorer is sent")
    public void givenProjectWhenWeSubscribeToTreeEventsOfItsExplorerThenTheTreeOfTheExplorerIsSent() {
        var treeRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(), List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), treeRepresentationId);
        var flux = this.treeEventSubscriptionRunner.run(input);

        Consumer<Object> projectContentMatcher = assertRefreshedTreeThat(tree -> assertThat(tree).isNotNull());

        StepVerifier.create(flux)
                .consumeNextWith(projectContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the explorer of a project, when we delete tree items, then the tree is refreshed")
    public void givenExplorerOfProjectWhenWeDeleteTreeItemsThenTheTreeIsRefreshed() {
        var expandedIds = this.getAllTreeItemIdsForEcoreSampleProject();
        var treeRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, expandedIds, List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), treeRepresentationId);
        var flux = this.treeEventSubscriptionRunner.run(input);

        var hasProjectContent = this.getTreeRefreshedEventPayloadMatcher(List.of(this.rootDocumentIsNamedEcore, this.ePackageIsNamedSample, this.ePackageTreeItemIsStyled, this.representationIsAPortal));
        var hasNoMoreRepresentation = this.getTreeRefreshedEventPayloadMatcher(List.of(this.ePackageHasNoRepresentation));
        var hasNoMoreObject = this.getTreeRefreshedEventPayloadMatcher(List.of(this.documentHasNoObject));

        Consumer<Object> hasNoMoreDocument = assertRefreshedTreeThat(tree -> assertThat(tree.getChildren()).isEmpty());

        StepVerifier.create(flux)
                .consumeNextWith(hasProjectContent)
                .then(this.deleteTreeItem(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), treeRepresentationId, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION))
                .consumeNextWith(hasNoMoreRepresentation)
                .then(this.deleteTreeItem(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), treeRepresentationId, TestIdentifiers.EPACKAGE_OBJECT))
                .consumeNextWith(hasNoMoreObject)
                .then(this.deleteTreeItem(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), treeRepresentationId, TestIdentifiers.ECORE_SAMPLE_DOCUMENT))
                .consumeNextWith(hasNoMoreDocument)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the explorer of a project, when we trigger a tree item rename, then the initial direct edit label is executed")
    public void givenExplorerOfProjectWhenWeTriggerATreeItemRenameThenTheInitialDirectEditTreeItemLabelIsExecuted() {
        var expandedIds = this.getAllTreeItemIdsForEcoreSampleProject();
        var treeRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, expandedIds, List.of());

        var treeEventInput = new ExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), treeRepresentationId);
        var treeFlux = this.treeEventSubscriptionRunner.run(treeEventInput);

        var hasProjectContent = this.getTreeRefreshedEventPayloadMatcher(List.of(this.rootDocumentIsNamedEcore, this.ePackageIsNamedSample, this.ePackageTreeItemIsStyled));

        StepVerifier.create(treeFlux)
                .consumeNextWith(hasProjectContent)
                .then(this.triggerDirectEditTreeItemLabel(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), treeRepresentationId, TestIdentifiers.EPACKAGE_OBJECT, "Sample"))
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the explorer of a project, when we rename a tree item, then the tree is refreshed")
    public void givenExplorerOfProjectWhenWeRenameTreeItemThenTheTreeIsRefreshed() {
        var portalEventInput = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var portalFlux = this.portalEventSubscriptionRunner.run(portalEventInput);

        Consumer<Object> portalRefreshedEventPayloadMatcher = assertRefreshedPortalThat(portal -> assertThat(portal).isNotNull());
        var expandedIds = this.getAllTreeItemIdsForEcoreSampleProject();
        var treeRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, expandedIds, List.of());

        var treeEventInput = new ExplorerEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), treeRepresentationId);
        var treeFlux = this.treeEventSubscriptionRunner.run(treeEventInput);

        var hasProjectContent = this.getTreeRefreshedEventPayloadMatcher(List.of(this.rootDocumentIsNamedEcore, this.ePackageIsNamedSample, this.ePackageTreeItemIsStyled, this.representationIsAPortal));
        var hasObjectNewLabel = this.getTreeRefreshedEventPayloadMatcher(List.of(this.ePackageIsNamedSampleRenamed));
        var hasDocumentNewLabel = this.getTreeRefreshedEventPayloadMatcher(List.of(this.rootDocumentIsNamedEcoreRenamed));

        StepVerifier.create(Flux.merge(portalFlux, treeFlux))
                .consumeNextWith(portalRefreshedEventPayloadMatcher)
                .consumeNextWith(hasProjectContent)
                .then(this.renameTreeItem(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), treeRepresentationId, TestIdentifiers.EPACKAGE_OBJECT, "SampleRenamed"))
                .consumeNextWith(hasObjectNewLabel)
                .then(this.renameTreeItem(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), treeRepresentationId, TestIdentifiers.ECORE_SAMPLE_DOCUMENT, "EcoreRenamed"))
                .consumeNextWith(hasDocumentNewLabel)
                .then(this.renameTreeItem(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), treeRepresentationId, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION, "PortalRenamed"))
                .consumeNextWith(portalRefreshedEventPayloadMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private List<String> getAllTreeItemIdsForEcoreSampleProject() {
        var optionalEditingContext = this.editingContextSearchService.findById(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString())
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

    private Consumer<Object> getTreeRefreshedEventPayloadMatcher(List<TreeItemMatcher> treeItemMatchers) {
        return assertRefreshedTreeThat(tree -> {
            assertThat(treeItemMatchers).allMatch(treeItemMatcher -> {
                var treeItem = treeItemMatcher.treeItemFinder.apply(tree);
                return treeItemMatcher.treeItemPredicate.test(treeItem);
            });
        });
    }

    private Runnable deleteTreeItem(String editingContextId, String treeId, UUID treeItemId) {
        return () -> {
            var input = new DeleteTreeItemInput(UUID.randomUUID(), editingContextId, treeId, treeItemId);
            var result = this.deleteTreeItemMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.deleteTreeItem.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        };
    }

    private Runnable renameTreeItem(String editingContextId, String treeId, UUID treeItemId, String newLabel) {
        return () -> {
            var input = new RenameTreeItemInput(UUID.randomUUID(), editingContextId, treeId, treeItemId, newLabel);
            var result = this.renameTreeItemMutationRunner.run(input);

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
