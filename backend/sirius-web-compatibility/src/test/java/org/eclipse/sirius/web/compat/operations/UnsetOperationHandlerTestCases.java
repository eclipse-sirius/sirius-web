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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.viewpoint.description.tool.Unset;
import org.eclipse.sirius.web.representations.Status;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the Unset operation handler.
 *
 * @author lfasani
 */
public class UnsetOperationHandlerTestCases {

    private static final String NAME_FEATURE = "name"; //$NON-NLS-1$

    private static final String EFACTORYINSTANCE_FEATURE = "eFactoryInstance"; //$NON-NLS-1$

    private static final String ECLASSIFIERS_FEATURE = "eClassifiers"; //$NON-NLS-1$

    private UnsetOperationHandler unsetOperationHandler;

    private Unset unset;

    private OperationTestContext operationTestContext;

    @Before
    public void initialize() {
        this.operationTestContext = new OperationTestContext();

        EClass class2 = EcoreFactory.eINSTANCE.createEClass();
        this.operationTestContext.getRootPackage().getEClassifiers().add(1, class2);

        this.operationTestContext.getClass1().getESuperTypes().add(class2);

        this.operationTestContext.getRootPackage().setEFactoryInstance(EcoreFactory.eINSTANCE.createEFactory());

        this.unset = ToolFactory.eINSTANCE.createUnset();
        this.unsetOperationHandler = new UnsetOperationHandler(this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(), this.unset);
    }

    @Test
    public void unsetMonoValueFeatureTests() {
        // check unset on mono value EAttribute
        this.unset.setFeatureName(NAME_FEATURE);
        this.unset.setElementExpression(null);

        Status handleResult = this.unsetOperationHandler.handle(this.operationTestContext.getVariables());

        assertEquals(Status.OK, handleResult);
        assertEquals(null, this.operationTestContext.getRootPackage().getName());

        // check unset on mono value EStrucuralFeature
        this.unset.setFeatureName(EFACTORYINSTANCE_FEATURE);
        this.unset.setElementExpression(null);

        handleResult = this.unsetOperationHandler.handle(this.operationTestContext.getVariables());

        assertEquals(Status.OK, handleResult);
        assertEquals(null, this.operationTestContext.getRootPackage().getEFactoryInstance());
    }

    @Test
    public void unsetMultiValuedFeatureTests() {
        // check that class1 has been removed from rootPackage
        this.unset.setFeatureName(ECLASSIFIERS_FEATURE);
        this.unset.setElementExpression("aql:self.eClassifiers->first()"); //$NON-NLS-1$

        Status handleResult = this.unsetOperationHandler.handle(this.operationTestContext.getVariables());

        assertEquals(Status.OK, handleResult);
        // This result is functionally unexpected but is the one in Sirius
        // The expected size is 1
        assertEquals(2, this.operationTestContext.getRootPackage().getEClassifiers().size());
        // the expected should be NotSame
        assertEquals(this.operationTestContext.getClass1(), this.operationTestContext.getRootPackage().getEClassifiers().get(0));

        // check that all classes are removed from rootPackage
        this.unset.setFeatureName(ECLASSIFIERS_FEATURE);
        this.unset.setElementExpression(null);

        handleResult = this.unsetOperationHandler.handle(this.operationTestContext.getVariables());

        assertEquals(Status.OK, handleResult);
        // This result is functionally unexpected but is the one in Sirius
        // The expected size is 0
        assertEquals(2, this.operationTestContext.getRootPackage().getEClassifiers().size());
        // the expected should be NotSame
        assertEquals(this.operationTestContext.getClass1(), this.operationTestContext.getRootPackage().getEClassifiers().get(0));
    }

    /**
     * Check that a null or empty expression or feature name do not stop the handle of subOperations.</br>
     * Check that a NPE does not stop the handle of subOperations.
     */
    @Test
    public void unsetOperationHandlerErrorCasesTest() {
        // Add a SubModelOperations to check that it is handled
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        this.unset.getSubModelOperations().add(subChangeContext);

        // Check null expression case
        this.handleAndCheckExecution(null, null, this.operationTestContext.getRootPackage());

        // Check empty expression case
        this.handleAndCheckExecution("", "", this.operationTestContext.getRootPackage()); //$NON-NLS-1$ //$NON-NLS-2$

        // Check expression with exception case
        this.handleAndCheckExecution(NAME_FEATURE, ModelOperationServices.AQL_THROW_ERROR_EXPRESSION, this.operationTestContext.getRootPackage());
    }

    /**
     * Execute the Unset operation and check that the sub ChangeContext is properly executed.
     */
    private void handleAndCheckExecution(String featureName, String elementExpression, ENamedElement renamedElement) {
        String newName = UUID.randomUUID().toString();
        String renameExpression = MessageFormat.format(ModelOperationServices.AQL_RENAME_EXPRESSION, newName);
        ((ChangeContext) this.unset.getSubModelOperations().get(0)).setBrowseExpression(renameExpression);

        // execute
        this.unset.setFeatureName(featureName);
        this.unset.setElementExpression(elementExpression);

        Status handleResult = this.unsetOperationHandler.handle(this.operationTestContext.getVariables());

        // check
        assertEquals(Status.OK, handleResult);
        assertEquals(newName, renamedElement.getName());
    }

}
