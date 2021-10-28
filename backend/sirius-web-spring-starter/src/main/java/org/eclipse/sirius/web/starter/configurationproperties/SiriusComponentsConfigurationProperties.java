/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.starter.configurationproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * This POJO will contain the list of all the configuration properties provided by Sirius Components.
 *
 * @author sbegaudeau
 */
@ConfigurationProperties(prefix = "sirius.components")
public class SiriusComponentsConfigurationProperties {
    @NestedConfigurationProperty
    private final CorsConfigurationProperty cors;

    @NestedConfigurationProperty
    private final EditingContextConfigurationProperty editingContext;

    public SiriusComponentsConfigurationProperties(CorsConfigurationProperty cors, EditingContextConfigurationProperty editingContext) {
        this.cors = cors;
        this.editingContext = editingContext;
    }

    public CorsConfigurationProperty getCors() {
        return this.cors;
    }

    public EditingContextConfigurationProperty getEditingContext() {
        return this.editingContext;
    }
}
