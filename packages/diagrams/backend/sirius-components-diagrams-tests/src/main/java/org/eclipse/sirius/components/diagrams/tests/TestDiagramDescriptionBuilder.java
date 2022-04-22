/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and others.
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
package org.eclipse.sirius.components.diagrams.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.tools.ToolSection;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Utility class used to help build diagram descriptions for unit tests.
 *
 * @author hmarchadour
 */
public class TestDiagramDescriptionBuilder {

    public DiagramDescription getDiagramDescription(String diagramDescriptionId, List<NodeDescription> nodeDescriptions, List<EdgeDescription> edgeDescriptions, List<ToolSection> toolSections) {
        // @formatter:off
        return DiagramDescription.newDiagramDescription(diagramDescriptionId)
            .label("") //$NON-NLS-1$
            .canCreatePredicate(variableManager -> Boolean.TRUE)
            .targetObjectIdProvider(variableManager -> "targetObjectId") //$NON-NLS-1$
            .labelProvider(variableManager -> "Diagram") //$NON-NLS-1$
            .nodeDescriptions(nodeDescriptions)
            .edgeDescriptions(edgeDescriptions)
            .toolSections(toolSections)
            .tools(List.of())
            .dropHandler(variableManager -> new Failure("")) //$NON-NLS-1$
            .build();
        // @formatter:on
    }

    public EdgeDescription getEdgeDescription(UUID edgeDescriptionId, NodeDescription nodeDescription) {
        // @formatter:off
        Function<VariableManager, List<Element>> sourceNodesProvider = variableManager -> List.of();
        Function<VariableManager, List<Element>> targetNodesProvider = variableManager -> List.of();

        Function<VariableManager, EdgeStyle> edgeStyleProvider = variableManager -> {
            return EdgeStyle.newEdgeStyle()
                    .size(2)
                    .lineStyle(LineStyle.Dash_Dot)
                    .sourceArrow(ArrowStyle.InputArrowWithDiamond)
                    .targetArrow(ArrowStyle.None)
                    .color("rgb(1, 2, 3)") //$NON-NLS-1$
                    .build();
        };

        Function<VariableManager, String> idProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, String.class).orElse(null);
        };

        return EdgeDescription.newEdgeDescription(edgeDescriptionId)
                .semanticElementsProvider(variableManager -> List.of())
                .sourceNodesProvider(sourceNodesProvider)
                .targetNodesProvider(targetNodesProvider)
                .sourceNodeDescriptions(List.of(nodeDescription))
                .targetNodeDescriptions(List.of(nodeDescription))
                .targetObjectIdProvider(idProvider)
                .targetObjectKindProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectLabelProvider(variableManager -> "")//$NON-NLS-1$
                .styleProvider(edgeStyleProvider)
                .labelEditHandler((variableManager, newValue) -> new Failure("")) //$NON-NLS-1$
                .deleteHandler(variableManager -> new Success())
                .build();
        // @formatter:on
    }

    public NodeDescription getNodeDescription(UUID nodeDescriptionId, Function<VariableManager, List<?>> semanticElementsProvider) {
        // @formatter:off
        LabelStyleDescription labelStyleDescription = LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> "#000000") //$NON-NLS-1$
                .fontSizeProvider(variableManager -> 16)
                .boldProvider(variableManager -> false)
                .italicProvider(variableManager -> false)
                .underlineProvider(variableManager -> false)
                .strikeThroughProvider(variableManager -> false)
                .iconURLProvider(variableManager -> "") //$NON-NLS-1$
                .build();

        LabelDescription labelDescription = LabelDescription.newLabelDescription("labelDescriptionId") //$NON-NLS-1$
                .idProvider(variableManager -> "labelId") //$NON-NLS-1$
                .textProvider(variableManager -> "Node") //$NON-NLS-1$
                .styleDescriptionProvider(variableManager -> labelStyleDescription)
                .build();

        Function<VariableManager, INodeStyle> nodeStyleProvider = variableManager -> {
            return RectangularNodeStyle.newRectangularNodeStyle()
                    .color("") //$NON-NLS-1$
                    .borderColor("") //$NON-NLS-1$
                    .borderSize(0)
                    .borderStyle(LineStyle.Solid)
                    .build();
        };

        Function<VariableManager, String> idProvider = variableManager -> {
            Object object = variableManager.getVariables().get(VariableManager.SELF);
            if (object instanceof String) {
                return this.getNodeId(nodeDescriptionId, (String) object);
            }
            return null;
        };

        return NodeDescription.newNodeDescription(nodeDescriptionId)
                .typeProvider(variableManager -> "") //$NON-NLS-1$
                .semanticElementsProvider(semanticElementsProvider)
                .targetObjectIdProvider(idProvider)
                .targetObjectKindProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectLabelProvider(variableManager -> "")//$NON-NLS-1$
                .labelDescription(labelDescription)
                .styleProvider(nodeStyleProvider)
                .childrenLayoutStrategyProvider(variableManager -> new FreeFormLayoutStrategy())
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();
        // @formatter:on
    }

    private String getNodeId(UUID nodeDescriptionId, String objectId) {
        return nodeDescriptionId + "_" + objectId; //$NON-NLS-1$
    }
}
