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
package org.eclipse.sirius.components.collaborative.trees.services.api;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.description.TreeDescription;

/**
 * Interface  for the TreeDescriptionBuilder service. This service is intended to help building specific tree descriptions.
 * @author fbarbin
 */
public interface ITreeDescriptionBuilder {

    TreeDescription createSelectableElementsTreeDescription(String descriptionId, String representationName, Predicate<VariableManager> canCreatePredicate, Function<VariableManager, Boolean> isSelectableProvider,
            Function<VariableManager, List<?>> elementsProvider);
}
