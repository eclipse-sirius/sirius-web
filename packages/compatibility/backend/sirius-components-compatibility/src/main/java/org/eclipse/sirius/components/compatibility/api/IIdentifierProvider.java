/*******************************************************************************
 * Copyright (c) 2021, 2022 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.compatibility.api;

import java.util.Optional;

/**
 * Used to compute a stable identifier for the element coming from the odesign.
 *
 * @author sbegaudeau
 */
public interface IIdentifierProvider {
    String getIdentifier(Object element);

    Optional<String> findVsmElementId(String id);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IIdentifierProvider {

        @Override
        public String getIdentifier(Object element) {
            return "";
        }

        @Override
        public Optional<String> findVsmElementId(String id) {
            return Optional.empty();
        }

    }
}
