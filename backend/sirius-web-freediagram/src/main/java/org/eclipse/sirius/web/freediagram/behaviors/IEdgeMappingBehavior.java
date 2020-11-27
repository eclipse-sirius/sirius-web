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
package org.eclipse.sirius.web.freediagram.behaviors;

import java.util.List;

import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Describe the expected edge mapping behavior (semanticElements, sourceElements, targetElements) according to the
 * {@link VariableManager} context.
 *
 * @author hmarchadour
 */
public interface IEdgeMappingBehavior {

    /**
     * Describe the expected semantic elements list according to the given {@link VariableManager}.
     */
    List<Object> getSemanticElements(VariableManager variableManager);

    /**
     * Describe the expected source elements list according to the given {@link VariableManager}.
     */
    List<Object> getSourceElements(VariableManager variableManager);

    /**
     * Describe the expected target elements list according to the given {@link VariableManager}.
     */
    List<Object> getTargetElements(VariableManager variableManager);

}
