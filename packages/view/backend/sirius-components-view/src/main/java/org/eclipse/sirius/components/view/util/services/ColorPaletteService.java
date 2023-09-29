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
package org.eclipse.sirius.components.view.util.services;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;

/**
 * Service class to access {@link ColorPalette}'s colors.
 *
 * @author arichard
 */
public class ColorPaletteService {

    /**
     * Studio color palettes don't need to be display in the explorer view.
     */
    public static final String SIRIUS_STUDIO_COLOR_PALETTES_URI = "sirius:///1952d117-7d88-32c4-a839-3858e5e779ae";

    private View colorPalettesView;

    public ColorPaletteService(View colorPalettesView) {
        this.colorPalettesView = colorPalettesView;
    }

    public UserColor getColorFromPalette(Object object, String colorName) {
        UserColor color = this.getColorFromPalette(this.colorPalettesView, colorName);

        if (color == null && object instanceof EObject eObject) {
            color = this.getColorFromPalette(this.getView(eObject), colorName);
        }
        return color;
    }

    private UserColor getColorFromPalette(View view, String colorName) {
        return view.getColorPalettes()
                .stream()
                .map(ColorPalette::getColors)
                .flatMap(Collection::stream)
                .filter(userColor -> userColor.getName().equals(colorName))
                .findFirst()
                .orElse(null);
    }

    private View getView(Object object) {
        View view = null;
        if (object instanceof View v) {
            view = v;
        } else  if (object instanceof EObject eObject) {
            view = this.getView(eObject.eContainer());
        }
        return view;
    }
}
