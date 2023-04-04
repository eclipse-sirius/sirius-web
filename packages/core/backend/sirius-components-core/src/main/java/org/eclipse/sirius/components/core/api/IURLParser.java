/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import java.util.Map;

/**
 * Used to parse URLs.
 *
 * @author sbegaudeau
 */
public interface IURLParser {
    Map<String, List<String>> getParameterValues(String url);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IURLParser {
        @Override
        public Map<String, List<String>> getParameterValues(String url) {
            return Map.of();
        }
    }
}
