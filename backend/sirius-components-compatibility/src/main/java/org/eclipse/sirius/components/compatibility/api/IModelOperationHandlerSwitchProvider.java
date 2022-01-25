/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.compatibility.api;

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;

/**
 * Used to provide an instance of the model operation handler switch which will then be used to execute model
 * operations.
 *
 * @author sbegaudeau
 */
public interface IModelOperationHandlerSwitchProvider {
    Function<ModelOperation, Optional<IModelOperationHandler>> getModelOperationHandlerSwitch(AQLInterpreter interpreter);
}
