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
package org.eclipse.sirius.web.domain.boundedcontexts.project.services;

import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectNameValidator;
import org.springframework.stereotype.Service;

/**
 * Used to validate the project name.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectNameValidator implements IProjectNameValidator {
    @Override
    public String sanitize(String name) {
        return name.trim();
    }

    @Override
    public boolean isValid(String name) {
        return !name.isEmpty() && name.length() <= 1024;
    }
}
