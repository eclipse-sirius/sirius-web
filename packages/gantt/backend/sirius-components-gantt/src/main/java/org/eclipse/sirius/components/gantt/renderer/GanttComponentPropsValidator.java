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
package org.eclipse.sirius.components.gantt.renderer;

import org.eclipse.sirius.components.gantt.renderer.component.GanttComponent;
import org.eclipse.sirius.components.gantt.renderer.component.GanttComponentProps;
import org.eclipse.sirius.components.gantt.renderer.component.TaskComponent;
import org.eclipse.sirius.components.gantt.renderer.component.TaskComponentProps;
import org.eclipse.sirius.components.representations.IComponentPropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of a component type.
 *
 * @author lfasani
 */
public class GanttComponentPropsValidator implements IComponentPropsValidator {

    @Override
    public boolean validateComponentProps(Class<?> componentType, IProps props) {
        boolean checkValidProps = false;

        if (GanttComponent.class.equals(componentType)) {
            checkValidProps = props instanceof GanttComponentProps;
        } else if (TaskComponent.class.equals(componentType)) {
            checkValidProps = props instanceof TaskComponentProps;
        }

        return checkValidProps;
    }
}
