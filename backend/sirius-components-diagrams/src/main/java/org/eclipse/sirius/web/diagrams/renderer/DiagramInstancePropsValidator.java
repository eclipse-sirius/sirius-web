/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.diagrams.renderer;

import org.eclipse.sirius.web.diagrams.elements.DiagramElementProps;
import org.eclipse.sirius.web.diagrams.elements.EdgeElementProps;
import org.eclipse.sirius.web.diagrams.elements.LabelElementProps;
import org.eclipse.sirius.web.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.web.representations.IInstancePropsValidator;
import org.eclipse.sirius.web.representations.IProps;

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
        }

        return checkValidProps;
    }

}
