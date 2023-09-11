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
package org.eclipse.sirius.components.view.builder.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.ViewFactory;

/**
 * Provide default color palettes for views
 * .
 * @author arichard
 */
public class DefaultColorPalettesProvider {

    private List<String> muiHues = List.of(
            "amber",
            "blue",
            "blueGrey",
            "brown",
            "cyan",
            "deepOrange",
            "deepPurple",
            "green",
            "grey",
            "indigo",
            "lightBlue",
            "lightGreen",
            "lime",
            "orange",
            "pink",
            "purple",
            "red",
            "teal",
            "yellow"
            );

    private List<String> muiShades = List.of(
            "50",
            "100",
            "200",
            "300",
            "400",
            "500",
            "600",
            "700",
            "800",
            "900",
            "A100",
            "A200",
            "A400",
            "A700"
            );

    public List<ColorPalette> getDefaultColorPalettes() {
        List<ColorPalette> colorPalettes = new ArrayList<>();
        colorPalettes.add(this.createThemeColorsPalette());
        colorPalettes.add(this.createSpecialColorsPalette());
        this.muiHues.forEach(hue -> {
            colorPalettes.add(this.createMUIColorsPalette(hue, this.muiShades));
        });
        return colorPalettes;
    }

    private ColorPalette createSpecialColorsPalette() {
        ColorPalette colorPalette = ViewFactory.eINSTANCE.createColorPalette();
        colorPalette.setName("Special Colors Palette");
        FixedColor black = ViewFactory.eINSTANCE.createFixedColor();
        black.setName("black");
        black.setValue("#000000");
        colorPalette.getColors().add(black);
        FixedColor white = ViewFactory.eINSTANCE.createFixedColor();
        white.setName("white");
        white.setValue("#ffffff");
        colorPalette.getColors().add(white);
        FixedColor transparent = ViewFactory.eINSTANCE.createFixedColor();
        transparent.setName("transparent");
        transparent.setValue("transparent");
        colorPalette.getColors().add(transparent);
        FixedColor inherit = ViewFactory.eINSTANCE.createFixedColor();
        inherit.setName("inherit");
        inherit.setValue("inherit");
        colorPalette.getColors().add(inherit);
        return colorPalette;
    }

    private ColorPalette createThemeColorsPalette() {
        ColorPalette colorPalette = ViewFactory.eINSTANCE.createColorPalette();
        colorPalette.setName("Theme Colors Palette");
        colorPalette.getColors().addAll(this.themePrimaryColors());
        colorPalette.getColors().addAll(this.themeSecondaryColors());
        colorPalette.getColors().addAll(this.themeTextColors());
        colorPalette.getColors().addAll(this.themeErrorColors());
        return colorPalette;
    }

    private List<FixedColor> themePrimaryColors() {
        List<FixedColor> colors = new ArrayList<>();
        FixedColor primaryMain = ViewFactory.eINSTANCE.createFixedColor();
        primaryMain.setName("theme.palette.primary.main");
        primaryMain.setValue("theme.palette.primary.main");
        colors.add(primaryMain);
        FixedColor primaryLight = ViewFactory.eINSTANCE.createFixedColor();
        primaryLight.setName("theme.palette.primary.light");
        primaryLight.setValue("theme.palette.primary.light");
        colors.add(primaryLight);
        FixedColor primaryDark = ViewFactory.eINSTANCE.createFixedColor();
        primaryDark.setName("theme.palette.primary.dark");
        primaryDark.setValue("theme.palette.primary.dark");
        colors.add(primaryDark);
        return colors;
    }

    private List<FixedColor> themeSecondaryColors() {
        List<FixedColor> colors = new ArrayList<>();
        FixedColor secondaryMain = ViewFactory.eINSTANCE.createFixedColor();
        secondaryMain.setName("theme.palette.secondary.main");
        secondaryMain.setValue("theme.palette.secondary.main");
        colors.add(secondaryMain);
        FixedColor secondaryLight = ViewFactory.eINSTANCE.createFixedColor();
        secondaryLight.setName("theme.palette.secondary.light");
        secondaryLight.setValue("theme.palette.secondary.light");
        colors.add(secondaryLight);
        FixedColor secondaryDark = ViewFactory.eINSTANCE.createFixedColor();
        secondaryDark.setName("theme.palette.secondary.dark");
        secondaryDark.setValue("theme.palette.secondary.dark");
        colors.add(secondaryDark);
        return colors;
    }

    private List<FixedColor> themeTextColors() {
        List<FixedColor> colors = new ArrayList<>();
        FixedColor textPrimary = ViewFactory.eINSTANCE.createFixedColor();
        textPrimary.setName("theme.palette.text.primary");
        textPrimary.setValue("theme.palette.text.primary");
        colors.add(textPrimary);
        FixedColor textDisabled = ViewFactory.eINSTANCE.createFixedColor();
        textDisabled.setName("theme.palette.text.disabled");
        textDisabled.setValue("theme.palette.text.disabled");
        colors.add(textDisabled);
        FixedColor textHint = ViewFactory.eINSTANCE.createFixedColor();
        textHint.setName("theme.palette.text.hint");
        textHint.setValue("theme.palette.text.hint");
        colors.add(textHint);
        return colors;
    }

    private List<FixedColor> themeErrorColors() {
        List<FixedColor> colors = new ArrayList<>();
        FixedColor errorMain = ViewFactory.eINSTANCE.createFixedColor();
        errorMain.setName("theme.palette.error.main");
        errorMain.setValue("theme.palette.error.main");
        colors.add(errorMain);
        FixedColor errorLight = ViewFactory.eINSTANCE.createFixedColor();
        errorLight.setName("theme.palette.error.light");
        errorLight.setValue("theme.palette.error.light");
        colors.add(errorLight);
        FixedColor errorDark = ViewFactory.eINSTANCE.createFixedColor();
        errorDark.setName("theme.palette.error.dark");
        errorDark.setValue("theme.palette.error.dark");
        colors.add(errorDark);
        return colors;
    }

    private ColorPalette createMUIColorsPalette(String hue, List<String> shades) {
        ColorPalette colorPalette = ViewFactory.eINSTANCE.createColorPalette();
        colorPalette.setName(hue.substring(0, 1).toUpperCase() + hue.substring(1) + " Colors Palette");
        for (String shade : shades) {
            FixedColor color = ViewFactory.eINSTANCE.createFixedColor();
            color.setName(hue + " " + shade);
            color.setValue(hue + "[" + shade + "]");
            colorPalette.getColors().add(color);
        }
        return colorPalette;
    }
}
