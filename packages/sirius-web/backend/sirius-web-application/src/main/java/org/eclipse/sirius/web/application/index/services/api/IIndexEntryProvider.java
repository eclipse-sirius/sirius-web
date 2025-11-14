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
package org.eclipse.sirius.web.application.index.services.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Provides index entries.
 *
 * @author gdaniel
 */
public interface IIndexEntryProvider {

    Optional<IIndexEntry> getIndexEntry(IEditingContext editingContext, Object object);
}
