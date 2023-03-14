/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.api;

/**
 * The border of an element.
 *
 * @param top The border top
 * @param right The border right
 * @param bottom The border bottom
 * @param left The border left
 *
 * @author sbegaudeau
 */
public record Border(int top, int right, int bottom, int left) {
}
