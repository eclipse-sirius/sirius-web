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
package org.eclipse.sirius.web.application.studio.services.library.api;

import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * Used to execute a library update.
 *
 * @author gdaniel
 */
public interface IUpdateLibraryExecutor {

    IStatus updateLibrary(IEditingContext editingContext, UUID libraryId, ICause cause);

}
