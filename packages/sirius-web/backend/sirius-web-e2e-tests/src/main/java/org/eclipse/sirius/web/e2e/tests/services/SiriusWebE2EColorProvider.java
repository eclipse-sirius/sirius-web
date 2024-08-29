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

package org.eclipse.sirius.web.e2e.tests.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;

/**
 * Used to find colors by their names.
 *
 * @author gcoutable
 */
public class SiriusWebE2EColorProvider implements IColorProvider {

    private final List<ColorPalette> colorPalettes;

    public SiriusWebE2EColorProvider(List<ColorPalette> colorPalettes) {
        this.colorPalettes = Objects.requireNonNull(colorPalettes);
    }

    @Override
    public UserColor getColor(String colorName) {
        return colorPalettes
                .stream()
                .map(ColorPalette::getColors)
                .flatMap(Collection::stream)
                .filter(userColor -> userColor.getName().equals(colorName))
                .findFirst()
                .orElse(null);
    }
}
