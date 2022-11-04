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
package org.eclipse.sirius.components.diagrams.components;

import java.util.Collection;

import org.eclipse.sirius.components.diagrams.ViewModifier;

/**
 * Computes the state from the various modifiers.
 *
 * @author sbegaudeau
 */
public class ViewStateProvider {
    /**
     * Compute the state from the given collection of modifiers where Hidden > Faded > Normal.
     *
     * @param modifiers
     *            the modifier collection from which extract the state
     * @return The state
     */
    public ViewModifier getState(Collection<ViewModifier> modifiers) {
        ViewModifier state = ViewModifier.Normal;
        if (modifiers.contains(ViewModifier.Hidden)) {
            state = ViewModifier.Hidden;
        } else if (modifiers.contains(ViewModifier.Faded)) {
            state = ViewModifier.Faded;
        }
        return state;
    }
}
