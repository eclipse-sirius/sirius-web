/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.PaletteQueryRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.GroupPaletteDiagramDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests for the group palette and group tools.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class InvokeSingleClickOnMultipleElementsControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private PaletteQueryRunner paletteQueryRunner;

    @Autowired
    private InvokeSingleClickOnDiagramElementToolMutationRunner invokeSingleClickOnDiagramElementToolMutationRunner;

    @Autowired
    private GroupPaletteDiagramDescriptionProvider groupPaletteDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToLifeCycleDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.groupPaletteDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "GroupPaletteDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes, when we request a group palette on several nodes, then it is returned properly")
    public void givenDiagramWithSomeNodesWhenWeRequestGroupPaletteOnSeveralNodesThenItIsReturnedProperly() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();
        var diagramId = new AtomicReference<String>();
        var componentIds = new ArrayList<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebDomainId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode().getId();
            var siriusWebApplicationId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode().getId();
            var siriusWebInfrastructureId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode().getId();

            componentIds.add(siriusWebDomainId);
            componentIds.add(siriusWebApplicationId);
            componentIds.add(siriusWebInfrastructureId);
        });

        Runnable requestGroupPalette = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", diagramId.get(),
                    "diagramElementIds", componentIds
            );
            var result = this.paletteQueryRunner.run(variables);

            List<String> topLevelToolsLabel = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].label");
            assertThat(topLevelToolsLabel)
                    .isNotEmpty();
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestGroupPalette)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes, when we execute a tool on a group of nodes, then it is executed")
    public void givenDiagramWithSomeNodesWhenWeExecuteToolOnGroupOfNodesThenItIsExecuted() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();
        var diagramId = new AtomicReference<String>();
        var componentIds = new ArrayList<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebDomainId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode().getId();
            var siriusWebApplicationId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode().getId();
            var siriusWebInfrastructureId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode().getId();

            componentIds.add(siriusWebDomainId);
            componentIds.add(siriusWebApplicationId);
            componentIds.add(siriusWebInfrastructureId);
        });

        Runnable executeTool = () -> {
            String toolId = this.groupPaletteDiagramDescriptionProvider.getEditToolId();
            var input = new InvokeSingleClickOnDiagramElementToolInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    componentIds,
                    toolId,
                    0,
                    0,
                    List.of()
            );
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result.data(), "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain Suffix");
            new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application Suffix");
            new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure Suffix");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(executeTool)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}