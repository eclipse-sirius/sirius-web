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

package org.eclipse.sirius.components.view.emf.diagram;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.api.IMinimapVisibilityProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.springframework.stereotype.Service;

/**
 * Service providing the diagram minimapVisible value for DiagramDescription based on the View DSL.
 *
 * @author fbarbin
 */
@Service
public class ViewMinimapVisibilityProvider implements IMinimapVisibilityProvider {

    private final IObjectSearchService objectSearchService;

    private final ViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    public ViewMinimapVisibilityProvider(IObjectSearchService objectSearchService, ViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public boolean isMinimapVisible(IEditingContext editingContext, DiagramDescription diagramDescription, String targetObjectId) {
        var defaultValue = true;
        var optionalTargetElement = this.objectSearchService.getObject(editingContext, targetObjectId);

        if (optionalTargetElement.isPresent()) {
            var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
            if (optionalDiagramDescription.isPresent()) {
                org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
                defaultValue = viewDiagramDescription.isMinimapVisible();
            }
        }
        return defaultValue;
    }
}
