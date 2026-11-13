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
package org.eclipse.sirius.components.collaborative.trees.api;

import org.eclipse.sirius.components.trees.Tree;

/**
 * Used to filter a tree by its item labels.
 *
 * @author mcharfadi
 * @since 2026.11.0
 */
public interface ITreeFilter {

    Tree filter(Tree tree, String searchedValue);
}
