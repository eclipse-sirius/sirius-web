/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.diagrams.description;

import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.diagrams.NodeDecoratorPosition;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * A decorator description.
 *
 * @author gdaniel
 */
public interface IDecoratorDescription {

    String getId();

    Function<VariableManager, String> getLabelProvider();

    Predicate<VariableManager> getPreconditionPredicate();

    Function<VariableManager, String> getIconURLProvider();

    NodeDecoratorPosition getPosition();
}
