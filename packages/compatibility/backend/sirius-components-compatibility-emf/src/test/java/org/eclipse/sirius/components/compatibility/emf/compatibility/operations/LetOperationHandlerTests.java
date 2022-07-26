/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and others.
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
package org.eclipse.sirius.components.compatibility.emf.compatibility.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.sirius.components.compatibility.emf.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.components.compatibility.emf.modeloperations.LetOperationHandler;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.Let;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests of the Let operation handler.
 *
 * @author lfasani
 */
public class LetOperationHandlerTests {
    private static final String VARIABLE_NAME = "myVariableName"; //$NON-NLS-1$

    private LetOperationHandler letOperationHandler;

    private Let letOperation;

    private OperationTestContext operationTestContext;

    @BeforeEach
    public void initialize() {
        this.operationTestContext = new OperationTestContext();

        this.letOperation = ToolFactory.eINSTANCE.createLet();
        this.letOperationHandler = new LetOperationHandler(this.operationTestContext.getObjectService(), this.operationTestContext.getRepresentationMetadataSearchService(),
                this.operationTestContext.getIdentifierProvider(), this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(List.of()), this.letOperation);
    }

    @Test
    public void letOperationHandlerNominalCaseTest() {
        // used to check that the let operation succeeded
        String newName = "newClass"; //$NON-NLS-1$
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        subChangeContext.setBrowseExpression("aql:" + VARIABLE_NAME + ".renameENamedElementService('" + newName + "'))"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        this.letOperation.getSubModelOperations().add(subChangeContext);

        // check the nominal case
        this.letOperation.setVariableName(VARIABLE_NAME);
        this.letOperation.setValueExpression("aql:self"); //$NON-NLS-1$

        IStatus handleResult = this.letOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);
        assertEquals(newName, this.operationTestContext.getRootPackage().getName());

    }

    /**
     * Check that a null or empty expression or variable name do not stop the handle of subOperations.</br>
     * Check that a NPE does not stop the handle of subOperations.
     */
    @Test
    public void letOperationHandlerErrorCasesTest() {
        // Add a SubModelOperations to check that it is handled
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        this.letOperation.getSubModelOperations().add(subChangeContext);

        // Check null expression case
        this.handleAndCheckExecution(null, null, this.operationTestContext.getRootPackage());

        // Check empty expression case
        this.handleAndCheckExecution("", "", this.operationTestContext.getRootPackage()); //$NON-NLS-1$ //$NON-NLS-2$

        // Check expression with exception case
        this.handleAndCheckExecution(VARIABLE_NAME, ModelOperationServices.AQL_THROW_ERROR_EXPRESSION, this.operationTestContext.getRootPackage());
    }

    /**
     * Execute the root operation and check that the sub ChangeContext is properly executed.
     */
    private void handleAndCheckExecution(String variableName, String setValueExpression, ENamedElement renamedElement) {
        String newName = UUID.randomUUID().toString();
        String renameExpression = MessageFormat.format(ModelOperationServices.AQL_RENAME_EXPRESSION, newName);
        ((ChangeContext) this.letOperation.getSubModelOperations().get(0)).setBrowseExpression(renameExpression);

        // execute
        this.letOperation.setVariableName(variableName);
        this.letOperation.setValueExpression(setValueExpression);

        IStatus handleResult = this.letOperationHandler.handle(this.operationTestContext.getVariables());

        // check
        assertTrue(handleResult instanceof Success);
        assertEquals(newName, renamedElement.getName());
    }
}
