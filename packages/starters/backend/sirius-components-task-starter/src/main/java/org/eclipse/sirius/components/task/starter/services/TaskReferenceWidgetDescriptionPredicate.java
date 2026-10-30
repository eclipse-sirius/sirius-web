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
package org.eclipse.sirius.components.task.starter.services;

import org.eclipse.sirius.components.task.starter.services.api.ITaskReferenceWidgetDescriptionPredicate;
import org.springframework.stereotype.Service;

/**
 * Used to test if a reference widget description belongs to custom task properties.
 *
 * @author tgiraudet
 */
@Service
public class TaskReferenceWidgetDescriptionPredicate implements ITaskReferenceWidgetDescriptionPredicate {

    @Override
    public boolean test(String referenceWidgetDescriptionId) {
        return AbstractTaskPropertiesConfigurer.DEPENDENCIES_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || AbstractTaskPropertiesConfigurer.TAGS_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || AbstractTaskPropertiesConfigurer.PERSONS_DESCRIPTION_ID.equals(referenceWidgetDescriptionId)
                || AbstractTaskPropertiesConfigurer.TEAMS_DESCRIPTION_ID.equals(referenceWidgetDescriptionId);
    }
}
