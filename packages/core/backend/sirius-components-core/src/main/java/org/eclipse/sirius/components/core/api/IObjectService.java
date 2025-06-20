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

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.labels.StyledString;

/**
 * Interface of that allow us to regroup several services.
 *
 * @deprecated This service has tightly coupled various concerns for a long time. We have introduced smaller services
 * like ILabelService, IObjectSearchService or IIdentityService a long time ago to replace it.
 * @author sbegaudeau
 * @since v0.1.11
 */
@Deprecated(forRemoval = true)
public interface IObjectService {

    StyledString getStyledLabel(Object object);

    List<Object> getContents(Object object);

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

    Optional<Object> getObject(IEditingContext editingContext, String objectId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author mcharfadi
     */
    class NoOp implements IObjectService {

        @Override
        public StyledString getStyledLabel(Object object) {
            return StyledString.of("");
        }

        @Override
        public List<Object> getContents(Object object) {
            return List.of();
        }

        @Override
        public String getId(Object object) {
            return "";
        }

        @Override
        public String getKind(Object object) {
            return "";
        }

        @Override
        public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
            return Optional.empty();
        }
    }

}
