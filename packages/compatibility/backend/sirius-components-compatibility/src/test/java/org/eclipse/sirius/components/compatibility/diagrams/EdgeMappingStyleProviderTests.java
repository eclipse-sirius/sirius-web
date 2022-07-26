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
package org.eclipse.sirius.components.compatibility.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.ConditionalEdgeStyleDescription;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.style.EdgeStyleDescription;
import org.eclipse.sirius.diagram.description.style.StyleFactory;
import org.eclipse.sirius.viewpoint.description.FixedColor;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the edge mapping style provider.
 *
 * @author pcdavid
 * @author sbegaudeau
 */
public class EdgeMappingStyleProviderTests {
    private static final String EXPRESSION_FALSE = "aql:false"; //$NON-NLS-1$

    private static final String EXPRESSION_TRUE = "aql:true"; //$NON-NLS-1$

    /**
     * Non-regression test for Conditional styles on edges.
     * <p>
     * When a mapping has several custom styles whose condition is true, the first one is selected: not the main style,
     * neither another of the matching conditional styles.
     * <p>
     * We use the border color attribute to identify which of the style candidates are actually selected
     */
    @Test
    public void testFirstMatchingConditionalStyleIsUsed() {
        EdgeMapping edgeMapping = DescriptionFactory.eINSTANCE.createEdgeMapping();
        edgeMapping.setStyle(this.createStyle(0, 0, 0));
        edgeMapping.getConditionnalStyles().add(this.createConditionalStyle(EXPRESSION_FALSE, this.createStyle(1, 1, 1)));
        edgeMapping.getConditionnalStyles().add(this.createConditionalStyle(EXPRESSION_TRUE, this.createStyle(2, 2, 2)));
        edgeMapping.getConditionnalStyles().add(this.createConditionalStyle(EXPRESSION_TRUE, this.createStyle(3, 3, 3)));

        VariableManager variableManager = new VariableManager();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        EdgeStyle edgeStyle = new EdgeMappingStyleProvider(interpreter, edgeMapping).apply(variableManager);

        assertThat(edgeStyle.getColor()).isEqualTo("#020202"); //$NON-NLS-1$
    }

    private ConditionalEdgeStyleDescription createConditionalStyle(String predicateExpression, EdgeStyleDescription edgeStyleDescription) {
        ConditionalEdgeStyleDescription conditionalEdgeStyle = DescriptionFactory.eINSTANCE.createConditionalEdgeStyleDescription();
        conditionalEdgeStyle.setPredicateExpression(predicateExpression);
        conditionalEdgeStyle.setStyle(edgeStyleDescription);
        return conditionalEdgeStyle;
    }

    private EdgeStyleDescription createStyle(int red, int green, int blue) {
        EdgeStyleDescription nodeStyleDescription = StyleFactory.eINSTANCE.createEdgeStyleDescription();

        FixedColor fixedColor = org.eclipse.sirius.viewpoint.description.DescriptionFactory.eINSTANCE.createFixedColor();
        fixedColor.setRed(red);
        fixedColor.setGreen(green);
        fixedColor.setBlue(blue);

        nodeStyleDescription.setStrokeColor(fixedColor);
        return nodeStyleDescription;
    }
}
