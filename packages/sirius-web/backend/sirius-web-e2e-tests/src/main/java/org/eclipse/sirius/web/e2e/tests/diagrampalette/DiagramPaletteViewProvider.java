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

package org.eclipse.sirius.web.e2e.tests.diagrampalette;

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
import org.eclipse.sirius.components.view.diagram.DiagramLayoutOption;
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
 * The view provider for the diagram palette test suite.
 *
 * @author frouene
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramPaletteViewProvider implements IE2EViewProvider {

    @Override
    public List<View> createViews() {
        var colorPalette = new SiriusWebE2EColorPaletteBuilderProvider().getColorPaletteBuilder().build();
        IColorProvider colorProvider = new SiriusWebE2EColorProvider(List.of(colorPalette));

        var diagramDescription = this.getDiagramDescription(colorProvider);

        var view = new ViewBuilders().newView()
                .colorPalettes(colorPalette)
                .descriptions(
                        diagramDescription
                )
                .build();

        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("diagramPaletteView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("diagramPaletteView"));
        resource.getContents().add(view);

        return List.of(view);
    }

    private DiagramDescription getDiagramDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newDiagramDescription()
                .name(DiagramPaletteDomainProvider.DOMAIN_NAME + " - palette")
                .domainType(DiagramPaletteDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramPaletteDomainProvider.DOMAIN_NAME + " diagram")
                .layoutOption(DiagramLayoutOption.NONE)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .nodeDescriptions(this.getNodeDescription(colorProvider))
                .palette(new DiagramBuilders().newDiagramPalette()
                        .toolSections(new DiagramBuilders().newDiagramToolSection()
                                        .name("DiagramToolSection")
                                        .nodeTools(new DiagramBuilders().newNodeTool()
                                                .name("NodeTool")
                                                .build())
                                        .build(),
                                new DiagramBuilders().newDiagramToolSection()
                                        .name("EmptyToolSection")
                                        .build()
                        )
                        .build())
                .build();
    }


    private NodeDescription getNodeDescription(IColorProvider colorProvider) {

        return new DiagramBuilders()
                .newNodeDescription()
                .name("Entity Node 1")
                .domainType(DiagramPaletteDomainProvider.DOMAIN_NAME + "::Entity1")
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
                .palette(new DiagramBuilders().newNodePalette()
                        .toolSections(new DiagramBuilders().newNodeToolSection()
                                .name("NodeToolSection")
                                .nodeTools(new DiagramBuilders().newNodeTool()
                                        .name("NodeTool")
                                        .preconditionExpression("aql:self.name!='Empty'")
                                        .build())
                                .build())
                        .build())
                .build();
    }

}
