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
package org.eclipse.sirius.components.compatibility.utils;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.compatibility.api.IIdOdesignElementsProvider;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.springframework.stereotype.Service;

/**
 * descriptionID for DiagramDescription & DiagramElementDescription.
 *
 * @author mcharfadi
 */
@Service
public class IdOdesignElementsProvider implements IIdOdesignElementsProvider {

    private final IIdentifierProvider identifierProvider;

    public IdOdesignElementsProvider(IIdentifierProvider identifierProvider) {
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
    }

    public EObject getDiagramDescription(EObject o) {
        if (!(o instanceof DiagramDescription)) {
            return this.getDiagramDescription(o.eContainer());
        }
        return o;
    }

    @Override
    public String getIdDiagramDescription(DiagramDescription diagramDescription) {
        String sourceId = this.identifierProvider.getIdentifier(diagramDescription);
        //String sourceElementId = this.identifierProvider.getIdentifier(diagramDescription);
        //System.out.println(diagramDescription.getName());
        //System.out.println(DIAGRAM_PATH_VIEW + AMPERSAND + SOURCE_ID + sourceId);
        return DIAGRAM_PATH_VIEW + AMPERSAND + SOURCE_ID + sourceId;
    }


    @Override
    public String getIdElementDescription(AbstractNodeMapping abstractNodeMapping) {
        String sourceElementId = this.identifierProvider.getIdentifier(abstractNodeMapping);
        EObject diagramDescription = this.getDiagramDescription(abstractNodeMapping);
        String sourceId = this.identifierProvider.getIdentifier(diagramDescription);
        //System.out.println(abstractNodeMapping.getName());
        //System.out.println(NODE_PATH_VIEW + AMPERSAND + SOURCE_ID + sourceId + AMPERSAND + SOURCE_ELEMENT_ID + sourceElementId);
        return NODE_PATH_VIEW + AMPERSAND + SOURCE_ID + sourceId + AMPERSAND + SOURCE_ELEMENT_ID + sourceElementId;
    }

    @Override
    public String getIdEdgeMapping(EdgeMapping edgeMapping) {
        String sourceElementId = this.identifierProvider.getIdentifier(edgeMapping);
        EObject diagramDescription = this.getDiagramDescription(edgeMapping);
        String sourceId = this.identifierProvider.getIdentifier(diagramDescription);
        //System.out.println(edgeMapping.getName());
        //System.out.println(NODE_PATH_VIEW + AMPERSAND + SOURCE_ID + sourceId + AMPERSAND + SOURCE_ELEMENT_ID + sourceElementId);
        return EDGE_PATH_VIEW + AMPERSAND + SOURCE_ID + sourceId + AMPERSAND + SOURCE_ELEMENT_ID + sourceElementId;
    }


}
