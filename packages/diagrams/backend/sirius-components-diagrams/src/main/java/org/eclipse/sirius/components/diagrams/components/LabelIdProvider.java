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
package org.eclipse.sirius.components.diagrams.components;

import java.util.UUID;

/**
 * Used to compute the identifier of a label.
 *
 * @author frouene
 */
public class LabelIdProvider {

    public static final String INSIDE_LABEL_SUFFIX = "_insideLabel";
    public static final String OUTSIDE_LABEL_SUFFIX = "_outsideLabel";
    public static final String EDGE_BEGIN_LABEL_SUFFIX = "_beginlabel";
    public static final String EDGE_CENTER_LABEL_SUFFIX = "_centerlabel";
    public static final String EDGE_END_LABEL_SUFFIX = "_endlabel";

    public String getInsideLabelId(String parentId) {
        return UUID.nameUUIDFromBytes((parentId + INSIDE_LABEL_SUFFIX).getBytes()).toString();
    }

    public String getOutsideLabelId(String parentId, String position) {
        return UUID.nameUUIDFromBytes((parentId + OUTSIDE_LABEL_SUFFIX + position).getBytes()).toString();
    }

    public String getEdgeLabelId(String parentId, String position) {
        return UUID.nameUUIDFromBytes((parentId + position).getBytes()).toString();
    }
}
