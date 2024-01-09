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
package org.eclipse.sirius.web.services.editingcontext.api;

import java.util.List;

import org.eclipse.sirius.components.view.View;

/**
 * Used to load properly view models.
 *
 * @author sbegaudeau
 */
public interface IViewLoader {
    List<View> load();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IViewLoader {
        @Override
        public List<View> load() {
            return List.of();
        }
    }
}
