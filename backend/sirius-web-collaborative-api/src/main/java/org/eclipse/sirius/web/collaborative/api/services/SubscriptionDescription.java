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
package org.eclipse.sirius.web.collaborative.api.services;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * Details of the subscription including at least both the principal and raw identifier of the subscription.
 *
 * @author sbegaudeau
 */
public class SubscriptionDescription {
    private final Principal principal;

    private final String subscriptionId;

    public SubscriptionDescription(Principal principal, String subscriptionId) {
        this.principal = Objects.requireNonNull(principal);
        this.subscriptionId = Objects.requireNonNull(subscriptionId);
    }

    public Principal getPrincipal() {
        return this.principal;
    }

    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SubscriptionDescription) {
            SubscriptionDescription subscriptionDescription = (SubscriptionDescription) object;
            return subscriptionDescription.getPrincipal().getName().equals(this.principal.getName()) && subscriptionDescription.getSubscriptionId().equals(this.subscriptionId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.principal.getName(), this.subscriptionId);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'username: {1}, subscriptionId: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.principal.getName(), this.subscriptionId);
    }
}
