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
package org.eclipse.sirius.web.emf.compatibility.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.UUID;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.SetValue;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.SetValueOperationHandler;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests of the SetValue operation handler.
 *
 * @author lfasani
 */
public class SetValueOperationHandlerTests {
    private static final String NAME_FEATURE = "name"; //$NON-NLS-1$

    private SetValueOperationHandler setValueOperationHandler;

    private SetValue setValue;

    private OperationTestContext operationTestContext;

    @BeforeEach
    public void initialize() {
        this.operationTestContext = new OperationTestContext();

        this.setValue = ToolFactory.eINSTANCE.createSetValue();
        this.setValueOperationHandler = new SetValueOperationHandler(this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(), this.setValue);
    }

    @Test
    public void setValueOperationHandlerNominalCaseTest() {
        // check the nominal case
        String newName = "newName"; //$NON-NLS-1$
        this.setValue.setFeatureName(NAME_FEATURE);
        this.setValue.setValueExpression("aql:'" + newName + "'"); //$NON-NLS-1$//$NON-NLS-2$

        IStatus handleResult = this.setValueOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);
        assertEquals(newName, this.operationTestContext.getRootPackage().getName());
    }

    /**
     * Check that a null or empty expression or feature name do not stop the handle of subOperations.</br>
     * Check that a NPE does not stop the handle of subOperations.
     */
    @Test
    public void setValueOperationHandlerErrorCasesTest() {
        // Add a SubModelOperations to check that it is handled
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        this.setValue.getSubModelOperations().add(subChangeContext);

        // Check null expression case
        this.handleAndCheckExecution(null, null, this.operationTestContext.getRootPackage());

        // Check empty expression case
        this.handleAndCheckExecution("", "", this.operationTestContext.getRootPackage()); //$NON-NLS-1$ //$NON-NLS-2$

        // Check expression with exception case
        this.handleAndCheckExecution(NAME_FEATURE, ModelOperationServices.AQL_THROW_ERROR_EXPRESSION, this.operationTestContext.getRootPackage());
    }

    /**
     * Execute the root operation and check that the sub ChangeContext is properly executed.
     *
     * @param browseExpression
     *            expression of the root ChangeContext
     */
    private void handleAndCheckExecution(String featureName, String setValueExpression, ENamedElement renamedElement) {
        String newName = UUID.randomUUID().toString();
        String renameExpression = MessageFormat.format(ModelOperationServices.AQL_RENAME_EXPRESSION, newName);
        ((ChangeContext) this.setValue.getSubModelOperations().get(0)).setBrowseExpression(renameExpression);

        // execute
        this.setValue.setFeatureName(featureName);
        this.setValue.setValueExpression(setValueExpression);

        IStatus handleResult = this.setValueOperationHandler.handle(this.operationTestContext.getVariables());

        // check
        assertTrue(handleResult instanceof Success);
        assertEquals(newName, renamedElement.getName());
    }
}
