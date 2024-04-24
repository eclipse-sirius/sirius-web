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
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.FadeDiagramElementInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.HideDiagramElementInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.PinDiagramElementInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.EditTreeCheckboxInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.PropertiesEventInput;
import org.eclipse.sirius.components.collaborative.forms.dto.PushButtonInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.tests.graphql.FadeDiagramElementMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.HideDiagramElementMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.PinDiagramElementMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.components.forms.Button;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.SplitButton;
import org.eclipse.sirius.components.forms.TreeNode;
import org.eclipse.sirius.components.forms.TreeWidget;
import org.eclipse.sirius.components.forms.tests.graphql.EditTreeCheckboxMutationRunner;
import org.eclipse.sirius.components.forms.tests.graphql.PushButtonMutationRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.ExpandCollapseDiagramDescriptionProvider;
import org.eclipse.sirius.web.tests.graphql.DiagramFilterEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the "Diagram Filter" view.
 *
 * @author gdaniel
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DiagramFilterControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private InvokeSingleClickOnDiagramElementToolMutationRunner invokeSingleClickOnDiagramElementToolMutationRunner;

    @Autowired
    private EditTreeCheckboxMutationRunner editTreeCheckboxMutationRunner;

    @Autowired
    private PushButtonMutationRunner pushButtonMutationRunner;

    @Autowired
    private FadeDiagramElementMutationRunner fadeDiagramElementMutationRunner;

    @Autowired
    private PinDiagramElementMutationRunner pinDiagramElementMutationRunner;

    @Autowired
    private HideDiagramElementMutationRunner hideDiagramElementMutationRunner;

    @Autowired
    private ExpandCollapseDiagramDescriptionProvider expandCollapseDiagramDescriptionProvider;

    @Autowired
    private DiagramFilterEventSubscriptionRunner diagramFilterEventSubscriptionRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<DiagramRefreshedEventPayload> givenSubscriptionToExpandedCollapseDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                this.expandCollapseDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "ExpandCollapseDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a diagram and a diagram filter, when a tool collapsing nodes is invoked on the diagram, then the diagram filter is updated")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramAndDiagramFilterWhenToolCollapsingNodeIsInvokedOnDiagramThenDiagramFilterIsUpdated() {
        BiFunction<Diagram, String, Void> collapseNodes = (diagram, nodeId) -> {
            String collapseToolId = this.expandCollapseDiagramDescriptionProvider.getCollapseNodeToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagram.getId(), nodeId, collapseToolId, 0, 0, null);
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());

            return null;
        };

        this.givenDiagramAndDiagramFilterWhenOperationIsPerformedOnDiagramThenDiagramFilterIsUpdated(collapseNodes);
    }

    @Test
    @DisplayName("Given a diagram and a diagram filter, when a tool fading nodes is invoked on the diagram, then the diagram filter is updated")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramAndDiagramFilterWhenToolFadingNodeIsInvokedOnDiagramThenDiagramFilterIsUpdated() {
        BiFunction<Diagram, String, Void> fadeNodes = (diagram, nodeId) -> {
            var input = new FadeDiagramElementInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagram.getId(), Set.of(nodeId), true);
            var result = this.fadeDiagramElementMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.fadeDiagramElement.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

            return null;
        };

        this.givenDiagramAndDiagramFilterWhenOperationIsPerformedOnDiagramThenDiagramFilterIsUpdated(fadeNodes);
    }

    @Test
    @DisplayName("Given a diagram and a diagram filter, when a tool pinning nodes is invoked on the diagram, then the diagram filter is updated")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramAndDiagramFilterWhenToolPinningNodeIsInvokedOnDiagramThenDiagramFilterIsUpdated() {
        BiFunction<Diagram, String, Void> pinNodes = (diagram, nodeId) -> {
            var input = new PinDiagramElementInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagram.getId(), Set.of(nodeId), true);
            var result = this.pinDiagramElementMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.pinDiagramElement.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

            return null;
        };

        this.givenDiagramAndDiagramFilterWhenOperationIsPerformedOnDiagramThenDiagramFilterIsUpdated(pinNodes);
    }

    @Test
    @DisplayName("Given a diagram and a diagram filter, when a tool hiding nodes is invoked on the diagram, then the diagram filter is updated")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramAndDiagramFilterWhenToolHidingNodeIsInvokedOnDiagramThenDiagramFilterIsUpdated() {
        BiFunction<Diagram, String, Void> hideNodes = (diagram, nodeId) -> {
            var input = new HideDiagramElementInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), diagram.getId(), Set.of(nodeId), true);
            var result = this.hideDiagramElementMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.hideDiagramElement.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

            return null;
        };

        this.givenDiagramAndDiagramFilterWhenOperationIsPerformedOnDiagramThenDiagramFilterIsUpdated(hideNodes);
    }

    private void givenDiagramAndDiagramFilterWhenOperationIsPerformedOnDiagramThenDiagramFilterIsUpdated(BiFunction<Diagram, String, Void> operationToPerform) {
        var diagramFlux = this.givenSubscriptionToExpandedCollapseDiagram();

        AtomicReference<Diagram> diagramReference = new AtomicReference<>();
        AtomicReference<String> nodeId = new AtomicReference<>();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramReference.set(diagram);
                    diagram.getNodes().stream()
                            .filter(node -> node.getCollapsingState().equals(CollapsingState.EXPANDED))
                            .map(Node::getId)
                            .findFirst()
                            .ifPresent(nodeId::set);
                }, () -> fail("Missing diagram"));

        StepVerifier.create(diagramFlux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var propertiesInput = new PropertiesEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), List.of(diagramReference.get().getId()));
        var diagramFilterFlux = this.diagramFilterEventSubscriptionRunner.run(propertiesInput);

        Predicate<Object> formContentMatcher = object -> this.refreshedForm(object)
                .filter(form -> {
                    var diagramNavigator = new DiagramNavigator(diagramReference.get());
                    var treeWidget = new FormNavigator(form)
                            .page("ExpandCollapseDiagram")
                            .group("Filter elements")
                            .findWidget("", TreeWidget.class);
                    assertThat(treeWidget.getNodes()).hasSize(diagramNavigator.findDiagramNodeCount());
                    for (TreeNode treeNode : treeWidget.getNodes()) {
                        var node = diagramNavigator.nodeWithLabel(treeNode.getLabel()).getNode();
                        this.assertThatNodeMatchesTreeNodeEndIcons(node, treeNode);
                    }
                    return true;
                })
                .isPresent();

        Consumer<Object> udpatedDiagramContentConsumer = object -> this.refreshedDiagram(object).ifPresentOrElse(diagramReference::set, () -> fail("Missing diagram"));

        var diagramAndPropertiesFlux = Flux.merge(diagramFlux, diagramFilterFlux);

        StepVerifier.create(diagramAndPropertiesFlux)
                .expectNextMatches(DiagramRefreshedEventPayload.class::isInstance)
                .expectNextMatches(formContentMatcher)
                .then(() -> operationToPerform.apply(diagramReference.get(), nodeId.get()))
                .consumeNextWith(udpatedDiagramContentConsumer)
                .expectNextMatches(formContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a diagram and a diagram filter, when a tool collapsing nodes is invoked on the diagram filter, then the diagram is updated")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramAndDiagramFilterWhenToolCollapsingNodeIsInvokedOnDiagramFilterThenDiagramIsUpdated() {
        Predicate<Node> nodeToSelectPredicate = node -> node.getCollapsingState().equals(CollapsingState.EXPANDED);

        Predicate<Button> buttonToClickPredicate = button -> button.getButtonLabel().equals("Collapse");
        Predicate<Button> buttonToClickToRevertPredicate = button -> button.getButtonLabel().equals("Expand");

        BiFunction<Diagram, String, Boolean> isDiagramUpdatedPredicate = (diagram, nodeId) -> new DiagramNavigator(diagram)
                .nodeWithId(nodeId)
                .getNode()
                .getCollapsingState().equals(CollapsingState.COLLAPSED);

        this.givenDiagramAndDiagramFilterWhenNodesAreSelectedAndActionIsPerformedThenDiagramIsUpdated(nodeToSelectPredicate, buttonToClickPredicate, buttonToClickToRevertPredicate, isDiagramUpdatedPredicate);
    }

    @Test
    @DisplayName("Given a diagram and a diagram filter, when a tool fading nodes is invoked on the diagram filter, then the diagram is updated")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramAndDiagramFilterWhenToolFadingNodeIsInvokedOnDiagramFilterThenDiagramIsUpdated() {
        Predicate<Node> nodeToSelectPredicate = node -> !node.getModifiers().contains(ViewModifier.Faded);

        Predicate<Button> buttonToClickPredicate = button -> button.getButtonLabel().equals("Fade");
        Predicate<Button> buttonToClickToRevertPredicate = button -> button.getButtonLabel().equals("Reveal faded elements");

        BiFunction<Diagram, String, Boolean> isDiagramUpdatedPredicate = (diagram, nodeId) -> new DiagramNavigator(diagram)
                .nodeWithId(nodeId)
                .getNode()
                .getModifiers().contains(ViewModifier.Faded);

        this.givenDiagramAndDiagramFilterWhenNodesAreSelectedAndActionIsPerformedThenDiagramIsUpdated(nodeToSelectPredicate, buttonToClickPredicate, buttonToClickToRevertPredicate, isDiagramUpdatedPredicate);
    }

    @Test
    @DisplayName("Given a diagram and a diagram filter, when a tool hiding nodes is invoked on the diagram filter, then the diagram is updated")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramAndDiagramFilterWhenToolHidingNodeIsInvokedOnDiagramFilterThenDiagramIsUpdated() {
        Predicate<Node> nodeToSelectPredicate = node -> !node.getModifiers().contains(ViewModifier.Hidden);

        Predicate<Button> buttonToClickPredicate = button -> button.getButtonLabel().equals("Hide");
        Predicate<Button> buttonToClickToRevertPredicate = button -> button.getButtonLabel().equals("Show");

        BiFunction<Diagram, String, Boolean> isDiagramUpdatedPredicate = (diagram, nodeId) -> new DiagramNavigator(diagram)
                .nodeWithId(nodeId)
                .getNode()
                .getModifiers().contains(ViewModifier.Hidden);

        this.givenDiagramAndDiagramFilterWhenNodesAreSelectedAndActionIsPerformedThenDiagramIsUpdated(nodeToSelectPredicate, buttonToClickPredicate, buttonToClickToRevertPredicate, isDiagramUpdatedPredicate);
    }

    @Test
    @DisplayName("Given a diagram and a diagram filter, when a tool pinning nodes is invoked on the diagram filter, then the diagram is updated")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDiagramAndDiagramFilterWhenToolPinningNodeIsInvokedOnDiagramFilterThenDiagramIsUpdated() {
        Predicate<Node> nodeToSelectPredicate = node -> !node.isPinned();

        Predicate<Button> buttonToClickPredicate = button -> button.getButtonLabel().equals("Pin");
        Predicate<Button> buttonToClickToRevertPredicate = button -> button.getButtonLabel().equals("Unpin");

        BiFunction<Diagram, String, Boolean> isDiagramUpdatedPredicate = (diagram, nodeId) -> new DiagramNavigator(diagram)
                .nodeWithId(nodeId)
                .getNode()
                .isPinned();

        this.givenDiagramAndDiagramFilterWhenNodesAreSelectedAndActionIsPerformedThenDiagramIsUpdated(nodeToSelectPredicate, buttonToClickPredicate, buttonToClickToRevertPredicate, isDiagramUpdatedPredicate);
    }

    private void givenDiagramAndDiagramFilterWhenNodesAreSelectedAndActionIsPerformedThenDiagramIsUpdated(Predicate<Node> nodeToSelectPredicate, Predicate<Button> buttonToClickPredicate, Predicate<Button> buttonToClickToRevertPredicate, BiFunction<Diagram, String, Boolean> isDiagramUpdatedPredicate) {
        var diagramFlux = this.givenSubscriptionToExpandedCollapseDiagram();

        AtomicReference<Diagram> diagramReference = new AtomicReference<>();
        AtomicReference<String> nodeId = new AtomicReference<>();

        Consumer<DiagramRefreshedEventPayload> initialDiagramContentConsumer = payload -> Optional.of(payload)
                .map(DiagramRefreshedEventPayload::diagram)
                .ifPresentOrElse(diagram -> {
                    diagramReference.set(diagram);
                    diagram.getNodes().stream()
                            .filter(nodeToSelectPredicate)
                            .map(Node::getId)
                            .findFirst()
                            .ifPresent(nodeId::set);
                }, () -> fail("Missing diagram"));

        StepVerifier.create(diagramFlux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var propertiesInput = new PropertiesEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), List.of(diagramReference.get().getId()));
        var diagramFilterFlux = this.diagramFilterEventSubscriptionRunner.run(propertiesInput);

        AtomicReference<String> formId = new AtomicReference<>();
        AtomicReference<String> treeId = new AtomicReference<>();
        AtomicReference<String> treeNodeToCheckId = new AtomicReference<>();

        Predicate<Object> initialFormContentMatcher = object -> this.refreshedForm(object)
                .filter(form -> {
                    formId.set(form.getId());
                    var diagramNavigator = new DiagramNavigator(diagramReference.get());
                    var treeWidget = new FormNavigator(form)
                            .page("ExpandCollapseDiagram")
                            .group("Filter elements")
                            .findWidget("", TreeWidget.class);
                    treeId.set(treeWidget.getId());
                    for (TreeNode treeNode : treeWidget.getNodes()) {
                        var node = diagramNavigator.nodeWithLabel(treeNode.getLabel()).getNode();
                        if (node.getId().equals(nodeId.get())) {
                            treeNodeToCheckId.set(treeNode.getId());
                        }
                    }
                    return true;
                })
                .isPresent();

        AtomicReference<String> actionId = new AtomicReference<>();
        AtomicReference<String> revertActionId = new AtomicReference<>();

        Predicate<Object> updatedFormContentMatcher = object -> this.refreshedForm(object)
                .filter(form -> {
                    // We have to set the collapseButtonId after we check the tree node because the id of split button actions change between renders.
                    var splitButton = new FormNavigator(form)
                            .page("ExpandCollapseDiagram")
                            .group("Filter elements")
                            .findWidget("Apply to 1 selected element: ", SplitButton.class);
                    splitButton.getActions().stream()
                            .filter(buttonToClickPredicate)
                            .map(Button::getId)
                            .findFirst()
                            .ifPresent(actionId::set);
                    splitButton.getActions().stream()
                            .filter(buttonToClickToRevertPredicate)
                            .map(Button::getId)
                            .findFirst()
                            .ifPresent(revertActionId::set);
                    return true;
                })
                .isPresent();

        Predicate<Object> updatedDiagramContentMatcher = object -> this.refreshedDiagram(object)
                .filter(diagram -> isDiagramUpdatedPredicate.apply(diagram, nodeId.get()))
                .isPresent();

        Predicate<Object> revertedDiagramContentMatcher = object -> this.refreshedDiagram(object)
                .filter(Predicate.not(diagram -> isDiagramUpdatedPredicate.apply(diagram, nodeId.get())))
                .isPresent();

        var diagramAndPropertiesFlux = Flux.merge(diagramFlux, diagramFilterFlux);
        StepVerifier.create(diagramAndPropertiesFlux)
                .expectNextMatches(DiagramRefreshedEventPayload.class::isInstance)
                .expectNextMatches(initialFormContentMatcher)
                .then(() -> checkTreeNode(formId.get(), treeId.get(), treeNodeToCheckId.get()))
                .expectNextMatches(updatedFormContentMatcher)
                .expectNextMatches(DiagramRefreshedEventPayload.class::isInstance)
                .then(() -> performAction(formId.get(), actionId.get()))
                // Skip the potential FormRefreshedEventPayload that may be sent before the DiagramRefreshedEventPayload
                .thenConsumeWhile(payload -> !(payload instanceof DiagramRefreshedEventPayload))
                .expectNextMatches(updatedDiagramContentMatcher)
                .then(() -> performAction(formId.get(), revertActionId.get()))
                .thenConsumeWhile(payload -> !(payload instanceof DiagramRefreshedEventPayload))
                // The first diagram refreshed event payload will be triggered by the semantic change since we have pressed the button
                .expectNextMatches(DiagramRefreshedEventPayload.class::isInstance)
                .thenConsumeWhile(payload -> !(payload instanceof DiagramRefreshedEventPayload))
                .expectNextMatches(revertedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private void checkTreeNode(String formId, String treeId, String treeNodeToCheckId) {
        var input = new EditTreeCheckboxInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), formId, treeId, treeNodeToCheckId, true);
        var editTreeCheckboxMutationResult = this.editTreeCheckboxMutationRunner.run(input);
        String editTreeCheckboxMutationResultTypename = JsonPath.read(editTreeCheckboxMutationResult, "$.data.editTreeCheckbox.__typename");
        assertThat(editTreeCheckboxMutationResultTypename).isEqualTo(SuccessPayload.class.getSimpleName());
    }

    private void performAction(String formId, String actionId) {
        var input = new PushButtonInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), formId, actionId);
        var pushButtonMutationResult = this.pushButtonMutationRunner.run(input);
        String pushButtonMutationResultTypename = JsonPath.read(pushButtonMutationResult, "$.data.pushButton.__typename");
        assertThat(pushButtonMutationResultTypename).isEqualTo(SuccessPayload.class.getSimpleName());
    }

    private void assertThatNodeMatchesTreeNodeEndIcons(Node node, TreeNode treeNode) {
        if (node.getModifiers().contains(ViewModifier.Hidden)) {
            assertThat(treeNode.getEndIconsURL()).contains(List.of("/icons/full/obj16/HideTool.svg"));
        }
        if (node.getModifiers().contains(ViewModifier.Faded)) {
            assertThat(treeNode.getEndIconsURL()).contains(List.of(DiagramImageConstants.FADE_SVG));
        }
        if (node.isPinned()) {
            assertThat(treeNode.getEndIconsURL()).contains(List.of(DiagramImageConstants.PIN_SVG));
        }
        if (node.getCollapsingState().equals(CollapsingState.COLLAPSED)) {
            assertThat(treeNode.getEndIconsURL()).contains(List.of(DiagramImageConstants.COLLAPSE_SVG));
        }
    }

    private Optional<Form> refreshedForm(Object object) {
        return Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form);
    }

    private Optional<Diagram> refreshedDiagram(Object object) {
        return Optional.of(object)
                .filter(DiagramRefreshedEventPayload.class::isInstance)
                .map(DiagramRefreshedEventPayload.class::cast)
                .map(DiagramRefreshedEventPayload::diagram);
    }
}
