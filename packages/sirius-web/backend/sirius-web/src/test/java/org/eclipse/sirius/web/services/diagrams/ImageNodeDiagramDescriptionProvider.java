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
import org.eclipse.sirius.components.view.builder.generated.diagram.DeleteToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.ImageNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodePaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.OutsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramLayoutOption;
import org.eclipse.sirius.components.view.diagram.OutsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test diagrams with image nodes.
 *
 * @author mcharfadi
 */
@Service
@Conditional(OnStudioTests.class)
public class ImageNodeDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    public ImageNodeDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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
        View viewForDiagramDesc = viewBuilder.build();
        viewForDiagramDesc.getDescriptions().add(this.createDiagramDescription());

        viewForDiagramDesc.eAllContents().forEachRemaining(eObject ->
                eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes()))));

        String resourcePath = UUID.nameUUIDFromBytes("ImageNodeDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("ImageNodeDiagramDescription"));
        resource.getContents().add(viewForDiagramDesc);

        return viewForDiagramDesc;
    }

    private DiagramDescription createDiagramDescription() {
        var outsideLabel = new OutsideLabelDescriptionBuilder()
                .labelExpression("aql:self.name")
                .style(DiagramFactory.eINSTANCE.createOutsideLabelStyle())
                .position(OutsideLabelPosition.BOTTOM_CENTER)
                .build();

        var deleteTool = new DeleteToolBuilder()
                .name("Delete")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self.defaultDelete()")
                                .build()
                )
                .build();

        var nodeDescription = new NodeDescriptionBuilder()
                .name("Component")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self.eContents()")
                .outsideLabels(outsideLabel)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .palette(new NodePaletteBuilder()
                        .deleteTool(deleteTool)
                        .build())
                .style(new ImageNodeStyleDescriptionBuilder()
                        .shape("component.svg")
                        .borderSize(0)
                        .build())
                .borderNodesDescriptions(new NodeDescriptionBuilder()
                        .name("Package")
                        .domainType("papaya:Package")
                        .semanticCandidatesExpression("aql:self.eContents()")
                        .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                        .style(new ImageNodeStyleDescriptionBuilder()
                                .shape("package.svg")
                                .positionDependentRotation(true)
                                .build())
                        .build())
                .build();

        this.diagramDescription = new DiagramDescriptionBuilder()
                .name("Diagram")
                .titleExpression("aql:'ImageNodeDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(nodeDescription)
                .edgeDescriptions()
                .layoutOption(DiagramLayoutOption.NONE)
                .build();

        return this.diagramDescription;
    }
}
