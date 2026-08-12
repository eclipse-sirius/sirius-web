/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.dto.palette;

import java.util.List;

import org.eclipse.sirius.components.collaborative.dto.KeyBinding;
import org.eclipse.sirius.components.palette.dto.ITool;

/**
 * Used to represent a single click entry inside a context menu of a tree item.
 *
 * @author mcharfadi
 */
public record SingleClickTreeItemTool(String id, String label, List<String> iconURL, boolean withImpactAnalysis, List<KeyBinding> keyBindings) implements ITool { }

