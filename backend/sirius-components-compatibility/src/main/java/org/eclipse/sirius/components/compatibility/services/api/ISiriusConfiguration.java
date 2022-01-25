/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.components.compatibility.services.api;

import java.util.List;

/**
 * Interface to be implemented to easily configure the Sirius compatibility layer.
 *
 * @author sbegaudeau
 */
public interface ISiriusConfiguration {
    /**
     * Returns the list of the path of the odesign files to consider in the classpath.
     *
     * @return The paths of the odesign, for example: List.of("description/flow.odesign")
     */
    List<String> getODesignPaths();
}
