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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.trees.tests.graphql.ExpandAllTreePathQueryRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.studio.services.representations.DomainViewTreeDescriptionProvider;
import org.eclipse.sirius.web.application.studio.services.representations.api.IDomainDiagramDescriptionProvider;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
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
import reactor.test.StepVerifier;

/**
 * Integration tests of the tree representation with a Domain model.
 *
 * @author Jerome Gout
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DomainExplorerControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

    @Autowired
    private ExpandAllTreePathQueryRunner expandAllTreePathQueryRunner;

    @Autowired
    private DomainViewTreeDescriptionProvider domainViewTreeDescriptionProvider;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private IDomainDiagramDescriptionProvider domainDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an explorer representation, when we subscribe to its event, then the representation data are received")
    public void givenAnExplorerRepresentationWhenWeSubscribeToItsEventThenTheRepresentationDataAreReceived() {
        var representationId = new RepresentationIdBuilder().buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(), List.of());
        var defaultExplorerInput = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), representationId);
        var defaultFlux = this.explorerEventSubscriptionRunner.run(defaultExplorerInput);
        var defaultTreeId = new AtomicReference<String>();

        Consumer<Object> initialDefaultExplorerContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId()).isEqualTo(ExplorerDescriptionProvider.DESCRIPTION_ID);
            assertThat(tree.getChildren()).hasSize(5);
            assertThat(tree.getChildren()).allSatisfy(treeItem -> assertThat(treeItem.getChildren()).isEmpty());

            defaultTreeId.set(tree.getId());
        });

        StepVerifier.create(defaultFlux)
                .consumeNextWith(initialDefaultExplorerContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        representationId = new RepresentationIdBuilder().buildExplorerRepresentationId(this.domainViewTreeDescriptionProvider.getRepresentationDescriptionId(), List.of(), List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), representationId);
        var flux = this.explorerEventSubscriptionRunner.run(input);

        var treeId = new AtomicReference<String>();

        Consumer<Object> initialExplorerContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getDescriptionId().startsWith("siriusComponents://representationDescription?kind=treeDescription&sourceKind=view")).isTrue();
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren()).allSatisfy(treeItem -> assertThat(treeItem.getChildren()).isEmpty());

            treeId.set(tree.getId());
        });

        var treeItemIds = new AtomicReference<List<String>>();

        Runnable getTreePath = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                    "treeId", treeId.get(),
                    "treeItemId", StudioIdentifiers.DOMAIN_DOCUMENT.toString()
            );
            var result = this.expandAllTreePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result, "$.data.viewer.editingContext.expandAllTreePath.treeItemIdsToExpand");
            assertThat(treeItemIdsToExpand).isNotEmpty();

            treeItemIds.set(treeItemIdsToExpand);
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialExplorerContentConsumer)
                .then(getTreePath)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        representationId = new RepresentationIdBuilder().buildExplorerRepresentationId(this.domainViewTreeDescriptionProvider.getRepresentationDescriptionId(), treeItemIds.get(), List.of());
        var expandedTreeInput = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), representationId);
        var expandedTreeFlux = this.explorerEventSubscriptionRunner.run(expandedTreeInput);

        var settingId = new AtomicReference<String>();

        Consumer<Object> expandedExplorerContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getLabel().toString()).isEqualTo("Domain");
            assertThat(tree.getChildren().get(0).getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren().get(0).getLabel().toString()).isEqualTo("buck");
            assertThat(tree.getChildren().get(0).getChildren().get(0).getChildren()).hasSize(3);
            assertThat(tree.getChildren().get(0).getChildren().get(0).getChildren().get(2).getLabel().toString()).contains("[Entity] Human");
            assertThat(tree.getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren()).hasSize(4);
            assertThat(tree.getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(0).getLabel().toString()).isEqualTo("superTypes");
            settingId.set(tree.getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(0).getId());
        });

        Runnable getTreePathFromSetting = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                    "treeId", treeId.get(),
                    "treeItemId", settingId.get()
            );
            var result = this.expandAllTreePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result, "$.data.viewer.editingContext.expandAllTreePath.treeItemIdsToExpand");
            assertThat(treeItemIdsToExpand).isNotEmpty().hasSize(4); // Human:superTypes, NamedElement, NamedElement:superType, NamedElement:name
        };

        StepVerifier.create(expandedTreeFlux)
                .consumeNextWith(expandedExplorerContentConsumer)
                .then(getTreePathFromSetting)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a domain studio with a domain model and domain representation, when we subscribe to the domain view tree representation, then the tree item representing the domain representation is correct ")
    public void givenAStudioWithADomainModelAndDomainRepresentationWhenSubscribeToTheDomainViewTreeRepresentationThenTheTreeItemRepresentingTheDomainRepresentationIsCorrect() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        var createRepresentationInput = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), this.domainDiagramDescriptionProvider.getDescriptionId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain");
        this.givenCreatedDiagramSubscription.createAndSubscribe(createRepresentationInput);

        List<String> treeItemIds = new ArrayList<>();
        treeItemIds.add(StudioIdentifiers.DOMAIN_DOCUMENT.toString());
        treeItemIds.add(StudioIdentifiers.DOMAIN_OBJECT.toString());
        var representationId = new RepresentationIdBuilder().buildExplorerRepresentationId(this.domainViewTreeDescriptionProvider.getRepresentationDescriptionId(), treeItemIds, List.of());
        var expandedTreeInput = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), representationId);
        var expandedTreeFlux = this.explorerEventSubscriptionRunner.run(expandedTreeInput);

        Consumer<Object> expandedExplorerContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getLabel().toString()).isEqualTo("Domain");
            assertThat(tree.getChildren().get(0).getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren().get(0).getLabel().toString()).isEqualTo("buck");
            assertThat(tree.getChildren().get(0).getChildren().get(0).getChildren()).hasSize(4);

            var treeItem = tree.getChildren().get(0).getChildren().get(0).getChildren().get(0);
            assertThat(treeItem.getId()).isNotBlank();
            assertThat(treeItem.getKind()).isEqualTo(Diagram.KIND);
            assertThat(treeItem.getLabel().toString()).isEqualTo("Domain");
        });

        StepVerifier.create(expandedTreeFlux)
                .consumeNextWith(expandedExplorerContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
