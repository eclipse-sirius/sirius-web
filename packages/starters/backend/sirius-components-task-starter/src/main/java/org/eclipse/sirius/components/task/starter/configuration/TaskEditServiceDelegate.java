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
package org.eclipse.sirius.components.task.starter.configuration;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditServiceDelegate;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.DefaultEditService;
import org.eclipse.sirius.components.emf.services.ISuggestedRootObjectTypesProvider;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.components.task.TaskTag;
import org.springframework.stereotype.Service;

/**
 *  This class allows to override {@link IEditService} behavior.
 *
 * @author Laurent Fasani
 */
@Service
public class TaskEditServiceDelegate extends DefaultEditService implements IEditServiceDelegate {

    public TaskEditServiceDelegate(IEMFKindService emfKindService, ComposedAdapterFactory composedAdapterFactory, ISuggestedRootObjectTypesProvider suggestedRootObjectsProvider,
            IObjectService objectService, IFeedbackMessageService feedbackMessageService, IEMFMessageService messageService) {
        super(emfKindService, composedAdapterFactory, suggestedRootObjectsProvider, objectService, feedbackMessageService, messageService);
    }

    @Override
    public boolean canHandle(Object object) {
        return object instanceof TaskTag;
    }

    @Override
    public boolean canHandle(IEditingContext editingContext) {
        return true;
    }

    @Override
    public void editLabel(Object object, String labelField, String newValue) {
        if (object instanceof TaskTag tag) {
            String[] split = newValue.split("::");
            if (split.length > 0) {
                tag.setPrefix(split[0]);
            }
            if (split.length > 1) {
                tag.setSuffix(split[1]);
            }
        } else {
            super.editLabel(object, labelField, newValue);
        }
    }
}
