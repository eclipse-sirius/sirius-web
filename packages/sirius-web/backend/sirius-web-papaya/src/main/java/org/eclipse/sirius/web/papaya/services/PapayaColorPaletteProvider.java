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
package org.eclipse.sirius.web.papaya.services;

import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;

/**
 * Used to provide the color palette.
 *
 * @author sbegaudeau
 */
public class PapayaColorPaletteProvider {

    public static final String PALETTE_TEXT_PRIMARY = "theme.palette.text.primary";

    public static final String PALETTE_ERROR_MAIN = "theme.palette.error.main";

    public static final String DEFAULT_BACKGROUND = "default.background";

    public static final String EMPTY_DIAGRAM_BACKGROUND = "empty-diagram-background";

    public ColorPalette getColorPalette() {
        var emptyDiagramBackground = new ViewBuilders().newFixedColor()
                .name(EMPTY_DIAGRAM_BACKGROUND)
                .value("url('data:image/svg+xml;utf8,<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"200px\" width=\"200px\"> <text fill=\"rgb(38, 30, 88)\" x=\"100px\" y=\"100px\" alignment-baseline=\"middle\" text-anchor=\"middle\" font-size=\"12\"> Drag and drop elements to get started </text> </svg>') no-repeat center center / contain")
                .build();

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
                        emptyDiagramBackground,
                        paletteTextPrimary,
                        paletteErrorMain,
                        defaultBackground
                )
                .build();
    }
}
