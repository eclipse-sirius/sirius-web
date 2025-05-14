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

package org.eclipse.sirius.web.e2e.tests.diagramlist;

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
 * The view provider for the diagram-list.cy.ts test suite.
 *
 * @author frouene
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramListViewProvider implements IE2EViewProvider {

    @Override
    public List<View> createViews() {
        var colorPalette = new SiriusWebE2EColorPaletteBuilderProvider().getColorPaletteBuilder().build();
        IColorProvider colorProvider = new SiriusWebE2EColorProvider(List.of(colorPalette));

        var fullyDisplayInsideLabelDiagramDescription = this.fullyDisplayInsideLabelDiagramDescription(colorProvider);

        var view = new ViewBuilders().newView()
                .colorPalettes(colorPalette)
                .descriptions(
                        fullyDisplayInsideLabelDiagramDescription
                )
                .build();

        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("DiagramListView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("DiagramListView"));
        resource.getContents().add(view);

        return List.of(view);
    }

    private DiagramDescription fullyDisplayInsideLabelDiagramDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newDiagramDescription()
                .name(DiagramListDomainProvider.DOMAIN_NAME + " - simple list node")
                .domainType(DiagramListDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramListDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .nodeDescriptions(this.getNodeDescription(colorProvider))
                .build();
    }


    private NodeDescription getNodeDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newNodeDescription()
                .name("Entity Node 1")
                .domainType(DiagramListDomainProvider.DOMAIN_NAME + "::Entity1")
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
                .childrenDescriptions(this.getChildrenListDescription(colorProvider), this.getChildrenFreeFormDescription(colorProvider))
                .build();
    }

    private NodeDescription getChildrenListDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newNodeDescription()
                .name("CompartmentList")
                .semanticCandidatesExpression("aql:self")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(false)
                .userResizable(UserResizableDirection.BOTH)
                .keepAspectRatio(false)
                .style(
                        new DiagramBuilders()
                                .newRectangularNodeStyleDescription()
                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_TRANSPARENT))
                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .borderRadius(0)
                                .borderSize(1)
                                .borderLineStyle(LineStyle.SOLID)
                                .childrenLayoutStrategy(new DiagramBuilders().newListLayoutStrategyDescription().build())
                                .build()
                )
                .childrenDescriptions(this.getIconListDescription(colorProvider))
                .build();
    }

    private NodeDescription getIconListDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newNodeDescription()
                .name("Compartment")
                .semanticCandidatesExpression("aql:self")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(false)
                .userResizable(UserResizableDirection.BOTH)
                .keepAspectRatio(false)
                .style(
                        new DiagramBuilders()
                                .newIconLabelNodeStyleDescription()
                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_TRANSPARENT))
                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .borderRadius(0)
                                .borderSize(0)
                                .borderLineStyle(LineStyle.SOLID)
                                .build()
                )
                .insideLabel(
                        new DiagramBuilders()
                                .newInsideLabelDescription()
                                .labelExpression("aql:self.name")
                                .textAlign(LabelTextAlign.LEFT)
                                .position(InsideLabelPosition.TOP_LEFT)
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
                                                .withHeader(false)
                                                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.NEVER)
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private NodeDescription getChildrenFreeFormDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newNodeDescription()
                .name("CompartmentFreeForm")
                .semanticCandidatesExpression("aql:self")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(false)
                .userResizable(UserResizableDirection.BOTH)
                .keepAspectRatio(false)
                .style(
                        new DiagramBuilders()
                                .newRectangularNodeStyleDescription()
                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_TRANSPARENT))
                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .borderRadius(0)
                                .borderSize(1)
                                .borderLineStyle(LineStyle.SOLID)
                                .childrenLayoutStrategy(new DiagramBuilders().newFreeFormLayoutStrategyDescription().build())
                                .build()
                )
                .childrenDescriptions(this.getSimpleRectangularDescription(colorProvider))
                .build();
    }

    private NodeDescription getSimpleRectangularDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newNodeDescription()
                .name("Simple rectangular")
                .semanticCandidatesExpression("aql:self")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(false)
                .userResizable(UserResizableDirection.NONE)
                .keepAspectRatio(false)
                .defaultHeightExpression("20")
                .defaultWidthExpression("20")
                .style(
                        new DiagramBuilders()
                                .newRectangularNodeStyleDescription()
                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_GREEN))
                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_DARK))
                                .borderRadius(0)
                                .borderSize(1)
                                .borderLineStyle(LineStyle.SOLID)
                                .build()
                )
                .build();
    }

}
