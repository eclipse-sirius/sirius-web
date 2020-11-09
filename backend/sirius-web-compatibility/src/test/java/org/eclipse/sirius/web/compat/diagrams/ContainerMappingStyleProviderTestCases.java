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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.diagram.LineStyle;
import org.eclipse.sirius.diagram.description.ConditionalContainerStyleDescription;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.style.ContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.StyleFactory;
import org.eclipse.sirius.viewpoint.description.FixedColor;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.Test;

/**
 * Unit tests of the container mapping style provider.
 *
 * @author pcdavid
 * @author sbegaudeau
 */
public class ContainerMappingStyleProviderTestCases {

    private static final String EXPRESSION_FALSE = "aql:false"; //$NON-NLS-1$

    private static final String EXPRESSION_TRUE = "aql:true"; //$NON-NLS-1$

    /**
     * Non-regression test for Conditional styles issue on containers.
     * <p>
     * When a mapping has several custom styles whose condition is true, the first one is selected: not the main style,
     * neither another of the matching conditional styles.
     * <p>
     * We use the border color attribute to identify which of the style candidates are actually selected
     */
    @Test
    public void testFirstMatchingConditionalStyleIsUsed() {
        ContainerMapping containerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        containerMapping.setStyle(this.createFlatStyle(0, 0, 0));
        containerMapping.getConditionnalStyles().add(this.createConditionalStyle(EXPRESSION_FALSE, this.createFlatStyle(1, 1, 1)));
        containerMapping.getConditionnalStyles().add(this.createConditionalStyle(EXPRESSION_TRUE, this.createFlatStyle(2, 2, 2)));
        containerMapping.getConditionnalStyles().add(this.createConditionalStyle(EXPRESSION_TRUE, this.createFlatStyle(3, 3, 3)));

        VariableManager variableManager = new VariableManager();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        INodeStyle nodeStyle = new ContainerMappingStyleProvider(interpreter, containerMapping).apply(variableManager);

        assertThat(nodeStyle).isInstanceOf(RectangularNodeStyle.class);
        RectangularNodeStyle rectangularNodeStyle = (RectangularNodeStyle) nodeStyle;
        assertThat(rectangularNodeStyle.getBorderColor()).isEqualTo("#020202"); //$NON-NLS-1$
    }

    private ConditionalContainerStyleDescription createConditionalStyle(String predicateExpression, ContainerStyleDescription containerStyleDescription) {
        ConditionalContainerStyleDescription conditionalContainerStyle = DescriptionFactory.eINSTANCE.createConditionalContainerStyleDescription();
        conditionalContainerStyle.setPredicateExpression(predicateExpression);
        conditionalContainerStyle.setStyle(containerStyleDescription);
        return conditionalContainerStyle;
    }

    private ContainerStyleDescription createFlatStyle(int red, int green, int blue) {
        ContainerStyleDescription containerStyleDescription = StyleFactory.eINSTANCE.createFlatContainerStyleDescription();

        FixedColor fixedColor = org.eclipse.sirius.viewpoint.description.DescriptionFactory.eINSTANCE.createFixedColor();
        fixedColor.setRed(red);
        fixedColor.setGreen(green);
        fixedColor.setBlue(blue);

        containerStyleDescription.setBorderColor(fixedColor);
        return containerStyleDescription;
    }

    @Test
    public void testBorderLineStyleConversion() {
        // @formatter:off
        var conversions = Map.of(LineStyle.DASH_DOT_LITERAL, org.eclipse.sirius.web.diagrams.LineStyle.Dash_Dot,
                                LineStyle.DASH_LITERAL, org.eclipse.sirius.web.diagrams.LineStyle.Dash,
                                LineStyle.DOT_LITERAL, org.eclipse.sirius.web.diagrams.LineStyle.Dot,
                                LineStyle.SOLID_LITERAL, org.eclipse.sirius.web.diagrams.LineStyle.Solid);
        // @formatter:on

        for (var entry : conversions.entrySet()) {
            ContainerMapping containerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
            ContainerStyleDescription style = this.createFlatStyle(0, 0, 0);
            style.setBorderLineStyle(entry.getKey());
            containerMapping.setStyle(style);

            VariableManager variableManager = new VariableManager();
            AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
            INodeStyle nodeStyle = new ContainerMappingStyleProvider(interpreter, containerMapping).apply(variableManager);
            assertThat(nodeStyle).isInstanceOf(RectangularNodeStyle.class);
            RectangularNodeStyle rectangularNodeStyle = (RectangularNodeStyle) nodeStyle;
            assertThat(rectangularNodeStyle.getBorderStyle()).isEqualTo(entry.getValue());
        }
    }
}
