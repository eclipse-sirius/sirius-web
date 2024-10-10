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
package org.eclipse.sirius.components.trees;

import org.eclipse.sirius.components.representations.VariableManager;

import java.util.List;
import java.util.function.Function;

/**
 * Interface implemented by all the tree item context menu entries.
 *
 * @author Jerome Gout
 */
public interface ITreeItemContextMenuEntry {
    String getId();

    Function<VariableManager, String> getLabel();

    Function<VariableManager, List<String>> getIconURL();
}
