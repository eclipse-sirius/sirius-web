/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import static org.eclipse.sirius.components.diagrams.tests.DiagramAssertions.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.ILayoutStrategyDeserializer;
import org.eclipse.sirius.components.collaborative.diagrams.INodeStyleDeserializer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.layout.ELKLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.IELKDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.LayoutService;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.services.DefaultTestDiagramDescriptionProvider;
import org.eclipse.sirius.components.diagrams.layout.services.TestDiagramCreationService;
import org.eclipse.sirius.components.diagrams.layout.services.TestLayoutObjectService;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Used to tests the incremental layout on various kind of diagrams during a refresh with no impact on the diagram.
 *
 * @author sbegaudeau
 */
public class IncrementalLayoutTests {

    private TestLayoutObjectService objectService = new TestLayoutObjectService();

    private DefaultTestDiagramDescriptionProvider defaultTestDiagramDescriptionProvider = new DefaultTestDiagramDescriptionProvider(this.objectService);

    @Test
    @Disabled
    public void testClassDiagramIncrementalLayout() {
        ListLayoutStrategy columnListLayoutStrategy = new ListLayoutStrategy();

        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Class") //$NON-NLS-1$
            .nodes()
                .rectangleNode("Engine").at(100, 100).of(200, 300) //$NON-NLS-1$
                    .childNodes(columnListLayoutStrategy)
                        .iconLabelNode("rpm: Integer").at(0, 0).of(0, 0).and() //$NON-NLS-1$
                        .iconLabelNode("start(): void").at(0, 0).of(0, 0).and() //$NON-NLS-1$
                        .iconLabelNode("accelerate(): void").at(0, 0).of(0, 0).and() //$NON-NLS-1$
                        .iconLabelNode("deccelerate(): void").at(0, 0).of(0, 0).and() //$NON-NLS-1$
                    .and()
                .and()
                .rectangleNode("Display").at(500, 500).of(200, 300) //$NON-NLS-1$
                    .childNodes(columnListLayoutStrategy)
                        .iconLabelNode("show(): void").at(0, 0).of(0, 0).and() //$NON-NLS-1$
                        .iconLabelNode("update(): void").at(0, 0).of(0, 0).and() //$NON-NLS-1$
                    .and()
                .and()
                .rectangleNode("Temperature Sensor").at(500, 500).of(400, 300) //$NON-NLS-1$
                    .childNodes(columnListLayoutStrategy)
                        .iconLabelNode("read(): Integer").at(0, 0).of(0, 0).and() //$NON-NLS-1$
                    .and()
                .and()
            .and()
        .build();
        // @formatter:on

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        Diagram layoutedDiagram = diagramCreationService.performLayout(new IEditingContext.NoOp(), diagram, null);
        assertThat(layoutedDiagram).isValid().hasNoOverflow();
    }

