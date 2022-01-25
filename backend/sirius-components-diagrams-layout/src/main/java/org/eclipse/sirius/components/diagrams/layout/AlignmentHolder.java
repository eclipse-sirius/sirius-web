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
package org.eclipse.sirius.components.diagrams.layout;

import java.util.Objects;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.sirius.components.diagrams.Position;

/**
 * EMF adapter used to store the alignment of a label since the ElkLabel does not have this property which is required
 * by the front-end.
 *
 * @author sbegaudeau
 */
public class AlignmentHolder implements Adapter {

    private final Position alignment;

    private Notifier target;

    public AlignmentHolder(Position alignment) {
        this.alignment = Objects.requireNonNull(alignment);
    }

    public Position getAlignment() {
        return this.alignment;
    }

    @Override
    public void notifyChanged(Notification notification) {
        // Do nothing on purpose
    }

    @Override
    public Notifier getTarget() {
        return this.target;
    }

    @Override
    public void setTarget(Notifier newTarget) {
        this.target = newTarget;
    }

    @Override
    public boolean isAdapterForType(Object type) {
        return false;
    }

}
