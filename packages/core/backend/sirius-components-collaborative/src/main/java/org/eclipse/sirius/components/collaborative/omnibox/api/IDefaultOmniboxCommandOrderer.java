/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.omnibox.api;

import java.util.List;

import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;

/**
 * The default implementation that sorts omnibox commands.
 *
 * @author gdaniel
 */
public interface IDefaultOmniboxCommandOrderer {

    List<OmniboxCommand> order(List<OmniboxCommand> omniboxCommands);

    /**
     * Implementation which does nothing, used for mocks and unit tests.
     *
     * @author gdaniel
     */
    class NoOp implements IDefaultOmniboxCommandOrderer {

        @Override
        public List<OmniboxCommand> order(List<OmniboxCommand> omniboxCommands) {
            return null;
        }
    }
}
