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
import java.util.function.Function;

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
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.DeleteViewOperationHandler;
import org.eclipse.sirius.web.emf.services.EditingContext;
import org.eclipse.sirius.web.representations.Failure;
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
    /**
     *
     */
    private static final String AQL = "aql:"; //$NON-NLS-1$

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
        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(UUID.randomUUID())
                .label("DiagramDescriptionTest") //$NON-NLS-1$
                .targetObjectIdProvider(variableManager -> "diagramTargetObjectId") //$NON-NLS-1$
                .canCreatePredicate(variableManager -> true)
                .labelProvider(variableManager -> "Diagram") //$NON-NLS-1$
                .toolSections(List.of())
                .nodeDescriptions(List.of(this.getNodeDescription(UUID.randomUUID())))
                .edgeDescriptions(List.of())
                .dropHandler(variableManager -> new Failure("")) //$NON-NLS-1$
                .build();

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
                .descriptionId(diagramDescription.getId())
                .targetObjectId(UUID.randomUUID().toString())
                .label("DiagramTest") //$NON-NLS-1$
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
        subChangeContext.setBrowseExpression(AQL + VARIABLE_NAME + ".renameENamedElementService('" + className + "'))"); //$NON-NLS-1$ //$NON-NLS-2$
        this.deleteViewOperation.getSubModelOperations().add(subChangeContext);

        // check the nominal case
        IStatus handleResult = this.deleteViewOperationHandler.handle(this.operationTestContext.getVariables());

        assertTrue(handleResult instanceof Success);
    }

    private NodeDescription getNodeDescription(UUID nodeDescriptionId) {
        // @formatter:off
        LabelStyleDescription labelStyleDescription = LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> "#000000") //$NON-NLS-1$
                .fontSizeProvider(variableManager -> 16)
                .boldProvider(variableManager -> false)
                .italicProvider(variableManager -> false)
                .underlineProvider(variableManager -> false)
                .strikeThroughProvider(variableManager -> false)
                .iconURLProvider(variableManager -> "") //$NON-NLS-1$
                .build();

        LabelDescription labelDescription = LabelDescription.newLabelDescription("labelDescriptionId") //$NON-NLS-1$
                .idProvider(variableManager -> "labelId") //$NON-NLS-1$
                .textProvider(variableManager -> "Node") //$NON-NLS-1$
                .styleDescriptionProvider(variableManager -> labelStyleDescription)
                .build();

        Function<VariableManager, INodeStyle> nodeStyleProvider = variableManager -> {
            return RectangularNodeStyle.newRectangularNodeStyle()
                    .color("") //$NON-NLS-1$
                    .borderColor("") //$NON-NLS-1$
                    .borderSize(0)
                    .borderStyle(LineStyle.Solid)
                    .build();
        };

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            Object object = variableManager.getVariables().get(VariableManager.SELF);
            if (object instanceof String) {
                return nodeDescriptionId + "__" +  object; //$NON-NLS-1$
            }
            return null;
        };

        return NodeDescription.newNodeDescription(nodeDescriptionId)
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .typeProvider(variableManager -> "") //$NON-NLS-1$
                .semanticElementsProvider(variableManager -> List.of())
                .targetObjectIdProvider(targetObjectIdProvider)
                .targetObjectKindProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectLabelProvider(variableManager -> "")//$NON-NLS-1$
                .labelDescription(labelDescription)
                .styleProvider(nodeStyleProvider)
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();
        // @formatter:on
    }

}
