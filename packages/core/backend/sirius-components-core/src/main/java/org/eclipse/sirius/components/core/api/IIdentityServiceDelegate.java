/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
 * Interface of the delegation service interacting with domain objects.
 *
 * @author mcharfadi
 */
public interface IIdentityServiceDelegate {
    boolean canHandle(Object object);

    String getId(Object object);

    /**
     * Provides the kind of the given semantic element.
     *
     * @param object A semantic element
     *
     * @return The kind of the object
     *
     * @technical-debt This method should be deleted, see {@link IIdentityService#getKind(Object)} for additional details
     */
    String getKind(Object object);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author mcharfadi
     */
    class NoOp implements IIdentityServiceDelegate {
        @Override
        public boolean canHandle(Object object) {
            return true;
        }

        @Override
        public String getId(Object object) {
            return "";
        }

        @Override
        public String getKind(Object object) {
            return null;
        }

    }
}
