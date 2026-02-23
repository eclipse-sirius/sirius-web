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
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test dragging and dropping nodes.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DropNodeDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    private DropNodeTool dropNodeTool;

    public DropNodeDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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

    public String getDropNodeToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.dropNodeTool).toString().getBytes()).toString();
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View unsynchronizedView = viewBuilder.build();
        unsynchronizedView.getDescriptions().add(this.createDiagramDescription());

        unsynchronizedView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("DropNodeDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("DropNodeDiagramDescription"));
        resource.getContents().add(unsynchronizedView);

        return unsynchronizedView;
    }

    private DiagramDescription createDiagramDescription() {
        var packageNodeDescription = this.packageNodeDescription();
        var componentNodeDescription = this.componentNodeDescription(packageNodeDescription);

        var edgeStyle = new DiagramBuilders().newEdgeStyle()
                .edgeWidth(1)
                .build();

        var edgePalette = new DiagramBuilders().newEdgePalette()
                .build();

        var edgeDescription = new DiagramBuilders().newEdgeDescription()
                .centerLabelExpression("aql:semanticEdgeSource.name + ' -> ' + semanticEdgeTarget.name")
                .sourceExpression("aql:self")
                .targetExpression("aql:self.dependencies")
                .sourceDescriptions(componentNodeDescription)
                .targetDescriptions(componentNodeDescription)
                .palette(edgePalette)
                .style(edgeStyle)
                .build();

        var createComponentFromPackage = new ViewBuilders().newChangeContext()
                .expression("aql:targetElement")
                .children(
                        new ViewBuilders().newCreateInstance()
                                .typeName("papaya::Component")
                                .referenceName("elements")
                                .variableName("newComponent")
                                .children(
                                        new ViewBuilders().newChangeContext()
                                                .expression("aql:newComponent")
                                                .children(
                                                        new ViewBuilders().newSetValue()
                                                                .featureName("name")
                                                                .valueExpression("aql:droppedElement.name")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        var diagramPalette = new DiagramBuilders().newDiagramPalette()
                .dropNodeTool(
                        new DiagramBuilders().newDropNodeTool()
                                .acceptedNodeTypes(packageNodeDescription)
                                .body(createComponentFromPackage)
                                .build()
                )
                .build();

        var toolbar = new DiagramBuilders().newDiagramToolbar()
            .expandedByDefault(true)
            .build();

        this.diagramDescription = new DiagramBuilders().newDiagramDescription()
                .name("Diagram")
                .titleExpression("aql:'DropNodeDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(componentNodeDescription)
                .edgeDescriptions(edgeDescription)
                .palette(diagramPalette)
                .toolbar(toolbar)
                .autoLayout(false)
                .build();

        return this.diagramDescription;
    }

    private NodeDescription packageNodeDescription() {
        return new DiagramBuilders().newNodeDescription()
                .name("Package")
                .domainType("papaya:Package")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(
                        new DiagramBuilders().newInsideLabelDescription()
                                .labelExpression("aql:self.name")
                                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                                .position(InsideLabelPosition.TOP_CENTER)
                                .build()
                )
                .style(
                        new DiagramBuilders().newRectangularNodeStyleDescription()
                                .build()
                )
                .build();
    }

    private NodeDescription componentNodeDescription(NodeDescription packageNodeDescription) {
        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        this.dropNodeTool = new DiagramBuilders().newDropNodeTool()
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:targetElement")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("dependencies")
                                                .valueExpression("aql:self.dependencies->union(droppedElements)")
                                                .build()
                                )
                                .build()
                )
                .build();

        var nodePalette = new DiagramBuilders().newNodePalette()
                .dropNodeTool(this.dropNodeTool)
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name("Component")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self.eContents()")
                .childrenDescriptions(packageNodeDescription)
                .insideLabel(insideLabel)
                .style(
                        new DiagramBuilders().newRectangularNodeStyleDescription()
                                .build()
                )
                .palette(nodePalette)
                .build();
    }
}
