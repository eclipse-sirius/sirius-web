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
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;

/**
 * Used to provide the color palette.
 *
 * @author sbegaudeau
 */
public class PapayaColorPaletteProvider {

    public static final String PRIMARY = "primary";

    public static final String ERROR = "error";

    public static final String BACKGROUND = "background";

    public static final String DOMAIN_LIGHT = "domain.light";

    public static final String DOMAIN_DARK = "domain.dark";

    public static final String APPLICATION_LIGHT = "application.light";

    public static final String APPLICATION_DARK = "application.dark";

    public static final String CONTROLLER_LIGHT = "controller.light";

    public static final String CONTROLLER_DARK = "controller.dark";

    public static final String SERVICE_LIGHT = "service.light";

    public static final String SERVICE_DARK = "service.dark";

    public static final String REPOSITORY_LIGHT = "repository.light";

    public static final String REPOSITORY_DARK = "repository.dark";

    public static final String COMMAND_LIGHT = "command.light";

    public static final String COMMAND_DARK = "command.dark";

    public static final String QUERY_LIGHT = "query.light";

    public static final String QUERY_DARK = "query.dark";

    public static final String EVENT_LIGHT = "event.light";

    public static final String EVENT_DARK = "event.dark";

    public ColorPalette getColorPalette() {
        return new ViewBuilders().newColorPalette()
                .name("Papaya Color Palette")
                .colors(
                        this.fixedColor(PRIMARY, "#261E58"),
                        this.fixedColor(ERROR, "#D32F2F"),
                        this.fixedColor(BACKGROUND, "#FFFFFF"),
                        this.fixedColor(DOMAIN_LIGHT, "#E0F2F1"),
                        this.fixedColor(DOMAIN_DARK, "#009688"),
                        this.fixedColor(APPLICATION_LIGHT, "#F3E5F5"),
                        this.fixedColor(APPLICATION_DARK, "#9C27B0"),
                        this.fixedColor(CONTROLLER_LIGHT, "#EDE7F6"),
                        this.fixedColor(CONTROLLER_DARK, "#673AB7"),
                        this.fixedColor(SERVICE_LIGHT, "#ECEFF1"),
                        this.fixedColor(SERVICE_DARK, "#607D8B"),
                        this.fixedColor(REPOSITORY_LIGHT, "#E0F2F1"),
                        this.fixedColor(REPOSITORY_DARK, "#009688"),
                        this.fixedColor(COMMAND_LIGHT, "#E3F2FD"),
                        this.fixedColor(COMMAND_DARK, "#2196F3"),
                        this.fixedColor(QUERY_LIGHT, "#E8F5E9"),
                        this.fixedColor(QUERY_DARK, "#4CAF50"),
                        this.fixedColor(EVENT_LIGHT, "#FFF3E0"),
                        this.fixedColor(EVENT_DARK, "#FF9800")
                )
                .build();
    }

    private FixedColor fixedColor(String name, String value) {
        return new ViewBuilders().newFixedColor()
                .name(name)
                .value(value)
                .build();
    }
}
