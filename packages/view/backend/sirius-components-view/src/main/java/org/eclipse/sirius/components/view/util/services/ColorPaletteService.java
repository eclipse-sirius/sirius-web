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

    public UserColor getColorFromPalette(View view, String colorName) {
        return view.getColorPalettes()
                .stream()
                .map(ColorPalette::getColors)
                .flatMap(Collection::stream)
                .filter(userColor -> userColor.getName().equals(colorName))
                .findFirst()
                .orElse(null);
    }

    public UserColor getColorFromPalette(Object object, String colorName) {
        if (object instanceof View view) {
            return this.getColorFromPalette(view, colorName);
        } else if (object instanceof EObject eObject) {
            return this.getColorFromPalette(eObject.eContainer(), colorName);
        }
        return null;
    }
}
