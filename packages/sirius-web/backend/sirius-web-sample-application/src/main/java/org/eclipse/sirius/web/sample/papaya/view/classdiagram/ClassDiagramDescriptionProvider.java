/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view.classdiagram;

import java.util.List;

import org.eclipse.sirius.components.view.DiagramPalette;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IDiagramElementDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.IRepresentationDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Used to create the description of the class diagram.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ClassDiagramDescriptionProvider implements IRepresentationDescriptionProvider {
    @Override
    public RepresentationDescription create() {
        var classDiagramDescription = ViewFactory.eINSTANCE.createDiagramDescription();
        classDiagramDescription.setDomainType("papaya_logical_architecture::Component");
        classDiagramDescription.setName("Class Diagram");
        classDiagramDescription.setTitleExpression("aql:self.name + ' class diagram'");
        classDiagramDescription.setAutoLayout(false);

        var cache = new PapayaViewCache();
        var diagramElementDescriptionProviders = List.of(
                new CDClassNodeDescriptionProvider(),
                new CDInterfaceNodeDescriptionProvider(),
                new CDPackageNodeDescriptionProvider(),
                new CDExtendsClassEdgeDescriptionProvider(),
                new CDExtendsInterfaceEdgeDescriptionProvider(),
                new CDImplementsInterfaceEdgeDescriptionProvider()
        );

        diagramElementDescriptionProviders.stream().
                map(IDiagramElementDescriptionProvider::create)
                .forEach(cache::put);
        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> diagramElementDescriptionProvider.link(classDiagramDescription, cache));

        var palette = this.palette(cache);
        classDiagramDescription.setPalette(palette);

        return classDiagramDescription;
    }


    private DiagramPalette palette(PapayaViewCache cache) {
        var diagramPalette = ViewFactory.eINSTANCE.createDiagramPalette();

        diagramPalette.getNodeTools().add(new CDCreatePackageNodeToolProvider().create(cache));

        return diagramPalette;
    }
}
