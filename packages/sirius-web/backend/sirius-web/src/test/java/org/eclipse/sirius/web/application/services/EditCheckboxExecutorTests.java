/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription;
import org.eclipse.sirius.components.view.table.customcells.CustomcellsFactory;
import org.eclipse.sirius.web.application.views.table.customcells.EditCheckboxExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Used to test the edit execution service of the checkbox table cell.
 *
 * @author Jerome Gout
 */
public class EditCheckboxExecutorTests {

    @Test
    @DisplayName("Given a checkbox cell description with a bad operation, then an error is returned")
    public void givenACheckboxCellDescriptionWithBadOperationThenErrorIsReturned() {
        EditCheckboxExecutor executor = new EditCheckboxExecutor(new IFeedbackMessageService.NoOp(), new IEditService.NoOp(), new IViewEMFMessageService.NoOp());

        VariableManager variableManager = new VariableManager();

        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of());

        Operation badOperation = ViewFactory.eINSTANCE.createSetValue();
        CellCheckboxWidgetDescription checkboxWidgetDescription = CustomcellsFactory.eINSTANCE.createCellCheckboxWidgetDescription();
        checkboxWidgetDescription.getBody().add(badOperation);

        IStatus status = executor.execute(variableManager, interpreter, checkboxWidgetDescription);

        assertThat(status).isInstanceOf(Failure.class);
    }
}
