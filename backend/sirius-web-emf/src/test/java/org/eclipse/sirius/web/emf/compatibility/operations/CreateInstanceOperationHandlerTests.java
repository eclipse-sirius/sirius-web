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

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.CreateInstance;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.emf.compatibility.EPackageService;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.CreateInstanceOperationHandler;
import org.eclipse.sirius.web.emf.services.EditingContext;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests of the CreateInstance operation handler.
 *
 * @author lfasani
 */
public class CreateInstanceOperationHandlerTests {
    private static final String VARIABLE_NAME = "myVariableName"; //$NON-NLS-1$

    private static final String REFERENCE_NAME = "eClassifiers"; //$NON-NLS-1$

    private static final String TYPE_NAME = "ecore::EClass"; //$NON-NLS-1$

    private CreateInstanceOperationHandler createInstanceOperationHandler;

    private CreateInstance createInstanceOperation;

    private OperationTestContext operationTestContext;

    @BeforeEach
    public void initialize() {
        this.operationTestContext = new OperationTestContext();

        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory();
        composedAdapterFactory.addAdapterFactory(new EcoreAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

        EPackage.Registry ePackageRegistry = new EPackageRegistryImpl();
        ePackageRegistry.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);

        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.setPackageRegistry(ePackageRegistry);

        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(composedAdapterFactory, new BasicCommandStack(), resourceSet);
        EditingContext editingContext = new EditingContext(UUID.randomUUID(), editingDomain);
        this.operationTestContext.getVariables().put(IEditingContext.EDITING_CONTEXT, editingContext);

        this.createInstanceOperation = ToolFactory.eINSTANCE.createCreateInstance();
        this.createInstanceOperationHandler = new CreateInstanceOperationHandler(this.operationTestContext.getInterpreter(), new EPackageService(), new ChildModelOperationHandler(),
                this.createInstanceOperation);
    }

    @Test
    public void createInstanceOperationHandlerNominalCaseTest() {
        // used to check that the variable name is added in variable scope
        String className = "newClass"; //$NON-NLS-1$
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        subChangeContext.setBrowseExpression("aql:" + VARIABLE_NAME + ".renameENamedElementService('" + className + "'))"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        this.createInstanceOperation.getSubModelOperations().add(subChangeContext);

        // check the nominal case
        this.createInstanceOperation.setReferenceName(REFERENCE_NAME);
        this.createInstanceOperation.setTypeName(TYPE_NAME);
        this.createInstanceOperation.setVariableName(VARIABLE_NAME);

        IStatus handleResult = this.createInstanceOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);
        assertEquals(2, this.operationTestContext.getRootPackage().getEClassifiers().size());
        assertEquals(className, this.operationTestContext.getRootPackage().getEClassifiers().get(1).getName());

        // check that an empty variable name is a valid case
        this.createInstanceOperation.setVariableName(""); //$NON-NLS-1$

        handleResult = this.createInstanceOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);
        assertEquals(3, this.operationTestContext.getRootPackage().getEClassifiers().size());
        assertEquals(null, this.operationTestContext.getRootPackage().getEClassifiers().get(2).getName());
    }

    @Test
    public void createInstanceOperationHandlerChangeSelfCaseTest() {
        // used to check that the variable name is added in variable scope
        String className = "newClass"; //$NON-NLS-1$
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        subChangeContext.setBrowseExpression("aql:self.renameENamedElementService('" + className + "'))"); //$NON-NLS-1$//$NON-NLS-2$
        this.createInstanceOperation.getSubModelOperations().add(subChangeContext);

        // check the nominal case
        this.createInstanceOperation.setReferenceName(REFERENCE_NAME);
        this.createInstanceOperation.setTypeName(TYPE_NAME);
        this.createInstanceOperation.setVariableName(VARIABLE_NAME);

        IStatus handleResult = this.createInstanceOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);
        assertEquals(2, this.operationTestContext.getRootPackage().getEClassifiers().size());
        assertEquals(className, this.operationTestContext.getRootPackage().getEClassifiers().get(1).getName());

        // check that an empty variable name is a valid case
        this.createInstanceOperation.setVariableName(""); //$NON-NLS-1$

        handleResult = this.createInstanceOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);
        assertEquals(3, this.operationTestContext.getRootPackage().getEClassifiers().size());
        assertEquals(className, this.operationTestContext.getRootPackage().getEClassifiers().get(2).getName());
    }

    /**
     * Check that a null or empty data do not stop the handle of subOperations.</br>
     */
    @Test
    public void createInstanceOperationHandlerErrorCasesTest() {
        // Add a SubModelOperations to check that it is handled
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        this.createInstanceOperation.getSubModelOperations().add(subChangeContext);

        // Check null expression case
        this.handleAndCheckExecution(null, null, null, this.operationTestContext.getRootPackage());

        // Check empty expression case
        this.handleAndCheckExecution("", "", "", this.operationTestContext.getRootPackage()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        this.handleAndCheckExecution(REFERENCE_NAME, "UnknownClass", VARIABLE_NAME, this.operationTestContext.getRootPackage()); //$NON-NLS-1$
    }

    /**
     * Execute the root operation and check that the sub ChangeContext operation is properly executed.
     */
    private void handleAndCheckExecution(String referenceName, String typeName, String variableName, ENamedElement renamedElement) {
        String newName = UUID.randomUUID().toString();
        String renameExpression = MessageFormat.format(ModelOperationServices.AQL_RENAME_EXPRESSION, newName);
        ((ChangeContext) this.createInstanceOperation.getSubModelOperations().get(0)).setBrowseExpression(renameExpression);

        // execute
        this.createInstanceOperation.setReferenceName(referenceName);
        this.createInstanceOperation.setTypeName(typeName);
        this.createInstanceOperation.setVariableName(variableName);

        IStatus handleResult = this.createInstanceOperationHandler.handle(this.operationTestContext.getVariables());

        // check
        assertTrue(handleResult instanceof Success);
        assertEquals(newName, renamedElement.getName());
    }

}
