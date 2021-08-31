/*******************************************************************************
 * Copyright (c) 2021 Obeo and others.
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
import java.util.ArrayList;
import java.util.List;
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
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.tool.CreateView;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.ViewCreationRequest;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.CreateViewOperationHandler;
import org.eclipse.sirius.web.emf.services.EditingContext;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests of the CreateView operation handler.
 *
 * @author arichard
 */
public class CreateViewOperationHandlerTests {

    private static final String AQL = "aql:"; //$NON-NLS-1$

    private static final String VARIABLE_NAME = "myVariableName"; //$NON-NLS-1$

    private static final String CONTAINER_VIEW = "containerView"; //$NON-NLS-1$

    private CreateViewOperationHandler createViewOperationHandler;

    private CreateView createViewOperation;

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

        // @formatter:off
        Diagram diagram = Diagram.newDiagram(UUID.randomUUID().toString())
                .targetObjectId(UUID.randomUUID().toString())
                .position(Position.at(0, 0))
                .size(Size.of(100, 100))
                .nodes(new ArrayList<>())
                .edges(List.of())
                .build();

        IDiagramContext diagramContext = new IDiagramContext.NoOp() {
            @Override
            public List<ViewCreationRequest> getViewCreationRequests() {
                return new ArrayList<>();
            }
        };
        this.operationTestContext.getVariables().put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
        // @formatter:on

        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(composedAdapterFactory, new BasicCommandStack(), resourceSet);
        EditingContext editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain);
        this.operationTestContext.getVariables().put(IEditingContext.EDITING_CONTEXT, editingContext);
        this.operationTestContext.getVariables().put(CONTAINER_VIEW, diagram);
        this.createViewOperation = org.eclipse.sirius.diagram.description.tool.ToolFactory.eINSTANCE.createCreateView();
        this.createViewOperationHandler = new CreateViewOperationHandler(this.operationTestContext.getObjectService(), this.operationTestContext.getIdentifierProvider(),
                this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(List.of()), this.createViewOperation);
    }

    @Test
    public void createViewOperationHandlerNominalCaseTest() {
        // used to check that the variable name is added in variable scope
        String className = "newClass"; //$NON-NLS-1$
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        subChangeContext.setBrowseExpression(AQL + VARIABLE_NAME + ".renameENamedElementService('" + className + "'))"); //$NON-NLS-1$ //$NON-NLS-2$
        this.createViewOperation.getSubModelOperations().add(subChangeContext);

        // check the nominal case
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setName(UUID.randomUUID().toString());
        this.createViewOperation.setMapping(nodeMapping);
        this.createViewOperation.setContainerViewExpression(AQL + CONTAINER_VIEW);
        this.createViewOperation.setVariableName(VARIABLE_NAME);

        IStatus handleResult = this.createViewOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);

        // check that an empty variable name is a valid case
        this.createViewOperation.setVariableName(""); //$NON-NLS-1$

        handleResult = this.createViewOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);
    }

    /**
     * Check that a null or empty data do not stop the handle of subOperations.</br>
     */
    @Test
    public void createViewOperationHandlerErrorCasesTest() {
        // Add a SubModelOperations to check that it is handled
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        this.createViewOperation.getSubModelOperations().add(subChangeContext);

        // Check null expression case
        this.handleAndCheckExecution(null, this.operationTestContext.getRootPackage());

        // Check empty expression case
        this.handleAndCheckExecution("", this.operationTestContext.getRootPackage()); //$NON-NLS-1$
        // $NON-NLS-3$

        this.handleAndCheckExecution(VARIABLE_NAME, this.operationTestContext.getRootPackage());
    }

    /**
     * Execute the root operation and check that the sub ChangeContext operation is properly executed.
     */
    private void handleAndCheckExecution(String variableName, ENamedElement renamedElement) {
        String newName = UUID.randomUUID().toString();
        String renameExpression = MessageFormat.format(ModelOperationServices.AQL_RENAME_EXPRESSION, newName);
        ((ChangeContext) this.createViewOperation.getSubModelOperations().get(0)).setBrowseExpression(renameExpression);

        // execute
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setName(UUID.randomUUID().toString());
        this.createViewOperation.setContainerViewExpression(AQL + CONTAINER_VIEW);
        this.createViewOperation.setVariableName(variableName);

        IStatus handleResult = this.createViewOperationHandler.handle(this.operationTestContext.getVariables());

        // check
        assertTrue(handleResult instanceof Success);
        assertEquals(newName, renamedElement.getName());
    }

}
