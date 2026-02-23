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

package org.eclipse.sirius.web.e2e.tests.diagramResize;

import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeDescriptionBuilder;
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
 * The view provider for the diagram-resize test suite.
 *
 * @author mcharfadi
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramResizeViewProvider implements IE2EViewProvider {

    @Override
    public List<View> createViews() {
        var colorPalette = new SiriusWebE2EColorPaletteBuilderProvider().getColorPaletteBuilder().build();
        IColorProvider colorProvider = new SiriusWebE2EColorProvider(List.of(colorPalette));

        var fullyDisplayInsideLabelDiagramDescription = this.diagramDescription(colorProvider);

        var view = new ViewBuilders().newView()
                .colorPalettes(colorPalette)
                .descriptions(
                        fullyDisplayInsideLabelDiagramDescription
                )
                .build();

        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("DiagramResizeView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("DiagramResizeView"));
        resource.getContents().add(view);

        return List.of(view);
    }

    private DiagramDescription diagramDescription(IColorProvider colorProvider) {
        var toolbar = new DiagramBuilders().newDiagramToolbar()
            .expandedByDefault(true)
            .build();

        return new DiagramBuilders()
                .newDiagramDescription()
                .name(DiagramResizeDomainProvider.DOMAIN_NAME + " - simple resize node")
                .domainType(DiagramResizeDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramResizeDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .nodeDescriptions(this.getNodeDescription(colorProvider, "Entity1 - Resize Both", "::Entity1", UserResizableDirection.BOTH),
                        this.getNodeDescription(colorProvider, "Entity2 - Resize NONE", "::Entity2", UserResizableDirection.NONE),
                        this.getNodeDescription(colorProvider, "Entity3 - Resize HORIZONTAL", "::Entity3", UserResizableDirection.HORIZONTAL),
                        this.getNodeDescription(colorProvider, "Entity4 - Resize VERTICAL", "::Entity4", UserResizableDirection.VERTICAL),
                        this.getNodeDescriptionWithDefaultSize(colorProvider, "Entity5 - Resize Both - default size: (200, 100)", "::Entity5", UserResizableDirection.BOTH, "200", "100"))
                .toolbar(toolbar)
                .build();
    }


    private NodeDescription getNodeDescription(IColorProvider colorProvider, String name, String domain, UserResizableDirection resizeDirection) {
        return this.getDefaultNodeDescriptionBuilder(colorProvider, name, domain, resizeDirection)
                .build();
    }

    private NodeDescription getNodeDescriptionWithDefaultSize(IColorProvider colorProvider, String name, String domain, UserResizableDirection resizeDirection, String defaultWidth, String defaultHeight) {
        return this.getDefaultNodeDescriptionBuilder(colorProvider, name, domain, resizeDirection)
                .defaultWidthExpression(defaultWidth)
                .defaultHeightExpression(defaultHeight)
                .build();
    }

    private NodeDescriptionBuilder getDefaultNodeDescriptionBuilder(IColorProvider colorProvider, String name, String domain, UserResizableDirection resizeDirection) {
        return new DiagramBuilders()
                .newNodeDescription()
                .name(name)
                .domainType(DiagramResizeDomainProvider.DOMAIN_NAME + domain)
                .semanticCandidatesExpression("aql:self.eContents()")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(true)
                .userResizable(resizeDirection)
                .keepAspectRatio(false)
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
                );
    }

}
