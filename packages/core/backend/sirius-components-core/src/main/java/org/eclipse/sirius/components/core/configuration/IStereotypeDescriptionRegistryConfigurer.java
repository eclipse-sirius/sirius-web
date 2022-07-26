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
package org.eclipse.sirius.components.core.configuration;

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * Interface to be implemented as a Spring configuration in order to configure the stereotype description registry.
 *
 * @author sbegaudeau
 */
@PublicApi
public interface IStereotypeDescriptionRegistryConfigurer {
    void addStereotypeDescriptions(IStereotypeDescriptionRegistry registry);
}
