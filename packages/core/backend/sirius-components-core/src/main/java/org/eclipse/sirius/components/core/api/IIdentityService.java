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
 * Interface of the service used to compute the identity of an object.
 *
 * @author sbegaudeau
 * @since v2024.3.0
 */
public interface IIdentityService {
    /**
     * Provides a unique identifier for the given semantic element.
     *
     * <p>
     *     This identifier will be used to attach representations to a semantic element and it will be provided to the
     *     frontend to let end users send requests involving some specific objects.
     *     As a result, this method should work perfectly with {@link IObjectSearchService#getObject(IEditingContext, String)}
     *     in order to find the semantic element from its unique identifier.
     * </p>
     *
     * @param object A semantic element
     *
     * @return The identifier of the object
     */
    String getId(Object object);

    /**
     * Provides the kind of the given semantic element.
     *
     * @param object A semantic element
     *
     * @return The kind of the object
     *
     * @technical-debt This method should be deleted since it was created to compute a "kind" property for any semantic
     * element. This property was abusively used to tightly couple some behavior, especially in the frontend, to the kind
     * of the element. While it seemed like a good idea in the beginning, we had to provide more powerful mechanisms to
     * allow Sirius Components based application to be more dynamics with richer preconditions for example. As a result,
     * no new piece of code should be contributed using this method.
     */
    String getKind(Object object);

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
