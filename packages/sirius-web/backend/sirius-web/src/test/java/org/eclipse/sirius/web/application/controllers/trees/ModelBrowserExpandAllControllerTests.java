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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.browser.dto.ModelBrowserEventInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionSuccessPayload;
import org.eclipse.sirius.components.trees.tests.graphql.ExpandAllTreePathQueryRunner;
import org.eclipse.sirius.components.trees.tests.graphql.TreePathQueryRunner;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.browser.DefaultModelBrowsersTreeDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.modelbrowser.ModelBrowserEventSubscriptionRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the computation of the expand all tree path in reference widget model browser.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModelBrowserExpandAllControllerTests extends AbstractIntegrationTests {

    private static final String TREE_ID = "modelBrowser://?treeDescriptionId=" + DefaultModelBrowsersTreeDescriptionProvider.CONTAINER_DESCRIPTION_ID + "&ownerKind=siriusComponents%3A%2F%2Fsemantic%3Fdomain%3Dview%26entity%3DUserColor&targetType=siriusComponents%3A%2F%2Fsemantic%3Fdomain%3Dview%26entity%3DUserColor" +
            "&ownerId=7ec7c9bf-96a1-4f69-8441-176161c08877&descriptionId=rectangular.nodestyle.background&isContainment=false";

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ModelBrowserEventSubscriptionRunner treeEventSubscriptionRunner;

    @Autowired
    private ExpandAllTreePathQueryRunner expandAllTreePathQueryRunner;

    @Autowired
    private TreePathQueryRunner treePathQueryRunner;

    @Autowired
    private IIdentityService identityService;

    @Autowired
    private ExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget, when we ask for the tree path to expand all a document, then its path in the explorer is returned")
    public void givenReferenceWidgetWhenWeAskForTheTreePathToExpandAllThenItsPathInTheExplorerIsReturned() {
        var representationId = TREE_ID + "&expandedIds=[]";
        var input = new ModelBrowserEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, representationId);
        var flux = this.treeEventSubscriptionRunner.run(input);

        var treeId = new AtomicReference<String>();
        var objectId = new AtomicReference<String>();

        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(6);
            assertThat(tree.getChildren()).allSatisfy(treeItem -> assertThat(treeItem.getChildren()).isEmpty());

            treeId.set(tree.getId());
            objectId.set(tree.getChildren().get(0).getId());
        });


        var treeItemIds = new AtomicReference<List<String>>();

        Runnable getTreePath = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    "treeId", treeId.get(),
                    "treeItemId", objectId.get()
            );
            var result = this.expandAllTreePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result, "$.data.viewer.editingContext.expandAllTreePath.treeItemIdsToExpand");
            assertThat(treeItemIdsToExpand).isNotEmpty();

            treeItemIds.set(treeItemIdsToExpand);
        };


        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
                .then(getTreePath)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var expandedRepresentationId = TREE_ID + "&expandedIds=[" + String.join(",", treeItemIds.get()) + "]";
        var expandedTreeInput = new ModelBrowserEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, expandedRepresentationId);
        var expandedTreeFlux = this.treeEventSubscriptionRunner.run(expandedTreeInput);

        Consumer<Object> initialExpandedTreeContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(6);
            assertThat(tree.getChildren().get(0).isExpanded()).isTrue();
            assertThat(tree.getChildren()).anySatisfy(treeItem -> assertThat(treeItem.getChildren()).isNotEmpty());

            treeId.set(tree.getId());
        });

        StepVerifier.create(expandedTreeFlux)
                .consumeNextWith(initialExpandedTreeContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when we ask for the tree path of a color object, then its path in the model browser is returned")
    public void givenStudioWhenWeAskForTheTreePathOfAnObjectInTheModelBrowserThenItsPathInTheModelBrowserIsReturned() {
        var representationId = TREE_ID + "&expandedIds=[]";
        var input = new ModelBrowserEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, representationId);
        var flux = this.treeEventSubscriptionRunner.run(input);

        var treeId = new AtomicReference<String>();
        var colorObjectId = new AtomicReference<String>();

        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> treeId.set(tree.getId()));

        Runnable readObjectId = () -> {
            BiFunction<IEditingContext, IInput, IPayload> getObjectIdFunction = (editingContext, executeEditingContextFunctionInput) -> {
                var id = Optional.of(editingContext)
                        .filter(IEMFEditingContext.class::isInstance)
                        .map(IEMFEditingContext.class::cast)
                        .flatMap(emfEditingContext -> {
                            var iterator = emfEditingContext.getDomain().getResourceSet().getAllContents();
                            var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
                            return stream.filter(eObject -> eObject instanceof FixedColor).findFirst();
                        })
                        .map(this.identityService::getId)
                        .orElse("");
                return new ExecuteEditingContextFunctionSuccessPayload(executeEditingContextFunctionInput.id(), id);
            };

            var getObjectIdInput = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), getObjectIdFunction, new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), input));
            var payload = this.executeEditingContextFunctionRunner.execute(getObjectIdInput).block();

            assertThat(payload).isInstanceOf(ExecuteEditingContextFunctionSuccessPayload.class);
            ExecuteEditingContextFunctionSuccessPayload successPayload = (ExecuteEditingContextFunctionSuccessPayload) payload;
            colorObjectId.set(successPayload.result().toString());
        };

        Consumer<Object> updatedTreeContentConsumer = assertRefreshedTreeThat(tree -> assertThat(tree).isNotNull());

        Runnable getTreePath = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                    "treeId", treeId.get(),
                    "selectionEntryIds", List.of(colorObjectId.get())
            );
            var result = this.treePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result, "$.data.viewer.editingContext.treePath.treeItemIdsToExpand");
            // The first color is inside: 1. the studioColorPalettes document, 2. the View container, 3. the "Theme Colors Palette".
            // So we expect to get 3 parents to expand to reveal it.
            assertThat(treeItemIdsToExpand).hasSize(3);
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
                .then(readObjectId)
                .consumeNextWith(updatedTreeContentConsumer)
                .then(getTreePath)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
