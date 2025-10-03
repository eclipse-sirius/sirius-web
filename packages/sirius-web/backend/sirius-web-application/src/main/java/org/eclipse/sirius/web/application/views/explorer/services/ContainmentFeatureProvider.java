/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.List;

import org.eclipse.sirius.web.application.views.explorer.dto.ContainmentFeature;
import org.eclipse.sirius.web.application.views.explorer.services.api.IContainmentFeatureNameProviderDelegate;
import org.eclipse.sirius.web.application.views.explorer.services.api.IContainmentFeatureProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IDefaultContainmentFeatureProvider;
import org.springframework.stereotype.Service;

/**
 * Service in charge of providing the list of containment features available to add a child in a container.
 *
 * @author Arthur Daussy
 */
@Service
public class ContainmentFeatureProvider implements IContainmentFeatureProvider {

    private final List<IContainmentFeatureNameProviderDelegate> featureNameDelegates;

    private final IDefaultContainmentFeatureProvider defaultProvider;

    public ContainmentFeatureProvider(List<IContainmentFeatureNameProviderDelegate> featureNameDelegates,
            IDefaultContainmentFeatureProvider defaultProvider) {
        this.featureNameDelegates = featureNameDelegates;
        this.defaultProvider = defaultProvider;
    }

    @Override
    public List<ContainmentFeature> getContainmentFeatureNames(Object child, Object container) {
        if (child != null && container != null) {
            return featureNameDelegates.stream().filter(delegate -> delegate.canHandle(child, container))
                    .findFirst()
                    .map(delegate -> delegate.getContainmentFeatureNames(child, container))
                    .orElseGet(() -> defaultProvider.getContainmentFeatureNames(child, container));
        }
        return List.of();
    }
}
