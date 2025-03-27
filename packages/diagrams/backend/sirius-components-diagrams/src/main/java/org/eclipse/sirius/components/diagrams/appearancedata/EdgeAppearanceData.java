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
package org.eclipse.sirius.components.diagrams.appearancedata;

import java.util.List;

/**
 * The customized appearance data of an edge and its labels.
 *
 * @author nvannier
 */
public record EdgeAppearanceData(String id, EdgeCustomizedStyle customizedEdgeStyle, List<LabelAppearanceData> labelsAppearanceData) {

    public static final String EDGE_APPEARANCE_DATA = "edgeAppearanceData";
}
