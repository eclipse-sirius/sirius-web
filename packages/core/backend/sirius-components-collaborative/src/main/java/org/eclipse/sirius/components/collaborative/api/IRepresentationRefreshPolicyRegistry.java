/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.api;

import java.util.Optional;

import org.eclipse.sirius.components.representations.IRepresentationDescription;

/**
 * Registry of contributed representation refresh policy.
 *
 * @author gcoutable
 */
public interface IRepresentationRefreshPolicyRegistry {

    Optional<IRepresentationRefreshPolicy> getRepresentationRefreshPolicy(IRepresentationDescription representationDescription);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author gcoutable
     */
    class NoOp implements IRepresentationRefreshPolicyRegistry {

        @Override
        public Optional<IRepresentationRefreshPolicy> getRepresentationRefreshPolicy(IRepresentationDescription representationDescription) {
            return Optional.empty();
        }

    }
}
