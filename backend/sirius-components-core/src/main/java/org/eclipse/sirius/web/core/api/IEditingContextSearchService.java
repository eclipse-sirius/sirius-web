/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.core.api;

import java.util.Optional;

/**
 * Interface used to determine if an editing context exist and to retrieve it.
 *
 * @author sbegaudeau
 */
public interface IEditingContextSearchService {

    boolean existsById(String editingContextId);

    Optional<IEditingContext> findById(String editingContextId);

}
