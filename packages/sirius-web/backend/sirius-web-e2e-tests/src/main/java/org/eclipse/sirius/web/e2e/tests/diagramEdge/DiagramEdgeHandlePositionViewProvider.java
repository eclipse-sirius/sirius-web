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
 * The view provider for the handle position test suite.
 *
 * @author frouene
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramEdgeHandlePositionViewProvider implements IE2EViewProvider {

    @Override
    public List<View> createViews() {
        var colorPalette = new SiriusWebE2EColorPaletteBuilderProvider().getColorPaletteBuilder().build();
        IColorProvider colorProvider = new SiriusWebE2EColorProvider(List.of(colorPalette));

        var diagramDescriptionUndefined = this.createDiagramDescription(colorProvider, ArrangeLayoutDirection.UNDEFINED);
        var diagramDescriptionDown = this.createDiagramDescription(colorProvider, ArrangeLayoutDirection.DOWN);
        var diagramDescriptionRight = this.createDiagramDescription(colorProvider, ArrangeLayoutDirection.RIGHT);

        var view = new ViewBuilders().newView()
                .colorPalettes(colorPalette)
                .descriptions(diagramDescriptionUndefined, diagramDescriptionDown, diagramDescriptionRight)
                .build();

        view.eAllContents().forEachRemaining(eObject ->
                eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())))
        );

        String resourcePath = UUID.nameUUIDFromBytes("DiagramEdgeHandlePosition".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("DiagramEdgeHandlePositionView"));
        resource.getContents().add(view);

        return List.of(view);
    }

    private DiagramDescription createDiagramDescription(IColorProvider colorProvider, ArrangeLayoutDirection arrangeLayoutDirection) {
        var nodeDescription1 = this.getNodeDescription(colorProvider, "Entity1");
        var nodeDescription2 = this.getNodeDescription(colorProvider, "Entity2");
        nodeDescription1.getReusedChildNodeDescriptions().add(nodeDescription2);

        var edgeDescription1 = this.getEdgeDescription(colorProvider, nodeDescription1, nodeDescription2);


        return new DiagramBuilders()
                .newDiagramDescription()
                .id(DiagramEdgeHandlePositionDomainProvider.DOMAIN_NAME + " - simple edge handle position " + arrangeLayoutDirection.name())
                .domainType(DiagramEdgeHandlePositionDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramEdgeHandlePositionDomainProvider.DOMAIN_NAME + " diagram " + arrangeLayoutDirection.name())
                .autoLayout(false)
                .arrangeLayoutDirection(arrangeLayoutDirection)
                .nodeDescriptions(nodeDescription1, nodeDescription2)
                .edgeDescriptions(edgeDescription1)
                .build();
    }


    private NodeDescription getNodeDescription(IColorProvider colorProvider, String entityName) {
        return new DiagramBuilders()
                .newNodeDescription()
                .id(entityName)
                .domainType(DiagramEdgeHandlePositionDomainProvider.DOMAIN_NAME + "::" + entityName)
                .semanticCandidatesExpression("aql:self.eContents()")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(true)
                .userResizable(UserResizableDirection.BOTH)
                .keepAspectRatio(false)
                .defaultWidthExpression("50")
                .defaultHeightExpression("50")
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
                .build();
    }

    private EdgeDescription getEdgeDescription(IColorProvider colorProvider, DiagramElementDescription sourceDescription, DiagramElementDescription targetDescription) {
        return new DiagramBuilders()
                .newEdgeDescription()
                .id("LinkedTo Edge")
                .isDomainBasedEdge(false)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .sourceExpression("aql:self")
                .sourceDescriptions(sourceDescription)
                .targetExpression("aql:self.entity1LinkedToEntity2")
                .targetDescriptions(targetDescription)
                .centerLabelExpression("")
                .style(
                        new DiagramBuilders()
                                .newEdgeStyleDescription()
                                .fontSize(14)
                                .color(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_TRANSPARENT))
                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .borderRadius(3)
                                .borderSize(0)
                                .borderLineStyle(LineStyle.SOLID)
                                .build()
                )
                .build();
    }


}
