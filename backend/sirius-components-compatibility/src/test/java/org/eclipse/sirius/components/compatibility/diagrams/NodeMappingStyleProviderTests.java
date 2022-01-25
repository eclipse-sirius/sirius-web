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
import java.util.Map;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.LineStyle;
import org.eclipse.sirius.diagram.description.ConditionalNodeStyleDescription;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.style.NodeStyleDescription;
import org.eclipse.sirius.diagram.description.style.StyleFactory;
import org.eclipse.sirius.viewpoint.description.FixedColor;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the node mapping style provider.
 *
 * @author pcdavid
 * @author sbegaudeau
 */
public class NodeMappingStyleProviderTests {
    private static final String EXPRESSION_FALSE = "aql:false"; //$NON-NLS-1$

    private static final String EXPRESSION_TRUE = "aql:true"; //$NON-NLS-1$

    /**
     * Non-regression test for Conditional styles issue on nodes.
     * <p>
     * When a mapping has several custom styles whose condition is true, the first one is selected: not the main style,
     * neither another of the matching conditional styles.
     * <p>
     * We use the border color attribute to identify which of the style candidates are actually selected
     */
    @Test
    public void testFirstMatchingConditionalStyleIsUsed() {
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setStyle(this.createSquareStyle(0, 0, 0));
        nodeMapping.getConditionnalStyles().add(this.createConditionalStyle(EXPRESSION_FALSE, this.createSquareStyle(1, 1, 1)));
        nodeMapping.getConditionnalStyles().add(this.createConditionalStyle(EXPRESSION_TRUE, this.createSquareStyle(2, 2, 2)));
        nodeMapping.getConditionnalStyles().add(this.createConditionalStyle(EXPRESSION_TRUE, this.createSquareStyle(3, 3, 3)));

        VariableManager variableManager = new VariableManager();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        INodeStyle nodeStyle = new AbstractNodeMappingStyleProvider(interpreter, nodeMapping).apply(variableManager);

        assertThat(nodeStyle).isInstanceOf(RectangularNodeStyle.class);
        RectangularNodeStyle rectangularNodeStyle = (RectangularNodeStyle) nodeStyle;
        assertThat(rectangularNodeStyle.getBorderColor()).isEqualTo("#020202"); //$NON-NLS-1$
    }

    private ConditionalNodeStyleDescription createConditionalStyle(String predicateExpression, NodeStyleDescription nodeStyleDescription) {
        ConditionalNodeStyleDescription conditionalNodeStyle = DescriptionFactory.eINSTANCE.createConditionalNodeStyleDescription();
        conditionalNodeStyle.setPredicateExpression(predicateExpression);
        conditionalNodeStyle.setStyle(nodeStyleDescription);
        return conditionalNodeStyle;
    }

    private NodeStyleDescription createSquareStyle(int red, int green, int blue) {
        NodeStyleDescription nodeStyleDescription = StyleFactory.eINSTANCE.createSquareDescription();

        FixedColor fixedColor = org.eclipse.sirius.viewpoint.description.DescriptionFactory.eINSTANCE.createFixedColor();
        fixedColor.setRed(red);
        fixedColor.setGreen(green);
        fixedColor.setBlue(blue);

        nodeStyleDescription.setBorderColor(fixedColor);
        return nodeStyleDescription;
    }

    @Test
    public void testBorderLineStyleConversion() {
        // @formatter:off
        var conversions = Map.of(LineStyle.DASH_DOT_LITERAL, org.eclipse.sirius.components.diagrams.LineStyle.Dash_Dot,
                                LineStyle.DASH_LITERAL, org.eclipse.sirius.components.diagrams.LineStyle.Dash,
                                LineStyle.DOT_LITERAL, org.eclipse.sirius.components.diagrams.LineStyle.Dot,
                                LineStyle.SOLID_LITERAL, org.eclipse.sirius.components.diagrams.LineStyle.Solid);
        // @formatter:on

        for (var entry : conversions.entrySet()) {
            NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
            NodeStyleDescription style = this.createSquareStyle(0, 0, 0);
            style.setBorderLineStyle(entry.getKey());
            nodeMapping.setStyle(style);

            VariableManager variableManager = new VariableManager();
            AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
            INodeStyle nodeStyle = new AbstractNodeMappingStyleProvider(interpreter, nodeMapping).apply(variableManager);
            assertThat(nodeStyle).isInstanceOf(RectangularNodeStyle.class);
            RectangularNodeStyle rectangularNodeStyle = (RectangularNodeStyle) nodeStyle;
            assertThat(rectangularNodeStyle.getBorderStyle()).isEqualTo(entry.getValue());
        }
    }
}
