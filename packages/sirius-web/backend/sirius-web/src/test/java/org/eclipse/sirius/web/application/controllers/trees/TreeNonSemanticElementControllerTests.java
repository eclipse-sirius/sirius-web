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

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeEventInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.tests.graphql.ExpandAllTreePathQueryRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.tree.DomainTreeDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedTreeSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.eclipse.sirius.web.tests.services.tree.TreeEventSubscriptionRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of a non semantic element in a tree representation.
 *
 * @author Jerome Gout
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TreeNonSemanticElementControllerTests extends AbstractIntegrationTests {

    private static final String ROOT_ENTITY_ID = "c341bf91-d315-4264-9787-c51b121a6375";

    private static final String ROOT_SETTING_ID = "setting:c341bf91-d315-4264-9787-c51b121a6375::superTypes";

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedTreeSubscription givenCreatedTreeSubscription;

    @Autowired
    private TreeEventSubscriptionRunner treeEventSubscriptionRunner;

    @Autowired
    private ExpandAllTreePathQueryRunner expandAllTreePathQueryRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToTree() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                DomainTreeDescriptionProvider.DESCRIPTION_ID,
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "Tree"
        );
        return this.givenCreatedTreeSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a domain tree representation, when we subscribe to its event, then the representation data contains a superTypes node")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenADomainTreeRepresentationWhenWeSubscribeToItsEventThenTheRepresentationDataContainsASuperTypesNode() {

        var flux = this.givenSubscriptionToTree();

        var treeId = new AtomicReference<String>();

        var initialTreeContentConsumer = this.getTreeSubscriptionConsumer(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(1);
            treeId.set(tree.getId());
        });

        Runnable getTreePathFromSetting = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                    "treeId", treeId.get(),
                    "treeItemId", ROOT_SETTING_ID
            );
            var result = this.expandAllTreePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result, "$.data.viewer.editingContext.expandAllTreePath.treeItemIdsToExpand");
            assertThat(treeItemIdsToExpand).isNotEmpty();
            assertThat(treeItemIdsToExpand.stream().filter(id -> id.startsWith(DomainTreeDescriptionProvider.SETTING)).toList()).hasSize(1);
        };

        Runnable getTreePath = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                    "treeId", treeId.get(),
                    "treeItemId", StudioIdentifiers.DOMAIN_OBJECT.toString()
                    );
            var result = this.expandAllTreePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result, "$.data.viewer.editingContext.expandAllTreePath.treeItemIdsToExpand");
            assertThat(treeItemIdsToExpand).isNotEmpty();
            assertThat(treeItemIdsToExpand.stream().filter(id -> id.startsWith(DomainTreeDescriptionProvider.SETTING)).toList()).hasSize(3);
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
                .then(getTreePathFromSetting)
                .then(getTreePath)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        String representationId = this.representationIdBuilder.buildTreeRepresentationId(treeId.get().substring(0, treeId.get().indexOf("?expandedIds=")), List.of(StudioIdentifiers.DOMAIN_OBJECT.toString(), ROOT_ENTITY_ID));

        var expandedTreeInput = new TreeEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), representationId);
        Flux<Object> expandedTreeFlux = this.treeEventSubscriptionRunner.run(expandedTreeInput);

        Consumer<Object> initialExpandedTreeContentConsumer = this.getTreeSubscriptionConsumer(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren()).hasSize(4);
            assertThat(tree.getChildren().get(0).getChildren().get(1).getChildren()).hasSize(3);
            assertThat(tree.getChildren().get(0).getChildren().get(1).getChildren().get(0).getId()).startsWith(DomainTreeDescriptionProvider.SETTING);
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
