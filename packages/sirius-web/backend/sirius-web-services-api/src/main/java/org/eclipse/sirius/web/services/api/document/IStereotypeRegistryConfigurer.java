/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.services.api.document;

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * Interface to be implemented as a Spring configuration in order to configure the stereotype registry.
 *
 * @author sbegaudeau
 */
@PublicApi
public interface IStereotypeRegistryConfigurer {
    void addStereotypes(IStereotypeRegistry registry);
}
