/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.diagram.description.ConditionalContainerStyleDescription;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.style.ContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.FlatContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.WorkspaceImageDescription;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Used to compute the style using the definition of a container mapping.
 *
 * @author sbegaudeau
 */
public class ContainerMappingStyleProvider implements Function<VariableManager, INodeStyle> {

    private final AQLInterpreter interpreter;

    private final ContainerMapping containerMapping;

    public ContainerMappingStyleProvider(AQLInterpreter interpreter, ContainerMapping containerMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.containerMapping = Objects.requireNonNull(containerMapping);
    }

    @Override
    public INodeStyle apply(VariableManager variableManager) {
        INodeStyle style = null;

        List<ConditionalContainerStyleDescription> conditionnalStyles = this.containerMapping.getConditionnalStyles();
        for (ConditionalContainerStyleDescription conditionalStyle : conditionnalStyles) {
            String predicateExpression = conditionalStyle.getPredicateExpression();
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), predicateExpression);
            boolean shouldUseStyle = result.asBoolean().orElse(Boolean.FALSE).booleanValue();
            if (shouldUseStyle) {
                style = this.getNodeStyle(variableManager, conditionalStyle.getStyle());
                break;
            }
        }

        if (style == null) {
            style = this.getNodeStyle(variableManager, this.containerMapping.getStyle());
        }

        return style;
    }

    private INodeStyle getNodeStyle(VariableManager variableManager, ContainerStyleDescription containerStyleDescription) {
        INodeStyle style = null;

        if (containerStyleDescription instanceof FlatContainerStyleDescription) {
            FlatContainerStyleDescription flatContainerStyleDescription = (FlatContainerStyleDescription) containerStyleDescription;
            style = this.createRectangularNodeStyle(variableManager, flatContainerStyleDescription);
        } else if (containerStyleDescription instanceof WorkspaceImageDescription) {
            WorkspaceImageDescription workspaceImageDescription = (WorkspaceImageDescription) containerStyleDescription;
            WorkspaceImageDescriptionConverter workspaceImageDescriptionConverter = new WorkspaceImageDescriptionConverter(this.interpreter, variableManager, workspaceImageDescription);
            style = workspaceImageDescriptionConverter.convert();
        }

        return style;
    }

    private RectangularNodeStyle createRectangularNodeStyle(VariableManager variableManager, FlatContainerStyleDescription flatContainerStyleDescription) {
        ColorDescriptionConverter backgroundColorProvider = new ColorDescriptionConverter(this.interpreter, variableManager);
        ColorDescriptionConverter borderColorProvider = new ColorDescriptionConverter(this.interpreter, variableManager);

        String color = backgroundColorProvider.convert(flatContainerStyleDescription.getBackgroundColor());
        String borderColor = borderColorProvider.convert(flatContainerStyleDescription.getBorderColor());

        LineStyle borderStyle = LineStyle.Solid;

        Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), flatContainerStyleDescription.getBorderSizeComputationExpression());
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