    @Test
    @Disabled
    public void testComponentDiagramIncrementalLayout() {
        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Component") //$NON-NLS-1$
            .nodes()
                .rectangleNode("Server").at(100, 100).of(400, 200) //$NON-NLS-1$
                    .borderNodes()
                        .rectangleNode("ServerOutput1").at(-1, -1).of(20, 20) //$NON-NLS-1$
                        .and()
                        .rectangleNode("ServerInput1").at(-1, -1).of(20, 20) //$NON-NLS-1$
                        .and()
                    .and()
                .and()
                .rectangleNode("Gateway").at(100, 500).of(400, 200) //$NON-NLS-1$
                    .borderNodes()
                        .rectangleNode("GatewayInput1").at(-1, -1).of(20, 20) //$NON-NLS-1$
                        .and()
                        .rectangleNode("GatewayInput2").at(-1, -1).of(20, 20) //$NON-NLS-1$
                        .and()
                        .rectangleNode("GatewayOutput1").at(-1, -1).of(20, 20) //$NON-NLS-1$
                        .and()
                        .rectangleNode("GatewayOutput2").at(-1, -1).of(20, 20) //$NON-NLS-1$
                        .and()
                    .and()
                .and()
                .rectangleNode("Client").at(100, 1000).of(400, 200) //$NON-NLS-1$
                    .borderNodes()
                        .rectangleNode("ClientInput1").at(-1, -1).of(20, 20) //$NON-NLS-1$
                        .and()
                        .rectangleNode("ClientOutput1").at(-1, -1).of(20, 20) //$NON-NLS-1$
                        .and()
                    .and()
                .and()
            .and()
            .edge("ClientOutput1 to GatewayInput1") //$NON-NLS-1$
                .from("ClientOutput1").at(0.5, 0.5) //$NON-NLS-1$
                .to("GatewayInput1").at(0.5, 0.5) //$NON-NLS-1$
            .and()
                .edge("GatewayOutput1 to ServerInput1") //$NON-NLS-1$
                .from("GatewayOutput1").at(0.5, 0.5) //$NON-NLS-1$
                .to("ServerInput1").at(0.5, 0.5) //$NON-NLS-1$
            .and()
                .edge("ServerOutput1 to GatewayInput2") //$NON-NLS-1$
                .from("ServerOutput1").at(0.5, 0.5) //$NON-NLS-1$
                .to("GatewayInput2").at(0.5, 0.5) //$NON-NLS-1$
            .and()
                .edge("GatewayOutput2 to ClientInput1") //$NON-NLS-1$
                .from("GatewayOutput2").at(0.5, 0.5) //$NON-NLS-1$
                .to("ClientInput1").at(0.5, 0.5) //$NON-NLS-1$
            .and()
        .build();
        // @formatter:on

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        Diagram layoutedDiagram = diagramCreationService.performLayout(new IEditingContext.NoOp(), diagram, null);
        assertThat(layoutedDiagram).isValid().hasNoOverflow();
    }

    @Disabled
    @ParameterizedTest
    // @formatter:off
    @ValueSource(strings = {
        "src/test/resources/diagrams/topography_with_autolayout.json",
        "src/test/resources/diagrams/domain.json",
        "src/test/resources/diagrams/bpmn.json"
    })
    // @formatter:on
    public void testSavedDiagramIncrementalLayout(String path) throws IOException {
        this.isValidDiagram(Files.readString(Paths.get(path)));
    }

    private void isValidDiagram(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleDeserializers simpleDeserializers = new SimpleDeserializers();
        Map<Class<?>, JsonDeserializer<?>> deserializers = new HashMap<>();
        deserializers.put(INodeStyle.class, new INodeStyleDeserializer());
        deserializers.put(ILayoutStrategy.class, new ILayoutStrategyDeserializer());
        simpleDeserializers.addDeserializers(deserializers);
        SimpleModule module = new SimpleModule();
        module.setDeserializers(simpleDeserializers);
        objectMapper.registerModule(module);

        Diagram diagram = objectMapper.readValue(json, Diagram.class);
        assertThat(diagram).isValid().hasNoOverflow();

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);
        Diagram layoutedDiagram = diagramCreationService.performLayout(new IEditingContext.NoOp(), diagram, null);
        assertThat(layoutedDiagram).isValid().hasNoOverflow();
    }

    private TestDiagramCreationService createDiagramCreationService(Diagram diagram) {
        IRepresentationDescriptionSearchService.NoOp representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                DiagramDescription diagramDescription = IncrementalLayoutTests.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);
                return Optional.of(diagramDescription);
            }
        };

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider, List.of());

        LayoutService layoutService = new LayoutService(new IELKDiagramConverter.NoOp(), new IncrementalLayoutDiagramConverter(), new LayoutConfiguratorRegistry(List.of()),
                new ELKLayoutedDiagramProvider(List.of()), new IncrementalLayoutedDiagramProvider(), representationDescriptionSearchService, incrementalLayoutEngine);

        return new TestDiagramCreationService(this.objectService, representationDescriptionSearchService, layoutService);
    }
}
