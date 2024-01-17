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
package org.eclipse.sirius.components.core.api;

/**
 * Interface of the service used to compute the identity and find domain objects.
 *
 * @author sbegaudeau
 */
public interface IIdentityService {
    String getId(Object object);

    String getKind(Object object);

    interface Delegate {
        boolean canHandle(Object object);
    }

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IIdentityService {

        @Override
        public String getId(Object object) {
            return "";
        }

        @Override
        public String getKind(Object object) {
            return "";
        }
    }
}
