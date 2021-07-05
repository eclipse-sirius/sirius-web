/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.MoveElement;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.MoveElementOperationHandler;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests of the Move operation handler.
 *
 * @author lfasani
 */
public class MoveElementOperationHandlerTests {
    private static final String SUBPACKAGE1_VARIABLE_NAME = "subPackage1"; //$NON-NLS-1$

    private static final String AQL_NEWCONTAINER_EXPRESSION = "aql:" + SUBPACKAGE1_VARIABLE_NAME; //$NON-NLS-1$

    private static final String NAME_FEATURE = "eClassifiers"; //$NON-NLS-1$

    private MoveElementOperationHandler moveElementOperationHandler;

    private MoveElement moveElementOperation;

    private EPackage subPackage1;

    private OperationTestContext operationTestContext;

    @BeforeEach
    public void initialize() {
        this.operationTestContext = new OperationTestContext();

        this.subPackage1 = EcoreFactory.eINSTANCE.createEPackage();
        this.operationTestContext.getRootPackage().getESubpackages().add(this.subPackage1);

        this.operationTestContext.getVariables().put(SUBPACKAGE1_VARIABLE_NAME, this.subPackage1);

        this.operationTestContext.getVariables().put(VariableManager.SELF, this.operationTestContext.getClass1());

        this.moveElementOperation = ToolFactory.eINSTANCE.createMoveElement();

        this.moveElementOperationHandler = new MoveElementOperationHandler(this.operationTestContext.getObjectService(), this.operationTestContext.getIdentifierProvider(),
                this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(List.of()), this.moveElementOperation);
    }

    @Test
    public void moveElementOperationHandlerNominalCaseTest() {
        // check the nominal case
        this.moveElementOperation.setFeatureName(NAME_FEATURE);
        this.moveElementOperation.setNewContainerExpression(AQL_NEWCONTAINER_EXPRESSION);

        IStatus handleResult = this.moveElementOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);
        assertEquals(0, this.operationTestContext.getRootPackage().getEClassifiers().size());
        assertEquals(this.operationTestContext.getClass1(), this.subPackage1.getEClassifiers().get(0));
    }

    /**
     * Check that a null or empty data do not stop the handle of subOperations.</br>
     */
    @Test
    public void moveElementOperationHandlerErrorCasesTest() {
        // Add a SubModelOperations to check that it is handled
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        this.moveElementOperation.getSubModelOperations().add(subChangeContext);

        // Check null expression case
        this.handleAndCheckExecution(null, null, this.operationTestContext.getClass1());

        // Check empty expression case
        this.handleAndCheckExecution("", "", this.operationTestContext.getClass1()); //$NON-NLS-1$ //$NON-NLS-2$

        this.handleAndCheckExecution(NAME_FEATURE, "UnknownExpression", this.operationTestContext.getClass1()); //$NON-NLS-1$
        this.handleAndCheckExecution(NAME_FEATURE, ModelOperationServices.AQL_THROW_ERROR_EXPRESSION, this.operationTestContext.getClass1());
        this.handleAndCheckExecution(NAME_FEATURE, null, this.operationTestContext.getClass1());
        this.handleAndCheckExecution("unknownFeature", AQL_NEWCONTAINER_EXPRESSION, this.operationTestContext.getClass1()); //$NON-NLS-1$
        this.handleAndCheckExecution(null, AQL_NEWCONTAINER_EXPRESSION, this.operationTestContext.getClass1());
    }

    /**
     * Execute the root operation and check that the sub ChangeContext operation is properly executed.
     */
    private void handleAndCheckExecution(String featureName, String newContainerExpression, ENamedElement renamedElement) {
        String newName = UUID.randomUUID().toString();
        String renameExpression = MessageFormat.format(ModelOperationServices.AQL_RENAME_EXPRESSION, newName);
        ((ChangeContext) this.moveElementOperation.getSubModelOperations().get(0)).setBrowseExpression(renameExpression);

        // execute
        this.moveElementOperation.setFeatureName(featureName);
        this.moveElementOperation.setNewContainerExpression(newContainerExpression);

        IStatus handleResult = this.moveElementOperationHandler.handle(this.operationTestContext.getVariables());

        // check
        assertTrue(handleResult instanceof Success);
        assertEquals(newName, renamedElement.getName());
    }

}
