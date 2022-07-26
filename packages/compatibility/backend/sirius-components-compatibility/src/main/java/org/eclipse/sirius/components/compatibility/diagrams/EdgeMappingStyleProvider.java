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
package org.eclipse.sirius.components.compatibility.diagrams;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.style.EdgeStyleDescription;

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
        EdgeStyleDescription edgeStyleDescription = new EdgeStyleDescriptionProvider(this.interpreter, this.edgeMapping).getEdgeStyleDescription(variableManager);
        return this.getEdgeStyle(variableManager, edgeStyleDescription);
    }

    private EdgeStyle getEdgeStyle(VariableManager variableManager, EdgeStyleDescription style) {
        Map<String, Object> variables = variableManager.getVariables();
        ColorDescriptionConverter colorDescriptionConverter = new ColorDescriptionConverter(this.interpreter, variables);
        LineStyleConverter lineStyleConverter = new LineStyleConverter();
        ArrowStyleConverter arrowStyleConverter = new ArrowStyleConverter();

        int size = this.interpreter.evaluateExpression(variables, style.getSizeComputationExpression()).asInt().orElse(1);
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
