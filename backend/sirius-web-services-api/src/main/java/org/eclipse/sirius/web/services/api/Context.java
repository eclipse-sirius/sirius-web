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
package org.eclipse.sirius.web.services.api;

import java.security.Principal;
import java.util.Objects;

/**
 * Data representing the context in which the operation is performed. This object is used to provide additional data to
 * the various handlers on top of the raw input from the end users.
 *
 * @author sbegaudeau
 */
public class Context {
    private final Principal principal;

    public Context(Principal principal) {
        this.principal = Objects.requireNonNull(principal);
    }

    public Principal getPrincipal() {
        return this.principal;
    }
}
