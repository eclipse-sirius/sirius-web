/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.diagram.UserResizableDirection;
import org.eclipse.sirius.web.papaya.representations.classdiagram.tools.classnode.ClassNodePaletteProvider;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the class node description.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ClassNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "Class";

    public static final String CONSTRUCTORS_NAME = "Class Constructors";

    public static final String CONSTRUCTOR_NAME = "Class Constructor";

    public static final String ATTRIBUTES_NAME = "Class Attributes";

    public static final String ATTRIBUTE_NAME = "Class Attribute";

    public static final String OPERATIONS_NAME = "Class Operations";

    public static final String OPERATION_NAME = "Class Operation";

    private final IColorProvider colorProvider;

    public ClassNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIconExpression("aql:true")
                .withHeader(true)
                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.IF_CHILDREN)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .position(InsideLabelPosition.TOP_CENTER)
                .style(insideLabelStyle)
                .overflowStrategy(LabelOverflowStrategy.NONE)
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

        var classNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(1)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .build();

        var newAttributeAction = new DiagramBuilders().newAction()
                .body(new ViewBuilders().newChangeContext()
                        .expression("aql:self")
                        .children(
                                new ViewBuilders().newCreateInstance()
                                        .typeName("papaya:Attribute")
                                        .referenceName("attributes")
                                        .build()
                        )
                        .build())
                .iconURLsExpression("aql:'/icons/papaya/full/obj16/Attribute.svg'")
                .tooltipExpression("New attribute")
                .name("New Attribute Action")
                .build();

        var newOperationAction = new DiagramBuilders().newAction()
                .body(new ViewBuilders().newChangeContext()
                        .expression("aql:self")
                        .children(
                                new ViewBuilders().newCreateInstance()
                                        .typeName("papaya:Operation")
                                        .referenceName("operations")
                                        .build()
                        )
                        .build())
                .iconURLsExpression("aql:'/icons/papaya/full/obj16/Operation.svg'")
                .tooltipExpression("New operation")
                .name("New Operation Action")
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(NAME)
                .domainType("papaya::Class")
                .semanticCandidatesExpression("aql:editingContext.getSynchronizedObjects(semanticElementIds)")
                .insideLabel(insideLabel)
                .style(classNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .childrenDescriptions(
                        constructorsNodeDescription,
                        attributesNodeDescription,
                        operationsNodeDescription
                )
                .userResizable(UserResizableDirection.BOTH)
                .collapsible(true)
                .actions(newAttributeAction, newOperationAction)
                .build();
    }

    private NodeDescription constructorsNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIconExpression("aql:false")
                .withHeader(true)
                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.NEVER)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("Constructors")
                .style(insideLabelStyle)
                .build();

        var childrenLayoutStrategy = new DiagramBuilders().newListLayoutStrategyDescription()
                .areChildNodesDraggableExpression("aql:false")
                .build();

        var constructorsNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(1)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .build();

        var constructorNodeDescription = this.constructorNodeDescription();

        return new DiagramBuilders().newNodeDescription()
                .name(CONSTRUCTORS_NAME)
                .domainType("papaya::Class")
                .semanticCandidatesExpression("aql:self")
                .insideLabel(insideLabel)
                .style(constructorsNodeStyle)
                .isHiddenByDefaultExpression("aql:true")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .childrenDescriptions(constructorNodeDescription)
                .build();
    }

    private NodeDescription constructorNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIconExpression("aql:true")
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .position(InsideLabelPosition.MIDDLE_LEFT)
                .style(insideLabelStyle)
                .build();

        var constructorNodeStyle = new DiagramBuilders().newIconLabelNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
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
                .showIconExpression("aql:false")
                .withHeader(true)
                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.NEVER)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("Attributes")
                .style(insideLabelStyle)
                .build();

        var childrenLayoutStrategy = new DiagramBuilders().newListLayoutStrategyDescription()
                .areChildNodesDraggableExpression("aql:false")
                .build();

        var attributesNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(1)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .build();

        var attributeNodeDescription = this.attributeNodeDescription();

        return new DiagramBuilders().newNodeDescription()
                .name(ATTRIBUTES_NAME)
                .domainType("papaya::Class")
                .semanticCandidatesExpression("aql:self")
                .insideLabel(insideLabel)
                .style(attributesNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .childrenDescriptions(attributeNodeDescription)
                .build();
    }

    private NodeDescription attributeNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIconExpression("aql:true")
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .position(InsideLabelPosition.MIDDLE_LEFT)
                .style(insideLabelStyle)
                .build();

        var attributeNodeStyle = new DiagramBuilders().newIconLabelNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
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
                .showIconExpression("aql:false")
                .withHeader(true)
                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.NEVER)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("Operations")
                .style(insideLabelStyle)
                .build();

        var childrenLayoutStrategy = new DiagramBuilders().newListLayoutStrategyDescription()
                .areChildNodesDraggableExpression("aql:false")
                .build();

        var operationsNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(1)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .build();

        var operationNodeDescription = this.operationNodeDescription();

        return new DiagramBuilders().newNodeDescription()
                .name(OPERATIONS_NAME)
                .domainType("papaya::Class")
                .semanticCandidatesExpression("aql:self")
                .insideLabel(insideLabel)
                .style(operationsNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .childrenDescriptions(operationNodeDescription)
                .build();
    }

    private NodeDescription operationNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIconExpression("aql:true")
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .position(InsideLabelPosition.MIDDLE_LEFT)
                .style(insideLabelStyle)
                .build();

        var operationNodeStyle = new DiagramBuilders().newIconLabelNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(OPERATION_NAME)
                .domainType("papaya:Operation")
                .semanticCandidatesExpression("aql:self.operations")
                .insideLabel(insideLabel)
                .style(operationNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalClassNodeDescription = cache.getNodeDescription(NAME);
        if (optionalClassNodeDescription.isPresent()) {
            var classNodeDescription = optionalClassNodeDescription.get();

            var palette = new ClassNodePaletteProvider().getNodePalette(cache);
            classNodeDescription.setPalette(palette);

            diagramDescription.getNodeDescriptions().add(classNodeDescription);
        }
    }
}
