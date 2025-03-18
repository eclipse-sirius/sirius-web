/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.task.starter.services;

import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerLabelServiceDelegate;
import org.springframework.stereotype.Service;

/**
 *  This class allows to override {@link IEditService} behavior.
 *
 * @author Laurent Fasani
 */
@Service
public class TaskEditServiceDelegate implements IExplorerLabelServiceDelegate {

    @Override
    public boolean canHandle(Object object) {
        return object instanceof TaskTag;
    }

    @Override
    public boolean isEditable(Object self) {
        return true;
    }

    @Override
    public void editLabel(Object self, String newValue) {
        if (self instanceof TaskTag tag) {
            String[] split = newValue.split("::");
            if (split.length > 0) {
                tag.setPrefix(split[0]);
            }
            if (split.length > 1) {
                tag.setSuffix(split[1]);
            }
        }
    }
}
