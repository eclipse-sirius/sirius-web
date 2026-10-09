/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.gantt.api.IGanttContext;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.renderer.events.IGanttEvent;

/**
 * The Gantt Context implementation.
 *
 * @author lfasani
 */
public record GanttContext(Gantt representation, List<IGanttEvent> events) implements IGanttContext {
    public GanttContext {
        Objects.requireNonNull(representation);
        Objects.requireNonNull(events);
    }

    @Override
    public Gantt getGantt() {
        return this.representation;
    }

    @Override
    public void setGanttEvent(IGanttEvent ganttEvent) {
        this.events.add(ganttEvent);
    }
}
