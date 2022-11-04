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
 * Event to fade or unfade a diagram element.
 *
 * @author tgiraudet
 */
public class FadeDiagramElementEvent implements IDiagramEvent {

    private final Set<String> elementIds;

    private final boolean fade;

    public FadeDiagramElementEvent(Set<String> elementIds, boolean fade) {
        this.elementIds = Objects.requireNonNull(elementIds);
        this.fade = fade;
    }

    public Set<String> getElementIds() {
        return this.elementIds;
    }

    public boolean fadeElement() {
        return this.fade;
    }

}
