/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.charts.hierarchy.renderer;

import org.eclipse.sirius.components.charts.hierarchy.elements.HierarchyElementProps;
import org.eclipse.sirius.components.charts.hierarchy.elements.HierarchyNodeElementProps;
import org.eclipse.sirius.components.representations.IInstancePropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of the instance type.
 *
 * @author sbegaudeau
 */
public class HierarchyInstancePropsValidator implements IInstancePropsValidator {

    @Override
    public boolean validateInstanceProps(String type, IProps props) {
        boolean checkValidProps = false;

        if (HierarchyElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof HierarchyElementProps;
        } else if (HierarchyNodeElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof HierarchyNodeElementProps;
        }

        return checkValidProps;
    }

}
