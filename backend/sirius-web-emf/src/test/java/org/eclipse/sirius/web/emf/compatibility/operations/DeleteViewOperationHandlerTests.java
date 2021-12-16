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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.emf.common.command.BasicCommandStack;
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
import org.eclipse.sirius.viewpoint.description.tool.DeleteView;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.DeleteViewOperationHandler;
import org.eclipse.sirius.web.emf.services.EditingContext;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests of the DeleteView operation handler.
 *
 * @author arichard
 */
public class DeleteViewOperationHandlerTests {
    private static final String VARIABLE_NAME = "myVariableName"; //$NON-NLS-1$

    private static final String CONTAINER_VIEW = "containerView"; //$NON-NLS-1$

    private DeleteViewOperationHandler deleteViewOperationHandler;

    private DeleteView deleteViewOperation;

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

        Node node = Node.newNode(UUID.randomUUID().toString())
                .descriptionId(UUID.randomUUID())
                .type("Node") //$NON-NLS-1$
                .targetObjectId(UUID.randomUUID().toString())
                .targetObjectKind("ecore::EPackage") //$NON-NLS-1$
                .targetObjectLabel(OperationTestContext.ROOT_PACKAGE_NAME)
                .label(Label.newLabel(UUID.randomUUID().toString())
                        .type("Label") //$NON-NLS-1$
                        .text(OperationTestContext.ROOT_PACKAGE_NAME)
                        .position(Position.at(0, 0))
                        .size(Size.of(10, 10))
                        .alignment(Position.at(0, 0))
                        .style(LabelStyle.newLabelStyle().color("").fontSize(0).iconURL("").build()) //$NON-NLS-1$ //$NON-NLS-2$
                        .build())
                .style(ImageNodeStyle.newImageNodeStyle().imageURL("").scalingFactor(0).build()) //$NON-NLS-1$
                .position(Position.at(0, 0))
                .size(Size.of(10, 10))
                .borderNodes(List.of())
                .childNodes(List.of())
                .build();

        Diagram diagram = Diagram.newDiagram(UUID.randomUUID().toString())
                .targetObjectId(UUID.randomUUID().toString())
                .position(Position.at(0, 0))
                .size(Size.of(100, 100))
                .nodes(List.of(node))
                .edges(List.of())
                .build();

        IDiagramContext diagramContext = new IDiagramContext.NoOp() {
            @Override
            public List<ViewDeletionRequest> getViewDeletionRequests() {
                return new ArrayList<>();
            }
        };
        this.operationTestContext.getVariables().put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
        // @formatter:on

        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(composedAdapterFactory, new BasicCommandStack(), resourceSet);
        EditingContext editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain);
        this.operationTestContext.getVariables().put(IEditingContext.EDITING_CONTEXT, editingContext);
        this.operationTestContext.getVariables().put(CONTAINER_VIEW, diagram);
        this.operationTestContext.getVariables().put(VariableManager.SELF, diagram.getNodes().get(0));
        this.deleteViewOperation = ToolFactory.eINSTANCE.createDeleteView();
        this.deleteViewOperationHandler = new DeleteViewOperationHandler(this.operationTestContext.getObjectService(), this.operationTestContext.getIdentifierProvider(),
                this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(List.of()), this.deleteViewOperation);
    }

    @Test
    public void deleteViewOperationHandlerNominalCaseTest() {
        // used to check that the variable name is added in variable scope
        String className = "newClass"; //$NON-NLS-1$
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        subChangeContext.setBrowseExpression(AQLInterpreter.AQL_PREFIX + VARIABLE_NAME + ".renameENamedElementService('" + className + "'))"); //$NON-NLS-1$ //$NON-NLS-2$
        this.deleteViewOperation.getSubModelOperations().add(subChangeContext);

        // check the nominal case
        IStatus handleResult = this.deleteViewOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);
    }

}
