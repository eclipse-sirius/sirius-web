/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo and others.
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
package org.eclipse.sirius.components.compatibility.diagrams;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.business.api.query.ContainerMappingQuery;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.style.DotDescription;
import org.eclipse.sirius.diagram.description.style.FlatContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.SquareDescription;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.diagram.description.style.WorkspaceImageDescription;
import org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription;

/**
 * Used to compute the style using the definition of an abstract node mapping.
 *
 * @author sbegaudeau
 */
public class AbstractNodeMappingStyleProvider implements Function<VariableManager, INodeStyle> {

    private final AQLInterpreter interpreter;

    private final AbstractNodeMapping abstractNodeMapping;

    public AbstractNodeMappingStyleProvider(AQLInterpreter interpreter, AbstractNodeMapping abstractNodeMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.abstractNodeMapping = Objects.requireNonNull(abstractNodeMapping);
    }

    @Override
    public INodeStyle apply(VariableManager variableManager) {
        LabelStyleDescription nodeStyleDescription = new LabelStyleDescriptionProvider(this.interpreter, this.abstractNodeMapping).apply(variableManager);
        return this.getNodeStyle(variableManager, nodeStyleDescription);
    }

    private INodeStyle getNodeStyle(VariableManager variableManager, LabelStyleDescription nodeStyleDescription) {
        INodeStyle style = null;

        if (this.shouldBeConsideredAsListItemNodeStyle(variableManager, nodeStyleDescription)) {
            style = this.createListItemNodeStyle(variableManager, nodeStyleDescription);
        } else if (nodeStyleDescription instanceof SquareDescription squareDescription) {
            style = this.createRectangularNodeStyle(variableManager, squareDescription);
        } else if (nodeStyleDescription instanceof DotDescription dotDescription) {
            style = this.createRectangularNodeStyle(variableManager, dotDescription);
        } else if (nodeStyleDescription instanceof FlatContainerStyleDescription flatContainerStyleDescription) {
            if (this.abstractNodeMapping instanceof ContainerMapping && new ContainerMappingQuery((ContainerMapping) this.abstractNodeMapping).isListContainer()) {
                style = this.createListNodeStyle(variableManager, flatContainerStyleDescription);
            } else {
                style = this.createRectangularNodeStyle(variableManager, flatContainerStyleDescription);
            }
        } else if (nodeStyleDescription instanceof WorkspaceImageDescription workspaceImageDescription) {
            WorkspaceImageDescriptionConverter workspaceImageDescriptionConverter = new WorkspaceImageDescriptionConverter(this.interpreter, variableManager, workspaceImageDescription);
            style = workspaceImageDescriptionConverter.convert();
        } else {
            // Fallback on Rectangular node style for now, until other styles are supported
            style = RectangularNodeStyle.newRectangularNodeStyle()
                    .background("rgb(200, 200, 200)")
                    .borderColor("rgb(0, 0, 0)")
                    .borderSize(1)
                    .borderStyle(LineStyle.Solid)
                    .build();
        }

        return style;
    }

    /**
     * Returns <code>true</code> if the <em>nodeStyleDescription</em> represents a simple figure (square, ellipse or
     * lozenge) and the {@link #nodeMapping} container represents his children in a list, <code>false</code> otherwise.
     *
     * @param variableManager
     *         The variable manager
     * @param nodeStyleDescription
     *         the node style description that could be considered as a list item node style
     * @return <code>true</code> if the <em>nodeStyleDescription</em> should be considered as a list item node style
     */
    private boolean shouldBeConsideredAsListItemNodeStyle(VariableManager variableManager, LabelStyleDescription nodeStyleDescription) {
        if (this.abstractNodeMapping.eContainer() instanceof ContainerMapping parentMapping) {
            return new ContainerMappingQuery(parentMapping).isListContainer();
        }
        return false;
    }

    private INodeStyle createListItemNodeStyle(VariableManager variableManager, LabelStyleDescription nodeStyleDescription) {
        return IconLabelNodeStyle.newIconLabelNodeStyle()
                .background("transparent")
                .build();
    }

