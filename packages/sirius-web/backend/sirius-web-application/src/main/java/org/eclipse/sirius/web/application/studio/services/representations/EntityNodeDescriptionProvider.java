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
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.provider.DefaultToolsFactory;

/**
 * Used to create the entity node description.
 *
 * @author sbegaudeau
 */
public class EntityNodeDescriptionProvider implements INodeDescriptionProvider {

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

        var palette = new DiagramBuilders()
                .newNodePalette()
                .toolSections(new DefaultToolsFactory().createDefaultHideRevealNodeToolSection())
                .build();

        return new DiagramBuilders()
                .newNodeDescription()
                .name("Entity")
                .domainType("domain::Entity")
                .semanticCandidatesExpression("aql:self.types")
                .insideLabel(this.entityNodeLabelDescription())
                .childrenDescriptions(this.attributeNodeDescription())
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .style(nodeStyle)
                .conditionalStyles(conditionalNodeStyle)
                .palette(palette)
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

        var palette = new DiagramBuilders()
                .newNodePalette()
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
                .position(InsideLabelPosition.TOP_CENTER)
                .style(insideLabelStyle)
                .conditionalStyles(conditionalLabelStyle)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalEntityNodeDescription = cache.getNodeDescription("Entity");
        if (optionalEntityNodeDescription.isPresent()) {
            var entityNodeDescription = optionalEntityNodeDescription.get();
            diagramDescription.getNodeDescriptions().add(entityNodeDescription);
        }
    }
}
