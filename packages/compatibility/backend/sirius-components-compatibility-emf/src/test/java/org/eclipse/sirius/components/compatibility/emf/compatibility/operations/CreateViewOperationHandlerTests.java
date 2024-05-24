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

import static org.assertj.core.api.Assertions.assertThat;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.compatibility.emf.compatibility.services.EditingDomainFactory;
import org.eclipse.sirius.components.compatibility.emf.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.components.compatibility.emf.modeloperations.CreateViewOperationHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
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
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.tool.CreateView;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests of the CreateView operation handler.
 *
 * @author arichard
 */
public class CreateViewOperationHandlerTests {

    private static final String AQL = "aql:";

    private static final String VARIABLE_NAME = "myVariableName";

    private static final String CONTAINER_VIEW = "containerView";

    private CreateViewOperationHandler createViewOperationHandler;

    private CreateView createViewOperation;

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

        Diagram diagram = Diagram.newDiagram(UUID.randomUUID().toString())
                .descriptionId(diagramDescription.getId())
                .targetObjectId(UUID.randomUUID().toString())
                .label("DiagramTest")
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
        this.createViewOperation = org.eclipse.sirius.diagram.description.tool.ToolFactory.eINSTANCE.createCreateView();
        this.createViewOperationHandler = new CreateViewOperationHandler(this.operationTestContext.getObjectService(), this.operationTestContext.getRepresentationMetadataSearchService(),
                this.operationTestContext.getIdentifierProvider(), this.operationTestContext.getInterpreter(), new ChildModelOperationHandler(List.of()), this.createViewOperation);
    }

    @Test
    public void createViewOperationHandlerNominalCaseTest() {
        // used to check that the variable name is added in variable scope
        String className = "newClass";
        ChangeContext subChangeContext = ToolFactory.eINSTANCE.createChangeContext();
        subChangeContext.setBrowseExpression(AQL + VARIABLE_NAME + ".renameENamedElementService('" + className + "'))");
        this.createViewOperation.getSubModelOperations().add(subChangeContext);

        // check the nominal case
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setName(UUID.randomUUID().toString());
        this.createViewOperation.setMapping(nodeMapping);
        this.createViewOperation.setContainerViewExpression(AQL + CONTAINER_VIEW);
        this.createViewOperation.setVariableName(VARIABLE_NAME);

        IStatus handleResult = this.createViewOperationHandler.handle(this.operationTestContext.getVariables());

        assertThat(handleResult).isInstanceOf(Success.class);

        // check that an empty variable name is a valid case
        this.createViewOperation.setVariableName("");

        handleResult = this.createViewOperationHandler.handle(this.operationTestContext.getVariables());

        assertThat(handleResult).isInstanceOf(Success.class);
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
        this.handleAndCheckExecution("", this.operationTestContext.getRootPackage());

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
        assertThat(handleResult).isInstanceOf(Success.class);
        assertThat(renamedElement.getName()).isEqualTo(newName);
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
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .build();

        Function<VariableManager, INodeStyle> nodeStyleProvider = variableManager -> RectangularNodeStyle.newRectangularNodeStyle()
                .background("")
                .borderColor("")
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();

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
