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
package org.eclipse.sirius.components.collaborative.forms.api;

import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Interface to give the possibility to make changes on a form.
 *
 * @author frouene
 */
public interface IFormPostProcessor {

    Form postProcess(Form form, VariableManager variableManager);

    /**
     * Implementation which does nothing, used for default behavior.
     *
     * @author frouene
     */
    class NoOp implements IFormPostProcessor {

        @Override
        public Form postProcess(Form form, VariableManager variableManager) {
            return form;
        }
    }
}