    private INodeStyle createListNodeStyle(VariableManager variableManager, FlatContainerStyleDescription flatContainerStyleDescription) {
        ColorDescriptionConverter colorDescriptionConverter = new ColorDescriptionConverter(this.interpreter, variableManager.getVariables());

        String color = colorDescriptionConverter.convert(flatContainerStyleDescription.getBackgroundColor());
        String borderColor = colorDescriptionConverter.convert(flatContainerStyleDescription.getBorderColor());

        LineStyle borderStyle = new LineStyleConverter().getStyle(flatContainerStyleDescription.getBorderLineStyle());

        int borderRadius = this.getBorderRadius(flatContainerStyleDescription);

        Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), flatContainerStyleDescription.getBorderSizeComputationExpression());
        int borderSize = result.asInt().getAsInt();

        return RectangularNodeStyle.newRectangularNodeStyle()
                .background(color)
                .borderColor(borderColor)
                .borderSize(borderSize)
                .borderRadius(borderRadius)
                .borderStyle(borderStyle)
                .build();
    }

    private int getBorderRadius(FlatContainerStyleDescription flatContainerStyleDescription) {
        int borderRadius = 0;
        if (flatContainerStyleDescription.isRoundedCorner()) {
            if (flatContainerStyleDescription.eIsSet(StylePackage.Literals.ROUNDED_CORNER_STYLE_DESCRIPTION__ARC_HEIGHT)) {
                borderRadius = Math.max(borderRadius, flatContainerStyleDescription.getArcHeight());
            }
            if (flatContainerStyleDescription.eIsSet(StylePackage.Literals.ROUNDED_CORNER_STYLE_DESCRIPTION__ARC_WIDTH)) {
                borderRadius = Math.max(borderRadius, flatContainerStyleDescription.getArcWidth());
            }
        }
        return borderRadius;
    }

    private RectangularNodeStyle createRectangularNodeStyle(VariableManager variableManager, SquareDescription squareDescription) {
        ColorDescriptionConverter colorProvider = new ColorDescriptionConverter(this.interpreter, variableManager.getVariables());

        String color = colorProvider.convert(squareDescription.getColor());
        String borderColor = colorProvider.convert(squareDescription.getBorderColor());

        LineStyle borderStyle = new LineStyleConverter().getStyle(squareDescription.getBorderLineStyle());

        Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), squareDescription.getBorderSizeComputationExpression());
        int borderSize = result.asInt().getAsInt();

        return RectangularNodeStyle.newRectangularNodeStyle()
                .background(color)
                .borderColor(borderColor)
                .borderSize(borderSize)
                .borderStyle(borderStyle)
                .build();
    }

    private RectangularNodeStyle createRectangularNodeStyle(VariableManager variableManager, FlatContainerStyleDescription flatContainerStyleDescription) {
        Map<String, Object> variables = variableManager.getVariables();
        ColorDescriptionConverter colorProvider = new ColorDescriptionConverter(this.interpreter, variables);

        String color = colorProvider.convert(flatContainerStyleDescription.getBackgroundColor());
        String borderColor = colorProvider.convert(flatContainerStyleDescription.getBorderColor());

        LineStyle borderStyle = new LineStyleConverter().getStyle(flatContainerStyleDescription.getBorderLineStyle());
        int borderRadius = this.getBorderRadius(flatContainerStyleDescription);
        Result result = this.interpreter.evaluateExpression(variables, flatContainerStyleDescription.getBorderSizeComputationExpression());
        int borderSize = result.asInt().getAsInt();

        return RectangularNodeStyle.newRectangularNodeStyle()
                .background(color)
                .borderColor(borderColor)
                .borderSize(borderSize)
                .borderRadius(borderRadius)
                .borderStyle(borderStyle)
                .build();
    }

    private RectangularNodeStyle createRectangularNodeStyle(VariableManager variableManager, DotDescription dotDescription) {
        Map<String, Object> variables = variableManager.getVariables();
        ColorDescriptionConverter colorProvider = new ColorDescriptionConverter(this.interpreter, variables);

        String color = colorProvider.convert(dotDescription.getBackgroundColor());
        String borderColor = colorProvider.convert(dotDescription.getBorderColor());

        LineStyle borderStyle = new LineStyleConverter().getStyle(dotDescription.getBorderLineStyle());
        int borderRadius = 100;
        Result result = this.interpreter.evaluateExpression(variables, dotDescription.getBorderSizeComputationExpression());
        int borderSize = result.asInt().getAsInt();

        return RectangularNodeStyle.newRectangularNodeStyle()
                .background(color)
                .borderColor(borderColor)
                .borderSize(borderSize)
                .borderRadius(borderRadius)
                .borderStyle(borderStyle)
                .build();
    }

}
