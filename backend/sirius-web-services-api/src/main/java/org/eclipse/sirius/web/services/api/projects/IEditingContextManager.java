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
package org.eclipse.sirius.web.services.api.projects;

import java.util.UUID;

import org.eclipse.sirius.web.services.api.objects.IEditingContext;

/**
 * Interface of the editing context manager.
 *
 * @author gcoutable
 */
public interface IEditingContextManager {
    IEditingContext createEditingContext(UUID projectID);

    void persist(UUID projectId, IEditingContext editingContext);
}
