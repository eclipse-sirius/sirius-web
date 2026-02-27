/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.application.controllers.selection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.sirius.components.trees.tests.TreeEventPayloadConsumer.assertRefreshedTreeThat;

import com.jayway.jsonpath.JsonPath;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.selection.dto.SelectionDialogTreeEventInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.tests.graphql.ExpandAllTreePathQueryRunner;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.selection.SelectionDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.eclipse.sirius.web.tests.services.selection.SelectionDialogTreeEventSubscriptionRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the selection controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,  properties = { "sirius.web.test.enabled=studio" })
public class SelectionControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String GET_SELECTION_DESCRIPTION = """
            query getSelectionDescription($editingContextId: ID!, $representationId: ID!, $variables: [SelectionDialogVariable!]!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    description {
                      ... on SelectionDescription {
                          message(variables: $variables)
                          noSelectionLabel(variables: $variables)
                          treeDescription {
                            id
                          }
                          multiple
                          optional
                        }
                    }
                  }
                }
              }
            }
            """;

    private static final String GET_TREE_EVENT_SUBSCRIPTION = """
            subscription selectionDialogTreeEvent($input: SelectionDialogTreeEventInput!) {
              selectionDialogTreeEvent(input: $input) {
                __typename
                ... on TreeRefreshedEventPayload {
                  id
                  tree {
                    id
                    children {
                      iconURL
                    }
                  }
                }
              }
            }
            """;


    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private SelectionDialogTreeEventSubscriptionRunner selectionDialogTreeEventSubscriptionRunner;

    @Autowired
    private SelectionDescriptionProvider selectionDescriptionProvider;

    @Autowired
    private ExpandAllTreePathQueryRunner expandAllTreePathQueryRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @Autowired
    private ILabelService labelService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a semantic object and a dialog description id, when requesting the Selection Description, then the Selection Description is sent")
    public void givenSemanticObjectWhenRequestingSelectionDescriptionThenTheSelectionDescriptionIsSent() {
        String representationId = "selectionDialog://?representationDescription=" + URLEncoder.encode(this.selectionDescriptionProvider.getSelectionDialogDescriptionId(), StandardCharsets.UTF_8);

        List<Map<String, Object>> variablesParameter = List.of(
                Map.of(
                        "name", "targetObjectId",
                        "value", PapayaIdentifiers.PROJECT_OBJECT.toString()
                ),
                Map.of(
                        "name", "sourceDiagramElementTargetObjectId",
                        "value", PapayaIdentifiers.FIRST_TASK_OBJECT.toString()
                )
        );

        Map<String, Object> variables = Map.of(
                "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                "representationId", representationId,
                "variables", variablesParameter);
        var result = this.graphQLRequestor.execute(GET_SELECTION_DESCRIPTION, variables);

        String message = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.message");
        String noSelectionLabel = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.noSelectionLabel");
        String treeDescriptionId = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.treeDescription.id");

        assertThat(message).isEqualTo("Select the objects to consider");
        assertThat(noSelectionLabel).isEqualTo("Execute the tool without making selection");
        assertThat(treeDescriptionId).isEqualTo(this.selectionDescriptionProvider.getSelectionDialogTreeDescriptionId());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a semantic object, when we subscribe to its selection dialog tree, then the tree is sent")
    public void givenSemanticObjectWhenWeSubscribeToItsSelectionEventsThenTheSelectionIsSent() {
        var representationId = this.representationIdBuilder.buildSelectionRepresentationId(this.selectionDescriptionProvider.getSelectionDialogTreeDescriptionId(), PapayaIdentifiers.PROJECT_OBJECT.toString(), List.of());
        var input = new SelectionDialogTreeEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationId);
        var flux = this.selectionDialogTreeEventSubscriptionRunner.run(input).flux();

        var hasResourceRootContent = assertRefreshedTreeThat(tree -> {
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getIconURL().get(0)).isEqualTo("/icons/Resource.svg");
            assertThat(tree.getChildren().get(0).getLabel().toString()).isEqualTo("Sirius Web Architecture");
            assertThat(tree.getChildren().get(0).getChildren()).isEmpty();
        });
        StepVerifier.create(flux)
            .consumeNextWith(hasResourceRootContent)
            .thenCancel()
            .verify();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the selection dialog tree, when we expand the first item, then the children are sent")
    public void givenSelectionDialogTreeWhenWeExpandTheFirstItemThenChildrenAreSent() {
        var representationId = this.representationIdBuilder.buildSelectionRepresentationId(this.selectionDescriptionProvider.getSelectionDialogTreeDescriptionId(), PapayaIdentifiers.PROJECT_OBJECT.toString(), List.of());
        var input = new SelectionDialogTreeEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationId);
        var flux = this.selectionDialogTreeEventSubscriptionRunner.run(input).flux();

        var treeItemId = new AtomicReference<String>();

        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren()).allSatisfy(treeItem -> assertThat(treeItem.getChildren()).isEmpty());

            treeItemId.set(tree.getChildren().get(0).getId());
        });

        StepVerifier.create(flux)
            .consumeNextWith(initialTreeContentConsumer)
            .thenCancel()
            .verify();

        var representationIdExpanded = this.representationIdBuilder.buildSelectionRepresentationId(this.selectionDescriptionProvider.getSelectionDialogTreeDescriptionId(), PapayaIdentifiers.PROJECT_OBJECT.toString(), List.of(treeItemId.get()));
        var expandedTreeInput = new SelectionDialogTreeEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationIdExpanded);
        var expandedTreeFlux = this.selectionDialogTreeEventSubscriptionRunner.run(expandedTreeInput).flux();

        var treeRefreshedEventPayloadExpandMatcher = assertRefreshedTreeThat(tree -> {
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren().get(0).getChildren()).isNotEmpty();
        });

        StepVerifier.create(expandedTreeFlux)
            .consumeNextWith(treeRefreshedEventPayloadExpandMatcher)
            .thenCancel()
            .verify();
    }


    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the selection dialog tree, when we perform expand all on the first item, then the children are sent")
    public void givenSelectionDialogTreeWhenWePerformExpandAllOnTheFirstItemThenChildrenAreSent() {
        var representationId = this.representationIdBuilder.buildSelectionRepresentationId(this.selectionDescriptionProvider.getSelectionDialogTreeDescriptionId(), PapayaIdentifiers.PROJECT_OBJECT.toString(), List.of());
        var input = new SelectionDialogTreeEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationId);
        var flux = this.selectionDialogTreeEventSubscriptionRunner.run(input).flux();

        var treeItemId = new AtomicReference<String>();
        var treeInstanceId = new AtomicReference<String>();

        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren()).allSatisfy(treeItem -> assertThat(treeItem.getChildren()).isEmpty());
            treeInstanceId.set(tree.getId());
            treeItemId.set(tree.getChildren().get(0).getId());
        });

        var treeItemIds = new AtomicReference<List<String>>();

        Runnable getTreePath = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "treeId", treeInstanceId.get(),
                    "treeItemId", treeItemId.get()
            );
            var result = this.expandAllTreePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result.data(), "$.data.viewer.editingContext.expandAllTreePath.treeItemIdsToExpand");
            assertThat(treeItemIdsToExpand).isNotEmpty();

            treeItemIds.set(treeItemIdsToExpand);
        };

        StepVerifier.create(flux)
            .consumeNextWith(initialTreeContentConsumer)
            .then(getTreePath)
            .thenCancel()
            .verify(Duration.ofSeconds(10));

        Consumer<Object> initialExpandedTreeContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren()).anySatisfy(treeItem -> assertThat(treeItem.getChildren()).isNotEmpty());

        });

        var representationIdExpanded = this.representationIdBuilder.buildSelectionRepresentationId(this.selectionDescriptionProvider.getSelectionDialogTreeDescriptionId(), PapayaIdentifiers.PROJECT_OBJECT.toString(), treeItemIds.get());
        var expandedTreeInput = new SelectionDialogTreeEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationIdExpanded);
        var expandedTreeFlux = this.selectionDialogTreeEventSubscriptionRunner.run(expandedTreeInput).flux();

        StepVerifier.create(expandedTreeFlux)
                .consumeNextWith(initialExpandedTreeContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the selection dialog tree, when we perform expand all on the second item (the root project), then the children are sent")
    public void givenSelectionDialogTreeWhenWePerformExpandAllOnTheSecondItemThenChildrenAreSent() {
        var representationId = this.representationIdBuilder.buildSelectionRepresentationId(this.selectionDescriptionProvider.getSelectionDialogTreeDescriptionId(), PapayaIdentifiers.PROJECT_OBJECT.toString(), List.of());
        var input = new SelectionDialogTreeEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationId);
        var flux = this.selectionDialogTreeEventSubscriptionRunner.run(input).flux();

        var rootTreeItemId = new AtomicReference<String>();
        var treeInstanceId = new AtomicReference<String>();

        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(1);
            assertThat(tree.getChildren()).allSatisfy(treeItem -> assertThat(treeItem.getChildren()).isEmpty());
            treeInstanceId.set(tree.getId());
            rootTreeItemId.set(tree.getChildren().get(0).getId());
        });

        StepVerifier.create(flux)
            .consumeNextWith(initialTreeContentConsumer)
            .thenCancel()
            .verify(Duration.ofSeconds(10));

        //We expand the first tree item (representing the resource)
        var representationIdExpandedFirstTreeItem = this.representationIdBuilder.buildSelectionRepresentationId(this.selectionDescriptionProvider.getSelectionDialogTreeDescriptionId(), PapayaIdentifiers.PROJECT_OBJECT.toString(), List.of(rootTreeItemId.get()));
        var expandedFirstTreeItemInput = new SelectionDialogTreeEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationIdExpandedFirstTreeItem);
        var expandedFirstTreeItemFlux = this.selectionDialogTreeEventSubscriptionRunner.run(expandedFirstTreeItemInput).flux();

        //Used to retrieve the Papaya Root Project tree item
        var rootProjectTreeItemId = new AtomicReference<String>();
        Consumer<Object> firstTreeItemExpandedContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(1);
            var rootResourceTreeItem = tree.getChildren().get(0);
            assertThat(tree.getChildren()).hasSize(1);
            rootProjectTreeItemId.set(rootResourceTreeItem.getChildren().get(0).getId());
        });

        var treeItemIds = new AtomicReference<List<String>>();

        Runnable getTreePath = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "treeId", treeInstanceId.get(),
                    "treeItemId", rootProjectTreeItemId.get()
                    );
            var result = this.expandAllTreePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result.data(), "$.data.viewer.editingContext.expandAllTreePath.treeItemIdsToExpand");
            assertThat(treeItemIdsToExpand).isNotEmpty();
            treeItemIdsToExpand.add(0, rootTreeItemId.get());
            treeItemIds.set(treeItemIdsToExpand);
        };

        StepVerifier.create(expandedFirstTreeItemFlux)
                .consumeNextWith(firstTreeItemExpandedContentConsumer)
                //Now that we have expand and retrieve the Papaya Root Project tree item, we can perform the expandAll from it
                .then(getTreePath)
                .thenCancel()
                .verify(Duration.ofSeconds(10));


        Consumer<Object> initialExpandedTreeContentConsumer = assertRefreshedTreeThat(tree -> {
            assertThat(tree).isNotNull();
            assertThat(tree.getChildren()).hasSize(1);
            TreeItem rootProjectTreeItem = tree.getChildren().get(0);
            assertThat(rootProjectTreeItem.getChildren()).isNotEmpty();
        });

        var representationIdExpanded = this.representationIdBuilder.buildSelectionRepresentationId(this.selectionDescriptionProvider.getSelectionDialogTreeDescriptionId(), PapayaIdentifiers.PROJECT_OBJECT.toString(), treeItemIds.get());
        var expandedTreeInput = new SelectionDialogTreeEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationIdExpanded);
        var expandedTreeFlux = this.selectionDialogTreeEventSubscriptionRunner.run(expandedTreeInput).flux();
        // We now verify the expandAll result
        StepVerifier.create(expandedTreeFlux)
                .consumeNextWith(initialExpandedTreeContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a semantic object, when we subscribe to its selection dialog tree, then the URL of its treeItems is valid")
    public void givenSemanticObjectWhenWeSubscribeToItsSelectionEventsThenTheURLOfItsObjectsIsValid() {
        var representationId = this.representationIdBuilder.buildSelectionRepresentationId(this.selectionDescriptionProvider.getSelectionDialogTreeDescriptionId(), PapayaIdentifiers.PROJECT_OBJECT.toString(), List.of());
        var input = new SelectionDialogTreeEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationId);
        var flux = this.graphQLRequestor.subscribeToSpecification(GET_TREE_EVENT_SUBSCRIPTION, input)
                .flux()
                .map(Object::toString);

        Consumer<String> treeContentConsumer = payload -> Optional.of(payload)
                .ifPresentOrElse(body -> {
                    String typename = JsonPath.read(body, "$.data.selectionDialogTreeEvent.__typename");
                    assertThat(typename).isEqualTo(TreeRefreshedEventPayload.class.getSimpleName());

                    List<List<String>> objectIconURLs = JsonPath.read(body, "$.data.selectionDialogTreeEvent.tree.children[*].iconURL");
                    assertThat(objectIconURLs)
                            .isNotEmpty()
                            .allSatisfy(iconURLs -> {
                                assertThat(iconURLs)
                                        .isNotEmpty()
                                        .hasSize(1)
                                        .allSatisfy(iconURL -> assertThat(iconURL).startsWith(URLConstants.IMAGE_BASE_PATH));
                            });
                }, () -> fail("Missing selection"));

        StepVerifier.create(flux)
                .consumeNextWith(treeContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("given a selectionDescription then the image and the label are the expected ones")
    public void givenASelectionDescriptionThenTheImageAndLabelAreTheExpectedOnes() {
        SelectionDialogDescription selectionDRialogDescription = this.selectionDescriptionProvider.getSelectionDialog();
        String selectionDialogLabel = this.labelService.getStyledLabel(selectionDRialogDescription).toString();
        assertThat(selectionDialogLabel).isEqualTo(selectionDRialogDescription.getSelectionMessage());
        List<String> imagePath = this.labelService.getImagePaths(selectionDRialogDescription);
        assertThat(imagePath).hasSize(1).first().isEqualTo("/icons/full/obj16/SelectionDialogDescription.svg");

        SelectionDialogTreeDescription selectionDialogTreeDescription = selectionDRialogDescription.getSelectionDialogTreeDescription();
        String selectionDialogTreeDescriptionLabel = this.labelService.getStyledLabel(selectionDialogTreeDescription).toString();
        assertThat(selectionDialogTreeDescriptionLabel).isEqualTo(selectionDialogTreeDescription.getElementsExpression());
        List<String> selectionDialogTreeDescriptionImagePath = this.labelService.getImagePaths(selectionDialogTreeDescription);
        assertThat(selectionDialogTreeDescriptionImagePath).hasSize(1).first().isEqualTo("/icons/full/obj16/SelectionDialogTreeDescription.svg");
    }

}
