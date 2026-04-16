/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeDecorator;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.NodeDecoratorDiagramDescriptionProvider;
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
 * Integration tests of the node decorator controllers.
 *
 * @author gdaniel
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class NodeDecoratorDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private NodeDecoratorDiagramDescriptionProvider nodeDecoratorDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToNodeDecoratorDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.nodeDecoratorDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "NodeDecoratorDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with nodes with decorators, when we create and subscribe to the diagram, then the diagram contains the appropriate decorators")
    public void givenDiagramWithNodeDecoratorsWhenWeCreateAndSubscribeToTheDiagramThenTheDiagramContainsTheAppropriateDecorators() {
        var flux = this.givenSubscriptionToNodeDecoratorDiagram();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            DiagramNavigator diagramNavigator = new DiagramNavigator(diagram);
            Node siriusWebStarterNode = diagramNavigator.nodeWithLabel("sirius-web-starter").getNode();
            List<NodeDecorator> siriusWebStarterDecorators = siriusWebStarterNode.getDecorators();
            assertThat(siriusWebStarterDecorators).hasSize(3);
            assertThat(siriusWebStarterDecorators)
                    .anySatisfy(decorator -> assertThat(decorator.label()).isEqualTo("Decorator1 label"))
                    .anySatisfy(decorator -> assertThat(decorator.label()).isEqualTo("Decorator2 label"))
                    .anySatisfy(decorator -> assertThat(decorator.label()).isEqualTo("Decorator3 sirius-web-starter"));

            Node siriusWebDomainNode = diagramNavigator.nodeWithLabel("sirius-web-domain").getNode();
            List<NodeDecorator> siriusWebDomainDecorators = siriusWebDomainNode.getDecorators();
            assertThat(siriusWebDomainDecorators).hasSize(2);
            assertThat(siriusWebDomainDecorators)
                    .anySatisfy(decorator -> assertThat(decorator.label()).isEqualTo("Decorator1 label"))
                    .anySatisfy(decorator -> assertThat(decorator.label()).isEqualTo("Decorator2 label"));

            Node failureNode = diagramNavigator.nodeWithLabel("Failure").getNode();
            List<NodeDecorator> failureNodeDecorators = failureNode.getDecorators();
            assertThat(failureNodeDecorators).hasSize(1)
                    .anySatisfy(decorator -> assertThat(decorator.label()).isEqualTo("SemanticDecorator Classifier"));

            Node abstractTestNode = diagramNavigator.nodeWithLabel("AbstractTest").getNode();
            List<NodeDecorator> abstractTestNodeDecorators = abstractTestNode.getDecorators();
            assertThat(abstractTestNodeDecorators).hasSize(2)
                    .anySatisfy(decorator -> assertThat(decorator.label()).isEqualTo("SemanticDecorator Classifier"))
                    .anySatisfy(decorator -> assertThat(decorator.label()).isEqualTo("SemanticDecorator Interface"));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
