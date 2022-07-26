/*******************************************************************************
 * Copyright (c) 2021 Obeo and others.
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
package org.eclipse.sirius.components.compatibility.emf.api;

import java.util.Optional;

import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

/**
 * Used to provide external Java actions.
 *
 * @author sbegaudeau
 * @author Charles Wu
 */
public interface IExternalJavaActionProvider {
    Optional<IExternalJavaAction> findById(String id);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IExternalJavaActionProvider {

        @Override
        public Optional<IExternalJavaAction> findById(String id) {
            return Optional.empty();
        }

    }
}
