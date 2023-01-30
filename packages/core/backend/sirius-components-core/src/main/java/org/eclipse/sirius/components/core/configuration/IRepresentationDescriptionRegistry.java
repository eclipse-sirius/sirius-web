/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import org.eclipse.sirius.components.representations.IRepresentationDescription;

/**
 * The registry of all the representation descriptions.
 *
 * @author sbegaudeau
 */
@PublicApi
public interface IRepresentationDescriptionRegistry {
    void add(IRepresentationDescription representationDescription);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IRepresentationDescriptionRegistry {
        @Override
        public void add(IRepresentationDescription representationDescription) {
            // Do nothing
        }
    }
}
