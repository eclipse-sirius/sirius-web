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
package org.eclipse.sirius.components.emf.services;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

/**
 * The EMF adapter used to store the id of a notifier.
 *
 * @author sbegaudeau
 */
public class IDAdapter implements Adapter {

    private UUID id;

    private Notifier notifier;

    public IDAdapter(UUID id) {
        this.id = Objects.requireNonNull(id);
    }

    public UUID getId() {
        return this.id;
    }

    @Override
    public void notifyChanged(Notification notification) {
        // do nothing
    }

    @Override
    public Notifier getTarget() {
        return this.notifier;
    }

    @Override
    public void setTarget(Notifier newTarget) {
        this.notifier = newTarget;
    }

    @Override
    public boolean isAdapterForType(Object type) {
        return false;
    }

}
