/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.web.application.representation.services;

import java.util.Set;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.web.application.views.details.services.PropertiesEventProcessorFactory;
import org.springframework.stereotype.Service;

/**
 * The representation refresh policy provider for the representation metadata properties page.
 *
 * @author Jerome Gout
 */
@Service
public class RepresentationMetadataDetailsViewRefreshPolicyProvider implements IRepresentationRefreshPolicyProvider {

    private static final Set<String> IMPACTING_CHANGES = Set.of(
            ChangeKind.REPRESENTATION_CREATION,
            ChangeKind.REPRESENTATION_DELETION,
            ChangeKind.REPRESENTATION_RENAMING,
            ChangeKind.REPRESENTATION_TO_DELETE,
            ChangeKind.REPRESENTATION_METADATA_UPDATE,
            ChangeKind.SEMANTIC_CHANGE);


    @Override
    public boolean canHandle(IRepresentationDescription representationDescription) {
        return PropertiesEventProcessorFactory.DETAILS_VIEW_ID.equals(representationDescription.getId());
    }

    @Override
    public IRepresentationRefreshPolicy getRepresentationRefreshPolicy(IRepresentationDescription representationDescription) {
        return changeDescription -> IMPACTING_CHANGES.contains(changeDescription.getKind());
    }
}
