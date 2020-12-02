/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.emf.services;

import org.eclipse.sirius.web.services.api.IPathService;

/**
 * Implementation of the path service which does nothing.
 *
 * @author hmarchadour
 */
public class NoOpPathService implements IPathService {

    @Override
    public String resolvePath(String path) {
        return path;
    }

    @Override
    public boolean isObfuscated(String path) {
        return true;
    }

    @Override
    public String obfuscatePath(String path) {
        return path;
    }

}
