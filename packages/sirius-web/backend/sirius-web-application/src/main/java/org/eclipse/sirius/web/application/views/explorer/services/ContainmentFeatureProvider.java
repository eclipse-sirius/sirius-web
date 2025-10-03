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
import java.util.Objects;

import org.eclipse.sirius.web.application.views.explorer.dto.ContainmentFeature;
import org.eclipse.sirius.web.application.views.explorer.services.api.IContainmentFeatureProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IContainmentFeatureProviderDelegate;
import org.eclipse.sirius.web.application.views.explorer.services.api.IDefaultContainmentFeatureProvider;
import org.springframework.stereotype.Service;

/**
 * Service in charge of providing the list of containment features available to add a child in a container.
 *
 * @author Arthur Daussy
 */
@Service
public class ContainmentFeatureProvider implements IContainmentFeatureProvider {

    private final List<IContainmentFeatureProviderDelegate> containmentFeatureProviderDelegates;

    private final IDefaultContainmentFeatureProvider defaultProvider;

    public ContainmentFeatureProvider(List<IContainmentFeatureProviderDelegate> containmentFeatureProviderDelegates, IDefaultContainmentFeatureProvider defaultProvider) {
        this.containmentFeatureProviderDelegates = Objects.requireNonNull(containmentFeatureProviderDelegates);
        this.defaultProvider = Objects.requireNonNull(defaultProvider);
    }

    @Override
    public List<ContainmentFeature> getContainmentFeatures(Object container, Object child) {
        return this.containmentFeatureProviderDelegates.stream().filter(delegate -> delegate.canHandle(container, child))
                .findFirst()
                .map(delegate -> delegate.getContainmentFeatures(container, child))
                .orElseGet(() -> this.defaultProvider.getContainmentFeatures(container, child));
    }
}
