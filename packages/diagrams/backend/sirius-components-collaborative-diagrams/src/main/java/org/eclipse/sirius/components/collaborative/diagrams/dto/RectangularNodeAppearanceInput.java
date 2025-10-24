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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import org.eclipse.sirius.components.diagrams.LineStyle;

/**
 * Input for the edition of a rectangular node's appearance.
 *
 * @author nvannier
 */
public record RectangularNodeAppearanceInput(String background, String borderColor, Integer borderRadius, Integer borderSize, LineStyle borderStyle) {

}
