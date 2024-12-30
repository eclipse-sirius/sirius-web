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
package org.eclipse.sirius.web.data;

import java.util.UUID;

/**
 * Used to store some test identifiers related to flow projects.
 *
 * @author gcoutable
 */
public final class FlowIdentifiers {

    public static final UUID SAMPLE_FLOW_PROJECT = UUID.fromString("02b932be-8ba9-40ff-a6e2-61630d47f398");

    public static final String SAMPLE_FLOW_TOPOGRAPHY_UNSYNCHRONIZED_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=fac1c3c2-cc89-30fc-ada3-2c51f24d5275";

    public static final UUID UNSYNCHRONIZED_TOPOGRAPHY_WITH_CENTRAL_UNIT_REPRESENTATION_ID = UUID.fromString("a3ca97fa-bf7d-4b15-861d-e88cee71da42");

    public static final UUID FLOW_SYSTEM_ID = UUID.fromString("0aaa281a-b6b6-4140-b610-582509bc1158");

    public static final UUID FLOW_CENTRAL_UNIT_ID = UUID.fromString("bec959eb-092b-4842-95a9-6de2329bd68d");
}
