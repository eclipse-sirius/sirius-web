/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.layout.incremental;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.web.diagrams.events.ResizeEvent;
import org.eclipse.sirius.web.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.web.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.eclipse.sirius.web.diagrams.tests.TestDiagramBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link NodeSizeProvider}.
 *
 * @author fbarbin
 */
public class NodeSizeProviderTests {

    private static final int HEIGHT_70 = 70;

    private static final int WIDTH_150 = 150;

    private static final int HEIGHT_50 = 50;

    private static final int WIDTH_80 = 80;

    @BeforeAll
    public static void beforeAll() {
        LayoutMetaDataService.getInstance().registerLayoutMetaDataProviders(new LayeredMetaDataProvider());
    }

    @Test
    public void testNodeSize() {
        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        ISiriusWebLayoutConfigurator layoutConfigurator = new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator();
        Size size = nodeSizeProvider.getSize(Optional.empty(), this.createNodeLayoutData(Size.UNDEFINED), layoutConfigurator);
        assertThat(size).extracting(Size::getHeight).isEqualTo(Double.valueOf(HEIGHT_70));
        assertThat(size).extracting(Size::getWidth).isEqualTo(Double.valueOf(WIDTH_150));

        imageSizeProvider.dispose();
    }

    @Test
    public void testNodeSizeWithExistingSize() {
        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        ISiriusWebLayoutConfigurator layoutConfigurator = new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator();
        Size size = nodeSizeProvider.getSize(Optional.empty(), this.createNodeLayoutData(Size.of(WIDTH_80, HEIGHT_50)), layoutConfigurator);
        assertThat(size).extracting(Size::getHeight).isEqualTo(Double.valueOf(HEIGHT_50));
        assertThat(size).extracting(Size::getWidth).isEqualTo(Double.valueOf(WIDTH_80));

        imageSizeProvider.dispose();
    }

    private NodeLayoutData createNodeLayoutData(Size size) {
        TestDiagramBuilder testDiagramBuilder = new TestDiagramBuilder();

        NodeLayoutData nodeLayoutData = new NodeLayoutData();
        nodeLayoutData.setId(UUID.randomUUID().toString());
        nodeLayoutData.setSize(size);
        nodeLayoutData.setNodeType(NodeType.NODE_RECTANGLE);
        nodeLayoutData.setStyle(testDiagramBuilder.getRectangularNodeStyle());
        return nodeLayoutData;
    }

    @Test
    public void testResizeNodeEvent() {
        Size initialNodeSize = Size.of(200, 100);
        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(initialNodeSize);
        NodeLayoutData nodeLayoutData2 = this.createNodeLayoutData(initialNodeSize);
        Size newSize = Size.of(100, 50);

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        Optional<IDiagramEvent> optionalEvent = Optional.of(new ResizeEvent(nodeLayoutData.getId(), Position.UNDEFINED, newSize));
        ISiriusWebLayoutConfigurator layoutConfigurator = new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator();

        Size newSizeFromProvider = nodeSizeProvider.getSize(optionalEvent, nodeLayoutData, layoutConfigurator);
        Size newSizeFromProvider2 = nodeSizeProvider.getSize(optionalEvent, nodeLayoutData2, layoutConfigurator);
        assertThat(newSizeFromProvider).isEqualTo(newSize);
        assertThat(newSizeFromProvider2).isEqualTo(initialNodeSize);
    }

}
