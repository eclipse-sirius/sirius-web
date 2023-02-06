/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.assertj.core.data.Offset;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.layout.ELKDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.ELKLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.ELKPropertiesService;
import org.eclipse.sirius.components.diagrams.layout.ILayoutEngineHandlerSwitchProvider;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.LayoutService;
import org.eclipse.sirius.components.diagrams.layout.TextBoundsService;
import org.eclipse.sirius.components.diagrams.layout.incremental.BorderNodeLayoutEngine;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutEngine;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.LayoutEngineHandlerSwitch;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageNodeStyleSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.eclipse.sirius.components.diagrams.tests.builder.JsonBasedEditingContext;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Used to test the diagram full layout.
 *
 * @author lfasani
 */
public class DiagramELKLayoutTest {

    private TestLayoutObjectService objectService = new TestLayoutObjectService();

    private DefaultTestDiagramDescriptionProvider defaultTestDiagramDescriptionProvider = new DefaultTestDiagramDescriptionProvider(this.objectService);

    private TextBoundsService textBoundsService = new TextBoundsService();

    private ELKPropertiesService elkPropertiesService = new ELKPropertiesService();

    private TestDiagramCreationService createDiagramCreationService(Diagram diagram) {
        IRepresentationDescriptionSearchService.NoOp representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                DiagramDescription diagramDescription = DiagramELKLayoutTest.this.defaultTestDiagramDescriptionProvider.getDefaultDiagramDescription(diagram);
                return Optional.of(diagramDescription);
            }
        };

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);

        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        LayoutService layoutService = new LayoutService(new ELKDiagramConverter(new TextBoundsService(), new ImageSizeProvider(), this.elkPropertiesService),
                new IncrementalLayoutDiagramConverter(this.textBoundsService, this.elkPropertiesService), new LayoutConfiguratorRegistry(List.of()),
                new ELKLayoutedDiagramProvider(List.of(), this.elkPropertiesService), new IncrementalLayoutedDiagramProvider(), representationDescriptionSearchService, incrementalLayoutEngine);

        return new TestDiagramCreationService(this.objectService, representationDescriptionSearchService, layoutService);
    }

    @Test
    @Disabled
    public void testNodeLayoutWithMultilineLabel() throws IOException {
        String nodeLabelWithMultiple = "First LineAAAAAAAA\nSecond LineBBBBBBBBB";
        String firstChildTargetObjectId = "First child";

        // @formatter:off
        Diagram diagram = TestLayoutDiagramBuilder.diagram("Root")
            .nodes()
                .rectangleNode(nodeLabelWithMultiple).at(10, 10).of(200, 200)
                    .childNodes(new FreeFormLayoutStrategy())
                        .rectangleNode(firstChildTargetObjectId).at(10, 10).of(50, 50).and()
                    .and()
                .and()
            .and()
            .build();
        // @formatter:on

        Path path = Paths.get("src", "test", "resources", "editing-contexts", "testNodeLayoutWithMultilineLabel");
        JsonBasedEditingContext editingContext = new JsonBasedEditingContext(path);

        TestDiagramCreationService diagramCreationService = this.createDiagramCreationService(diagram);

        Diagram layoutedDiagram = diagramCreationService.performElKLayout(editingContext, diagram);

        Node firstParent = layoutedDiagram.getNodes().get(0);

        // Check that the parent node and the label have the right size
        assertThat(firstParent.getLabel().getSize().getWidth()).isCloseTo(190.0, Offset.offset(5.0));
        assertThat(firstParent.getLabel().getSize().getHeight()).isCloseTo(40.0, Offset.offset(5.0));
        assertThat(firstParent.getSize().getWidth()).isCloseTo(200.0, Offset.offset(5.0));
        assertThat(firstParent.getSize().getHeight()).isCloseTo(155.0, Offset.offset(5.0));

        // Check that the inner node is under the multi line label area
        assertThat(firstParent.getChildNodes().get(0).getPosition().getX()).isCloseTo(12, Offset.offset(0.1));
        assertThat(firstParent.getChildNodes().get(0).getPosition().getY()).isCloseTo(55.8, Offset.offset(0.1));

    }
}
