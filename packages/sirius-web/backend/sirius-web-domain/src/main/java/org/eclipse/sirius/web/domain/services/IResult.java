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

/**
 * Interface used as the return value of all mutations of the system performed by domain services.
 *
 * @param <T> The expected type of the result
 *
 * @author sbegaudeau
 */
public sealed interface IResult<T> permits Failure, Success {
    // Nothing on purpose
}
