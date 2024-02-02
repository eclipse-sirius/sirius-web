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
package org.eclipse.sirius.web.domain.services;

import java.util.Objects;

/**
 * Used to indicate that an invariant of the domain has been violated.
 *
 * @param message Description of the violation
 * @param <T> The expected type of the result
 *
 * @author sbegaudeau
 */
public record Failure<T>(String message) implements IResult<T> {
    public Failure {
        Objects.requireNonNull(message);
    }
}
