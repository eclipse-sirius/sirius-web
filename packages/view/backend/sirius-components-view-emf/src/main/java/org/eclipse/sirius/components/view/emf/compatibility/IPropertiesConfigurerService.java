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
package org.eclipse.sirius.components.view.emf.compatibility;

import java.util.List;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Configuration for the properties view for some of the View DSL elements.
 *
 * @author mcharfadi
 */
public interface IPropertiesConfigurerService {

    Function<VariableManager, String> getSemanticTargetIdProvider();
    Function<VariableManager, List<?>> getSemanticElementsProvider();
    Function<VariableManager, List<?>> getDiagnosticsProvider(Object feature);
    Function<Object, String> getKindProvider();
    Function<Object, String> getMessageProvider();
}
