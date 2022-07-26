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
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the computation of the label of a diagram.
 *
 * @author sbegaudeau
 */
public class DiagramLabelProviderTests {
    @Test
    public void testUseDiagramNameFromUser() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        org.eclipse.sirius.diagram.description.DiagramDescription diagramDescription = DescriptionFactory.eINSTANCE.createDiagramDescription();
        DiagramLabelProvider labelProvider = new DiagramLabelProvider(interpreter, diagramDescription);

        VariableManager variableManager = new VariableManager();
        String userProvidedDiagramName = "diagram name from the user"; //$NON-NLS-1$
        variableManager.put(DiagramDescription.LABEL, userProvidedDiagramName);
        String result = labelProvider.apply(variableManager);

        assertThat(result).isEqualTo(userProvidedDiagramName);
    }

    @Test
    public void testFallbackToTitleExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        org.eclipse.sirius.diagram.description.DiagramDescription diagramDescription = DescriptionFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setTitleExpression("aql:'NewLabel'"); //$NON-NLS-1$

        DiagramLabelProvider labelProvider = new DiagramLabelProvider(interpreter, diagramDescription);

        VariableManager variableManager = new VariableManager();
        String result = labelProvider.apply(variableManager);

        assertThat(result).isEqualTo("NewLabel"); //$NON-NLS-1$
    }

    @Test
    public void testUseDefaultTitleExpressionWithLabel() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        org.eclipse.sirius.diagram.description.DiagramDescription diagramDescription = DescriptionFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setLabel("DefaultLabel"); //$NON-NLS-1$
        DiagramLabelProvider labelProvider = new DiagramLabelProvider(interpreter, diagramDescription);

        VariableManager variableManager = new VariableManager();
        String result = labelProvider.apply(variableManager);

        assertThat(result).isEqualTo("new DefaultLabel"); //$NON-NLS-1$
    }

    @Test
    public void testUseDefaultTitleExpressionWithName() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        org.eclipse.sirius.diagram.description.DiagramDescription diagramDescription = DescriptionFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName("DefaultName"); //$NON-NLS-1$
        DiagramLabelProvider labelProvider = new DiagramLabelProvider(interpreter, diagramDescription);

        VariableManager variableManager = new VariableManager();
        String result = labelProvider.apply(variableManager);

        assertThat(result).isEqualTo("new DefaultName"); //$NON-NLS-1$
    }
}
