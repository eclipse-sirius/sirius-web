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
package org.eclipse.sirius.components.interpreter;

import java.util.Objects;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

/**
 * Adapter attached to a (static) EPackage to cache the result of AQL services discovery for this EPackage.
 *
 * @author pcdavid
 */
public class AQLServicesAdapter implements Adapter {
    private final Set<IService<?>> services;

    private Notifier notifier;

    public AQLServicesAdapter(Set<IService<?>> services) {
        this.services = Objects.requireNonNull(services);
    }

    @Override
    public void notifyChanged(Notification notification) {
        // do nothing
    }

    public Set<IService<?>> getServices() {
        return this.services;
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
