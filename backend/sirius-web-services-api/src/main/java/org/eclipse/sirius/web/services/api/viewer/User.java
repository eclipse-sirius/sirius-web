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
package org.eclipse.sirius.web.services.api.viewer;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * Class used to represent a user.
 *
 * @author gcoutable
 */
public class User implements IViewer {

    private UUID id;

    private String username;

    public User(UUID id, String username) {
        this.id = id;
        this.username = username;

    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, username: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.username);
    }

}
