/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
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
 * The view provider for the diagram-label.cy.ts test suite.
 *
 * @author gcoutable
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramLabelViewProvider implements IE2EViewProvider {

    @Override
    public List<View> createViews() {
        var colorPalette = new SiriusWebE2EColorPaletteBuilderProvider().getColorPaletteBuilder().build();
        IColorProvider colorProvider = new SiriusWebE2EColorProvider(List.of(colorPalette));

        var fullyDisplayInsideLabelDiagramDescription = this.fullyDisplayInsideLabelDiagramDescription(colorProvider);
        var wrapLabelWithoutChangingNodeWidthDiagramDescription = this.wrapLabelWithoutChangingNodeWidthDiagramDescription(colorProvider);
        var labelIsTruncatedWithoutChangingNodeWidthDiagramDescription = this.labelIsTruncatedWithoutChangingNodeWidthDiagramDescription(colorProvider);

        var view = new ViewBuilders().newView()
                .colorPalettes(colorPalette)
                .descriptions(
                        fullyDisplayInsideLabelDiagramDescription,
                        wrapLabelWithoutChangingNodeWidthDiagramDescription,
                        labelIsTruncatedWithoutChangingNodeWidthDiagramDescription
                )
                .build();

        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("DiagramLabelView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("DiagramLabelView"));
        resource.getContents().add(view);

        return List.of(view);
    }

    private DiagramDescription fullyDisplayInsideLabelDiagramDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newDiagramDescription()
                .id(DiagramLabelDomainProvider.DOMAIN_NAME + " - Fully display the inside label Diagram")
                .domainType(DiagramLabelDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramLabelDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .palette(this.getDiagramPalette())
                .nodeDescriptions(this.getNodeDescription(colorProvider, LabelOverflowStrategy.NONE))
                .build();
    }

    private DiagramDescription wrapLabelWithoutChangingNodeWidthDiagramDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newDiagramDescription()
                .id(DiagramLabelDomainProvider.DOMAIN_NAME + " - Wrap the label without changing the node width")
                .domainType(DiagramLabelDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramLabelDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .palette(this.getDiagramPalette())
                .nodeDescriptions(this.getNodeDescription(colorProvider, LabelOverflowStrategy.WRAP))
                .build();
    }

    private DiagramDescription labelIsTruncatedWithoutChangingNodeWidthDiagramDescription(IColorProvider colorProvider) {
        return new DiagramBuilders()
                .newDiagramDescription()
                .id(DiagramLabelDomainProvider.DOMAIN_NAME + " - The label is truncated without changing the node width")
                .domainType(DiagramLabelDomainProvider.DOMAIN_NAME + "::Root")
                .titleExpression(DiagramLabelDomainProvider.DOMAIN_NAME + " diagram")
                .autoLayout(false)
                .arrangeLayoutDirection(ArrangeLayoutDirection.UNDEFINED)
                .palette(this.getDiagramPalette())
                .nodeDescriptions(this.getNodeDescription(colorProvider, LabelOverflowStrategy.ELLIPSIS))
                .build();
    }

    private NodeDescription getNodeDescription(IColorProvider colorProvider, LabelOverflowStrategy labelOverflowStrategy) {
        return new DiagramBuilders()
                .newNodeDescription()
                .id("Entity Node 1")
                .domainType(DiagramLabelDomainProvider.DOMAIN_NAME + "::Entity1")
                .semanticCandidatesExpression("aql:self.eContents()")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .collapsible(false)
                .userResizable(UserResizableDirection.BOTH)
                .keepAspectRatio(false)
                .palette(
                        new DiagramBuilders()
                                .newNodePalette()
                                .labelEditTool(
                                        new DiagramBuilders()
                                                .newLabelEditTool()
                                                .name("Edit Label")
                                                .body(
                                                        new ViewBuilders()
                                                                .newChangeContext()
                                                                .expression("aql:self.defaultEditLabel(newLabel)")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
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
                                .overflowStrategy(labelOverflowStrategy)
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
                                                .withHeader(false)
                                                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.ALWAYS)
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private DiagramPalette getDiagramPalette() {
        var newEntity1 = new DiagramBuilders()
                .newNodeTool()
                .name("New Entity 1")
                .body(
                        new ViewBuilders()
                                .newCreateInstance()
                                .typeName(DiagramLabelDomainProvider.DOMAIN_NAME + "::Entity1")
                                .referenceName("entity1s")
                                .variableName("newInstance")
                                .children(
                                        new ViewBuilders()
                                                .newChangeContext()
                                                .expression("aql:newInstance")
                                                .children(
                                                        new ViewBuilders()
                                                                .newSetValue()
                                                                .featureName("name")
                                                                .valueExpression("NewEntity1")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        return new DiagramBuilders()
                .newDiagramPalette()
                .nodeTools(
                        newEntity1
                )
                .build();
    }

}
