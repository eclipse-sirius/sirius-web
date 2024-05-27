/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.services;

import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;

/**
 * Used to provide the color palette.
 *
 * @author sbegaudeau
 */
public class PapayaColorPaletteProvider {

    public static final String PALETTE_TEXT_PRIMARY = "theme.palette.text.primary";

    public static final String PALETTE_ERROR_MAIN = "theme.palette.error.main";

    public static final String DEFAULT_BACKGROUND = "default.background";

    public ColorPalette getColorPalette() {
        var paletteTextPrimary = new ViewBuilders().newFixedColor()
                .name(PALETTE_TEXT_PRIMARY)
                .value(PALETTE_TEXT_PRIMARY)
                .build();

        var paletteErrorMain = new ViewBuilders().newFixedColor()
                .name(PALETTE_ERROR_MAIN)
                .value(PALETTE_ERROR_MAIN)
                .build();

        var defaultBackground = new ViewBuilders().newFixedColor()
                .name(DEFAULT_BACKGROUND)
                .value("#FFFFFF")
                .build();

        return new ViewBuilders().newColorPalette()
                .name("Papaya Color Palette")
                .colors(
                        paletteTextPrimary,
                        paletteErrorMain,
                        defaultBackground
                )
                .build();
    }
}
