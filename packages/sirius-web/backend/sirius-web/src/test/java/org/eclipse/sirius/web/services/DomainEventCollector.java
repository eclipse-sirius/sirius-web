/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.web.domain.events.IDomainEvent;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to collect domain events sent during the integration tests.
 *
 * @author sbegaudeau
 */
@Service
public class DomainEventCollector implements IDomainEventCollector {

    private final List<IDomainEvent> domainEvents = new ArrayList<>();

    @TransactionalEventListener
    public void onDomainEvent(IDomainEvent domainEvent) {
        this.domainEvents.add(domainEvent);
    }

    @Override
    public List<IDomainEvent> getDomainEvents() {
        return this.domainEvents;
    }

    @Override
    public void clear() {
        this.domainEvents.clear();
    }
}
