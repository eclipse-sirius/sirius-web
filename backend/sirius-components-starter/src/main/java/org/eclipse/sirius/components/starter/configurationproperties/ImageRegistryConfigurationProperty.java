/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.starter.configurationproperties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * ImageRegistry configuration property.
 *
 * @author lfasani
 */
public class ImageRegistryConfigurationProperty {
    @NestedConfigurationProperty
    private final RefererConfigurationProperty referer;

    public ImageRegistryConfigurationProperty(RefererConfigurationProperty referer) {
        this.referer = referer;
    }

    public RefererConfigurationProperty getReferer() {
        return this.referer;
    }

}
