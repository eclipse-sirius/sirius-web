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

import org.eclipse.sirius.components.gantt.renderer.elements.GanttElementProps;
import org.eclipse.sirius.components.gantt.renderer.elements.TaskElementProps;
import org.eclipse.sirius.components.representations.IInstancePropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of the instance type.
 *
 * @author lfasani
 */
public class GanttInstancePropsValidator implements IInstancePropsValidator {

    @Override
    public boolean validateInstanceProps(String type, IProps props) {
        boolean checkValidProps = false;

        if (GanttElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof GanttElementProps;
        } else if (TaskElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof TaskElementProps;
        }

        return checkValidProps;
    }

}
