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
package org.eclipse.sirius.web.services.api.dto;

/**
 * Interface implemented by all the inputs coming from the clients. An input represents a request from the client to
 * perform some operation on the system that will change its state (i.e. a mutation). Performing the operation will
 * produce one payload which represents the result of the operations.
 *
 * @author sbegaudeau
 */
public interface IInput {
    // Do nothing on purpose
}
