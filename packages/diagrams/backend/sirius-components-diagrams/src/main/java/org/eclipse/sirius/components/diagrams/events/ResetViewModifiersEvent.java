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
package org.eclipse.sirius.components.diagrams.events;

import java.util.Set;

/**
 * Event to reset the view modifiers of diagram elements to their default value.
 *
 * @author gdaniel
 */
public class ResetViewModifiersEvent implements IDiagramEvent {

    private final Set<String> elementIds;

    public ResetViewModifiersEvent(Set<String> elementIds) {
        this.elementIds = elementIds;
    }

    public Set<String> getElementIds() {
        return this.elementIds;
    }
}
