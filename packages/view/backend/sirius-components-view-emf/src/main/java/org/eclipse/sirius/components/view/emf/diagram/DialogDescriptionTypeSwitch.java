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
package org.eclipse.sirius.components.view.emf.diagram;

import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.diagram.util.DiagramSwitch;

/**
 * A switch dedicated to retrieve dialog kind value.
 *
 * @author fbarbin
 */
public class DialogDescriptionTypeSwitch extends DiagramSwitch<String> {

    @Override
    public String caseSelectionDialogDescription(SelectionDialogDescription selectionDialogDescription) {
        return SelectionDescription.TYPE;
    }

}
