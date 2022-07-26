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

import org.eclipse.sirius.components.charts.hierarchy.components.HierarchyComponent;
import org.eclipse.sirius.components.charts.hierarchy.components.HierarchyComponentProps;
import org.eclipse.sirius.components.charts.hierarchy.components.HierarchyNodeComponent;
import org.eclipse.sirius.components.charts.hierarchy.components.HierarchyNodeComponentProps;
import org.eclipse.sirius.components.representations.IComponentPropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of a component type.
 *
 * @author sbegaudeau
 */
public class HierarchyComponentPropsValidator implements IComponentPropsValidator {

    @Override
    public boolean validateComponentProps(Class<?> componentType, IProps props) {
        boolean checkValidProps = false;

        if (HierarchyComponent.class.equals(componentType)) {
            checkValidProps = props instanceof HierarchyComponentProps;
        } else if (HierarchyNodeComponent.class.equals(componentType)) {
            checkValidProps = props instanceof HierarchyNodeComponentProps;
        }

        return checkValidProps;
    }

}
