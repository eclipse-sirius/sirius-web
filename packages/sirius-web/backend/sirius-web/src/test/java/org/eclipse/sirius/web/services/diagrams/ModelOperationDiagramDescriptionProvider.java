/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.services.diagrams;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.SelectionDialogTreeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramPaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.InsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.RectangularNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.SelectionDialogDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.CreateInstanceBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test model operations.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ModelOperationDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    private NodeTool createNodeTool;

    private NodeTool createNodeToolWithComputedNewSelection;

    private NodeTool renameNodeTool;

    public ModelOperationDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.view = this.createView();
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    public String getRepresentationDescriptionId() {
        return this.diagramIdProvider.getId(this.diagramDescription);
    }

    public String getCreateNodeToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.createNodeTool).toString().getBytes()).toString();
    }

    public String getCreateNodeToolWithComputedNewSelectionId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.createNodeToolWithComputedNewSelection).toString().getBytes()).toString();
    }

    public String getRenameElementToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.renameNodeTool).toString().getBytes()).toString();
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View unsynchronizedView = viewBuilder.build();
        unsynchronizedView.getDescriptions().add(this.createDiagramDescription());

        unsynchronizedView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("ModelOperationDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("ModelOperationDiagramDescription"));
        resource.getContents().add(unsynchronizedView);

        return unsynchronizedView;
    }

    private DiagramDescription createDiagramDescription() {
        var nodeStyle = new RectangularNodeStyleDescriptionBuilder()
                .build();

        var insideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("aql:self.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        var nodeDescription = new NodeDescriptionBuilder()
                .name("Component")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(insideLabel)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(nodeStyle)
                .build();

        this.createCreateNodeTool();
        this.createCreateNodeToolWithComputedNewSelection();
        this.createRenameElementNodeTool();

        var diagramPalette = new DiagramPaletteBuilder()
                .nodeTools(this.createNodeTool, this.createNodeToolWithComputedNewSelection, this.renameNodeTool)
                .build();

        this.diagramDescription = new DiagramDescriptionBuilder()
                .name("Diagram")
                .titleExpression("aql:'ModelOperationDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(nodeDescription)
                .edgeDescriptions()
                .palette(diagramPalette)
                .autoLayout(false)
                .build();

        return this.diagramDescription;
    }

    private void createCreateNodeTool() {
        var createNewComponent = this.createCreateNewComponentOperation("newInstance", "aql:newComponentName");

        var createNewComponentForAllValues = new ViewBuilders().newLet()
                .valueExpression("aql:Sequence{'a', 'b', 'c'}")
                .variableName("newComponentNames")
                .children(
                        new ViewBuilders().newFor()
                                .expression("aql:newComponentNames")
                                .iteratorName("newComponentName")
                                .children(
                                        new ViewBuilders().newIf()
                                                .conditionExpression("aql:newComponentName <> 'b'")
                                                .children(createNewComponent)
                                                .build()
                                )
                                .build()
                )
                .build();

        this.createNodeTool = new NodeToolBuilder()
                .name("Create Component")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:self")
                                .children(
                                        createNewComponentForAllValues
                                )
                                .build()
                )
                .build();
    }

    private CreateInstance createCreateNewComponentOperation(String variableName, String componentNameExpression) {
        var createNewComponent = new CreateInstanceBuilder()
                .typeName("papaya:Component")
                .referenceName("components")
                .variableName(variableName)
                .children(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:" + variableName)
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("name")
                                                .valueExpression(componentNameExpression)
                                                .build()
                                )
                                .build()
                )
                .build();
        return createNewComponent;
    }

    private void createCreateNodeToolWithComputedNewSelection() {
        this.createNodeToolWithComputedNewSelection = new NodeToolBuilder()
                .name("Create Components")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:self")
                                .children(
                                        new ViewBuilders().newLet()
                                            .variableName("project")
                                            .valueExpression("aql:self")
                                            .children(
                                                    new ViewBuilders().newChangeContext().expression("aql:project").build(),
                                                    this.createCreateNewComponentOperation("component1", "aql:'Component1'"),
                                                    new ViewBuilders().newChangeContext().expression("aql:project").build(),
                                                    this.createCreateNewComponentOperation("component2", "aql:'Component2'"),
                                                    new ViewBuilders().newChangeContext().expression("aql:project").build(),
                                                    this.createCreateNewComponentOperation("component3", "aql:'Component3'"))
                                            .build()
                                )
                                .build()
                )
                .elementsToSelectExpression("aql:Sequence{component1, component2}") // Explicitly do not include component3
                .build();
    }

    private void createRenameElementNodeTool() {
        SelectionDialogTreeDescription selectionDialogTreeDescription = new SelectionDialogTreeDescriptionBuilder()
                .elementsExpression("aql:self.eResource().eAllContents()")
                .build();
        var selectionDialog = new SelectionDialogDescriptionBuilder()
                .selectionMessage("Select a new element")
                .selectionDialogTreeDescription(selectionDialogTreeDescription)
                .build();

        var setSelectedComponentName = new ViewBuilders().newSetValue()
                .featureName("name")
                .valueExpression("componentRenamedAfterSelectedElement")
                .build();

        this.renameNodeTool = new NodeToolBuilder()
                .name("Rename Component")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:selectedObject")
                                .children(setSelectedComponentName)
                                .build()
                )
                .dialogDescription(selectionDialog)
                .build();
    }
}
