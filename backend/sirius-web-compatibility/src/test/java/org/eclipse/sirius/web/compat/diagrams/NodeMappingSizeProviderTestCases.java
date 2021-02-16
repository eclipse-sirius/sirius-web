/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.style.SquareDescription;
import org.eclipse.sirius.diagram.description.style.StyleFactory;
import org.eclipse.sirius.viewpoint.description.FixedColor;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.Test;

/**
 * Unit tests of the node mapping size provider.
 *
 * @author fbarbin
 */
public class NodeMappingSizeProviderTestCases {

    private static final String AQL_10 = "aql:10"; //$NON-NLS-1$

    @Test
    public void testSizeProviderWithNoProvidedSizeFromVSM() {
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setStyle(this.createSquareStyle(0, 0, 0));

        VariableManager variableManager = new VariableManager();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));

        NodeMappingSizeProvider nodeMappingSizeProvider = new NodeMappingSizeProvider(interpreter, nodeMapping);
        Size size = nodeMappingSizeProvider.apply(variableManager);

        // by default, the size computation expression is 3.
        assertThat(size).extracting(Size::getHeight).isEqualTo(30.0);
        assertThat(size).extracting(Size::getWidth).isEqualTo(30.0);
    }

    @Test
    public void testSizeProviderWithProvidedSizeFromVSM() {
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        SquareDescription squareDescription = this.createSquareStyle(0, 0, 0);
        squareDescription.setSizeComputationExpression(AQL_10);
        nodeMapping.setStyle(squareDescription);

        VariableManager variableManager = new VariableManager();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));

        NodeMappingSizeProvider nodeMappingSizeProvider = new NodeMappingSizeProvider(interpreter, nodeMapping);
        Size size = nodeMappingSizeProvider.apply(variableManager);
        assertThat(size).extracting(Size::getHeight).isEqualTo(100.0);
        assertThat(size).extracting(Size::getWidth).isEqualTo(100.0);
    }

    private SquareDescription createSquareStyle(int red, int green, int blue) {
        SquareDescription squareDescription = StyleFactory.eINSTANCE.createSquareDescription();

        FixedColor fixedColor = org.eclipse.sirius.viewpoint.description.DescriptionFactory.eINSTANCE.createFixedColor();
        fixedColor.setRed(red);
        fixedColor.setGreen(green);
        fixedColor.setBlue(blue);

        squareDescription.setBorderColor(fixedColor);
        return squareDescription;
    }

}
