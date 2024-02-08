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

import org.eclipse.sirius.components.diagrams.elements.DiagramElementProps;
import org.eclipse.sirius.components.diagrams.elements.EdgeElementProps;
import org.eclipse.sirius.components.diagrams.elements.InsideLabelElementProps;
import org.eclipse.sirius.components.diagrams.elements.LabelElementProps;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.elements.OutsideLabelElementProps;
import org.eclipse.sirius.components.representations.IInstancePropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of an instance type.
 *
 * @author sbegaudeau
 */
public class DiagramInstancePropsValidator implements IInstancePropsValidator {

    @Override
    public boolean validateInstanceProps(String type, IProps props) {
        boolean checkValidProps = false;

        if (DiagramElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof DiagramElementProps;
        } else if (NodeElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof NodeElementProps;
        } else if (EdgeElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof EdgeElementProps;
        } else if (LabelElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof LabelElementProps;
        } else if (InsideLabelElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof InsideLabelElementProps;
        } else if (OutsideLabelElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof OutsideLabelElementProps;
        }

        return checkValidProps;
    }

}
