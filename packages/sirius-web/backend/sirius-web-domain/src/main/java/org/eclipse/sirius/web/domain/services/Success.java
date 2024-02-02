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
 * Used to indicate that a transformation of the domain has been performed successfully.
 *
 * @param data The data
 * @param <T> The expected type of the result
 *
 * @author sbegaudeau
 */
public record Success<T>(T data) implements IResult<T> {
}
