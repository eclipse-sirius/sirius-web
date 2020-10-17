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
import org.eclipse.sirius.viewpoint.description.tool.For;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.web.representations.Status;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the For operation handler.
 *
 * @author lfasani
 */
public class ForOperationHandlerTestCases {
    private static final String ITERATOR_VARIABLE_NAME = "i"; //$NON-NLS-1$

    private static final String EXPRESSION = "aql:Sequence{'a', 'b', 'c'}"; //$NON-NLS-1$

    private ForOperationHandler forOperationHandler;

    private For forOperation;

    private OperationTestContext operationTestContext;

    @Before
    public void initialize() {
        this.operationTestContext = new OperationTestContext();

        this.forOperation = ToolFactory.eINSTANCE.createFor();
        this.forOperationHandler = new ForOperationHandler(this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(), this.forOperation);
    }

    @Test
    public void forOperationHandlerNominalCaseTest() {
        // check the nominal case
        this.forOperation.setIteratorName(ITERATOR_VARIABLE_NAME);
        this.forOperation.setExpression(EXPRESSION);

        // used to check that the for operation succeeded
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        subChangeContext.setBrowseExpression("aql:self.renameENamedElementService(self.name+" + ITERATOR_VARIABLE_NAME + "))"); //$NON-NLS-1$//$NON-NLS-2$
        this.forOperation.getSubModelOperations().add(subChangeContext);

        Status handleResult = this.forOperationHandler.handle(this.operationTestContext.getVariables());

        assertEquals(Status.OK, handleResult);
        assertEquals(OperationTestContext.ROOT_PACKAGE_NAME + "abc", this.operationTestContext.getRootPackage().getName()); //$NON-NLS-1$

    }

    /**
     * Check that a null or empty expression or iterator DO stop the handle of subOperations.</br>
     * Check that a NPE DO stop the handle of subOperations.
     */
    @Test
    public void forOperationHandlerErrorCasesTest() {
        // Add a SubModelOperations to check that it is handled
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        this.forOperation.getSubModelOperations().add(subChangeContext);

        // Check null expression case
        this.handleAndCheckExecution(ITERATOR_VARIABLE_NAME, null, this.operationTestContext.getRootPackage());
        this.handleAndCheckExecution(null, EXPRESSION, this.operationTestContext.getRootPackage());

        // Check empty expression case
        this.handleAndCheckExecution(ITERATOR_VARIABLE_NAME, "", this.operationTestContext.getRootPackage()); //$NON-NLS-1$
        this.handleAndCheckExecution("", EXPRESSION, this.operationTestContext.getRootPackage()); //$NON-NLS-1$

        // Check expression with exception case
        this.handleAndCheckExecution(ITERATOR_VARIABLE_NAME, ModelOperationServices.AQL_THROW_ERROR_EXPRESSION, this.operationTestContext.getRootPackage());
    }

    /**
     * Execute the root operation and check that the sub ChangeContext is not executed.
     */
    private void handleAndCheckExecution(String iteratorVariableName, String expression, ENamedElement renamedElement) {
        String newName = UUID.randomUUID().toString();
        String renameExpression = MessageFormat.format(ModelOperationServices.AQL_RENAME_EXPRESSION, newName);
        ((ChangeContext) this.forOperation.getSubModelOperations().get(0)).setBrowseExpression(renameExpression);

        // execute
        this.forOperation.setIteratorName(iteratorVariableName);
        this.forOperation.setExpression(expression);

        Status handleResult = this.forOperationHandler.handle(this.operationTestContext.getVariables());

        // check
        assertEquals(Status.OK, handleResult);
        assertEquals(OperationTestContext.ROOT_PACKAGE_NAME, renamedElement.getName());
    }

}
