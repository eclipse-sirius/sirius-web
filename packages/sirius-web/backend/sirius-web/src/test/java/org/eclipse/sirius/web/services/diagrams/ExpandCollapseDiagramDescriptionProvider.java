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
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.DiagramDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.InsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.NodePaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.NodeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.RectangularNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilder;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test expand and collapse operations in diagrams.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
public class ExpandCollapseDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    private NodeTool expandNodeTool;

    private NodeTool collapseNodeTool;

    public ExpandCollapseDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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

    public String getExpandNodeToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.expandNodeTool).toString().getBytes()).toString();
    }

    public String getCollapseNodeToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.collapseNodeTool).toString().getBytes()).toString();
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View expandCollapseView = viewBuilder.build();
        expandCollapseView.getDescriptions().add(this.createDiagramDescription());

        expandCollapseView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("ExpandCollapseDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("ExpandCollapseDiagramDescription"));
        resource.getContents().add(expandCollapseView);

        return expandCollapseView;
    }

    private DiagramDescription createDiagramDescription() {
        var nodeStyle = new RectangularNodeStyleDescriptionBuilder()
                .build();

        var insideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("aql:self.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        this.expandNodeTool = new NodeToolBuilder()
                .name("Expand")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:diagramServices.expand(Sequence{ selectedNode })")
                                .build()
                )
                .build();


        this.collapseNodeTool = new NodeToolBuilder()
                .name("Collapse")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:diagramServices.collapse(Sequence{ selectedNode })")
                                .build()
                )
                .build();

        var nodePalette = new NodePaletteBuilder()
                .nodeTools(this.expandNodeTool, this.collapseNodeTool)
                .build();

        var nodeDescription = new NodeDescriptionBuilder()
                .name("Component")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(insideLabel)
                .style(nodeStyle)
                .collapsible(true)
                .isCollapsedByDefaultExpression("aql:self.name.endsWith('-domain')")
                .palette(nodePalette)
                .build();


        this.diagramDescription = new DiagramDescriptionBuilder()
                .name("Diagram")
                .titleExpression("aql:'ExpandCollapseDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(nodeDescription)
                .edgeDescriptions()
                .autoLayout(false)
                .build();

        return this.diagramDescription;
    }
}
