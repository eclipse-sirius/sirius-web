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

package org.eclipse.sirius.web.e2e.tests.diagramlabel;

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
 * The view provider for the label alignment test suite.
 *
 * @author frouene
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramLabelAlignmentViewProvider implements IE2EViewProvider {

    @Override
    public List<View> createViews() {
        var colorPalette = new SiriusWebE2EColorPaletteBuilderProvider().getColorPaletteBuilder().build();
        IColorProvider colorProvider = new SiriusWebE2EColorProvider(List.of(colorPalette));

        var diagramDescriptionWithOverflowNone = this.createDiagramDescriptionWithOverflowNone(colorProvider);
        var diagramDescriptionWithOverflowWrap = this.createDiagramDescriptionWithOverflowWrap(colorProvider);
        var diagramDescriptionWithOverflowEllipse = this.createDiagramDescriptionWithOverflowEllipse(colorProvider);

        var view = new ViewBuilders().newView()
                .colorPalettes(colorPalette)
                .descriptions(diagramDescriptionWithOverflowNone, diagramDescriptionWithOverflowWrap, diagramDescriptionWithOverflowEllipse)
                .build();

        view.eAllContents().forEachRemaining(eObject -> eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes()))));

        String resourcePath = UUID.nameUUIDFromBytes("DiagramLabelAlignmentView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("DiagramLabelAlignmentView"));
        resource.getContents().add(view);

        return List.of(view);
    }

    private DiagramDescription createDiagramDescriptionWithOverflowNone(IColorProvider colorProvider) {
        var toolbar = new DiagramBuilders().newDiagramToolbar()
                .expandedByDefault(true)
                .build();

        return new DiagramBuilders()
                .newDiagramDescription()
                .name(DiagramLabelDomainProvider.DOMAIN_NAME + " - inside label alignment with overflow none Diagram")
                .domainType(DiagramLabelDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramLabelDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .nodeDescriptions(this.getNodeDescription(colorProvider, InsideLabelPosition.TOP_LEFT, LabelOverflowStrategy.NONE),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.TOP_CENTER, LabelOverflowStrategy.NONE),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.TOP_RIGHT, LabelOverflowStrategy.NONE),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.MIDDLE_LEFT, LabelOverflowStrategy.NONE),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.MIDDLE_CENTER, LabelOverflowStrategy.NONE),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.MIDDLE_RIGHT, LabelOverflowStrategy.NONE),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.BOTTOM_LEFT, LabelOverflowStrategy.NONE),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.BOTTOM_CENTER, LabelOverflowStrategy.NONE),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.BOTTOM_RIGHT, LabelOverflowStrategy.NONE))
                .toolbar(toolbar)
                .style(new DiagramBuilders().newDiagramStyleDescription().build())
                .build();
    }

    private DiagramDescription createDiagramDescriptionWithOverflowWrap(IColorProvider colorProvider) {
        var toolbar = new DiagramBuilders().newDiagramToolbar()
                .expandedByDefault(true)
                .build();

        return new DiagramBuilders()
                .newDiagramDescription()
                .name(DiagramLabelDomainProvider.DOMAIN_NAME + " - inside label alignment with overflow wrap Diagram")
                .domainType(DiagramLabelDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramLabelDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .nodeDescriptions(this.getNodeDescription(colorProvider, InsideLabelPosition.TOP_LEFT, LabelOverflowStrategy.WRAP),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.TOP_CENTER, LabelOverflowStrategy.WRAP),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.TOP_RIGHT, LabelOverflowStrategy.WRAP),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.MIDDLE_LEFT, LabelOverflowStrategy.WRAP),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.MIDDLE_CENTER, LabelOverflowStrategy.WRAP),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.MIDDLE_RIGHT, LabelOverflowStrategy.WRAP),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.BOTTOM_LEFT, LabelOverflowStrategy.WRAP),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.BOTTOM_CENTER, LabelOverflowStrategy.WRAP),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.BOTTOM_RIGHT, LabelOverflowStrategy.WRAP))
                .toolbar(toolbar)
                .style(new DiagramBuilders().newDiagramStyleDescription().build())
                .build();
    }

    private DiagramDescription createDiagramDescriptionWithOverflowEllipse(IColorProvider colorProvider) {
        var toolbar = new DiagramBuilders().newDiagramToolbar()
                .expandedByDefault(true)
                .build();

        return new DiagramBuilders()
                .newDiagramDescription()
                .name(DiagramLabelDomainProvider.DOMAIN_NAME + " - inside label alignment with overflow ellipse Diagram")
                .domainType(DiagramLabelDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramLabelDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .nodeDescriptions(this.getNodeDescription(colorProvider, InsideLabelPosition.TOP_LEFT, LabelOverflowStrategy.ELLIPSIS),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.TOP_CENTER, LabelOverflowStrategy.ELLIPSIS),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.TOP_RIGHT, LabelOverflowStrategy.ELLIPSIS),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.MIDDLE_LEFT, LabelOverflowStrategy.ELLIPSIS),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.MIDDLE_CENTER, LabelOverflowStrategy.ELLIPSIS),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.MIDDLE_RIGHT, LabelOverflowStrategy.ELLIPSIS),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.BOTTOM_LEFT, LabelOverflowStrategy.ELLIPSIS),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.BOTTOM_CENTER, LabelOverflowStrategy.ELLIPSIS),
                        this.getNodeDescription(colorProvider, InsideLabelPosition.BOTTOM_RIGHT, LabelOverflowStrategy.ELLIPSIS))
                .toolbar(toolbar)
                .style(new DiagramBuilders().newDiagramStyleDescription().build())
                .build();
    }

    private NodeDescription getNodeDescription(IColorProvider colorProvider, InsideLabelPosition insideLabelPosition, LabelOverflowStrategy labelOverflowStrategy) {
        return new DiagramBuilders()
                .newNodeDescription()
                .name("Entity Node " + insideLabelPosition)
                .domainType(DiagramLabelDomainProvider.DOMAIN_NAME + "::Entity1")
                .semanticCandidatesExpression("aql:self.eContents()")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(false)
                .userResizable(UserResizableDirection.BOTH)
                .keepAspectRatio(false)
                .defaultHeightExpression("400")
                .defaultWidthExpression("400")
                .style(
                        new DiagramBuilders()
                                .newRectangularNodeStyleDescription()
                                .background(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.COLOR_BLUE))
                                .borderColor(colorProvider.getColor(SiriusWebE2EColorPaletteBuilderProvider.BORDER_BLUE))
                                .borderRadius(3)
                                .borderSize(1)
                                .borderLineStyle(LineStyle.SOLID)
                                .build()
                )
                .insideLabel(
                        new DiagramBuilders()
                                .newInsideLabelDescription()
                                .labelExpression(insideLabelPosition.getLiteral())
                                .overflowStrategy(labelOverflowStrategy)
                                .textAlign(LabelTextAlign.LEFT)
                                .position(insideLabelPosition)
                                .style(
                                        new DiagramBuilders()
                                                .newInsideLabelStyle()
                                                .fontSize(10)
                                                .italic(false)
                                                .bold(false)
                                                .underline(false)
                                                .strikeThrough(false)
                                                .maxWidthExpression("75px")
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
}
