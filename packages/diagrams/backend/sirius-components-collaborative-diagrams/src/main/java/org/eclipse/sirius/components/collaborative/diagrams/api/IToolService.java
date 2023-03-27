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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.tools.ITool;

/**
 * Interface used to manipulate tools.
 *
 * @author hmarchadour
 */
public interface IToolService {

    Optional<ITool> findToolById(IEditingContext editingContext, String toolId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IToolService {
        @Override
        public Optional<ITool> findToolById(IEditingContext editingContext, String toolId) {
            return Optional.empty();
        }
    }
}
