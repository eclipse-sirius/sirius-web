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
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.FixedLayouterOptions;
import org.eclipse.elk.core.options.HierarchyHandling;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.core.options.SizeOptions;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.NodeType;
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
 * @author gcoutable
 */
@Service
public class LayoutConfiguratorRegistry {

    /**
     * The space between two nodes.
     */
    private static final Double SPACING_NODE_NODE = Double.valueOf(50.0);

    /**
     * The space between node and edges.
     */
    private static final Double SPACING_NODE_EDGE = Double.valueOf(30.0);

    /**
     * The minimum height constraint value.
     */
    private static final int MIN_HEIGHT_CONSTRAINT = 70;

    /**
     * The minimum width constraint value.
     */
    private static final int MIN_WIDTH_CONSTRAINT = 150;

    private final List<IDiagramLayoutConfiguratorProvider> customLayoutProviders;

    public LayoutConfiguratorRegistry(List<IDiagramLayoutConfiguratorProvider> customLayoutProviders) {
        this.customLayoutProviders = List.copyOf(Objects.requireNonNull(customLayoutProviders));
    }

    public ISiriusWebLayoutConfigurator getDefaultLayoutConfigurator() {
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
                .setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.ASYMMETRICAL))
                .setProperty(CoreOptions.NODE_SIZE_MINIMUM, new KVector(LayoutOptionValues.MIN_WIDTH_CONSTRAINT, LayoutOptionValues.MIN_HEIGHT_CONSTRAINT))
                .setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopCenter())
                .setProperty(CoreOptions.NODE_SIZE_MINIMUM, new KVector(MIN_WIDTH_CONSTRAINT, MIN_HEIGHT_CONSTRAINT));

        configurator.configureByType(NodeType.NODE_LIST)
                .setProperty(CoreOptions.ALGORITHM, FixedLayouterOptions.ALGORITHM_ID)
                .setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopCenter())
                .setProperty(CoreOptions.NODE_SIZE_FIXED_GRAPH_SIZE, true);

        configurator.configureByType(NodeType.NODE_LIST_ITEM)
                .setProperty(CoreOptions.NO_LAYOUT, true)
                .setProperty(CoreOptions.NODE_LABELS_PADDING, new ElkPadding(0d, 12d, 0d, 6d));

        configurator.configureByType(NodeType.NODE_IMAGE)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, SizeConstraint.free())
                .setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.outsideTopCenter());

        // This image type does not match any diagram item. We add it to define the image size as constraint for the node image parent.
        configurator.configureByType(ELKDiagramConverter.DEFAULT_IMAGE_TYPE)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, SizeConstraint.fixed());

        configurator.configure(ElkEdge.class).setProperty(CoreOptions.SPACING_EDGE_LABEL, 3d);
        // @formatter:on

        return configurator;
    }

    public ISiriusWebLayoutConfigurator getLayoutConfigurator(Diagram diagram, DiagramDescription diagramDescription) {
        for (var customLayoutProvider : this.customLayoutProviders) {
            var customLayout = customLayoutProvider.getLayoutConfigurator(diagram, diagramDescription);
            if (customLayout.isPresent()) {
                return customLayout.get();
            }
        }
        return this.getDefaultLayoutConfigurator();
    }
}
