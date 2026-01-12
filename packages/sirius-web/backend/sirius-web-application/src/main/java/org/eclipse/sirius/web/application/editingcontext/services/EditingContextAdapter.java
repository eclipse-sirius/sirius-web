/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.editingcontext.services;


import java.util.Objects;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

/**
 * Used to retrieve the editing context id from the resource set.
 *
 * @author sbegaudeau
 * @since v2026.1.0
 */
public class EditingContextAdapter implements Adapter {

    private Notifier notifier;

    private final String editingContextId;

    public EditingContextAdapter(String editingContextId) {
        this.editingContextId = Objects.requireNonNull(editingContextId);
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    @Override
    public void notifyChanged(Notification notification) {
        // Do nothing
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
