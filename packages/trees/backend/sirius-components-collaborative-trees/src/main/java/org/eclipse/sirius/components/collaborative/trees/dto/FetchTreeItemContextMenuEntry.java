/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.dto.KeyBinding;

/**
 * Used to represent a fetch entry inside a context menu of a tree item.
 *
 * @author Jerome Gout
 */
public record FetchTreeItemContextMenuEntry(String id, String label, List<String> iconURL, List<KeyBinding> keyBindings) implements ITreeItemContextMenuEntry { }
