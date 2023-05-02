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
package org.eclipse.sirius.components.collaborative.api;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.springframework.stereotype.Service;

/**
 * Bundles the common dependencies that most {@link IRepresentationEventProcessorFactory} implementations need into a
 * single object for convenience.
 *
 * @author pcdavid
 */
@Service
public class RepresentationEventProcessorFactoryConfiguration {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    public RepresentationEventProcessorFactoryConfiguration(IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationSearchService representationSearchService,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry, ISubscriptionManagerFactory subscriptionManagerFactory) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
    }

    public IRepresentationDescriptionSearchService getRepresentationDescriptionSearchService() {
        return this.representationDescriptionSearchService;
    }

    public IRepresentationSearchService getRepresentationSearchService() {
        return this.representationSearchService;
    }

    public IRepresentationRefreshPolicyRegistry getRepresentationRefreshPolicyRegistry() {
        return this.representationRefreshPolicyRegistry;
    }

    public ISubscriptionManagerFactory getSubscriptionManagerFactory() {
        return this.subscriptionManagerFactory;
    }
}
