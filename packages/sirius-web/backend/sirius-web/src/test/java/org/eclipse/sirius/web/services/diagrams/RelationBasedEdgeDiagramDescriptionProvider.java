/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.query.EditingContextServices;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.EdgeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.EdgeStyleBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.FreeFormLayoutStrategyDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.InsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.InsideLabelStyleBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.ListLayoutStrategyDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.RectangularNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test a diagram with relation based edges.
 *
 * @author arichard
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class RelationBasedEdgeDiagramDescriptionProvider implements IEditingContextProcessor, IJavaServiceProvider {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    public RelationBasedEdgeDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.view = this.createView();
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    @Override
    public List<Class<?>> getServiceClasses(View aView) {
        boolean isRBEView = aView.getDescriptions().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .anyMatch(description -> description.getDomainType().equals("rbeDomain::Root"));
        if (isRBEView) {
            return List.of(RelationBasedEdgeService.class, EditingContextServices.class);
        }
        return List.of();
    }

    public String getRepresentationDescriptionId() {
        return this.diagramIdProvider.getId(this.diagramDescription);
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View unsynchronizedView = viewBuilder.build();
        unsynchronizedView.getDescriptions().add(this.createDiagramDescription());

        unsynchronizedView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("RelationBasedEdgeDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("RelationBasedEdgeDiagramDescription"));
        resource.getContents().add(unsynchronizedView);

        return unsynchronizedView;
    }

    private DiagramDescription createDiagramDescription() {
        var entityNodeDescription = this.entityNodeDescription();
        var subEntitiesCompartmentNodeDescription = this.subEntitiesCompartmentNodeDescription();

        entityNodeDescription.getChildrenDescriptions().add(subEntitiesCompartmentNodeDescription);
        subEntitiesCompartmentNodeDescription.getReusedChildNodeDescriptions().add(entityNodeDescription);

        var containmentEdgeDescription = this.containmentEdgeDecription(entityNodeDescription);

        this.diagramDescription = new DiagramDescriptionBuilder()
                .id("Diagram with RelationBasedEdge")
                .titleExpression("aql:'RelationBasedEdgeDiagram'")
                .domainType("rbeDomain::Root")
                .nodeDescriptions(entityNodeDescription)
                .edgeDescriptions(containmentEdgeDescription)
                .autoLayout(false)
                .build();

        return this.diagramDescription;
    }

    private NodeDescription entityNodeDescription() {
        var nodeStyle = new RectangularNodeStyleDescriptionBuilder()
                .childrenLayoutStrategy(
                        new ListLayoutStrategyDescriptionBuilder()
                                .areChildNodesDraggableExpression("aql:true")
                                .build())
                .build();

        var insideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("aql:self.name")
                .style(
                        new InsideLabelStyleBuilder()
                                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.ALWAYS)
                                .withHeader(true)
                                .build())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        return new NodeDescriptionBuilder()
                .id("Entity")
                .domainType("rbeDomain::Entity")
                .semanticCandidatesExpression("aql:if (self.oclIsKindOf(rbeDomain::Root) then self.entities->concat(self.entities.subEntities)->concat(self.entities.subEntities.subEntities) else self.subEntities endif")
                .insideLabel(insideLabel)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(nodeStyle)
                .build();
    }

    private NodeDescription subEntitiesCompartmentNodeDescription() {
        var nodeStyle = new RectangularNodeStyleDescriptionBuilder()
                .childrenLayoutStrategy(new FreeFormLayoutStrategyDescriptionBuilder().build())
                .build();

        var insideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("entities")
                .style(
                        new InsideLabelStyleBuilder()
                                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.ALWAYS)
                                .withHeader(true)
                                .build())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        return new NodeDescriptionBuilder()
                .id("Sub-entities compartment")
                .domainType("rbeDomain::Entity")
                .semanticCandidatesExpression("aql:self")
                .insideLabel(insideLabel)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(nodeStyle)
                .build();
    }

    private EdgeDescription containmentEdgeDecription(NodeDescription entityNodeDescription) {
        var edgeStyle = new EdgeStyleBuilder().build();

        return new EdgeDescriptionBuilder()
                .centerLabelExpression("")
                .isDomainBasedEdge(false)
                .id("Containment Edge")
                .preconditionExpression("aql:not graphicalEdgeSource.isAncestorOf(graphicalEdgeTarget, cache)")
                .sourceDescriptions(entityNodeDescription)
                .sourceExpression("aql:self")
                .targetDescriptions(entityNodeDescription)
                .targetExpression("aql:self.subEntities")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(edgeStyle).build();
    }
}
