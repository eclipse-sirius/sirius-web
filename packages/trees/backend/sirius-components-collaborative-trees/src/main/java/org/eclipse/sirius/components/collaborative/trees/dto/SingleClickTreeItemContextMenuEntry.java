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

import java.util.List;

/**
 * Used to represent a single click entry inside a context menu of a tree item.
 *
 * @author Jerome Gout
 */
public record SingleClickTreeItemContextMenuEntry(String id, String label, List<String> iconURL) implements ITreeItemContextMenuEntry { }

