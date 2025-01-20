/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.infrastructure.configuration.mvc;

/**
 * Interface used to declare paths which corresponds to paths handled by the backend itself (as opposed to paths
 * which corresponds to routes handled by the frontend code itself).
 *
 * @author pcdavid
 */
public interface IBackendPathPredicate {
    boolean isBackendPath(String path);
}
