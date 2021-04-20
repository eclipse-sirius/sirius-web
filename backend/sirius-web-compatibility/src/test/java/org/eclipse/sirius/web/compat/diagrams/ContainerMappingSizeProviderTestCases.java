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
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.style.FlatContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.StyleFactory;
import org.eclipse.sirius.viewpoint.description.FixedColor;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.Test;

/**
 * Unit tests of the container mapping size provider.
 *
 * @author fbarbin
 */
public class ContainerMappingSizeProviderTestCases {

    private static final String AQL_20 = "aql:20"; //$NON-NLS-1$

    private static final String AQL_10 = "aql:10"; //$NON-NLS-1$

    @Test
    public void testSizeProviderWithNoProvidedSizeFromVSM() {
        ContainerMapping containerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        containerMapping.setStyle(this.createFlatStyle(0, 0, 0));

        VariableManager variableManager = new VariableManager();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));

        var abstractNodeMappingSizeProvider = new AbstractNodeMappingSizeProvider(interpreter, containerMapping);
        Size size = abstractNodeMappingSizeProvider.apply(variableManager);
        assertThat(size).extracting(Size::getHeight).isEqualTo(-1.0);
        assertThat(size).extracting(Size::getWidth).isEqualTo(-1.0);
    }

    @Test
    public void testSizeProviderWithProvidedSizeFromVSM() {
        ContainerMapping containerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        FlatContainerStyleDescription flatStyle = this.createFlatStyle(0, 0, 0);
        flatStyle.setHeightComputationExpression(AQL_10);
        flatStyle.setWidthComputationExpression(AQL_20);
        containerMapping.setStyle(flatStyle);

        VariableManager variableManager = new VariableManager();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));

        var abstractNodeMappingSizeProvider = new AbstractNodeMappingSizeProvider(interpreter, containerMapping);
        Size size = abstractNodeMappingSizeProvider.apply(variableManager);
        assertThat(size).extracting(Size::getHeight).isEqualTo(100.0);
        assertThat(size).extracting(Size::getWidth).isEqualTo(200.0);
    }

    private FlatContainerStyleDescription createFlatStyle(int red, int green, int blue) {
        FlatContainerStyleDescription containerStyleDescription = StyleFactory.eINSTANCE.createFlatContainerStyleDescription();

        FixedColor fixedColor = org.eclipse.sirius.viewpoint.description.DescriptionFactory.eINSTANCE.createFixedColor();
        fixedColor.setRed(red);
        fixedColor.setGreen(green);
        fixedColor.setBlue(blue);

        containerStyleDescription.setBorderColor(fixedColor);
        return containerStyleDescription;
    }
}
