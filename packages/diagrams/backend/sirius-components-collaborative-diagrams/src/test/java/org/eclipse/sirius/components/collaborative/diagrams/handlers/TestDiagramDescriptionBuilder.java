/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.tools.Palette;
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

    public DiagramDescription getDiagramDescription(String diagramDescriptionId, List<NodeDescription> nodeDescriptions, List<EdgeDescription> edgeDescriptions,
            List<Palette> palettes) {
        return DiagramDescription.newDiagramDescription(diagramDescriptionId)
                .label("")
                .canCreatePredicate(variableManager -> Boolean.TRUE)
                .targetObjectIdProvider(variableManager -> "targetObjectId")
                .labelProvider(variableManager -> "Diagram")
                .nodeDescriptions(nodeDescriptions)
                .edgeDescriptions(edgeDescriptions)
                .palettes(palettes)
                .dropHandler(variableManager -> new Failure(""))
                .build();
    }

    public EdgeDescription getEdgeDescription(String edgeDescriptionId, NodeDescription nodeDescription) {
        Function<VariableManager, List<Element>> sourceNodesProvider = variableManager -> List.of();
        Function<VariableManager, List<Element>> targetNodesProvider = variableManager -> List.of();

        Function<VariableManager, EdgeStyle> edgeStyleProvider = variableManager -> {
            return EdgeStyle.newEdgeStyle()
                    .size(2)
                    .lineStyle(LineStyle.Dash_Dot)
                    .sourceArrow(ArrowStyle.InputArrowWithDiamond)
                    .targetArrow(ArrowStyle.None)
                    .color("rgb(1, 2, 3)")
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
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .styleProvider(edgeStyleProvider)
                .labelEditHandler((variableManager, edgeLabelKind, newLabel) -> new Failure(""))
                .deleteHandler(variableManager -> new Success())
                .build();
    }

    public NodeDescription getNodeDescription(String nodeDescriptionId, Function<VariableManager, List<?>> semanticElementsProvider) {
        LabelStyleDescription labelStyleDescription = LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> "#000000")
                .fontSizeProvider(variableManager -> 16)
                .boldProvider(variableManager -> false)
                .italicProvider(variableManager -> false)
                .underlineProvider(variableManager -> false)
                .strikeThroughProvider(variableManager -> false)
                .iconURLProvider(variableManager -> List.of())
                .backgroundProvider(variableManager -> "transparent")
                .borderColorProvider(variableManager -> "black")
                .borderRadiusProvider(variableManager -> 0)
                .borderSizeProvider(variableManager -> 0)
                .borderStyleProvider(variableManager -> LineStyle.Solid)
                .build();

        InsideLabelDescription insideLabelDescription = InsideLabelDescription.newInsideLabelDescription("insideLabelDescriptionId")
                .idProvider(variableManager -> "insideLabelId")
                .textProvider(variableManager -> "Node")
                .styleDescriptionProvider(variableManager -> labelStyleDescription)
                .isHeaderProvider(vm -> false)
                .displayHeaderSeparatorProvider(vm -> false)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .build();

        Function<VariableManager, INodeStyle> nodeStyleProvider = variableManager -> RectangularNodeStyle.newRectangularNodeStyle()
                .background("")
                .borderColor("")
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();

        Function<VariableManager, String> idProvider = variableManager -> {
            Object object = variableManager.getVariables().get(VariableManager.SELF);
            if (object instanceof String) {
                return this.getNodeId(nodeDescriptionId, (String) object);
            }
            return null;
        };

        return NodeDescription.newNodeDescription(nodeDescriptionId)
                .typeProvider(variableManager -> "")
                .semanticElementsProvider(semanticElementsProvider)
                .targetObjectIdProvider(idProvider)
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .insideLabelDescription(insideLabelDescription)
                .styleProvider(nodeStyleProvider)
                .childrenLayoutStrategyProvider(variableManager -> new FreeFormLayoutStrategy())
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();
    }

    private String getNodeId(String nodeDescriptionId, String objectId) {
        return nodeDescriptionId + "_" + objectId;
    }
}
