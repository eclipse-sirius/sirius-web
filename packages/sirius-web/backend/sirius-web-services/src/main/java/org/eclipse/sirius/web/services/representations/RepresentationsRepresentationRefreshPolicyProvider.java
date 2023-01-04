/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.web.services.representations;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyProvider;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.springframework.stereotype.Service;

/**
 * The representation refresh policy provider for the representation representations.
 *
 * @author gcoutable
 */
@Service
public class RepresentationsRepresentationRefreshPolicyProvider implements IRepresentationRefreshPolicyProvider {

    public RepresentationsRepresentationRefreshPolicyProvider(IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        representationRefreshPolicyRegistry.add(this);
    }

    @Override
    public boolean canHandle(IRepresentationDescription representationDescription) {
        return UUID.nameUUIDFromBytes(RepresentationsDescriptionProvider.REPRESENTATIONS_DEFAULT_FORM_DESCRIPTION_ID.getBytes()).toString().equals(representationDescription.getId());
    }

    @Override
    public IRepresentationRefreshPolicy getRepresentationRefreshPolicy(IRepresentationDescription representationDescription) {
        return (changeDescription) -> {
            boolean shouldRefresh = false;

            switch (changeDescription.getKind()) {
                case ChangeKind.REPRESENTATION_CREATION:
                    shouldRefresh = true;
                    break;
                case ChangeKind.REPRESENTATION_DELETION:
                    shouldRefresh = true;
                    break;
                case ChangeKind.REPRESENTATION_RENAMING:
                    shouldRefresh = true;
                    break;
                case ChangeKind.REPRESENTATION_TO_DELETE:
                    shouldRefresh = true;
                    break;
                default:
                    shouldRefresh = false;
            }
            return shouldRefresh;
        };
    }

}
