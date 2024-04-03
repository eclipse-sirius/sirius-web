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
package org.eclipse.sirius.components.collaborative.gantt;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.gantt.api.IGanttContext;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.renderer.events.IGanttEvent;

/**
 * The Gantt Context implementation.
 *
 * @author lfasani
 */
public class GanttContext implements IGanttContext {

    private Gantt gantt;

    private IGanttEvent ganttEvent;

    public GanttContext(Gantt initialGantt) {
        this.gantt = Objects.requireNonNull(initialGantt);
    }

    @Override
    public void update(Gantt newGantt) {
        this.gantt = Objects.requireNonNull(newGantt);
    }

    @Override
    public Gantt getGantt() {
        return this.gantt;
    }

    @Override
    public void reset() {
        this.ganttEvent = null;
    }

    @Override
    public IGanttEvent getGanttEvent() {
        return this.ganttEvent;
    }

    @Override
    public void setGanttEvent(IGanttEvent ganttEvent) {
        this.ganttEvent = Objects.requireNonNull(ganttEvent);
    }

}
