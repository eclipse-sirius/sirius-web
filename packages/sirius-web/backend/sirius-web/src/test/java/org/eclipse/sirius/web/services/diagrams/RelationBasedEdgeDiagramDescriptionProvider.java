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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Relation;
import org.eclipse.sirius.components.domain.emf.DomainConverter;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramPaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.EdgeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.EdgeStyleBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.FreeFormLayoutStrategyDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.InsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.InsideLabelStyleBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.LabelEditToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.ListLayoutStrategyDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodePaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.RectangularNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.CreateInstanceBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.SetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a domain and view based diagram description to test a diagram with relation based edges.
 *
 * @author arichard
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class RelationBasedEdgeDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final Domain domain;

    private final View view;

    private DiagramDescription diagramDescription;

    private NodeTool newEntityNodeTool;

    private NodeTool newSubEntityNodeTool;

    private LabelEditTool editSubEntityLabelTool;

    public RelationBasedEdgeDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.domain = this.createDomain();
        this.view = this.createView();
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var resourceSet = siriusWebEditingContext.getDomain().getResourceSet();
            new DomainConverter().convert(List.of(this.domain)).forEach(ePackage -> resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage));
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    public String getRepresentationDescriptionId() {
        return this.diagramIdProvider.getId(this.diagramDescription);
    }

    private Domain createDomain() {
        Domain myDomain = DomainFactory.eINSTANCE.createDomain();
        myDomain.setName("rbeDomain");
        Entity root = DomainFactory.eINSTANCE.createEntity();
        root.setName("Root");
        root.setAbstract(false);
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName("Entity");
        entity.setAbstract(false);
        Attribute nameAtt = DomainFactory.eINSTANCE.createAttribute();
        nameAtt.setMany(false);
        nameAtt.setName("name");
        nameAtt.setOptional(true);
        nameAtt.setType(DataType.STRING);
        entity.getAttributes().add(nameAtt);
        Relation entities = DomainFactory.eINSTANCE.createRelation();
        entities.setContainment(true);
        entities.setMany(true);
        entities.setName("entities");
        entities.setOptional(true);
        entities.setTargetType(entity);
        root.getRelations().add(entities);
        Relation subEntities = DomainFactory.eINSTANCE.createRelation();
        subEntities.setContainment(true);
        subEntities.setMany(true);
        subEntities.setName("subEntities");
        subEntities.setOptional(true);
        subEntities.setTargetType(entity);
        entity.getRelations().add(subEntities);
        myDomain.getTypes().add(root);
        myDomain.getTypes().add(entity);
        return myDomain;
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

        this.newEntityNodeTool();

        var diagramPalette = new DiagramPaletteBuilder()
                .nodeTools(this.newEntityNodeTool)
                .build();

        this.diagramDescription = new DiagramDescriptionBuilder()
                .name("Diagram with RelationBasedEdge")
                .titleExpression("aql:'RelationBasedEdgeDiagram'")
                .domainType("rbeDomain::Root")
                .nodeDescriptions(entityNodeDescription)
                .edgeDescriptions(containmentEdgeDescription)
                .palette(diagramPalette)
                .autoLayout(false)
                .build();

        return this.diagramDescription;
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

        var nodeDescription = new NodeDescriptionBuilder()
                .name("Sub-entities compartment")
                .domainType("rbeDomain::Entity")
                .semanticCandidatesExpression("aql:self")
                .insideLabel(insideLabel)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(nodeStyle)
                .build();

        return nodeDescription;
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

        this.newSubEntityNodeTool();
        this.editSubEntityLabelTool();

        var nodePalette = new NodePaletteBuilder()
                .nodeTools(this.newSubEntityNodeTool)
                .labelEditTool(this.editSubEntityLabelTool)
                .build();

        var nodeDescription = new NodeDescriptionBuilder()
                .name("Entity")
                .domainType("rbeDomain::Entity")
                .semanticCandidatesExpression(
                        "aql:if (self.oclIsKindOf(rbeDomain::Root) then self.entities->concat(self.entities.subEntities)->concat(self.entities.subEntities.subEntities) else self.subEntities endif")
                .insideLabel(insideLabel)
                .palette(nodePalette)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(nodeStyle)
                .build();

        return nodeDescription;
    }

    private void newEntityNodeTool() {
        this.newEntityNodeTool = new NodeToolBuilder()
                .name("New Entity")
                .body(
                        new CreateInstanceBuilder()
                                .typeName("rbeDomain::Entity")
                                .referenceName("entities")
                                .variableName("newInstance")
                                .children(
                                        new ChangeContextBuilder()
                                                .expression("aql:newInstance")
                                                .children(
                                                        new SetValueBuilder()
                                                                .featureName("name")
                                                                .valueExpression("NewEntity1")
                                                                .build())
                                                .build())
                .build())
                .build();
    }

    private void newSubEntityNodeTool() {
        this.newSubEntityNodeTool = new NodeToolBuilder()
                .name("New Sub-Entity")
                .body(
                        new CreateInstanceBuilder()
                                .typeName("rbeDomain::Entity")
                                .referenceName("subEntities")
                                .variableName("newInstance")
                                .children(
                                        new ChangeContextBuilder()
                                                .expression("aql:newInstance")
                                                .children(
                                                        new SetValueBuilder()
                                                                .featureName("name")
                                                                .valueExpression("NewSubEntity1")
                                                                .build())
                                                .build())
                                .build())
                .build();
    }

    private void editSubEntityLabelTool() {
        this.editSubEntityLabelTool = new LabelEditToolBuilder()
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:self.defaultEditLabel(newLabel)")
                                .build())
                .build();
    }

    private EdgeDescription containmentEdgeDecription(NodeDescription entityNodeDescription) {
        var edgeStyle = new EdgeStyleBuilder().build();

        var edgeDescriptionBuilder = new EdgeDescriptionBuilder()
                .centerLabelExpression("")
                .isDomainBasedEdge(false)
                .name("Containment Edge")
                .sourceDescriptions(entityNodeDescription)
                .sourceExpression("aql:self")
                .targetDescriptions(entityNodeDescription)
                .targetExpression("aql:self.subEntities")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(edgeStyle).build();

        return edgeDescriptionBuilder;
    }
}
