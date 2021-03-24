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
package org.eclipse.sirius.web.compat.diagrams;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.style.NodeStyleDescription;
import org.eclipse.sirius.diagram.description.style.SquareDescription;
import org.eclipse.sirius.diagram.description.style.WorkspaceImageDescription;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Used to compute the style using the definition of a node mapping.
 *
 * @author sbegaudeau
 */
public class NodeMappingStyleProvider implements Function<VariableManager, INodeStyle> {

    private final AQLInterpreter interpreter;

    private final NodeMapping nodeMapping;

    public NodeMappingStyleProvider(AQLInterpreter interpreter, NodeMapping nodeMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.nodeMapping = Objects.requireNonNull(nodeMapping);
    }

    @Override
    public INodeStyle apply(VariableManager variableManager) {
        NodeStyleDescription nodeStyleDescription = new NodeStyleDescriptionProvider(this.interpreter, this.nodeMapping).getNodeStyleDescription(variableManager);
        return this.getNodeStyle(variableManager, nodeStyleDescription);
    }

    private INodeStyle getNodeStyle(VariableManager variableManager, NodeStyleDescription nodeStyleDescription) {
        INodeStyle style = null;

        if (nodeStyleDescription instanceof SquareDescription) {
            SquareDescription squareDescription = (SquareDescription) nodeStyleDescription;
            style = this.createRectangularNodeStyle(variableManager, squareDescription);
        } else if (nodeStyleDescription instanceof WorkspaceImageDescription) {
            WorkspaceImageDescription workspaceImageDescription = (WorkspaceImageDescription) nodeStyleDescription;
            WorkspaceImageDescriptionConverter workspaceImageDescriptionConverter = new WorkspaceImageDescriptionConverter(this.interpreter, variableManager, workspaceImageDescription);
            style = workspaceImageDescriptionConverter.convert();
        } else {
            // Fallback on Rectangular node style for now, until other styles are supported
            LineStyle borderStyle = new LineStyleConverter().getStyle(nodeStyleDescription.getBorderLineStyle());

            // @formatter:off
            style = RectangularNodeStyle.newRectangularNodeStyle()
                    .color("rgb(200, 200, 200)") //$NON-NLS-1$
                    .borderColor("rgb(0, 0, 0)") //$NON-NLS-1$
                    .borderSize(1)
                    .borderStyle(borderStyle)
                    .build();
            // @formatter:on
        }

        return style;
    }

    private RectangularNodeStyle createRectangularNodeStyle(VariableManager variableManager, SquareDescription squareDescription) {
        ColorDescriptionConverter colorProvider = new ColorDescriptionConverter(this.interpreter, variableManager.getVariables());

        String color = colorProvider.convert(squareDescription.getColor());
        String borderColor = colorProvider.convert(squareDescription.getBorderColor());

        LineStyle borderStyle = new LineStyleConverter().getStyle(squareDescription.getBorderLineStyle());

        Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), squareDescription.getBorderSizeComputationExpression());
        int borderSize = result.asInt().getAsInt();

        // @formatter:off
        return RectangularNodeStyle.newRectangularNodeStyle()
                .color(color)
                .borderColor(borderColor)
                .borderSize(borderSize)
                .borderStyle(borderStyle)
                .build();
        // @formatter:on
    }

}
