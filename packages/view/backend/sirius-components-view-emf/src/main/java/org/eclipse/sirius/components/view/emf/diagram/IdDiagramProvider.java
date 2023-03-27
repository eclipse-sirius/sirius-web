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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.springframework.stereotype.Service;

/**
 * descriptionID for DiagramDescription & DiagramElementDescription.
 *
 * @author mcharfadi
 */
@Service
public class IdDiagramProvider implements IIdDiagramProvider {
    private IObjectService objectService;

    public IdDiagramProvider(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }


    @Override
    public String getIdDiagramDescription(DiagramDescription diagramDescription) {
        String sourceId = this.getSourceIdFromElementDescription(diagramDescription);
        String sourceElementId = this.objectService.getId(diagramDescription);
        return DIAGRAM_PATH_VIEW + AMPERSAND + SOURCE_ID + sourceId + AMPERSAND + SOURCE_ELEMENT_ID + sourceElementId;
    }

    @Override
    public String getIdElementDescription(DiagramElementDescription diagramElementDescription) {
        String sourceId = this.getSourceIdFromElementDescription(diagramElementDescription);
        String sourceElementId = this.objectService.getId(diagramElementDescription);
        if (diagramElementDescription instanceof org.eclipse.sirius.components.view.NodeDescription) {
            return NODE_PATH_VIEW + AMPERSAND + SOURCE_ID + sourceId + AMPERSAND + SOURCE_ELEMENT_ID + sourceElementId;
        }
        else {
            return EDGE_PATH_VIEW + AMPERSAND + SOURCE_ID + sourceId + AMPERSAND + SOURCE_ELEMENT_ID + sourceElementId;
        }
    }

    private String getSourceIdFromElementDescription(EObject elementDescription) {
        return elementDescription.eResource().getURI().toString().split("///")[1];
    }

}
