/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.core.api;

import java.util.List;
import java.util.Map;

/**
 * Used to parse the kind of an object.
 *
 * @author sbegaudeau
 */
public interface IKindParser {
    Map<String, List<String>> getParameterValues(String kind);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IKindParser {
        @Override
        public Map<String, List<String>> getParameterValues(String kind) {
            return Map.of();
        }
    }
}
