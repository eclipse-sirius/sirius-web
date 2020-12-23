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

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;
import java.util.UUID;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ChangeContextOperationHandler;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.web.representations.Status;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the ChangeContext operation handler.
 *
 * @author lfasani
 */
public class ChangeContextOperationHandlerTestCases {

    private ChangeContextOperationHandler changeContextOperationHandler;

    private ChangeContext changeContext;

    private OperationTestContext operationTestContext;

    @Before
    public void initialize() {
        this.operationTestContext = new OperationTestContext();

        this.changeContext = ToolFactory.eINSTANCE.createChangeContext();
        this.changeContextOperationHandler = new ChangeContextOperationHandler(this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(), this.changeContext);
    }

    @Test
    public void changeContextHandlerNominalCaseTest() {
        // check the nominal case
        String newName = "newName"; //$NON-NLS-1$
        this.changeContext.setBrowseExpression("aql:self.renameENamedElementService('" + newName + "')"); //$NON-NLS-1$//$NON-NLS-2$

        Status handleResult = this.changeContextOperationHandler.handle(this.operationTestContext.getVariables());

        assertEquals(Status.OK, handleResult);
        assertEquals(newName, this.operationTestContext.getRootPackage().getName());

        // check the ChangeContextOperation correctly change the context
        // Add a SubModelOperations to check that it is handled
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        this.changeContext.getSubModelOperations().add(subChangeContext);

        this.handleAndCheckExecution("aql:self.eClassifiers->first()", this.operationTestContext.getClass1()); //$NON-NLS-1$
    }

    /**
     * Check that a null or empty expression does not stop the handle of subOperations.</br>
     * Check that a NPE does not stop the handle of subOperations.
     */
    @Test
    public void changeContextHandlerErrorCasesTest() {
        // Add a SubModelOperations to check that it is handled
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        this.changeContext.getSubModelOperations().add(subChangeContext);

        // Check null expression case
        this.handleAndCheckExecution(null, this.operationTestContext.getRootPackage());

        // Check empty expression case
        this.handleAndCheckExecution("", this.operationTestContext.getRootPackage()); //$NON-NLS-1$

        // Check expression with exception case
        this.handleAndCheckExecution(ModelOperationServices.AQL_THROW_ERROR_EXPRESSION, this.operationTestContext.getRootPackage());
    }

    /**
     * Execute the root ChangeContext operation and check that the sub ChangeContext is properly executed.
     *
     * @param browseExpression
     *            expression of the root ChangeContext
     */
    private void handleAndCheckExecution(String browseExpression, ENamedElement renamedElement) {
        String newName = UUID.randomUUID().toString();
        String renameExpression = MessageFormat.format(ModelOperationServices.AQL_RENAME_EXPRESSION, newName);
        ((ChangeContext) this.changeContext.getSubModelOperations().get(0)).setBrowseExpression(renameExpression);

        // execute
        this.changeContext.setBrowseExpression(browseExpression);
        Status handleResult = this.changeContextOperationHandler.handle(this.operationTestContext.getVariables());

        // check
        assertEquals(Status.OK, handleResult);
        assertEquals(newName, renamedElement.getName());
    }
}
