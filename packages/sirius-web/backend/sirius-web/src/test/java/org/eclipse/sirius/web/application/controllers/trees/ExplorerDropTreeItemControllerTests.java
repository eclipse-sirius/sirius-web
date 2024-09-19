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
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.trees.dto.DropTreeItemInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.trees.tests.graphql.DropTreeItemMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
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
 * Integration tests of the drop tree item mutation in the explorer.
 *
 * @author frouene
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExplorerDropTreeItemControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;
    @Autowired
    private ExplorerEventSubscriptionRunner treeEventSubscriptionRunner;
    @Autowired
    private DropTreeItemMutationRunner dropTreeItemMutationRunner;
    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a studio, when we drag and drop an item in the explore, then the object is moved")
    @Sql(scripts = { "/scripts/studio.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenStudioWhenWeDragAndDropAnItemThenTheObjectIsMoved() {
        var explorerRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(StudioIdentifiers.DOMAIN_DOCUMENT.toString(), StudioIdentifiers.DOMAIN_OBJECT.toString(),
                        StudioIdentifiers.ROOT_ENTITY_OBJECT.toString(), StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString()),
                List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), explorerRepresentationId);
        var flux = this.treeEventSubscriptionRunner.run(input);


        Consumer<Object> initialTreeContentConsumer = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(TreeRefreshedEventPayload.class::isInstance)
                .map(TreeRefreshedEventPayload.class::cast)
                .map(TreeRefreshedEventPayload::tree)
                .ifPresentOrElse(tree -> {
                    assertThat(tree).isNotNull();
                    assertThat(tree.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren()).hasSize(2);
                    assertThat(tree.getChildren().get(1).getChildren().get(0).getChildren().get(1).getChildren()).hasSize(1);
                    assertThat(tree.getChildren().get(1).getChildren().get(0).getChildren().get(1).getChildren()).anyMatch(treeItem -> treeItem.getId()
                            .equals(StudioIdentifiers.NAME_ATTRIBUTE_OBJECT.toString()));
                }, () -> fail("Missing tree"));

        Runnable dropItemMutation = () -> {
            DropTreeItemInput dropTreeItemInput = new DropTreeItemInput(
                    UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                    explorerRepresentationId,
                    List.of(StudioIdentifiers.NAME_ATTRIBUTE_OBJECT.toString()),
                    StudioIdentifiers.ROOT_ENTITY_OBJECT.toString(),
                    -1
            );
            var result = this.dropTreeItemMutationRunner.run(dropTreeItemInput);
            String typename = JsonPath.read(result, "$.data.dropTreeItem.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        };


        Consumer<Object> updateTreeContentConsumer = object -> {
            Optional.of(object)
                    .filter(DataFetcherResult.class::isInstance)
                    .map(DataFetcherResult.class::cast)
                    .map(DataFetcherResult::getData)
                    .filter(TreeRefreshedEventPayload.class::isInstance)
                    .map(TreeRefreshedEventPayload.class::cast)
                    .map(TreeRefreshedEventPayload::tree)
                    .ifPresentOrElse(tree -> {
                        assertThat(tree).isNotNull();
                        assertThat(tree.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren()).hasSize(3);
                        assertThat(tree.getChildren().get(1).getChildren().get(0).getChildren().get(1).getChildren()).hasSize(0);
                        assertThat(tree.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren()).anyMatch(treeItem -> treeItem.getId()
                                .equals(StudioIdentifiers.NAME_ATTRIBUTE_OBJECT.toString()));
                    }, () -> fail("Missing tree"));
        };


        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
                .then(dropItemMutation)
                .consumeNextWith(updateTreeContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
