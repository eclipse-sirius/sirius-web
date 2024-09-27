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
package org.eclipse.sirius.components.collaborative.trees.dto;

import org.eclipse.sirius.components.trees.FetchTreeItemContextMenuEntryKind;

/**
 * Used to represent the data associated to a fetch entry inside a context menu of a tree item.
 *
 * @author Jerome Gout
 */
public record FetchTreeItemContextMenuEntryData(String urlToFetch, FetchTreeItemContextMenuEntryKind fetchKind) { }