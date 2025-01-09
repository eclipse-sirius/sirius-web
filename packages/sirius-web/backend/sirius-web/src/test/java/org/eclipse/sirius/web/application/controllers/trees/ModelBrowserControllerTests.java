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
import static org.assertj.core.api.Assertions.fail;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.browser.dto.ModelBrowserEventInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.modelbrowser.ModelBrowserEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.test.StepVerifier;

/**
 * Integration tests of the computation of the reference widget model browser.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModelBrowserControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ModelBrowserEventSubscriptionRunner treeEventSubscriptionRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget, when we ask for the model browser for a reference, then its content is properly returned")
    public void givenReferenceWidgetWhenWeAskForTheModelBrowserForReferenceThenItsContentIsProperlyReturned() {
        var representationId = this.representationIdBuilder.buildModelBrowserRepresentationId("reference", "siriusComponents://semantic?domain=view&entity=Entity", "siriusComponents://semantic?domain=view&entity=Entity", StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(), "domain.entity.superTypes", false, List.of(StudioIdentifiers.DOMAIN_DOCUMENT.toString(), StudioIdentifiers.DOMAIN_OBJECT.toString()));
        var input = new ModelBrowserEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, representationId);
        var flux = this.treeEventSubscriptionRunner.run(input);

        Consumer<Object> initialTreeContentConsumer = this.getTreeSubscriptionConsumer(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).isNotEmpty();

            var documentTreeItem = tree.getChildren().stream()
                    .filter(treeItem -> treeItem.getId().equals(StudioIdentifiers.DOMAIN_DOCUMENT.toString()))
                    .findFirst()
                    .orElse(null);
            assertThat(documentTreeItem.getChildren()).hasSize(1);

            var domainTreeItem = documentTreeItem.getChildren().get(0);
            assertThat(domainTreeItem.getId()).isEqualTo(StudioIdentifiers.DOMAIN_OBJECT.toString());
            assertThat(domainTreeItem.getChildren().stream().map(TreeItem::getId))
                    .hasSize(3)
                    .containsAll(List.of(
                            StudioIdentifiers.ROOT_ENTITY_OBJECT.toString(),
                            StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString(),
                            StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString()
                    ));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
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
