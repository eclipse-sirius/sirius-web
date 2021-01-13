/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.projects;

import java.security.Principal;
import java.util.Objects;

import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessorRegistry;
import org.eclipse.sirius.web.collaborative.api.services.SubscriptionDescription;
import org.eclipse.sirius.web.spring.graphql.api.ISubscriptionTerminatedHandler;
import org.springframework.stereotype.Service;

/**
 * This class is used to release the relevant event processors when a subscription is terminated.
 *
 * @author sbegaudeau
 */
@Service
public class SubscriptionTerminatedHandler implements ISubscriptionTerminatedHandler {

    private final IProjectEventProcessorRegistry projectEventProcessorRegistry;

    public SubscriptionTerminatedHandler(IProjectEventProcessorRegistry projectEventProcessorRegistry) {
        this.projectEventProcessorRegistry = Objects.requireNonNull(projectEventProcessorRegistry);
    }

    @Override
    public void dispose(Principal principal, String subscriptionId) {
        SubscriptionDescription subscriptionDescription = new SubscriptionDescription(principal, subscriptionId);
        // @formatter:off
        this.projectEventProcessorRegistry.getProjectEventProcessors().stream()
                .forEach(projectEventProcessor -> projectEventProcessor.release(subscriptionDescription));
        // @formatter:on
    }

}
