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

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.diagram.description.style.StyleFactory;
import org.eclipse.sirius.diagram.description.style.WorkspaceImageDescription;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.Test;

/**
 * Unit test of the WorkspaceImageDescriptionConverter.
 *
 * @author hmarchadour
 */
public class WorkspaceImageDescriptionConverterTestCases {

    private static final String WORKSPACE_IMAGE_PATH = "/org.eclipse.sirius.web.diagrams/path"; //$NON-NLS-1$

    /**
     * Test the default value of the scalingFactor according to an unset sizeComputationExpression. <br/>
     * <br/>
     * Expected <strong>3</strong>
     *
     * @see org.eclipse.sirius.diagram.description.style.impl.NodeStyleDescriptionImpl.SIZE_COMPUTATION_EXPRESSION_EDEFAULT
     */
    @Test
    public void testDefaultScalingFactor() {
        int defaultSizeComputation = 3;

        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));

        VariableManager variableManager = new VariableManager();

        WorkspaceImageDescription workspaceImageDescription = StyleFactory.eINSTANCE.createWorkspaceImageDescription();
        workspaceImageDescription.setWorkspacePath(WORKSPACE_IMAGE_PATH);

        ImageNodeStyle imageNodeStyle = new WorkspaceImageDescriptionConverter(interpreter, variableManager, workspaceImageDescription).convert();
        assertThat(imageNodeStyle.getScalingFactor()).isEqualTo(defaultSizeComputation);
    }

    /**
     * Test the value of the scalingFactor according to the following sizeComputationExpression.
     *
     * <pre>
     * aql:10
     * </pre>
     *
     * Expected <strong>10</strong>
     */
    @Test
    public void testBasicScalingFactorExp10() {
        int expectedSize = 10;
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));

        VariableManager variableManager = new VariableManager();

        WorkspaceImageDescription workspaceImageDescription = StyleFactory.eINSTANCE.createWorkspaceImageDescription();
        workspaceImageDescription.setWorkspacePath(WORKSPACE_IMAGE_PATH);
        workspaceImageDescription.setSizeComputationExpression("aql:" + expectedSize); //$NON-NLS-1$

        ImageNodeStyle imageNodeStyle = new WorkspaceImageDescriptionConverter(interpreter, variableManager, workspaceImageDescription).convert();
        assertThat(imageNodeStyle.getScalingFactor()).isEqualTo(expectedSize);
    }
}
