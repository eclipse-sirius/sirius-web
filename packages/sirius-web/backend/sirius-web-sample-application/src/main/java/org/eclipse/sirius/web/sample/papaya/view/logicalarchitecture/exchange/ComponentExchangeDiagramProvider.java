/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.exchange;

import java.util.List;

import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IDiagramElementDescriptionProvider;
import org.eclipse.sirius.components.view.builder.providers.IRepresentationDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.web.sample.papaya.view.ViewDiagramElementFinder;
import org.eclipse.sirius.web.sample.papaya.view.classdiagram.CDCreatePackageNodeToolProvider;


/**
 * Used to create the description of the component exchange diagram.
 *
 * @author mcharfadi
 */
public class ComponentExchangeDiagramProvider implements IRepresentationDescriptionProvider {

    @Override
    public DiagramDescription create(IColorProvider colorProvider) {
        var componentExchangeDiagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        componentExchangeDiagramDescription.setDomainType("papaya_core::Root");
        componentExchangeDiagramDescription.setName("Component Exchange Diagram");
        componentExchangeDiagramDescription.setTitleExpression("aql:'Component Exchange Diagram'");
        componentExchangeDiagramDescription.setAutoLayout(false);

        var cache = new ViewDiagramElementFinder();
        var diagramElementDescriptionProviders = List.of(
                new CEDComponentNodeProvider(colorProvider),
                new CEDComponentPortNodeProvider(colorProvider),
                new CEDComponentExchangeEdgeProvider(colorProvider)
        );

        diagramElementDescriptionProviders.stream().
                map(IDiagramElementDescriptionProvider::create)
                .forEach(cache::put);
        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> diagramElementDescriptionProvider.link(componentExchangeDiagramDescription, cache));

        var palette = this.palette(cache);
        componentExchangeDiagramDescription.setPalette(palette);

        return componentExchangeDiagramDescription;
    }

    private DiagramPalette palette(ViewDiagramElementFinder cache) {
        var diagramPalette = DiagramFactory.eINSTANCE.createDiagramPalette();

        diagramPalette.getNodeTools().add(new CDCreatePackageNodeToolProvider().create(cache));

        return diagramPalette;
    }

}
