/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.alg.layered.options.LayeringStrategy;
import org.eclipse.elk.alg.layered.options.OrderingStrategy;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.HierarchyHandling;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.PortLabelPlacement;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.core.options.SizeOptions;
import org.eclipse.elk.core.util.IndividualSpacings;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.ParametricSVGNodeType;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
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
     * The space between the outside label and the border it is associated with.
     */
    private static final Double SPACING_LABEL_NODE = Double.valueOf(5d);

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

    /**
     * The default value for port border offset.
     */
    private static final double DEFAULT_PORT_BORDER_OFFSET = -8;

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
                .setProperty(LayeredOptions.SPACING_LABEL_NODE, SPACING_LABEL_NODE)
                .setProperty(LayeredOptions.SPACING_NODE_NODE, SPACING_NODE_NODE)
                .setProperty(LayeredOptions.SPACING_NODE_NODE_BETWEEN_LAYERS, SPACING_NODE_NODE)
                .setProperty(LayeredOptions.SPACING_EDGE_NODE, SPACING_NODE_EDGE)
                .setProperty(LayeredOptions.SPACING_EDGE_NODE_BETWEEN_LAYERS, SPACING_NODE_EDGE);

        configurator.configureByType(NodeType.NODE_RECTANGLE)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.MINIMUM_SIZE, SizeConstraint.NODE_LABELS))
                .setProperty(CoreOptions.NODE_SIZE_MINIMUM, new KVector(MIN_WIDTH_CONSTRAINT, MIN_HEIGHT_CONSTRAINT))
                .setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopCenter())
                .setProperty(CoreOptions.PORT_BORDER_OFFSET, DEFAULT_PORT_BORDER_OFFSET)
                .setProperty(CoreOptions.PORT_LABELS_PLACEMENT, PortLabelPlacement.outside());

        configurator.configureByType(ParametricSVGNodeType.NODE_TYPE_PARAMETRIC_IMAGE)
                .setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopCenter())
                .setProperty(CoreOptions.PORT_BORDER_OFFSET, DEFAULT_PORT_BORDER_OFFSET)
                .setProperty(CoreOptions.PORT_LABELS_PLACEMENT, PortLabelPlacement.outside());

        /*
         * Two things here:
         * - First, the node label padding apply only on inside label on the side it is defined. Because we put it on V_CENTER and H_LEFT, we only need to defined the label padding on the left
         * - Secondly, by default the node label padding is not defined on node containing the label, but on its parent. With Spacing individual we can define the label padding on the node itself.
         */
        IndividualSpacings iconLabelIndividualSpacing = new IndividualSpacings();
        iconLabelIndividualSpacing.setProperty(CoreOptions.NODE_LABELS_PADDING, new ElkPadding(0, 5, 0, 5));
        configurator.configureByType(NodeType.NODE_ICON_LABEL)
                .setProperty(CoreOptions.SPACING_INDIVIDUAL, iconLabelIndividualSpacing)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS))
                .setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.ASYMMETRICAL))
                .setProperty(CoreOptions.NODE_LABELS_PLACEMENT, EnumSet.of(NodeLabelPlacement.INSIDE, NodeLabelPlacement.V_CENTER, NodeLabelPlacement.H_LEFT));

        configurator.configureByType(NodeType.NODE_IMAGE)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.MINIMUM_SIZE, SizeConstraint.PORT_LABELS, SizeConstraint.PORTS))
                .setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.outsideTopCenter())
                .setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.ASYMMETRICAL))
                .setProperty(CoreOptions.PORT_BORDER_OFFSET, DEFAULT_PORT_BORDER_OFFSET)
                .setProperty(CoreOptions.PORT_LABELS_PLACEMENT, PortLabelPlacement.outside())
                .setProperty(CoreOptions.PADDING, new ElkPadding(0))
                .setProperty(LayeredOptions.PADDING, new ElkPadding(0));

        // This image type does not match any diagram item. We add it to define the image size as constraint for the node image parent.
        configurator.configureByType(ELKDiagramConverter.DEFAULT_IMAGE_TYPE)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, SizeConstraint.fixed());

        configurator.configureByChildrenLayoutStrategy(ListLayoutStrategy.class)
                .setProperty(CoreOptions.HIERARCHY_HANDLING, HierarchyHandling.INCLUDE_CHILDREN)
                .setProperty(LayeredOptions.CONSIDER_MODEL_ORDER, OrderingStrategy.NODES_AND_EDGES)
                .setProperty(CoreOptions.PADDING, new ElkPadding(5, 0, 0, 0))
                .setProperty(CoreOptions.SPACING_NODE_NODE, 0d);

        configurator.configureByChildrenLayoutStrategy(FreeFormLayoutStrategy.class)
                .setProperty(CoreOptions.HIERARCHY_HANDLING, HierarchyHandling.INCLUDE_CHILDREN)
                .setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, SizeConstraint.free())
                .setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.ASYMMETRICAL))
                .setProperty(CoreOptions.NODE_SIZE_MINIMUM, new KVector(MIN_WIDTH_CONSTRAINT, MIN_HEIGHT_CONSTRAINT))
                .setProperty(LayeredOptions.SPACING_LABEL_NODE, SPACING_LABEL_NODE)
                .setProperty(CoreOptions.PADDING, new ElkPadding(12, 12, 12, 12));

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
