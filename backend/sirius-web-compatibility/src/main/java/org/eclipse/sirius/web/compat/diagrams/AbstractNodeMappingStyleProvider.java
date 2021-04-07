/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.diagram.ContainerLayout;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.style.EllipseNodeDescription;
import org.eclipse.sirius.diagram.description.style.FlatContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.LozengeNodeDescription;
import org.eclipse.sirius.diagram.description.style.SquareDescription;
import org.eclipse.sirius.diagram.description.style.WorkspaceImageDescription;
import org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.ListItemNodeStyle;
import org.eclipse.sirius.web.diagrams.ListNodeStyle;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

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
        } else if (nodeStyleDescription instanceof SquareDescription) {
            SquareDescription squareDescription = (SquareDescription) nodeStyleDescription;
            style = this.createRectangularNodeStyle(variableManager, squareDescription);
        } else if (nodeStyleDescription instanceof FlatContainerStyleDescription) {
            FlatContainerStyleDescription flatContainerStyleDescription = (FlatContainerStyleDescription) nodeStyleDescription;
            if (this.abstractNodeMapping instanceof ContainerMapping && ContainerLayout.LIST.equals(((ContainerMapping) this.abstractNodeMapping).getChildrenPresentation())) {
                style = this.createListNodeStyle(variableManager, flatContainerStyleDescription);
            } else {
                style = this.createRectangularNodeStyle(variableManager, flatContainerStyleDescription);
            }
        } else if (nodeStyleDescription instanceof WorkspaceImageDescription) {
            WorkspaceImageDescription workspaceImageDescription = (WorkspaceImageDescription) nodeStyleDescription;
            WorkspaceImageDescriptionConverter workspaceImageDescriptionConverter = new WorkspaceImageDescriptionConverter(this.interpreter, variableManager, workspaceImageDescription);
            style = workspaceImageDescriptionConverter.convert();
        } else {
            // Fallback on Rectangular node style for now, until other styles are supported

            // @formatter:off
            style = RectangularNodeStyle.newRectangularNodeStyle()
                    .color("rgb(200, 200, 200)") //$NON-NLS-1$
                    .borderColor("rgb(0, 0, 0)") //$NON-NLS-1$
                    .borderSize(1)
                    .borderStyle(LineStyle.Solid)
                    .build();
            // @formatter:on
        }

        return style;
    }

    /**
     * Returns <code>true</code> if the <em>nodeStyleDescription</em> represents a simple figure (square, ellipse or
     * lozenge) and the {@link #nodeMapping} container represents his children in a list, <code>false</code> otherwise.
     *
     * @param variableManager
     *            The variable manager
     * @param nodeStyleDescription
     *            the node style description that could be considered as a list item node style
     * @return <code>true</code> if the <em>nodeStyleDescription</em> should be considered as a list item node style
     */
    private boolean shouldBeConsideredAsListItemNodeStyle(VariableManager variableManager, LabelStyleDescription nodeStyleDescription) {
        if ((nodeStyleDescription instanceof SquareDescription || nodeStyleDescription instanceof EllipseNodeDescription || nodeStyleDescription instanceof LozengeNodeDescription)
                && this.abstractNodeMapping.eContainer() instanceof ContainerMapping) {
            ContainerMapping parentMapping = (ContainerMapping) this.abstractNodeMapping.eContainer();
            LabelStyleDescription labelStyleDescription = new LabelStyleDescriptionProvider(this.interpreter, parentMapping).apply(variableManager);
            return labelStyleDescription instanceof FlatContainerStyleDescription && ContainerLayout.LIST.equals(parentMapping.getChildrenPresentation());
        }
        return false;
    }

    private INodeStyle createListItemNodeStyle(VariableManager variableManager, LabelStyleDescription nodeStyleDescription) {
        // @formatter:off
        return ListItemNodeStyle.newListItemNodeStyle()
                .backgroundColor("transparent") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    private INodeStyle createListNodeStyle(VariableManager variableManager, FlatContainerStyleDescription flatContainerStyleDescription) {
        ColorDescriptionConverter backgroundColorProvider = new ColorDescriptionConverter(this.interpreter, variableManager.getVariables());
        ColorDescriptionConverter borderColorProvider = new ColorDescriptionConverter(this.interpreter, variableManager.getVariables());

        String color = backgroundColorProvider.convert(flatContainerStyleDescription.getBackgroundColor());
        String borderColor = borderColorProvider.convert(flatContainerStyleDescription.getBorderColor());

        LineStyle borderStyle = new LineStyleConverter().getStyle(flatContainerStyleDescription.getBorderLineStyle());

        Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), flatContainerStyleDescription.getBorderSizeComputationExpression());
        int borderSize = result.asInt().getAsInt();

        // @formatter:off
        return ListNodeStyle.newListNodeStyle()
                .color(color)
                .borderColor(borderColor)
                .borderSize(borderSize)
                .borderStyle(borderStyle)
                .build();
        // @formatter:on
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

    private RectangularNodeStyle createRectangularNodeStyle(VariableManager variableManager, FlatContainerStyleDescription flatContainerStyleDescription) {
        Map<String, Object> variables = variableManager.getVariables();
        ColorDescriptionConverter colorProvider = new ColorDescriptionConverter(this.interpreter, variables);

        String color = colorProvider.convert(flatContainerStyleDescription.getBackgroundColor());
        String borderColor = colorProvider.convert(flatContainerStyleDescription.getBorderColor());

        LineStyle borderStyle = new LineStyleConverter().getStyle(flatContainerStyleDescription.getBorderLineStyle());

        Result result = this.interpreter.evaluateExpression(variables, flatContainerStyleDescription.getBorderSizeComputationExpression());
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
