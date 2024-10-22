/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.tables.descriptions;

import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Common interface for cell description.
 *
 * @author frouene
 */
public interface ICellDescription {

    String getId();

    Predicate<VariableManager> getCanCreatePredicate();

    Function<VariableManager, String> getTargetObjectIdProvider();

    Function<VariableManager, String> getTargetObjectKindProvider();

}
