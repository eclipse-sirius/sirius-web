/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.gantt.dto.input.GetNonWorkingDaysInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.gantt.Gantt;

/**
 * Service used to manage gantt non-working days.
 *
 * @author ncouvert
 */
public interface IGanttNonWorkingDaysService {

    /**
     * Get Gantt's non-working days using the given parameters.
     */
    IPayload getNonWorkingDays(GetNonWorkingDaysInput getNonWorkingDaysInput, IEditingContext editingContext, Gantt gantt);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author ncouvert
     */
    class NoOp implements IGanttNonWorkingDaysService {

        @Override
        public IPayload getNonWorkingDays(GetNonWorkingDaysInput getNonWorkingDaysInput, IEditingContext editingContext, Gantt gantt) {
            return null;
        }

    }
}
