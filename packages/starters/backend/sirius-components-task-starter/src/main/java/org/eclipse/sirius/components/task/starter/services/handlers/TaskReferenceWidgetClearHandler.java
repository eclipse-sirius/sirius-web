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
package org.eclipse.sirius.components.task.starter.services.handlers;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetClearHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetReflectiveClearService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.task.starter.services.api.ITaskReferenceWidgetDescriptionPredicate;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Clears task references.
 *
 * @author tgiraudet
 */
@Service
public class TaskReferenceWidgetClearHandler implements IReferenceWidgetClearHandler {

    private final IReferenceWidgetReflectiveClearService reflectiveClearService;

    private final ITaskReferenceWidgetDescriptionPredicate taskReferenceWidgetDescriptionPredicate;

    public TaskReferenceWidgetClearHandler(IReferenceWidgetReflectiveClearService reflectiveClearService,
            ITaskReferenceWidgetDescriptionPredicate taskReferenceWidgetDescriptionPredicate) {
        this.reflectiveClearService = Objects.requireNonNull(reflectiveClearService);
        this.taskReferenceWidgetDescriptionPredicate = Objects.requireNonNull(taskReferenceWidgetDescriptionPredicate);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget) {
        return this.taskReferenceWidgetDescriptionPredicate.test(referenceWidget.getDescriptionId());
    }

    @Override
    public IStatus clear(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget) {
        return this.reflectiveClearService.clear(editingContext, referenceWidget);
    }
}
