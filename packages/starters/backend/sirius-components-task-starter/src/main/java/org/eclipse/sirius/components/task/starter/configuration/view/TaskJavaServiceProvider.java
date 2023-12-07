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
package org.eclipse.sirius.components.task.starter.configuration.view;

import java.util.List;

import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.springframework.stereotype.Service;

/**
 * Used to provide Java services for the Task related views.
 *
 * @author lfasani
 */
@Service
public class TaskJavaServiceProvider implements IJavaServiceProvider {

    @Override
    public List<Class<?>> getServiceClasses(View view) {
        boolean isTaskRelatedView = view.getDescriptions().stream()
                .filter(GanttDescription.class::isInstance)
                .map(GanttDescription.class::cast)
                .anyMatch(ganttDescription -> ganttDescription.getDomainType().equals("task::Task"));
        if (isTaskRelatedView) {
            return List.of(TaskJavaService.class);
        }
        return List.of();
    }

}
