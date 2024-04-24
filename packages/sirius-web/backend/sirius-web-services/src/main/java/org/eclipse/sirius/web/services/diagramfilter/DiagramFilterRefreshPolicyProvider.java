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
package org.eclipse.sirius.web.services.diagramfilter;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyProvider;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.springframework.stereotype.Service;

/**
 * The representation refresh policy provider for the "Diagram Filter" form representation.
 *
 * @author gdaniel
 */
@Service
public class DiagramFilterRefreshPolicyProvider implements IRepresentationRefreshPolicyProvider {


    @Override
    public boolean canHandle(IRepresentationDescription representationDescription) {
        return DiagramFilterDescriptionProvider.FORM_DESCRIPTION_ID.equals(representationDescription.getId());
    }

    @Override
    public IRepresentationRefreshPolicy getRepresentationRefreshPolicy(IRepresentationDescription representationDescription) {
        return changeDescription -> {
            boolean shouldRefresh = false;
            shouldRefresh = shouldRefresh || ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
            shouldRefresh = shouldRefresh || DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE.equals(changeDescription.getKind());
            shouldRefresh = shouldRefresh || DiagramChangeKind.DIAGRAM_ELEMENT_VISIBILITY_CHANGE.equals(changeDescription.getKind());
            shouldRefresh = shouldRefresh || DiagramChangeKind.DIAGRAM_ELEMENT_COLLAPSING_STATE_CHANGE.equals(changeDescription.getKind());
            return shouldRefresh;
        };
    }

}
