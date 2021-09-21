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

import org.eclipse.sirius.viewpoint.description.tool.Case;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.Default;
import org.eclipse.sirius.viewpoint.description.tool.Switch;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.SwitchOperationHandler;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests of the Switch operation handler.
 *
 * @author lfasani
 */
public class SwitchOperationHandlerTests {
    private static final String NAME_CASE1 = "nameCase1"; //$NON-NLS-1$

    private static final String NAME_CASE2 = "nameCase2"; //$NON-NLS-1$

    private static final String NAME_DEFAULT = "nameDefault"; //$NON-NLS-1$

    private static final String AQL_TRUE = "aql:true"; //$NON-NLS-1$

    private static final String AQL_FALSE = "aql:false"; //$NON-NLS-1$

    private SwitchOperationHandler switchOperationHandler;

    private Switch switchOperation;

    private Case case1;

    private Case case2;

    private Default defaultCase;

    private OperationTestContext operationTestContext;

    @BeforeEach
    public void initialize() {
        this.operationTestContext = new OperationTestContext();

        this.switchOperation = ToolFactory.eINSTANCE.createSwitch();

        this.case1 = this.addCase(NAME_CASE1);
        this.case2 = this.addCase(NAME_CASE2);

        this.defaultCase = ToolFactory.eINSTANCE.createDefault();
        ChangeContext subChangeContextDefault = ToolFactory.eINSTANCE.createChangeContext();
        subChangeContextDefault.setBrowseExpression(MessageFormat.format(ModelOperationServices.AQL_RENAME_EXPRESSION, NAME_DEFAULT));
        this.defaultCase.getSubModelOperations().add(subChangeContextDefault);
        this.switchOperation.setDefault(this.defaultCase);

        this.switchOperationHandler = new SwitchOperationHandler(this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(), this.switchOperation);
    }

    @Test
    /**
     * Check the switchOperationHandler nominal cases.</br>
     */
    public void switchOperationHandlerNominalCaseTest() {
        // check the nominal cases
        this.testSwitchCase(AQL_TRUE, AQL_TRUE, NAME_CASE1);
        this.testSwitchCase(AQL_TRUE, AQL_FALSE, NAME_CASE1);
        this.testSwitchCase(AQL_FALSE, AQL_TRUE, NAME_CASE2);
        this.testSwitchCase(AQL_FALSE, AQL_FALSE, NAME_DEFAULT);
    }

    private void testSwitchCase(String expressionCase1, String expressionCase2, String expectedRootName) {
        this.case1.setConditionExpression(expressionCase1);
        this.case2.setConditionExpression(expressionCase2);

        IStatus handleResult = this.switchOperationHandler.handle(this.operationTestContext.getVariables());
        assertTrue(handleResult instanceof Success);
        assertEquals(expectedRootName, this.operationTestContext.getRootPackage().getName());
    }

    /**
     * Check that bad expressions do not raise exceptions.</br>
     */
    @Test
    public void switchOperationHandlerErrorCasesTest() {
        // The behavior of the two following cases is strange because "" and "UnknownExpression" are considered as
        // true but it is the behavior in Sirius
        this.testSwitchCase("", AQL_TRUE, NAME_CASE1); //$NON-NLS-1$
        this.testSwitchCase("UnknownExpression", AQL_TRUE, NAME_CASE1); //$NON-NLS-1$
        this.testSwitchCase("aql:UnknownExpression", AQL_TRUE, NAME_CASE2); //$NON-NLS-1$
        this.testSwitchCase(null, AQL_TRUE, NAME_CASE2);
        this.testSwitchCase(ModelOperationServices.AQL_THROW_ERROR_EXPRESSION, AQL_FALSE, NAME_DEFAULT);
    }

    private Case addCase(String newName) {
        Case caseToReturn = ToolFactory.eINSTANCE.createCase();
        this.switchOperation.getCases().add(caseToReturn);
        ChangeContext subChangeContextCase1 = ToolFactory.eINSTANCE.createChangeContext();
        subChangeContextCase1.setBrowseExpression(MessageFormat.format(ModelOperationServices.AQL_RENAME_EXPRESSION, newName));
        caseToReturn.getSubModelOperations().add(subChangeContextCase1);
        return caseToReturn;
    }
}
