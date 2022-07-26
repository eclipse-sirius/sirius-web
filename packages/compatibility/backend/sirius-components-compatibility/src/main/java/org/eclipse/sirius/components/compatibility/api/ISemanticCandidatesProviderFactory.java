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

import java.util.List;
import java.util.function.Function;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Implementations of this interface should return a function used to provide semantic candidates.
 *
 * @author sbegaudeau
 */
public interface ISemanticCandidatesProviderFactory {
    Function<VariableManager, List<?>> getSemanticCandidatesProvider(AQLInterpreter interpreter, String domainClass, String semanticCandidatesExpression, String preconditionExpression);
}
