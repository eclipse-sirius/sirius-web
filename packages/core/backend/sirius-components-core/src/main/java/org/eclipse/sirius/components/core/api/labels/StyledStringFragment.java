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
package org.eclipse.sirius.components.core.api.labels;


/**
 * Fragment of string that each define a style.
 *
 * @author mcharfadi
 */
public record StyledStringFragment(String text, StyledStringFragmentStyle styledStringFragmentStyle) {

    /**
     * Return a new fragment with no style.
     *
     * @param text
     * @return a new fragment with no style
     */
    public static StyledStringFragment of(String text) {
        StyledStringFragmentStyle defaultStyle = StyledStringFragmentStyle.newDefaultStyledStringFragmentStyle().build();
        return new StyledStringFragment(text, defaultStyle);
    }
}
