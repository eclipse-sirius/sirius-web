/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
 * Used to provide a view based diagram description to test the manipulation of edges.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class EdgePaletteDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    public EdgePaletteDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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
        ViewBuilder viewBuilder = new ViewBuilder();
        View edgeView = viewBuilder.build();
        edgeView.getDescriptions().add(this.createDiagramDescription());
        edgeView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("EdgePaletteDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("EdgePaletteDiagramDescription"));
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
                .name("Domain Diagram")
                .domainType("domain:Entity")
                .semanticCandidatesExpression("aql:self.types")
                .insideLabel(insideLabel)
                .style(nodeStyle)
                .build();

        var edgeStyle = new DiagramBuilders().newEdgeStyle()
                .edgeWidth(1)
                .build();

        var doSomethingTool = new DiagramBuilders().newNodeTool()
                .name("Rename Source")
                .body(new ViewBuilders().newChangeContext()
                        .expression("aql:self")
                        .build())
                .build();

        var edgeToolSection = new DiagramBuilders().newEdgeToolSection()
                .name("Edge Tool Section")
                .nodeTools(doSomethingTool)
                .build();

        var renameSource = new DiagramBuilders().newNodeTool()
                .name("Rename Source")
                .body(new ViewBuilders().newChangeContext()
                        .expression("aql:self")
                        .build())
                .build();

        var edgePalette = new DiagramBuilders().newEdgePalette()
                .nodeTools(renameSource)
                .toolSections(edgeToolSection)
                .build();

        var edgeDescription = new DiagramBuilders().newEdgeDescription()
                .name("Edge Palette")
                .sourceExpression("aql:self")
                .targetExpression("aql:self.superTypes")
                .sourceDescriptions(nodeDescription)
                .targetDescriptions(nodeDescription)
                .palette(edgePalette)
                .style(edgeStyle)
                .build();

        this.diagramDescription = new DiagramBuilders().newDiagramDescription()
                .name("Diagram")
                .titleExpression("aql:'DomainDiagram'")
                .domainType("domain:domain")
                .nodeDescriptions(nodeDescription)
                .edgeDescriptions(edgeDescription)
                .layoutOption(DiagramLayoutOption.NONE)
                .build();

        return this.diagramDescription;
    }

}
