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
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test the manipulation of edges.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class EdgeDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    private EdgeTool edgeTool;

    public EdgeDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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

    public String getNewDependencyToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.edgeTool).toString().getBytes()).toString();
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View edgeView = viewBuilder.build();
        edgeView.getDescriptions().add(this.createDiagramDescription());
        edgeView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("EdgeDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("EdgeDiagramDescription"));
        resource.getContents().add(edgeView);

        return edgeView;
    }

    private DiagramDescription createDiagramDescription() {
        var nodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        var nodeDescription = new DiagramBuilders().newNodeDescription()
                .name("Component")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(insideLabel)
                .style(nodeStyle)
                .build();

        this.edgeTool = new DiagramBuilders().newEdgeTool()
                .name("New dependencies")
                .preconditionExpression("aql:semanticEdgeSource.dependencies->excludes(semanticEdgeTarget)")
                .targetElementDescriptions(nodeDescription)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:semanticEdgeSource")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("dependencies")
                                                .valueExpression("aql:self.dependencies->including(semanticEdgeTarget)")
                                                .build()
                                )
                                .build()
                )
                .build();

        var nodePalette = new DiagramBuilders().newNodePalette()
                .edgeTools(this.edgeTool)
                .build();

        nodeDescription.setPalette(nodePalette);

        var edgeStyle = new DiagramBuilders().newEdgeStyle()
                .edgeWidth(1)
                .build();

        var edgePalette = new DiagramBuilders().newEdgePalette()
                .edgeReconnectionTools(this.sourceEdgeReconnectionTool(), this.targetEdgeReconnectionTool())
                .build();

        var edgeDescription = new DiagramBuilders().newEdgeDescription()
                .centerLabelExpression("aql:semanticEdgeSource.name + ' -> ' + semanticEdgeTarget.name")
                .sourceNodesExpression("aql:self")
                .targetNodesExpression("aql:self.dependencies")
                .sourceNodeDescriptions(nodeDescription)
                .targetNodeDescriptions(nodeDescription)
                .palette(edgePalette)
                .style(edgeStyle)
                .build();

        this.diagramDescription = new DiagramBuilders().newDiagramDescription()
                .name("Diagram")
                .titleExpression("aql:'EdgeDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(nodeDescription)
                .edgeDescriptions(edgeDescription)
                .autoLayout(false)
                .style(new DiagramBuilders().newDiagramStyleDescription().build())
                .build();

        return this.diagramDescription;
    }

    private EdgeReconnectionTool sourceEdgeReconnectionTool() {
        return new DiagramBuilders().newSourceEdgeEndReconnectionTool()
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:semanticReconnectionSource")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("dependencies")
                                                .valueExpression("aql:semanticReconnectionSource.dependencies->excluding(semanticOtherEnd)")
                                                .build()
                                )
                                .build(),
                        new ViewBuilders().newChangeContext()
                                .expression("aql:semanticReconnectionTarget")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("dependencies")
                                                .valueExpression("aql:semanticReconnectionTarget.dependencies->including(semanticOtherEnd)")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private EdgeReconnectionTool targetEdgeReconnectionTool() {
        return new DiagramBuilders().newTargetEdgeEndReconnectionTool()
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:semanticOtherEnd")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("dependencies")
                                                .valueExpression("aql:semanticOtherEnd.dependencies->excluding(semanticReconnectionSource)")
                                                .build()
                                )
                                .build(),
                        new ViewBuilders().newChangeContext()
                                .expression("aql:semanticOtherEnd")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("dependencies")
                                                .valueExpression("aql:semanticOtherEnd.dependencies->including(semanticReconnectionTarget)")
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
