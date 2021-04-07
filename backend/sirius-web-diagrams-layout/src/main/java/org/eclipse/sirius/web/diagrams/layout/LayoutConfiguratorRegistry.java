/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.diagrams.layout;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.alg.layered.options.LayeringStrategy;
import org.eclipse.elk.alg.layered.options.OrderingStrategy;
import org.eclipse.elk.core.LayoutConfigurator;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.Alignment;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.HierarchyHandling;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;

/**
 * Registry of all the layout configurators. For now it only contains one hard coded layout but setting up this class
 * allows us to extract it from the rest of the code. It will be quite easy from now on to allow the registration or
 * customization of layout configurator now that this concern has been extracted from the rest of the code. Major
 * changes will happen to this class but not much to the rest of the layouting code.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class LayoutConfiguratorRegistry {

    /**
     * The minimum height constraint value.
     */
    private static final int MIN_HEIGHT_CONSTRAINT = 25;

    /**
     * The minimum width constraint value.
     */
    private static final int MIN_WIDTH_CONSTRAINT = 50;

    /**
     * The space between two nodes.
     */
    private static final Double SPACING_NODE_NODE = Double.valueOf(50.0);

    /**
     * The space between node and edges.
     */
    private static final Double SPACING_NODE_EDGE = Double.valueOf(30.0);

    private final List<IDiagramLayoutConfiguratorProvider> customLayoutProviders;

    private TextBoundsService textBoundsService;

    public LayoutConfiguratorRegistry(List<IDiagramLayoutConfiguratorProvider> customLayoutProviders, TextBoundsService textBoundsService) {
        this.textBoundsService = textBoundsService;
        this.customLayoutProviders = List.copyOf(Objects.requireNonNull(customLayoutProviders));
    }

    public LayoutConfigurator getDefaultLayoutConfigurator() {
        // @formatter:off
        SiriusWebLayoutConfigurator configurator = new SiriusWebLayoutConfigurator();
        configurator.configureByType(ELKDiagramConverter.DEFAULT_DIAGRAM_TYPE)
                .setProperty(CoreOptions.ALGORITHM, LayeredOptions.ALGORITHM_ID)
                .setProperty(CoreOptions.HIERARCHY_HANDLING, HierarchyHandling.INCLUDE_CHILDREN)
                .setProperty(LayeredOptions.LAYERING_STRATEGY, LayeringStrategy.NETWORK_SIMPLEX)
                .setProperty(LayeredOptions.SPACING_NODE_NODE, SPACING_NODE_NODE)
                .setProperty(LayeredOptions.SPACING_NODE_NODE_BETWEEN_LAYERS, SPACING_NODE_NODE)
                .setProperty(LayeredOptions.SPACING_EDGE_NODE, SPACING_NODE_EDGE)
                .setProperty(LayeredOptions.SPACING_EDGE_NODE_BETWEEN_LAYERS, SPACING_NODE_EDGE);

        configurator.configureByType(NodeType.NODE_RECTANGLE)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, SizeConstraint.free())
                .setProperty(CoreOptions.NODE_SIZE_MINIMUM, new KVector(MIN_WIDTH_CONSTRAINT, MIN_HEIGHT_CONSTRAINT))
                .setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopCenter());

        configurator.configureByType(NodeType.NODE_LIST)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, SizeConstraint.free())
                .setProperty(CoreOptions.NODE_SIZE_MINIMUM, new KVector(MIN_WIDTH_CONSTRAINT, MIN_HEIGHT_CONSTRAINT))
                .setProperty(CoreOptions.PADDING, new ElkPadding(6d, 12d, 12d, 0))
                .setProperty(CoreOptions.NODE_LABELS_PADDING, new ElkPadding())
                .setProperty(CoreOptions.SPACING_NODE_NODE, 6d)
                .setProperty(LayeredOptions.CONSIDER_MODEL_ORDER, OrderingStrategy.NODES_AND_EDGES)
                .setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopCenter());


        Size minimumLabelSize = this.textBoundsService.getTextMinimumBounds().getSize();
        configurator.configureByType(NodeType.NODE_LIST_ITEM)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS, SizeConstraint.MINIMUM_SIZE))
                .setProperty(CoreOptions.NODE_SIZE_MINIMUM, new KVector(minimumLabelSize.getWidth(), minimumLabelSize.getHeight()))
                .setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideCenter())
                .setProperty(CoreOptions.ALIGNMENT, Alignment.LEFT);

        configurator.configureByType(NodeType.NODE_IMAGE)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, SizeConstraint.free())
                .setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.outsideTopCenter());

        // This image type does not match any diagram item. We add it to define the image size as constraint for the node image parent.
        configurator.configureByType(ELKDiagramConverter.DEFAULT_IMAGE_TYPE)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, SizeConstraint.fixed());
        // @formatter:on

        return configurator;
    }

    public LayoutConfigurator getLayoutConfigurator(Diagram diagram, DiagramDescription diagramDescription) {
        for (var customLayoutProvider : this.customLayoutProviders) {
            var customLayout = customLayoutProvider.getLayoutConfigurator(diagram, diagramDescription);
            if (customLayout.isPresent()) {
                return customLayout.get();
            }
        }
        return this.getDefaultLayoutConfigurator();
    }
}
