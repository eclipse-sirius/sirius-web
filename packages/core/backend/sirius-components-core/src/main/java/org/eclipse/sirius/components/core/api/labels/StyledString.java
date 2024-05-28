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


import java.util.List;
import java.util.stream.Collectors;

/**
 * A String composed of Fragment that each define a style.
 *
 * @author mcharfadi
 */
public record StyledString(List<StyledStringFragment> styledStringFragments) {

    /**
     * Get a new StyledString with one fragment and no style.
     *
     * @param text
     * @return a new StyledString with one fragment and no style
     */
    public static StyledString of(String text) {
        StyledStringFragment styledStringFragment = StyledStringFragment.of(text);
        return new StyledString(List.of(styledStringFragment));
    }
    @Override
    public String toString() {
        return this.styledStringFragments.stream().map(StyledStringFragment::text).collect(Collectors.joining());
    }


}

