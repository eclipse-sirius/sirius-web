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
package org.eclipse.sirius.web.compat.operations;

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;
import java.util.UUID;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.If;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.web.representations.Status;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the If operation handler.
 *
 * @author lfasani
 */
public class IfOperationHandlerTestCases {
    private static final String AQL_TRUE = "true"; //$NON-NLS-1$

    private IfOperationHandler ifOperationHandler;

    private If ifOperation;

    private OperationTestContext operationTestContext;

    @Before
    public void initialize() {
        this.operationTestContext = new OperationTestContext();

        this.ifOperation = ToolFactory.eINSTANCE.createIf();
        this.ifOperationHandler = new IfOperationHandler(this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(), this.ifOperation);
    }

    @Test
    public void ifOperationHandlerNominalCaseTest() {
        // used to check that the if operation succeeded
        String newName = "newName"; //$NON-NLS-1$
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        subChangeContext.setBrowseExpression("aql:self.renameENamedElementService('" + newName + "'))"); //$NON-NLS-1$//$NON-NLS-2$
        this.ifOperation.getSubModelOperations().add(subChangeContext);

        // check the nominal case
        this.ifOperation.setConditionExpression(AQL_TRUE);

        Status handleResult = this.ifOperationHandler.handle(this.operationTestContext.getVariables());

        assertEquals(Status.OK, handleResult);
        assertEquals(newName, this.operationTestContext.getRootPackage().getName());

    }

    /**
     * Check that a null or empty condition expression DO stop the handle of subOperations.</br>
     * Check that a exception raised in expression do it as well.
     */
    @Test
    public void ifOperationHandlerErrorCasesTest() {
        // Add a SubModelOperations to check that it is handled
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        this.ifOperation.getSubModelOperations().add(subChangeContext);

        // Check null expression case
        this.handleAndCheckExecution(null, this.operationTestContext.getRootPackage());

        // Check empty expression case
        this.handleAndCheckExecution("", this.operationTestContext.getRootPackage()); //$NON-NLS-1$

        // Check expression with exception case
        this.handleAndCheckExecution(ModelOperationServices.AQL_THROW_ERROR_EXPRESSION, this.operationTestContext.getRootPackage());
    }

    /**
     * Execute the If operation and check that the sub ChangeContext is not executed.
     */
    private void handleAndCheckExecution(String conditionExpression, ENamedElement renamedElement) {
        String newName = UUID.randomUUID().toString();
        String renameExpression = MessageFormat.format(ModelOperationServices.AQL_RENAME_EXPRESSION, newName);
        ((ChangeContext) this.ifOperation.getSubModelOperations().get(0)).setBrowseExpression(renameExpression);

        // execute
        this.ifOperation.setConditionExpression(conditionExpression);

        Status handleResult = this.ifOperationHandler.handle(this.operationTestContext.getVariables());

        // check
        assertEquals(Status.OK, handleResult);
        assertEquals(OperationTestContext.ROOT_PACKAGE_NAME, renamedElement.getName());
    }

}
