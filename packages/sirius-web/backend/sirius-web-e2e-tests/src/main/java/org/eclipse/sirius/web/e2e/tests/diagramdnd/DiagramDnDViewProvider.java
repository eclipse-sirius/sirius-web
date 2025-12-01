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

package org.eclipse.sirius.web.e2e.tests.diagramdnd;

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
 * The view provider for "integration-tests-playwright/playwright/e2e/dnd.spec.ts".
 *
 * @author frouene
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramDnDViewProvider implements IE2EViewProvider {

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

        view.eAllContents().forEachRemaining(eObject -> eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes()))));

        String resourcePath = UUID.nameUUIDFromBytes("DiagramDnDView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("DiagramDnDView"));
        resource.getContents().add(view);

        return List.of(view);
    }

    private DiagramDescription getDiagramDescription(IColorProvider colorProvider) {
        var entity2NodeDescription = this.getEntitySimpleNodeDescription(colorProvider, "Entity2");
        var entity3NodeDescription = this.getEntitySimpleNodeDescription(colorProvider, "Entity3");
        var entity1NodeDescription = this.getEntity1NodeDescription(colorProvider, entity2NodeDescription, entity3NodeDescription);

        var dropEntity2Tool = new DiagramBuilders()
                .newDropNodeTool()
                .name("dropEntity2Tool")
                .acceptedNodeTypes(entity2NodeDescription)
                .body(new ViewBuilders()
                                .newChangeContext()
                                .expression("aql:targetElement")
                                .children(new ViewBuilders()
                                        .newCreateInstance()
                                        .typeName(DiagramDnDDomainProvider.DOMAIN_NAME + "::Entity2")
                                        .referenceName("entity2sOnRoot")
                                        .variableName("newInstance")
                                        .children(new ViewBuilders()
                                                .newChangeContext()
                                                .expression("aql:newInstance")
                                                .children(new ViewBuilders()
                                                        .newSetValue()
                                                        .featureName("name")
                                                        .valueExpression("dropPerformed")
                                                        .build())
                                                .build())
                                        .build())
                                .build(),
                        new ViewBuilders()
                                .newChangeContext()
                                .expression("aql:droppedElements->first()")
                                .children(new ViewBuilders()
                                        .newDeleteElement()
                                        .build())
                                .build())
                .build();

        return new DiagramBuilders()
                .newDiagramDescription()
                .name(DiagramDnDDomainProvider.DOMAIN_NAME + " - simple dnd view")
                .domainType(DiagramDnDDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramDnDDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .nodeDescriptions(entity1NodeDescription, entity2NodeDescription, entity3NodeDescription)
                .palette(new DiagramBuilders()
                        .newDiagramPalette()
                        .dropNodeTool(dropEntity2Tool)
                        .build())
                .build();
    }


    private NodeDescription getEntity1NodeDescription(IColorProvider colorProvider, NodeDescription entity2NodeDescription, NodeDescription entity3NodeDescription) {
        var dropEntity3Tool = new DiagramBuilders()
                .newDropNodeTool()
                .name("dropEntity3Tool")
                .acceptedNodeTypes(entity3NodeDescription)
                .body(new ViewBuilders()
                                .newChangeContext()
                                .expression("aql:targetElement")
                                .children(new ViewBuilders()
                                        .newCreateInstance()
                                        .typeName(DiagramDnDDomainProvider.DOMAIN_NAME + "::Entity3")
                                        .referenceName("entity3sOnEntity1")
                                        .variableName("newInstance")
                                        .children(new ViewBuilders()
                                                .newChangeContext()
                                                .expression("aql:newInstance")
                                                .children(new ViewBuilders()
                                                        .newSetValue()
                                                        .featureName("name")
                                                        .valueExpression("dropped")
                                                        .build())
                                                .build())
                                        .build())
                                .build(),
                        new ViewBuilders()
                                .newChangeContext()
                                .expression("aql:droppedElements->first()")
                                .children(new ViewBuilders()
                                        .newDeleteElement()
                                        .build())
                                .build())
                .build();

        return new DiagramBuilders()
                .newNodeDescription()
                .name("Entity Node 1")
                .domainType(DiagramDnDDomainProvider.DOMAIN_NAME + "::Entity1")
                .semanticCandidatesExpression("aql:self.eContents()")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(true)
                .userResizable(UserResizableDirection.BOTH)
                .keepAspectRatio(false)
                .reusedChildNodeDescriptions(entity2NodeDescription, entity3NodeDescription)
                .palette(new DiagramBuilders()
                        .newNodePalette()
                        .dropNodeTool(dropEntity3Tool)
                        .build())
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

    private NodeDescription getEntitySimpleNodeDescription(IColorProvider colorProvider, String domainType) {
        return new DiagramBuilders()
                .newNodeDescription()
                .name("Simple rectangular" + domainType)
                .domainType(DiagramDnDDomainProvider.DOMAIN_NAME + "::" + domainType)
                .semanticCandidatesExpression("aql:self.eContents()")
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
