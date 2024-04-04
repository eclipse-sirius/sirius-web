/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.representations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyProvider;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.springframework.stereotype.Service;

/**
 * Registry of the representation refresh policies.
 *
 * @author gcoutable
 */
@Service
public class RepresentationRefreshPolicyRegistry implements IRepresentationRefreshPolicyRegistry {

    private final List<IRepresentationRefreshPolicyProvider> representationRefreshPolicyProviders;

    public RepresentationRefreshPolicyRegistry(List<IRepresentationRefreshPolicyProvider> representationRefreshPolicyProviders) {
        this.representationRefreshPolicyProviders = Objects.requireNonNull(representationRefreshPolicyProviders);
    }

    @Override
    public Optional<IRepresentationRefreshPolicy> getRepresentationRefreshPolicy(IRepresentationDescription representationDescription) {
        return this.representationRefreshPolicyProviders.stream()
                .filter(representationRefreshPolicyProvider -> representationRefreshPolicyProvider.canHandle(representationDescription))
                .findFirst()
                .map(representationRefreshPolicyProvider -> representationRefreshPolicyProvider.getRepresentationRefreshPolicy(representationDescription));
    }
}
