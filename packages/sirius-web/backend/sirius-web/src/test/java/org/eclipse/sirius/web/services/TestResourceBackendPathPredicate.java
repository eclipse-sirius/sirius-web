/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.services;

import org.eclipse.sirius.web.infrastructure.configuration.mvc.IBackendPathPredicate;
import org.springframework.stereotype.Service;

/**
 * Used to ensure that we can retrieve the test resource.
 *
 * @author sbegaudeau
 */
@Service
public class TestResourceBackendPathPredicate implements IBackendPathPredicate {
    @Override
    public boolean isBackendPath(String path) {
        return path != null && path.endsWith("test-resource.txt");
    }
}
