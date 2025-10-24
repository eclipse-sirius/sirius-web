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
package org.eclipse.sirius.components.collaborative.diagrams.dto.appearance;

import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.EdgeType;
import org.eclipse.sirius.components.diagrams.LineStyle;

/**
 * Input for the edition of an edge appearance.
 *
 * @author mcharfadi
 */
public record EdgeAppearanceInput(Integer size, String color, LineStyle lineStyle, ArrowStyle sourceArrowStyle, ArrowStyle targetArrowStyle, EdgeType edgeType) {

}
