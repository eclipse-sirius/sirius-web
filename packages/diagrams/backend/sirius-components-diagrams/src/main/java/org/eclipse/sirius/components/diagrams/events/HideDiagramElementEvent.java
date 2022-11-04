/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import java.util.Objects;
import java.util.Set;

/**
 * Event to hide or unhide a diagram element.
 *
 * @author tgiraudet
 */
public class HideDiagramElementEvent implements IDiagramEvent {

    private final Set<String> elementIds;

    private final boolean hide;

    public HideDiagramElementEvent(Set<String> elementIds, boolean hide) {
        this.elementIds = Objects.requireNonNull(elementIds);
        this.hide = hide;
    }

    public Set<String> getElementIds() {
        return this.elementIds;
    }

    public boolean hideElement() {
        return this.hide;
    }

}
