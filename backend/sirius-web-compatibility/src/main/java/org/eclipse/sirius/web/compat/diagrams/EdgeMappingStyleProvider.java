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

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.diagram.description.ConditionalEdgeStyleDescription;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.style.EdgeStyleDescription;
import org.eclipse.sirius.web.diagrams.ArrowStyle;
import org.eclipse.sirius.web.diagrams.EdgeStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Used to compute the style using the definition of an edge mapping.
 *
 * @author sbegaudeau
 */
public class EdgeMappingStyleProvider implements Function<VariableManager, EdgeStyle> {

    private AQLInterpreter interpreter;

    private EdgeMapping edgeMapping;

    public EdgeMappingStyleProvider(AQLInterpreter interpreter, EdgeMapping edgeMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.edgeMapping = Objects.requireNonNull(edgeMapping);
    }

    @Override
    public EdgeStyle apply(VariableManager variableManager) {
        EdgeStyle edgeStyle = null;

        var conditionnalStyles = this.edgeMapping.getConditionnalStyles();
        for (ConditionalEdgeStyleDescription conditionalStyle : conditionnalStyles) {
            String predicateExpression = conditionalStyle.getPredicateExpression();
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), predicateExpression);
            boolean shouldUseStyle = result.asBoolean().orElse(Boolean.FALSE).booleanValue();
            if (shouldUseStyle) {
                edgeStyle = this.getEdgeStyle(variableManager, conditionalStyle.getStyle());
                break;
            }
        }

        if (edgeStyle == null) {
            edgeStyle = this.getEdgeStyle(variableManager, this.edgeMapping.getStyle());
        }
        return edgeStyle;
    }

    private EdgeStyle getEdgeStyle(VariableManager variableManager, EdgeStyleDescription style) {
        ColorDescriptionConverter colorDescriptionConverter = new ColorDescriptionConverter(this.interpreter, variableManager);
        LineStyleConverter lineStyleConverter = new LineStyleConverter();
        ArrowStyleConverter arrowStyleConverter = new ArrowStyleConverter();

        int size = 1;
        LineStyle lineStyle = lineStyleConverter.getStyle(style.getLineStyle());
        ArrowStyle sourceArrow = arrowStyleConverter.getStyle(style.getSourceArrow());
        ArrowStyle targetArrow = arrowStyleConverter.getStyle(style.getTargetArrow());
        String color = colorDescriptionConverter.convert(style.getStrokeColor());

        // @formatter:off
        return EdgeStyle.newEdgeStyle()
                .size(size)
                .lineStyle(lineStyle)
                .sourceArrow(sourceArrow)
                .targetArrow(targetArrow)
                .color(color)
                .build();
        // @formatter:on
    }

}
