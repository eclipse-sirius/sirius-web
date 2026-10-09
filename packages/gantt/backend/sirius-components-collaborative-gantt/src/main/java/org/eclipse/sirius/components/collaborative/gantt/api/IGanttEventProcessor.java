/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.gantt.GanttContext;
import org.eclipse.sirius.components.events.ICause;

/**
 * Interface implemented by the gantt event processor.
 *
 * @author lfasani
 */
public interface IGanttEventProcessor extends IRepresentationEventProcessor {

    /**
     * Returns the current representation context.
     *
     * @return The current representation context
     * @since v2026.11.0
     */
    GanttContext getRepresentationContext();

    /**
     * Used to update the content of the representation event processor.
     *
     * @param cause The cause which has triggered the update
     * @param representationContext The new version of the representation context
     *
     * @technical-debt This API should not be considered stable for the moment, it is still being evaluated against the
     * various use cases of our event processors
     */
    void update(ICause cause, GanttContext representationContext);
}
