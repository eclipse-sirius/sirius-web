/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.renderer;

import org.eclipse.sirius.components.diagrams.components.DiagramComponent;
import org.eclipse.sirius.components.diagrams.components.DiagramComponentProps;
import org.eclipse.sirius.components.diagrams.components.EdgeComponent;
import org.eclipse.sirius.components.diagrams.components.EdgeComponentProps;
import org.eclipse.sirius.components.diagrams.components.InsideLabelComponent;
import org.eclipse.sirius.components.diagrams.components.InsideLabelComponentProps;
import org.eclipse.sirius.components.diagrams.components.LabelComponent;
import org.eclipse.sirius.components.diagrams.components.LabelComponentProps;
import org.eclipse.sirius.components.diagrams.components.NodeComponent;
import org.eclipse.sirius.components.diagrams.components.NodeComponentProps;
import org.eclipse.sirius.components.diagrams.components.OutsideLabelComponent;
import org.eclipse.sirius.components.diagrams.components.OutsideLabelComponentProps;
import org.eclipse.sirius.components.representations.IComponentPropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of a component type.
 *
 * @author sbegaudeau
 */
public class DiagramComponentPropsValidator implements IComponentPropsValidator {

    @Override
    public boolean validateComponentProps(Class<?> componentType, IProps props) {
        boolean checkValidProps = false;

        if (DiagramComponent.class.equals(componentType)) {
            checkValidProps = props instanceof DiagramComponentProps;
        } else if (NodeComponent.class.equals(componentType)) {
            checkValidProps = props instanceof NodeComponentProps;
        } else if (EdgeComponent.class.equals(componentType)) {
            checkValidProps = props instanceof EdgeComponentProps;
        } else if (LabelComponent.class.equals(componentType)) {
            checkValidProps = props instanceof LabelComponentProps;
        } else if (InsideLabelComponent.class.equals(componentType)) {
            checkValidProps = props instanceof InsideLabelComponentProps;
        } else if (OutsideLabelComponent.class.equals(componentType)) {
            checkValidProps = props instanceof OutsideLabelComponentProps;
        }

        return checkValidProps;
    }

}
