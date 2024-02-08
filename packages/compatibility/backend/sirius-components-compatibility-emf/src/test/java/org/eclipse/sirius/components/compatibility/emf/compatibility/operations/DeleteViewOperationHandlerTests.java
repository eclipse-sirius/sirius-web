/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo and others.
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

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.compatibility.emf.compatibility.services.EditingDomainFactory;
import org.eclipse.sirius.components.compatibility.emf.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.components.compatibility.emf.modeloperations.DeleteViewOperationHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.DeleteView;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests of the DeleteView operation handler.
 *
 * @author arichard
 */
public class DeleteViewOperationHandlerTests {

    private static final String AQL = "aql:";

    private static final String VARIABLE_NAME = "myVariableName";

    private static final String CONTAINER_VIEW = "containerView";

    private DeleteViewOperationHandler deleteViewOperationHandler;

    private DeleteView deleteViewOperation;

    private OperationTestContext operationTestContext;

    @BeforeEach
    public void initialize() {
        this.operationTestContext = new OperationTestContext();

        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(UUID.randomUUID().toString())
                .label("DiagramDescriptionTest")
                .targetObjectIdProvider(variableManager -> "diagramTargetObjectId")
                .canCreatePredicate(variableManager -> true)
                .labelProvider(variableManager -> "Diagram")
                .palettes(List.of())
                .nodeDescriptions(List.of(this.getNodeDescription(UUID.randomUUID().toString())))
                .edgeDescriptions(List.of())
                .dropHandler(variableManager -> new Failure(""))
                .build();

        Node node = Node.newNode(UUID.randomUUID().toString())
                .descriptionId(UUID.randomUUID().toString())
                .type("Node")
                .targetObjectId(UUID.randomUUID().toString())
                .targetObjectKind("ecore::EPackage")
                .targetObjectLabel(OperationTestContext.ROOT_PACKAGE_NAME)
                .insideLabel(InsideLabel.newLabel(UUID.randomUUID().toString())
                        .text(OperationTestContext.ROOT_PACKAGE_NAME)
                        .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                        .style(LabelStyle.newLabelStyle().color("").fontSize(0).iconURL(List.of()).build())
                        .isHeader(false)
                        .displayHeaderSeparator(false)
                        .build())
                .style(ImageNodeStyle.newImageNodeStyle().imageURL("").scalingFactor(0).build())
                .position(Position.at(0, 0))
                .size(Size.of(10, 10))
                .borderNodes(List.of())
                .childNodes(List.of())
                .state(ViewModifier.Normal)
                .modifiers(Set.of())
                .collapsingState(CollapsingState.EXPANDED)
                .build();

        Diagram diagram = Diagram.newDiagram(UUID.randomUUID().toString())
                .descriptionId(diagramDescription.getId())
                .targetObjectId(UUID.randomUUID().toString())
                .label("DiagramTest")
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

        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();
        var editingContext = new IEMFEditingContext() {
            @Override
            public String getId() {
                return UUID.randomUUID().toString();
            }

            @Override
            public AdapterFactoryEditingDomain getDomain() {
                return editingDomain;
            }
        };

        this.operationTestContext.getVariables().put(IEditingContext.EDITING_CONTEXT, editingContext);
        this.operationTestContext.getVariables().put(CONTAINER_VIEW, diagram);
        this.operationTestContext.getVariables().put(VariableManager.SELF, diagram.getNodes().get(0));
        this.deleteViewOperation = ToolFactory.eINSTANCE.createDeleteView();
        this.deleteViewOperationHandler = new DeleteViewOperationHandler(this.operationTestContext.getObjectService(), this.operationTestContext.getRepresentationMetadataSearchService(),
                this.operationTestContext.getIdentifierProvider(), this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(List.of()), this.deleteViewOperation);
    }

    @Test
    public void deleteViewOperationHandlerNominalCaseTest() {
        // used to check that the variable name is added in variable scope
        String className = "newClass";
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        subChangeContext.setBrowseExpression(AQL + VARIABLE_NAME + ".renameENamedElementService('" + className + "'))");
        this.deleteViewOperation.getSubModelOperations().add(subChangeContext);

        // check the nominal case
        IStatus handleResult = this.deleteViewOperationHandler.handle(this.operationTestContext.getVariables());

        assertInstanceOf(Success.class, handleResult);
    }

    private NodeDescription getNodeDescription(String nodeDescriptionId) {
        LabelStyleDescription labelStyleDescription = LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> "#000000")
                .fontSizeProvider(variableManager -> 16)
                .boldProvider(variableManager -> false)
                .italicProvider(variableManager -> false)
                .underlineProvider(variableManager -> false)
                .strikeThroughProvider(variableManager -> false)
                .iconURLProvider(variableManager -> List.of())
                .build();

        InsideLabelDescription insideLabelDescription = InsideLabelDescription.newInsideLabelDescription("insideLabelDescriptionId")
                .idProvider(variableManager -> "insideLabelId")
                .textProvider(variableManager -> "Node")
                .styleDescriptionProvider(variableManager -> labelStyleDescription)
                .isHeaderProvider(vm -> false)
                .displayHeaderSeparatorProvider(vm -> false)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .build();

        Function<VariableManager, INodeStyle> nodeStyleProvider = variableManager -> {
            return RectangularNodeStyle.newRectangularNodeStyle()
                    .color("")
                    .borderColor("")
                    .borderSize(0)
                    .borderStyle(LineStyle.Solid)
                    .build();
        };

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            Object object = variableManager.getVariables().get(VariableManager.SELF);
            if (object instanceof String) {
                return nodeDescriptionId + "__" + object;
            }
            return null;
        };

        return NodeDescription.newNodeDescription(nodeDescriptionId)
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .typeProvider(variableManager -> "")
                .semanticElementsProvider(variableManager -> List.of())
                .targetObjectIdProvider(targetObjectIdProvider)
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .insideLabelDescription(insideLabelDescription)
                .styleProvider(nodeStyleProvider)
                .childrenLayoutStrategyProvider(variableManager -> new FreeFormLayoutStrategy())
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();
    }

}
