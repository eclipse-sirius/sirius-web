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
package org.eclipse.sirius.components.view.builder.providers;

import java.util.Collection;
import java.util.Objects;

import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;

/**
 * Used to find colors by their names.
 *
 * @author sbegaudeau
 */
public class DefaultColorProvider implements IColorProvider {
    private final View view;

    public DefaultColorProvider(View view) {
        this.view = Objects.requireNonNull(view);
    }

    @Override
    public UserColor getColor(String colorName) {
        return this.view.getColorPalettes()
                .stream()
                .map(ColorPalette::getColors)
                .flatMap(Collection::stream)
                .filter(userColor -> userColor.getName().equals(colorName))
                .findFirst()
                .orElse(null);
    }
}
