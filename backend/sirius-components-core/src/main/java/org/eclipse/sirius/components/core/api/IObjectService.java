/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

/**
 * Interface of the service interacting with domain objects.
 *
 * @author sbegaudeau
 */
public interface IObjectService {
    String getId(Object object);

    String getLabel(Object object);

    String getKind(Object object);

    String getFullLabel(Object object);

    String getImagePath(Object object);

    Optional<Object> getObject(IEditingContext editingContext, String objectId);

    List<Object> getContents(IEditingContext editingContext, String objectId);

    Optional<String> getLabelField(Object object);

    boolean isLabelEditable(Object object);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IObjectService {

        @Override
        public String getId(Object object) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String getLabel(Object object) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String getKind(Object object) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String getFullLabel(Object object) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public String getImagePath(Object object) {
            return ""; //$NON-NLS-1$
        }

        @Override
        public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
            return Optional.empty();
        }

        @Override
        public List<Object> getContents(IEditingContext editingContext, String objectId) {
            return List.of();
        }

        @Override
        public Optional<String> getLabelField(Object object) {
            return Optional.empty();
        }

        @Override
        public boolean isLabelEditable(Object object) {
            return false;
        }

    }
}
