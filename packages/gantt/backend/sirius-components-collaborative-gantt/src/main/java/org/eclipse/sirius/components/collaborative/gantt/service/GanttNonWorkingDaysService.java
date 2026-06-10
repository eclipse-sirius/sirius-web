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
package org.eclipse.sirius.components.collaborative.gantt.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.gantt.api.IGanttNonWorkingDaysService;
import org.eclipse.sirius.components.collaborative.gantt.dto.GetNonWorkingDaysEventPayload;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.GetNonWorkingDaysInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.NonWorkingDays;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Service used to manage non-working days.
 *
 * @author ncouvert
 */
@Service
public class GanttNonWorkingDaysService implements IGanttNonWorkingDaysService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public GanttNonWorkingDaysService(IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    private Optional<GanttDescription> findGanttDescription(String ganttDescriptionId, IEditingContext editingContext) {
        return this.representationDescriptionSearchService.findById(editingContext, ganttDescriptionId).filter(GanttDescription.class::isInstance).map(GanttDescription.class::cast);
    }

    @Override
    public IPayload getNonWorkingDays(GetNonWorkingDaysInput getNonWorkingDaysInput, IEditingContext editingContext, Gantt gantt) {
        IPayload payload = new ErrorPayload(getNonWorkingDaysInput.id(), "Get non-working days failed");
        Optional<GanttDescription> ganttDescriptionOpt = this.findGanttDescription(gantt.descriptionId(), editingContext);

        if (ganttDescriptionOpt.isPresent()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

            List<String> holidays = Optional.ofNullable(ganttDescriptionOpt.get().holidaysProvider())
                    .map(provider -> provider.apply(variableManager))
                    .orElse(List.of());

            List<String> weekends = Optional.ofNullable(ganttDescriptionOpt.get().weekendsProvider())
                    .map(provider -> provider.apply(variableManager))
                    .orElse(List.of());

            NonWorkingDays nonWorkingDays = new NonWorkingDays(holidays, weekends);

            payload = new GetNonWorkingDaysEventPayload(getNonWorkingDaysInput.id(), nonWorkingDays);
        }

        return payload;
    }
}
