/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.components.view.diagram.DiagramLayoutOption;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test connector palettes.
 *
 * @author gdaniel
 */
@Service
@Conditional(OnStudioTests.class)
public class ConnectorPaletteDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    public ConnectorPaletteDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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

    private View createView() {
        View connectorPaletteView = new ViewBuilder().build();
        connectorPaletteView.getDescriptions().add(this.createDiagramDescription());
        connectorPaletteView.eAllContents().forEachRemaining(eObject -> eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes()))));

        String resourcePath = UUID.nameUUIDFromBytes("ConnectorPaletteDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("ConnectorPaletteDiagramDescription"));
        resource.getContents().add(connectorPaletteView);

        return connectorPaletteView;
    }

    private DiagramDescription createDiagramDescription() {
        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        var nodeDescription = new DiagramBuilders().newNodeDescription()
                .name("Entity")
                .domainType("domain:Entity")
                .semanticCandidatesExpression("aql:self.types")
                .insideLabel(insideLabel)
                .style(new DiagramBuilders().newRectangularNodeStyleDescription().build())
                .build();

        var edgeTool = new DiagramBuilders().newEdgeTool()
                .name("New edge")
                .preconditionExpression("aql:self.name = 'Root'")
                .targetElementDescriptions(nodeDescription)
                .body(new ViewBuilders().newChangeContext()
                        .expression("aql:semanticEdgeSource")
                        .build())
                .build();

        nodeDescription.setPalette(new DiagramBuilders().newNodePalette()
                .edgeTools(edgeTool)
                .build());

        this.diagramDescription = new DiagramBuilders().newDiagramDescription()
                .name("Connector Palette Diagram")
                .titleExpression("aql:'Connector Palette Diagram'")
                .domainType("domain:Domain")
                .nodeDescriptions(nodeDescription)
                .layoutOption(DiagramLayoutOption.NONE)
                .style(new DiagramBuilders().newDiagramStyleDescription().build())
                .build();

        return this.diagramDescription;
    }
}
