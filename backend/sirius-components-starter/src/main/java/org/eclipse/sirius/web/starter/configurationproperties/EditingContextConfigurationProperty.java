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

import java.time.Duration;

/**
 * EditingContext configuration property.
 *
 * @author sbegaudeau
 */
public class EditingContextConfigurationProperty {
    private final Duration disposeDelay;

    public EditingContextConfigurationProperty(Duration disposeDelay) {
        this.disposeDelay = disposeDelay;
    }

    public Duration getDisposeDelay() {
        return this.disposeDelay;
    }
}
