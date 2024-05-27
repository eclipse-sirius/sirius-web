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
package org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the class node description.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ClassNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "Class";

    public static final String CONSTRUCTORS_NAME = "Constructors";

    public static final String CONSTRUCTOR_NAME = "Constructor";

    public static final String ATTRIBUTES_NAME = "Attributes";

    public static final String ATTRIBUTE_NAME = "Attribute";

    public static final String OPERATIONS_NAME = "Operations";

    public static final String OPERATION_NAME = "Operation";

    private final IColorProvider colorProvider;

    public ClassNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIcon(true)
                .displayHeaderSeparator(true)
                .withHeader(true)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .position(InsideLabelPosition.TOP_CENTER)
                .style(insideLabelStyle)
                .build();

        var classNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.DEFAULT_BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(1)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        var constructorsNodeDescription = this.constructorsNodeDescription();
        var attributesNodeDescription = this.attributesNodeDescription();
        var operationsNodeDescription = this.operationsNodeDescription();

        var childrenLayoutStrategy = new DiagramBuilders().newListLayoutStrategyDescription()
                .areChildNodesDraggableExpression("aql:false")
                .growableNodes(
                        attributesNodeDescription,
                        operationsNodeDescription
                )
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(NAME)
                .domainType("papaya::Class")
                .semanticCandidatesExpression("aql:editingContext.getSynchronizedObjects(semanticElementIds)")
                .insideLabel(insideLabel)
                .style(classNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .childrenDescriptions(
                        constructorsNodeDescription,
                        attributesNodeDescription,
                        operationsNodeDescription
                )
                .userResizable(true)
                .collapsible(true)
                .build();
    }

    private NodeDescription constructorsNodeDescription() {
        var constructorsNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.DEFAULT_BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(0)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        var constructorNodeDescription = this.constructorNodeDescription();

        var childrenLayoutStrategy = new DiagramBuilders().newListLayoutStrategyDescription()
                .areChildNodesDraggableExpression("aql:false")
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(CONSTRUCTORS_NAME)
                .domainType("papaya::Class")
                .semanticCandidatesExpression("aql:self")
                .style(constructorsNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .childrenDescriptions(constructorNodeDescription)
                .isCollapsedByDefaultExpression("aql:self.constructors->size() = 0")
                .build();
    }

    private NodeDescription constructorNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIcon(true)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .style(insideLabelStyle)
                .build();

        var constructorNodeStyle = new DiagramBuilders().newIconLabelNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.DEFAULT_BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(0)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(CONSTRUCTOR_NAME)
                .domainType("papaya:Constructor")
                .semanticCandidatesExpression("aql:self.constructors")
                .insideLabel(insideLabel)
                .style(constructorNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .build();
    }

    private NodeDescription attributesNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIcon(false)
                .withHeader(true)
                .displayHeaderSeparator(false)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("Attributes")
                .style(insideLabelStyle)
                .build();

        var attributesNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.DEFAULT_BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(0)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        var attributeNodeDescription = this.attributeNodeDescription();

        var childrenLayoutStrategy = new DiagramBuilders().newListLayoutStrategyDescription()
                .areChildNodesDraggableExpression("aql:false")
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(ATTRIBUTES_NAME)
                .domainType("papaya::Class")
                .semanticCandidatesExpression("aql:self")
                .insideLabel(insideLabel)
                .style(attributesNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .childrenDescriptions(attributeNodeDescription)
                .isCollapsedByDefaultExpression("aql:self.attributes->size() = 0")
                .build();
    }

    private NodeDescription attributeNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIcon(true)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .style(insideLabelStyle)
                .build();

        var attributeNodeStyle = new DiagramBuilders().newIconLabelNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.DEFAULT_BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(0)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(ATTRIBUTE_NAME)
                .domainType("papaya:Attribute")
                .semanticCandidatesExpression("aql:self.attributes")
                .insideLabel(insideLabel)
                .style(attributeNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .build();
    }

    private NodeDescription operationsNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIcon(false)
                .withHeader(true)
                .displayHeaderSeparator(false)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("Operations")
                .style(insideLabelStyle)
                .build();

        var operationsNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.DEFAULT_BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(0)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        var operationNodeDescription = this.operationNodeDescription();

        var childrenLayoutStrategy = new DiagramBuilders().newListLayoutStrategyDescription()
                .areChildNodesDraggableExpression("aql:false")
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(OPERATIONS_NAME)
                .domainType("papaya::Class")
                .semanticCandidatesExpression("aql:self")
                .insideLabel(insideLabel)
                .style(operationsNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .childrenDescriptions(operationNodeDescription)
                .isCollapsedByDefaultExpression("aql:self.operations->size() = 0")
                .build();
    }

    private NodeDescription operationNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIcon(true)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .style(insideLabelStyle)
                .build();

        var attributeNodeStyle = new DiagramBuilders().newIconLabelNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.DEFAULT_BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(0)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(OPERATION_NAME)
                .domainType("papaya:Operation")
                .semanticCandidatesExpression("aql:self.operations")
                .insideLabel(insideLabel)
                .style(attributeNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalClassNodeDescription = cache.getNodeDescription(NAME);
        if (optionalClassNodeDescription.isPresent()) {
            var classNodeDescription = optionalClassNodeDescription.get();
            diagramDescription.getNodeDescriptions().add(classNodeDescription);
        }
    }
}
