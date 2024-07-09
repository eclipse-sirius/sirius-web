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
package org.eclipse.sirius.web.application.studio.services.representations;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DeleteToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.EdgeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.LabelEditToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.NodeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.NodeToolSectionBuilder;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.provider.DefaultToolsFactory;

/**
 * Used to create the entity node description.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class EntityNodeDescriptionProvider implements INodeDescriptionProvider {

    private static final String ENTITY_NODE_DESCRIPTION_NAME = "Entity";

    private static final String ENTITY_NODE_TOOL_SECTION_NAME = "Entity";

    private final IColorProvider colorProvider;

    public EntityNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        return this.entityNodeDescription();
    }

    private NodeDescription entityNodeDescription() {
        var childrenLayoutStrategy = new DiagramBuilders()
                .newListLayoutStrategyDescription()
                .build();

        var nodeStyle = new DiagramBuilders()
                .newRectangularNodeStyleDescription()
                .borderRadius(8)
                .borderSize(3)
                .borderColor(this.colorProvider.getColor(DomainDiagramDescriptionProvider.MAIN_COLOR))
                .background(this.colorProvider.getColor(DomainDiagramDescriptionProvider.BACKGROUND_COLOR))
                .borderLineStyle(LineStyle.SOLID)
                .build();

        var abstractEntityNodeStyle = new DiagramBuilders()
                .newRectangularNodeStyleDescription()
                .borderRadius(8)
                .borderSize(3)
                .borderColor(this.colorProvider.getColor(DomainDiagramDescriptionProvider.MAIN_COLOR))
                .background(this.colorProvider.getColor(DomainDiagramDescriptionProvider.LIGHT_GREY_COLOR))
                .borderLineStyle(LineStyle.SOLID)
                .build();

        var conditionalNodeStyle = new DiagramBuilders()
                .newConditionalNodeStyle()
                .style(abstractEntityNodeStyle)
                .condition("aql:self.abstract")
                .build();


        var entityNodeDescription = new DiagramBuilders()
                .newNodeDescription()
                .name(ENTITY_NODE_DESCRIPTION_NAME)
                .domainType("domain::Entity")
                .semanticCandidatesExpression("aql:self.types")
                .insideLabel(this.entityNodeLabelDescription())
                .childrenDescriptions(this.attributeNodeDescription())
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .style(nodeStyle)
                .conditionalStyles(conditionalNodeStyle)
                .build();

        var palette = this.entityNodePalette(entityNodeDescription);
        entityNodeDescription.setPalette(palette);

        return entityNodeDescription;
    }

    private NodePalette entityNodePalette(NodeDescription edgeTargetNodeDescription) {
        var nodeToolSection = this.entityNodeCreationTools();
        var relationEdgeTool = this.entityRelationEdgeTool(edgeTargetNodeDescription);
        var containmentEdgeTool = this.entityContainmentEdgeTool(edgeTargetNodeDescription);
        var supertypeEdgeTool = this.entitySupertypeEdgeTool(edgeTargetNodeDescription);
        var labelEditTool = this.labelEditTool();
        var deleteTool = this.deleteTool();
        return new DiagramBuilders()
                .newNodePalette()
                .labelEditTool(labelEditTool)
                .deleteTool(deleteTool)
                .edgeTools(relationEdgeTool, containmentEdgeTool, supertypeEdgeTool)
                .toolSections(nodeToolSection, new DefaultToolsFactory().createDefaultHideRevealNodeToolSection())
                .build();
    }

    private EdgeTool entityRelationEdgeTool(NodeDescription edgeTargetNodeDescription) {
        var createInstance = new ViewBuilders().newCreateInstance()
                .typeName("domain::Relation")
                .variableName("newInstance")
                .referenceName("relations")
                .children(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:newInstance")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("name")
                                                .valueExpression("aql:semanticEdgeTarget.name.toLowerFirst()")
                                                .build(),
                                        new ViewBuilders().newSetValue()
                                                .featureName("targetType")
                                                .valueExpression("aql:semanticEdgeTarget")
                                                .build()
                                )
                                .build()
                )
                .build();

        return new EdgeToolBuilder()
                .name("Relation")
                .iconURLsExpression("aql:'/icons/full/obj16/Relation.svg'")
                .targetElementDescriptions(edgeTargetNodeDescription)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:semanticEdgeSource")
                                .children(
                                        createInstance
                                )
                                .build()
                )
                .build();
    }

    private EdgeTool entityContainmentEdgeTool(NodeDescription edgeTargetNodeDescription) {
        var createInstance = new ViewBuilders().newCreateInstance()
                .typeName("domain::Relation")
                .variableName("newInstance")
                .referenceName("relations")
                .children(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:newInstance")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("name")
                                                .valueExpression("aql:semanticEdgeTarget.name.toLowerFirst() + 's'")
                                                .build(),
                                        new ViewBuilders().newSetValue()
                                                .featureName("targetType")
                                                .valueExpression("aql:semanticEdgeTarget")
                                                .build(),
                                        new ViewBuilders().newSetValue()
                                                .featureName("containment")
                                                .valueExpression("aql:true")
                                                .build(),
                                        new ViewBuilders().newSetValue()
                                                .featureName("many")
                                                .valueExpression("aql:true")
                                                .build()
                                )
                                .build()
                )
                .build();

        return new EdgeToolBuilder()
                .name("Containment")
                .iconURLsExpression("aql:'/icons/full/obj16/Relation.svg'")
                .targetElementDescriptions(edgeTargetNodeDescription)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:semanticEdgeSource")
                                .children(
                                        createInstance
                                )
                                .build()
                )
                .build();
    }

    private EdgeTool entitySupertypeEdgeTool(NodeDescription edgeTargetNodeDescription) {
        return new EdgeToolBuilder()
                .name("Supertype")
                .iconURLsExpression("aql:'/icons/full/obj16/Relation.svg'")
                .targetElementDescriptions(edgeTargetNodeDescription)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:semanticEdgeSource")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("superTypes")
                                                .valueExpression("aql:semanticEdgeTarget")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private NodeToolSection entityNodeCreationTools() {
        var stringCreationTool = this.entityAttributeCreationTool("Text", "aql:'/icons/full/obj16/StringAttribute.svg'", "newString", "aql:domain::DataType::STRING");
        var booleanCreationTool = this.entityAttributeCreationTool("Boolean", "aql:'/icons/full/obj16/BooleanAttribute.svg'", "newBoolean", "aql:domain::DataType::BOOLEAN");
        var numberCreationTool = this.entityAttributeCreationTool("Number", "aql:'/icons/full/obj16/NumberAttribute.svg'", "newNumber", "aql:domain::DataType::NUMBER");

        return new NodeToolSectionBuilder()
                .name(ENTITY_NODE_TOOL_SECTION_NAME)
                .nodeTools(stringCreationTool, booleanCreationTool, numberCreationTool)
                .build();
    }

    private NodeTool entityAttributeCreationTool(String toolName, String iconExpression, String valueExpressionForName, String valueExpressionForType) {
        var createInstance = new ViewBuilders().newCreateInstance()
                .typeName("domain::Attribute")
                .referenceName("attributes")
                .variableName("newInstance")
                .children(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:newInstance")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("name")
                                                .valueExpression(valueExpressionForName)
                                                .build(),
                                        new ViewBuilders().newSetValue()
                                                .featureName("type")
                                                .valueExpression(valueExpressionForType)
                                                .build())
                                .build()
                )
                .build();

        return new NodeToolBuilder()
                .name(toolName)
                .iconURLsExpression(iconExpression)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        createInstance
                                )
                                .build()
                )
                .build();
    }

    private LabelEditTool labelEditTool() {
        return new LabelEditToolBuilder()
                .name("Edit Label")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self.defaultEditLabel(newLabel)")
                                .build()
                )
                .build();
    }

    private DeleteTool deleteTool() {
        return new DeleteToolBuilder()
                .name("Edit Label")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self.defaultDelete()")
                                .build()
                )
                .build();
    }

    private InsideLabelDescription entityNodeLabelDescription() {
        var insideLabelStyle = new DiagramBuilders()
                .newInsideLabelStyle()
                .labelColor(this.colorProvider.getColor(DomainDiagramDescriptionProvider.BLACK_COLOR))
                .showIcon(true)
                .withHeader(true)
                .displayHeaderSeparator(true)
                .borderSize(0)
                .build();

        var abstractEntityLabelStyle = new DiagramBuilders()
                .newInsideLabelStyle()
                .labelColor(this.colorProvider.getColor(DomainDiagramDescriptionProvider.BLACK_COLOR))
                .showIcon(true)
                .withHeader(true)
                .displayHeaderSeparator(true)
                .italic(true)
                .borderSize(0)
                .build();

        var conditionalLabelStyle = new DiagramBuilders()
                .newConditionalInsideLabelStyle()
                .style(abstractEntityLabelStyle)
                .condition("aql:self.abstract")
                .build();

        return new DiagramBuilders()
                .newInsideLabelDescription()
                .labelExpression("aql:self.name")
                .position(InsideLabelPosition.TOP_CENTER)
                .style(insideLabelStyle)
                .conditionalStyles(conditionalLabelStyle)
                .build();
    }

    private NodeDescription attributeNodeDescription() {
        var nodeStyle = new DiagramBuilders()
                .newIconLabelNodeStyleDescription()
                .background(this.colorProvider.getColor(DomainDiagramDescriptionProvider.BACKGROUND_COLOR))
                .build();

        var labelEditTool = this.labelEditTool();
        var deleteTool = this.deleteTool();

        var palette = new DiagramBuilders()
                .newNodePalette()
                .labelEditTool(labelEditTool)
                .deleteTool(deleteTool)
                .toolSections(new DefaultToolsFactory().createDefaultHideRevealNodeToolSection())
                .build();

        return new DiagramBuilders()
                .newNodeDescription()
                .name("Attribute")
                .domainType("domain::Attribute")
                .semanticCandidatesExpression("aql:self.attributes")
                .style(nodeStyle)
                .insideLabel(this.attributeNodeLabelDescription())
                .palette(palette)
                .build();
    }

    private InsideLabelDescription attributeNodeLabelDescription() {
        var insideLabelStyle = new DiagramBuilders()
                .newInsideLabelStyle()
                .fontSize(12)
                .labelColor(this.colorProvider.getColor(DomainDiagramDescriptionProvider.GREY_COLOR))
                .showIcon(true)
                .borderSize(0)
                .build();

        var requiredAttributeLabelStyle = new DiagramBuilders()
                .newInsideLabelStyle()
                .fontSize(12)
                .labelColor(this.colorProvider.getColor(DomainDiagramDescriptionProvider.GREY_COLOR))
                .showIcon(true)
                .bold(true)
                .borderSize(0)
                .build();

        var conditionalLabelStyle = new DiagramBuilders()
                .newConditionalInsideLabelStyle()
                .style(requiredAttributeLabelStyle)
                .condition("aql:not self.optional")
                .build();

        return new DiagramBuilders()
                .newInsideLabelDescription()
                .labelExpression("aql:self.renderAttribute()")
                .position(InsideLabelPosition.TOP_LEFT)
                .style(insideLabelStyle)
                .conditionalStyles(conditionalLabelStyle)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalEntityNodeDescription = cache.getNodeDescription(ENTITY_NODE_DESCRIPTION_NAME);
        if (optionalEntityNodeDescription.isPresent()) {
            var entityNodeDescription = optionalEntityNodeDescription.get();
            diagramDescription.getNodeDescriptions().add(entityNodeDescription);
        }
    }
}
