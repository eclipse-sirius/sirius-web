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
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramPaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.InsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeDecoratorDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.RectangularNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.diagram.DecoratorPosition;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Provides a view based diagram description to test diagrams with node decorators.
 *
 * @author gdaniel
 */
@Service
@Conditional(OnStudioTests.class)
public class NodeDecoratorDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    public NodeDecoratorDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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
        View diagramDescriptionView = new ViewBuilder().build();
        diagramDescriptionView.getDescriptions().add(this.createDiagramDescription());
        diagramDescriptionView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("NodeDecoratorDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("NodeDecoratorDiagramDescription"));
        resource.getContents().add(diagramDescriptionView);

        return diagramDescriptionView;
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

        var diagramPalette = new DiagramPaletteBuilder()
                .build();

        var decorator1 = new NodeDecoratorDescriptionBuilder()
                .name("Decorator1")
                .iconURLExpression("/icons/full/obj16/HideTool.svg")
                .preconditionExpression("aql:true")
                .labelExpression("aql:'Decorator1 label'")
                .nodeDescriptions(nodeDescription)
                .position(DecoratorPosition.NORTH_EAST)
                .build();

        var decorator2 = new NodeDecoratorDescriptionBuilder()
                .name("Decorator2")
                .iconURLExpression("/icons/full/obj16/DeleteTool.svg")
                .preconditionExpression("aql:true")
                .labelExpression("aql:'Decorator2 label'")
                .nodeDescriptions(nodeDescription)
                .position(DecoratorPosition.NORTH_EAST)
                .build();

        var decorator3 = new NodeDecoratorDescriptionBuilder()
                .name("Decorator3")
                .iconURLExpression("/icons/full/obj16/NodeTool.svg")
                .preconditionExpression("aql:self.oclIsKindOf(papaya::Component) and self.name = 'sirius-web-starter'")
                .labelExpression("aql:'Decorator3 ' + self.name")
                .nodeDescriptions(nodeDescription)
                .position(DecoratorPosition.SOUTH_EAST)
                .build();

        this.diagramDescription = new DiagramDescriptionBuilder()
                .name("Diagram")
                .titleExpression("aql:'NodeDecoratorDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(nodeDescription)
                .edgeDescriptions()
                .palette(diagramPalette)
                .autoLayout(false)
                .style(new DiagramBuilders().newDiagramStyleDescription().build())
                .decoratorDescriptions(decorator1, decorator2, decorator3)
                .build();

        return this.diagramDescription;
    }
}
