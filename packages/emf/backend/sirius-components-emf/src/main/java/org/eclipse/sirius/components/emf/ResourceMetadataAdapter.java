/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.emf;

import java.util.Objects;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

/**
 * An EMF adapter used to store some metadata related to EMF Resources.
 *
 * <p>
 * This adapter is currently used to store the following metadata:
 * </p>
 * <ul>
 * <li>name: The user-friendly name of the resource if presented in the UI</li>
 * </ul>
 *
 * @author sbegaudeau
 */
public class ResourceMetadataAdapter implements Adapter {
    private String name;

    private Notifier notifier;

    public ResourceMetadataAdapter(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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
