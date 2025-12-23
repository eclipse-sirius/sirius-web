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

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.AncestorsVariableDiagramDescriptionProvider;
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
 * Integration tests of the ancestors variable in diagrams.
 *
 * @author arichard
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class AncestorsVariableDiagramControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private AncestorsVariableDiagramDescriptionProvider ancestorsVariableDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.ancestorsVariableDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "AncestorsVariableDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an ancestors variable diagram, when it is opened, then nodes should appear with labels containing ancestors Ids")
    public void givenAncestorsVariableDiagramWhenItIsOpenedThenNodesShouldAppearWithLabelsContainingAncestorsIds() {
        var flux = this.givenSubscriptionToDiagram();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            List<Node> rootNodes = diagram.getNodes();
            assertThat(rootNodes).hasSize(5);

            Node rootNode = rootNodes.get(0);
            assertThat(rootNode.getInsideLabel().getText()).isEqualTo("[Sirius Web]");

            List<Node> childNodes = rootNode.getChildNodes();
            assertThat(childNodes).hasSize(1);

            Node childNode = childNodes.get(0);
            assertThat(childNode.getInsideLabel().getText()).isEqualTo("[sirius-web-domain, Sirius Web]");

            List<Node> leafNodes = childNode.getChildNodes();
            assertThat(leafNodes).hasSize(2);

            Node leafNode = leafNodes.get(0);
            assertThat(leafNode.getInsideLabel().getText()).isEqualTo("[services, sirius-web-domain, Sirius Web]");

        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
