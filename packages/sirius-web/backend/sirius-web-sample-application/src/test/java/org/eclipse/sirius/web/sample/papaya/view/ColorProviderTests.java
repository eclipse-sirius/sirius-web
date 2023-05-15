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

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.components.view.ViewFactory;
import org.junit.jupiter.api.Test;

/**
 * Unit test of the color provider.
 *
 * @author frouene
 */
public class ColorProviderTests {

    @Test
    public void testGetColor() {
        var view = ViewFactory.eINSTANCE.createView();
        var colorPalette1 = ViewFactory.eINSTANCE.createColorPalette();
        var colorPalette2 = ViewFactory.eINSTANCE.createColorPalette();
        view.getColorPalettes().add(colorPalette1);
        view.getColorPalettes().add(colorPalette2);
        var color1 = ViewFactory.eINSTANCE.createFixedColor();
        color1.setName("colorFromPalette1");
        colorPalette1.getColors().add(color1);
        var color2 = ViewFactory.eINSTANCE.createFixedColor();
        color2.setName("colorFromPalette2");
        colorPalette2.getColors().add(color2);

        var colorProvider = new ColorProvider(view);

        var result = colorProvider.getColor("colorFromPalette1");
        assertThat(result).isNotNull();

        result = colorProvider.getColor("colorFromPalette2");
        assertThat(result).isNotNull();

        result = colorProvider.getColor("notFound");
        assertThat(result).isNull();
    }
}
