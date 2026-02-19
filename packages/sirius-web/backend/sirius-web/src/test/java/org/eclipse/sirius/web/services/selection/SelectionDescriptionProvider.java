/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.services.selection;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramPaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.EdgeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.InsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodePaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.RectangularNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.SelectionDialogDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.SelectionDialogTreeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to provide a description for the selection representation.
 *
 * @author sbegaudeau
 */
@Service
public class SelectionDescriptionProvider implements IEditingContextProcessor {

    public static final String LABEL = "Selection";

    private static final String DIALOG_MESSAGE = "Select the objects to consider";

    private final View view;

    private DiagramDescription diagramDescription;

    private NodeTool createNodeTool;

    private final IDiagramIdProvider diagramIdProvider;

    private SelectionDialogTreeDescription selectionDialogTreeDescription;

    private SelectionDialogDescription selectionDialog;

    private EdgeTool edgeTool;


    public SelectionDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.view = this.createView();
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    public String getSelectionDialogTreeDescriptionId() {
        return this.diagramIdProvider.getId(this.selectionDialogTreeDescription);
    }

    public String getSelectionDialogDescriptionId() {
        return this.diagramIdProvider.getId(this.selectionDialog);
    }

    public SelectionDialogDescription getSelectionDialog() {
        return this.selectionDialog;
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View unsynchronizedView = viewBuilder.build();
        unsynchronizedView.getDescriptions().add(this.createDiagramDescription());

        unsynchronizedView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("SelectionDescriptionDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("SelectionDescriptionDiagramDescription"));
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

        this.createEdgeTool();

        var nodePalette = new NodePaletteBuilder()
                .edgeTools(this.edgeTool)
                .build();

        var nodeDescription = new NodeDescriptionBuilder()
                .name("Component")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(insideLabel)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(nodeStyle)
                .palette(nodePalette)
                .build();

        this.createNodeTool();


        var diagramPalette = new DiagramPaletteBuilder()
                .nodeTools(this.createNodeTool)
                .build();

        this.diagramDescription = new DiagramDescriptionBuilder()
                .name("Diagram")
                .titleExpression("aql:'SelectionDescriptionDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(nodeDescription)
                .edgeDescriptions()
                .palette(diagramPalette)
                .autoLayout(false)
                .build();

        return this.diagramDescription;
    }

    private void createNodeTool() {
        this.selectionDialog = this.createSelectionDialog();
        this.createNodeTool = new NodeToolBuilder()
                .name("Create Component")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:self")
                                .build()
                )
                .dialogDescription(this.selectionDialog)
                .build();
    }

    private void createEdgeTool() {
        var edgeSelectionDialog = this.createEdgeSelectionDialog();
        this.edgeTool = new EdgeToolBuilder()
                .name("Create relation")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:self")
                                .build()
                )
                .dialogDescription(edgeSelectionDialog)
                .build();
    }

    private SelectionDialogDescription createSelectionDialog() {
        this.selectionDialogTreeDescription = new SelectionDialogTreeDescriptionBuilder()
                .elementsExpression("aql:self.eResource()")
                .childrenExpression("aql:if self.oclIsKindOf(papaya::NamedElement) then self.eContents() else self.getContents() endif")
                .isSelectableExpression("aql:self.oclIsKindOf(papaya::Component)")
                .build();
        return new SelectionDialogDescriptionBuilder()
                .selectionMessage(DIALOG_MESSAGE)
                .selectionDialogTreeDescription(this.selectionDialogTreeDescription)
                .optional(true)
                .build();

    }

    private SelectionDialogDescription createEdgeSelectionDialog() {
        var edgeSelectionDialogTreeDescription = new SelectionDialogTreeDescriptionBuilder()
                .elementsExpression("aql:self.eResource()")
                .childrenExpression("aql:if self.oclIsKindOf(papaya::NamedElement) then self.eContents() else self.getContents() endif")
                .isSelectableExpression("aql:self.oclIsKindOf(papaya::Component)")
                .build();
        return new SelectionDialogDescriptionBuilder()
                .selectionMessage(DIALOG_MESSAGE)
                .selectionDialogTreeDescription(edgeSelectionDialogTreeDescription)
                .build();

    }
}
