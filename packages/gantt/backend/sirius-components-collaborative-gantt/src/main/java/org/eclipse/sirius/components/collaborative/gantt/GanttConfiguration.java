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
package org.eclipse.sirius.components.collaborative.gantt;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration used to create a gantt event processor.
 *
 * @author lfasani
 */
public class GanttConfiguration implements IRepresentationConfiguration {

    private final UUID ganttId;

    public GanttConfiguration(UUID ganttId) {
        this.ganttId = Objects.requireNonNull(ganttId);
    }

    @Override
    public String getId() {
        return this.ganttId.toString();
    }

}
