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
package org.eclipse.sirius.components.graphql.ws;

import reactor.core.Disposable;

/**
 * A GraphQL subscription with a specific identifier.
 *
 * @author sbegaudeau
 */
public class SubscriptionEntry {
    private String id;

    private Disposable subscription;

    public SubscriptionEntry(String id, Disposable subscription) {
        this.id = id;
        this.subscription = subscription;
    }

    public String getId() {
        return this.id;
    }

    public Disposable getSubscription() {
        return this.subscription;
    }
}
