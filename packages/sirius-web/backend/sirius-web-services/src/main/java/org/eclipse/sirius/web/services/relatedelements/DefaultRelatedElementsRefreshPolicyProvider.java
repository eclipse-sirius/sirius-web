/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.web.services.relatedelements;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyProvider;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.springframework.stereotype.Service;

/**
 * The representation refresh policy provider for the default "Related Elements" representation.
 *
 * @author pcdavid
 */
@Service
public class DefaultRelatedElementsRefreshPolicyProvider implements IRepresentationRefreshPolicyProvider {

    public DefaultRelatedElementsRefreshPolicyProvider(IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        representationRefreshPolicyRegistry.add(this);
    }

    @Override
    public boolean canHandle(IRepresentationDescription representationDescription) {
        return DefaultRelatedElementsDescriptionProvider.FORM_DESCRIPTION_ID.toString().equals(representationDescription.getId());
    }

    @Override
    public IRepresentationRefreshPolicy getRepresentationRefreshPolicy(IRepresentationDescription representationDescription) {
        return changeDescription -> ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
    }

}
