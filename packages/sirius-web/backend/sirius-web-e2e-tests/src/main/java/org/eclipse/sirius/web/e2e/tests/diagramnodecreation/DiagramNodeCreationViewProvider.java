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

package org.eclipse.sirius.web.e2e.tests.diagramnodecreation;

import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeType;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
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
 * The view provider for the node creation test suite.
 *
 * @author frouene
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramNodeCreationViewProvider implements IE2EViewProvider {

    @Override
    public List<View> createViews() {
        var colorPalette = new SiriusWebE2EColorPaletteBuilderProvider().getColorPaletteBuilder().build();
        IColorProvider colorProvider = new SiriusWebE2EColorProvider(List.of(colorPalette));

        var diagramDescription = this.gegtDiagramDescription(colorProvider);

        var view = new ViewBuilders().newView()
                .colorPalettes(colorPalette)
                .descriptions(
                        diagramDescription
                )
                .build();

        view.eAllContents().forEachRemaining(eObject -> eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes()))));

        String resourcePath = UUID.nameUUIDFromBytes("diagramNodeCreationView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("diagramNodeCreationView"));
        resource.getContents().add(view);

        return List.of(view);
    }

    private DiagramDescription gegtDiagramDescription(IColorProvider colorProvider) {
        var nodeDescription1 = this.getNode4Description(colorProvider, "Entity1");
        var nodeDescription2 = this.getNode4Description(colorProvider, "Entity2");
        var edgeDescription = this.getEdgeDescription(colorProvider, nodeDescription1, nodeDescription2);


        nodeDescription1.setPalette(new DiagramBuilders().newNodePalette()
                .nodeTools(new DiagramBuilders().newNodeTool()
                        .name("creationNode")
                        .body(new ViewBuilders().newLet()
                                .variableName("entity1")
                                .valueExpression("aql:self")
                                .children(new ViewBuilders().newChangeContext()
                                        .expression("aql:self.eContainer()")
                                        .children(new ViewBuilders().newCreateInstance()
                                                .typeName(DiagramNodeCreationDomainProvider.DOMAIN_NAME + "::Entity2")
                                                .referenceName("entity2s")
                                                .children(new ViewBuilders().newChangeContext()
                                                                .expression("aql:newInstance")
                                                                .children(new ViewBuilders().newSetValue()
                                                                        .featureName("name")
                                                                        .valueExpression("New node created")
                                                                        .build())
                                                                .build(),
                                                        new ViewBuilders().newChangeContext()
                                                                .expression("aql:entity1")
                                                                .children(new ViewBuilders().newSetValue()
                                                                        .featureName("entity1LinkToEntity2")
                                                                        .valueExpression("aql:newInstance")
                                                                        .build())
                                                                .build()
                                                )
                                                .build())
                                        .build())
                                .build())

                        .build())
                .build());

        return new DiagramBuilders()
                .newDiagramDescription()
                .id(DiagramNodeCreationDomainProvider.DOMAIN_NAME + " - node creation")
                .domainType(DiagramNodeCreationDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramNodeCreationDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .nodeDescriptions(nodeDescription1, nodeDescription2, this.getNode3Description(colorProvider))
                .edgeDescriptions(edgeDescription)
                .palette(new DiagramBuilders().newDiagramPalette()
                        .nodeTools(new DiagramBuilders().newNodeTool()
                                .name("create nodes")
                                .body(new ViewBuilders().newCreateInstance()
                                                .typeName(DiagramNodeCreationDomainProvider.DOMAIN_NAME + "::Entity1")
                                                .referenceName("entity1s")
                                                .build(),
                                        new ViewBuilders().newCreateInstance()
                                                .typeName(DiagramNodeCreationDomainProvider.DOMAIN_NAME + "::Entity2")
                                                .referenceName("entity2s")
                                                .build())
                                .build())
                        .build())
                .build();
    }

    private NodeDescription getNode4Description(IColorProvider colorProvider, String entityName) {
        return new DiagramBuilders()
                .newNodeDescription()
                .id(entityName)
                .domainType(DiagramNodeCreationDomainProvider.DOMAIN_NAME + "::" + entityName)
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
                                                .borderSize(0)
                                                .labelColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                                .withHeader(false)
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private NodeDescription getNode3Description(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newNodeDescription()
                .id("entity3")
                .domainType(DiagramNodeCreationDomainProvider.DOMAIN_NAME + "::" + "Entity3")
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
                                .childrenLayoutStrategy(new DiagramBuilders().newListLayoutStrategyDescription().build())
                                .build()
                )
                .insideLabel(
                        new DiagramBuilders()
                                .newInsideLabelDescription()
                                .labelExpression("Parent")
                                .textAlign(LabelTextAlign.LEFT)
                                .position(InsideLabelPosition.TOP_CENTER)
                                .style(
                                        new DiagramBuilders()
                                                .newInsideLabelStyle()
                                                .fontSize(14)
                                                .borderSize(0)
                                                .labelColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                                .withHeader(true)
                                                .build()
                                )
                                .build()
                )
                .childrenDescriptions(
                        new DiagramBuilders()
                                .newNodeDescription()
                                .id("entity3Compartment")
                                .semanticCandidatesExpression("aql:self")
                                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                                .collapsible(true)
                                .userResizable(UserResizableDirection.BOTH)
                                .keepAspectRatio(false)
                                .style(
                                        new DiagramBuilders()
                                                .newRectangularNodeStyleDescription()
                                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_PINK))
                                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.BORDER_GREEN))
                                                .borderRadius(3)
                                                .borderSize(1)
                                                .borderLineStyle(LineStyle.SOLID)
                                                .childrenLayoutStrategy(new DiagramBuilders().newFreeFormLayoutStrategyDescription().build())
                                                .build()
                                )
                                .insideLabel(
                                        new DiagramBuilders()
                                                .newInsideLabelDescription()
                                                .labelExpression("Compartment")
                                                .textAlign(LabelTextAlign.LEFT)
                                                .position(InsideLabelPosition.TOP_CENTER)
                                                .style(
                                                        new DiagramBuilders()
                                                                .newInsideLabelStyle()
                                                                .fontSize(14)
                                                                .borderSize(0)
                                                                .labelColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                                                .withHeader(true)
                                                                .build()
                                                )
                                                .build()
                                )
                                .childrenDescriptions(this.getNode4Description(colorProvider))
                                .build()
                )
                .palette(new DiagramBuilders().newNodePalette()
                        .nodeTools(new DiagramBuilders().newNodeTool()
                                .name("createEntity4")
                                .body(new ViewBuilders().newCreateInstance()
                                        .typeName(DiagramNodeCreationDomainProvider.DOMAIN_NAME + "::Entity4")
                                        .referenceName("entity4s")
                                        .build())
                                .build())
                        .build())
                .build();
    }

    private NodeDescription getNode4Description(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newNodeDescription()
                .id("entity4")
                .domainType(DiagramNodeCreationDomainProvider.DOMAIN_NAME + "::" + "Entity4")
                .semanticCandidatesExpression("aql:self.eContents()")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(true)
                .userResizable(UserResizableDirection.BOTH)
                .keepAspectRatio(false)
                .defaultHeightExpression("45")
                .defaultWidthExpression("45")
                .style(
                        new DiagramBuilders()
                                .newRectangularNodeStyleDescription()
                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .borderRadius(3)
                                .borderSize(1)
                                .borderLineStyle(LineStyle.SOLID)
                                .childrenLayoutStrategy(new DiagramBuilders().newFreeFormLayoutStrategyDescription().build())
                                .build()
                )
                .build();
    }

    private EdgeDescription getEdgeDescription(IColorProvider colorProvider, DiagramElementDescription sourceDescription, DiagramElementDescription targetDescription) {
        return new DiagramBuilders()
                .newEdgeDescription()
                .id("edge")
                .isDomainBasedEdge(false)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .centerLabelExpression("")
                .sourceExpression("aql:self")
                .sourceDescriptions(sourceDescription)
                .targetExpression("aql:self.entity1LinkToEntity2")
                .targetDescriptions(targetDescription)
                .style(
                        new DiagramBuilders()
                                .newEdgeStyleDescription()
                                .fontSize(14)
                                .color(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .borderSize(0)
                                .edgeType(EdgeType.MANHATTAN)
                                .build()
                )
                .build();
    }

}
