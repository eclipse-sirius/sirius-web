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
package org.eclipse.sirius.web.e2e.tests.diagramEdge;

import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.diagram.DeleteToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.diagram.EdgePaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeType;
import org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy;
import org.eclipse.sirius.components.view.diagram.LabelTextAlign;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.diagram.UserResizableDirection;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.e2e.tests.services.SiriusWebE2EColorPaletteBuilderProvider;
import org.eclipse.sirius.web.e2e.tests.services.SiriusWebE2EColorProvider;
import org.eclipse.sirius.web.e2e.tests.services.api.IE2EViewProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * The view provider for the edges-creation.cy.ts test suite.
 *
 * @author mcharfadi
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramEdgeViewProvider implements IE2EViewProvider {

    @Override
    public List<View> createViews() {
        var colorPalette = new SiriusWebE2EColorPaletteBuilderProvider().getColorPaletteBuilder().build();
        IColorProvider colorProvider = new SiriusWebE2EColorProvider(List.of(colorPalette));

        var diagramDescription = this.createDiagramDescription(colorProvider);

        var view = new ViewBuilders().newView()
                .colorPalettes(colorPalette)
                .descriptions(diagramDescription)
                .build();

        view.eAllContents().forEachRemaining(eObject ->
                eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())))
        );

        String resourcePath = UUID.nameUUIDFromBytes("DiagramEdgeView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("DiagramEdgeView"));
        resource.getContents().add(view);

        return List.of(view);
    }

    private DiagramDescription createDiagramDescription(IColorProvider colorProvider) {
        var nodeDescription1 = this.getNodeDescription(colorProvider, "Entity1");
        var nodeDescription2 = this.getNodeDescription(colorProvider, "Entity2");

        var edgeDescription1 = this.getEdgeDescriptionWithLabel(colorProvider, "E1toE2A", nodeDescription1, nodeDescription2);
        var edgeDescription2 = this.getEdgeDescription(colorProvider, "E1toE2B", nodeDescription1, nodeDescription2);
        var edgeDescription3 = this.getEdgeDescription(colorProvider, "EdgeToE2", edgeDescription1, nodeDescription2);

        var edgeDescription4 = this.getEdgeDescription(colorProvider, "E2ToEdge1A", nodeDescription2, edgeDescription1);
        var edgeDescription5 = this.getEdgeDescription(colorProvider, "E2ToEdge1B", nodeDescription2, edgeDescription1);

        nodeDescription1.setPalette(new DiagramBuilders().newNodePalette()
                .nodeTools(new DiagramBuilders().newNodeTool()
                        .name("Hide")
                        .body(new ViewBuilders().newChangeContext().expression("aql:diagramServices.hide(Sequence{selectedNode})").build())
                        .build())
                .edgeTools(
                        this.createEgeCreationTool("E1toE2A", nodeDescription2, edgeDescription1.getDomainType(), "toEdge1"),
                        this.createEgeCreationTool("E1toE2B", nodeDescription2, edgeDescription2.getDomainType(), "toEdge2")
                ).build());

        edgeDescription1.setPalette(new DiagramBuilders().newEdgePalette().edgeTools(
                this.createEgeCreationTool("EdgeToE2", nodeDescription2, edgeDescription3.getDomainType(), "toEdge3")
        ).build());

        nodeDescription2.setPalette(new DiagramBuilders().newNodePalette().edgeTools(
                this.createEgeCreationTool("E2ToEdge1A", edgeDescription1, edgeDescription4.getDomainType(), "toEdge4"),
                this.createEgeCreationTool("E2ToEdge1B", edgeDescription1, edgeDescription5.getDomainType(), "toEdge5")
        ).build());

        var toolbar = new DiagramBuilders().newDiagramToolbar()
            .expandedByDefault(true)
            .build();

        return new DiagramBuilders()
                .newDiagramDescription()
                .name(DiagramEdgeDomainProvider.DOMAIN_NAME + " - simple edges")
                .domainType(DiagramEdgeDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramEdgeDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .nodeDescriptions(nodeDescription1, nodeDescription2)
                .edgeDescriptions(edgeDescription1, edgeDescription2, edgeDescription3, edgeDescription4, edgeDescription5)
                .toolbar(toolbar)
                .build();
    }


    private NodeDescription getNodeDescription(IColorProvider colorProvider, String entityName) {
        return new DiagramBuilders()
                .newNodeDescription()
                .name(entityName)
                .domainType(DiagramEdgeDomainProvider.DOMAIN_NAME + "::" + entityName)
                .semanticCandidatesExpression("aql:self.eContents()")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(true)
                .userResizable(UserResizableDirection.BOTH)
                .keepAspectRatio(false)
                .style(
                        new DiagramBuilders()
                                .newRectangularNodeStyleDescription()
                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_BLUE))
                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.BORDER_BLUE))
                                .borderRadius(3)
                                .borderSize(1)
                                .borderLineStyle(LineStyle.SOLID)
                                .childrenLayoutStrategy(new DiagramBuilders().newFreeFormLayoutStrategyDescription().build())
                                .build()
                )
                .insideLabel(
                        new DiagramBuilders()
                                .newInsideLabelDescription()
                                .labelExpression("aql:self.name")
                                .textAlign(LabelTextAlign.LEFT)
                                .position(InsideLabelPosition.TOP_CENTER)
                                .style(
                                        new DiagramBuilders()
                                                .newInsideLabelStyle()
                                                .fontSize(14)
                                                .italic(false)
                                                .bold(false)
                                                .underline(false)
                                                .strikeThrough(false)
                                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                                .borderRadius(3)
                                                .borderSize(0)
                                                .borderLineStyle(LineStyle.SOLID)
                                                .labelColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_TRANSPARENT))
                                                .withHeader(true)
                                                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.ALWAYS)
                                                .build()
                                )
                                .build()
                )
                .outsideLabels(
                        new DiagramBuilders()
                                .newOutsideLabelDescription()
                                .labelExpression("aql:self.outsideName")
                                .textAlign(LabelTextAlign.LEFT)
                                .overflowStrategy(LabelOverflowStrategy.WRAP)
                                .style(
                                        new DiagramBuilders()
                                                .newOutsideLabelStyle()
                                                .fontSize(14)
                                                .italic(false)
                                                .bold(false)
                                                .underline(false)
                                                .strikeThrough(false)
                                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                                .borderRadius(3)
                                                .borderSize(0)
                                                .borderLineStyle(LineStyle.SOLID)
                                                .labelColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_TRANSPARENT))
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private EdgeDescription getEdgeDescription(IColorProvider colorProvider, String entityName, DiagramElementDescription sourceDescription, DiagramElementDescription targetDescription) {
        var deleteTool = new DeleteToolBuilder()
                .name("Delete")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self.defaultDelete()")
                                .build()
                )
                .build();

        return new DiagramBuilders()
                .newEdgeDescription()
                .name(entityName)
                .isDomainBasedEdge(true)
                .domainType(DiagramEdgeDomainProvider.DOMAIN_NAME + "::" + entityName)
                .semanticCandidatesExpression("aql:self.eContents()")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .centerLabelExpression("")
                .sourceExpression("aql:self.source")
                .sourceDescriptions(sourceDescription)
                .targetExpression("aql:self.target")
                .targetDescriptions(targetDescription)
                .palette(new EdgePaletteBuilder()
                        .deleteTool(deleteTool)
                        .build())
                .style(
                        new DiagramBuilders()
                                .newEdgeStyle()
                                .fontSize(14)
                                .color(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_BLUE))
                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_TRANSPARENT))
                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .borderRadius(3)
                                .borderSize(0)
                                .borderLineStyle(LineStyle.SOLID)
                                .edgeType(EdgeType.MANHATTAN)
                                .build()
                )
                .conditionalStyles(
                        new DiagramBuilders()
                                .newConditionalEdgeStyle()
                                .condition("aql:self.name=='TestConditionalEdgeStyle'")
                                .color(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_RED))
                                .edgeType(EdgeType.SMART_MANHATTAN)
                                .build()
                )
                .conditionalStyles(
                        new DiagramBuilders()
                                .newConditionalEdgeStyle()
                                .condition("aql:self.name=='TestObliqueEdgeType'")
                                .edgeType(EdgeType.OBLIQUE)
                                .build()
                )
                .build();
    }

    private EdgeDescription getEdgeDescriptionWithLabel(IColorProvider colorProvider, String entityName, DiagramElementDescription sourceDescription, DiagramElementDescription targetDescription) {
        return new DiagramBuilders()
                .newEdgeDescription()
                .name(entityName)
                .isDomainBasedEdge(true)
                .domainType(DiagramEdgeDomainProvider.DOMAIN_NAME + "::" + entityName)
                .semanticCandidatesExpression("aql:self.eContents()")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .centerLabelExpression("aql:self.name")
                .beginLabelExpression("aql:self.beginName")
                .endLabelExpression("aql:self.endName")
                .sourceExpression("aql:self.source")
                .sourceDescriptions(sourceDescription)
                .targetExpression("aql:self.target")
                .targetDescriptions(targetDescription)
                .style(
                        new DiagramBuilders()
                                .newEdgeStyle()
                                .fontSize(14)
                                .color(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_BLUE))
                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_TRANSPARENT))
                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .borderRadius(3)
                                .borderSize(0)
                                .borderLineStyle(LineStyle.SOLID)
                                .edgeType(EdgeType.MANHATTAN)
                                .build()
                )
                .conditionalStyles(
                        new DiagramBuilders()
                                .newConditionalEdgeStyle()
                                .condition("aql:self.name=='TestConditionalEdgeStyle'")
                                .color(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_RED))
                                .edgeType(EdgeType.SMART_MANHATTAN)
                                .build()
                )
                .conditionalStyles(
                        new DiagramBuilders()
                                .newConditionalEdgeStyle()
                                .condition("aql:self.name=='TestObliqueEdgeType'")
                                .edgeType(EdgeType.OBLIQUE)
                                .build()
                )
                .build();
    }

    private EdgeTool createEgeCreationTool(String name, DiagramElementDescription target, String typeName, String referenceName) {
        return new DiagramBuilders().newEdgeTool()
                .name(name)
                .targetElementDescriptions(target)
                .body(new ViewBuilders().newChangeContext()
                        .expression("aql:semanticEdgeSource.eContainer()")
                        .children(new ViewBuilders().newCreateInstance()
                                .typeName(typeName)
                                .referenceName(referenceName)
                                .variableName("newInstance")
                                .children(new ViewBuilders().newChangeContext()
                                        .expression("aql:newInstance")
                                        .children(new ViewBuilders().newSetValue()
                                                        .featureName("source")
                                                        .valueExpression("aql:semanticEdgeSource")
                                                        .build(),
                                                new ViewBuilders().newSetValue()
                                                        .featureName("target")
                                                        .valueExpression("aql:semanticEdgeTarget")
                                                        .build())
                                        .build())
                                .build()
                        )
                        .build()
                )
                .build();
    }

}
