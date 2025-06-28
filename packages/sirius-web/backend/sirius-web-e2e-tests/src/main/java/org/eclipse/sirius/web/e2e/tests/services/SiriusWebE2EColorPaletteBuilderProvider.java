/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

package org.eclipse.sirius.web.e2e.tests.services;

import org.eclipse.sirius.components.view.builder.generated.view.ColorPaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;

/**
 * Used to provide a default color palette builder already containing some colors.
 *
 * <p>
 * color_dark, color_blue, color_green, border_blue, border_green and color_transparent.
 * </p>
 *
 * @author gcoutable
 */
public class SiriusWebE2EColorPaletteBuilderProvider {

    public static final String COLOR_DARK = "color_dark";

    public static final String COLOR_BLUE = "color_blue";

    public static final String COLOR_GREEN = "color_green";

    public static final String BORDER_BLUE = "border_blue";

    public static final String BORDER_GREEN = "border_green";

    public static final String COLOR_TRANSPARENT = "color_transparent";

    public static final String COLOR_RED = "color_red";

    public ColorPaletteBuilder getColorPaletteBuilder() {
        var colorDark = new ViewBuilders().newFixedColor()
                .name(COLOR_DARK)
                .value("#002639")
                .build();

        var colorBlue = new ViewBuilders().newFixedColor()
                .name(COLOR_BLUE)
                .value("#E5F5F8")
                .build();

        var colorGreen = new ViewBuilders().newFixedColor()
                .name(COLOR_GREEN)
                .value("#B1D8B7")
                .build();

        var borderBlue = new ViewBuilders().newFixedColor()
                .name(BORDER_BLUE)
                .value("#33B0C3")
                .build();

        var borderGreen = new ViewBuilders().newFixedColor()
                .name(BORDER_GREEN)
                .value("#76B947")
                .build();

        var colorTransparent = new ViewBuilders().newFixedColor()
                .name(COLOR_TRANSPARENT)
                .value("transparent")
                .build();


        var colorRed = new ViewBuilders().newFixedColor()
                .name(COLOR_RED)
                .value("red")
                .build();

        return new ViewBuilders().newColorPalette()
                .name("Color Palette")
                .colors(
                        colorDark,
                        colorBlue,
                        colorGreen,
                        borderBlue,
                        borderGreen,
                        colorTransparent,
                        colorRed
                );
    }
}
