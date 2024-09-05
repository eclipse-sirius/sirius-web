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
package org.eclipse.sirius.web.application.studio.services.representations;

import java.util.List;

import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.springframework.stereotype.Service;

/**
 * Used to provide the services of both domain diagram and explorer.
 *
 * @author sbegaudeau
 */
@Service
public class DomainServiceProvider implements IJavaServiceProvider {
    @Override
    public List<Class<?>> getServiceClasses(View view) {
        List<Class<?>> result = List.of();

        boolean isDomainDiagramView = view.getDescriptions().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .anyMatch(diagramDescription -> diagramDescription.getDomainType().equals("domain::Domain"));

        boolean isDomainTreeView = view.getDescriptions().stream()
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast)
                .anyMatch(treeDescription -> treeDescription.getDomainType().equals("domain::Domain"));

        if (isDomainDiagramView) {
            result = List.of(DomainDiagramServices.class);
        } else if (isDomainTreeView) {
            result = List.of(DomainExplorerServices.class);
        }

        return result;
    }
}
