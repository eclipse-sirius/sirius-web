/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

package org.eclipse.sirius.web.e2e.tests.impactanalysis;

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
 * The view provider for the diagram-impact-analysis.cy.ts test suite.
 *
 * @author frouene
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramImpactAnalysisViewProvider implements IE2EViewProvider {

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

        String resourcePath = UUID.nameUUIDFromBytes("DiagramImpactAnalysisView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("DiagramImpactAnalysisView"));
        resource.getContents().add(view);

        return List.of(view);
    }

    private DiagramDescription fullyDisplayInsideLabelDiagramDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newDiagramDescription()
                .name(DiagramImpactAnalysisDomainProvider.DOMAIN_NAME + " - simple node")
                .domainType(DiagramImpactAnalysisDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramImpactAnalysisDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .nodeDescriptions(this.getNodeDescription(colorProvider))
                .build();
    }


    private NodeDescription getNodeDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newNodeDescription()
                .name("Entity Node 1")
                .domainType(DiagramImpactAnalysisDomainProvider.DOMAIN_NAME + "::Entity1")
                .semanticCandidatesExpression("aql:self.eContents()")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(true)
                .userResizable(UserResizableDirection.BOTH)
                .keepAspectRatio(false)
                .childrenLayoutStrategy(new DiagramBuilders().newFreeFormLayoutStrategyDescription().build())
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
                .palette(
                        new DiagramBuilders()
                                .newNodePalette()
                                .nodeTools(
                                        new DiagramBuilders()
                                                .newNodeTool()
                                                .name("Tool with impact analysis")
                                                .withImpactAnalysis(true)
                                                .body(
                                                        new ViewBuilders()
                                                                .newChangeContext()
                                                                .expression("aql:self")
                                                                .children(
                                                                        new ViewBuilders()
                                                                                .newSetValue()
                                                                                .featureName("name")
                                                                                .valueExpression("newName")
                                                                                .build()
                                                                )
                                                                .build(),
                                                        new ViewBuilders()
                                                                .newChangeContext()
                                                                .expression("aql:self.eContainer()")
                                                                .children(
                                                                        new ViewBuilders()
                                                                                .newCreateInstance()
                                                                                .typeName(DiagramImpactAnalysisDomainProvider.DOMAIN_NAME + "::Entity1")
                                                                                .referenceName("entity1s")
                                                                                .build()
                                                                )
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
    }

}
