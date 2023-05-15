/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view;

import java.util.Collection;
import java.util.Objects;

import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;

/**
 * Used to find a color in the palette.
 *
 * @author sbegaudeau
 */
public class ColorProvider implements IColorProvider {

    private final View view;

    public ColorProvider(View view) {
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
