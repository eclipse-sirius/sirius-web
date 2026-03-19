/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.Set;

import org.eclipse.sirius.components.diagrams.EdgeStyle;

/**
 * Wrapper for an edge style and the list of its customized style properties.
 *
 * @author mcharfadi
 */
public record EdgeAppearance(EdgeStyle style, Set<String> customizedStyleProperties) {

}
