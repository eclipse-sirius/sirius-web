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
package org.eclipse.sirius.components.collaborative.gantt.api;

import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.renderer.events.IGanttEvent;

/**
 * Information used to perform some operations on the gantt representation.
 *
 * @author lfasani
 */
public interface IGanttContext {

    void update(Gantt gantt);

    Gantt getGantt();

    void reset();

    IGanttEvent getGanttEvent();

    void setGanttEvent(IGanttEvent ganttEvent);
}
