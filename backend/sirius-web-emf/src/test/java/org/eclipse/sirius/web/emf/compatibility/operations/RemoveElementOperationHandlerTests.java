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

import org.eclipse.sirius.viewpoint.description.tool.RemoveElement;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.RemoveElementOperationHandler;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests of the RemoveElement operation handler.
 *
 * @author lfasani
 */
public class RemoveElementOperationHandlerTests {
    private RemoveElementOperationHandler removeElementOperationHandler;

    private RemoveElement removeElementOperation;

    private OperationTestContext operationTestContext;

    @BeforeEach
    public void initialize() {
        this.operationTestContext = new OperationTestContext();

        this.operationTestContext.getVariables().put(VariableManager.SELF, this.operationTestContext.getClass1());

        this.removeElementOperation = ToolFactory.eINSTANCE.createRemoveElement();
        this.removeElementOperationHandler = new RemoveElementOperationHandler(this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(), this.removeElementOperation);
    }

    @Test
    public void removeElementOperationHandlerNominalCaseTest() {
        // check the nominal case
        IStatus handleResult = this.removeElementOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);
        // the class1 under root package has been removed
        assertEquals(0, this.operationTestContext.getRootPackage().getEClassifiers().size());

    }

}
