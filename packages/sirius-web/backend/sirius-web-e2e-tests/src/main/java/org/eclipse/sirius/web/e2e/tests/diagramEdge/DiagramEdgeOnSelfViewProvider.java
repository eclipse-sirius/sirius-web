/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeType;
import org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode;
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
 * The view provider for diagrams with an edge on self.
 *
 * @author frouene
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramEdgeOnSelfViewProvider implements IE2EViewProvider {

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

        String resourcePath = UUID.nameUUIDFromBytes("DiagramEdgeOnSelfView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("DiagramEdgeOnSelfView"));
        resource.getContents().add(view);

        return List.of(view);
    }

    private DiagramDescription createDiagramDescription(IColorProvider colorProvider) {
        var nodeDescription1 = this.getNodeDescription(colorProvider, "Entity1");
        nodeDescription1.getReusedChildNodeDescriptions().add(nodeDescription1);

        var edgeDescription1 = this.getEdgeDescription(colorProvider, "E1toE1", nodeDescription1, nodeDescription1);

        return new DiagramBuilders()
                .newDiagramDescription()
                .name(DiagramEdgeOnSelfDomainProvider.DOMAIN_NAME + " - simple edges")
                .domainType(DiagramEdgeOnSelfDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramEdgeOnSelfDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .nodeDescriptions(nodeDescription1)
                .edgeDescriptions(edgeDescription1)
                .build();
    }


    private NodeDescription getNodeDescription(IColorProvider colorProvider, String entityName) {
        return new DiagramBuilders()
                .newNodeDescription()
                .name(entityName)
                .domainType(DiagramEdgeOnSelfDomainProvider.DOMAIN_NAME + "::" + entityName)
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
                .build();
    }

    private EdgeDescription getEdgeDescription(IColorProvider colorProvider, String entityName, DiagramElementDescription sourceDescription, DiagramElementDescription targetDescription) {

        return new DiagramBuilders()
                .newEdgeDescription()
                .name(entityName)
                .isDomainBasedEdge(false)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .centerLabelExpression("")
                .sourceExpression("aql:self")
                .sourceDescriptions(sourceDescription)
                .targetExpression("aql:self.entity1LinkedToEntity1")
                .targetDescriptions(targetDescription)
                .style(
                        new DiagramBuilders()
                                .newEdgeStyle()
                                .fontSize(14)
                                .color(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_TRANSPARENT))
                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .borderRadius(3)
                                .borderSize(0)
                                .borderLineStyle(LineStyle.SOLID)
                                .edgeType(EdgeType.MANHATTAN)
                                .build()
                )
                .build();
    }

}
