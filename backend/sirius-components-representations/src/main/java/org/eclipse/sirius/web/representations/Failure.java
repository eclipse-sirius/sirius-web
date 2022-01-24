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
package org.eclipse.sirius.web.representations;

import java.util.Objects;

/**
 * The message when the representation description handler fails.
 *
 * @author gcoutable
 */
public class Failure implements IStatus {

    private final String message;

    public Failure(String message) {
        this.message = Objects.requireNonNull(message);
    }

    public String getMessage() {
        return this.message;
    }
}
