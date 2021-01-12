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
package org.eclipse.sirius.web.spring.collaborative.handlers;

import java.util.UUID;

import org.eclipse.sirius.web.core.api.IEditingContext;

/**
 * Implementation of the editing context which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpEditingContext implements IEditingContext {

    @Override
    public UUID getProjectId() {
        return UUID.randomUUID();
    }

    @Override
    public Object getDomain() {
        return null;
    }

}
