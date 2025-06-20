/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

package org.eclipse.sirius.components.emf.forms.api;

import java.util.function.Function;

import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to indicate if a widget from the EMF form description should be read only.
 *
 * @author sbegaudeau
 */
public interface IWidgetReadOnlyProvider extends Function<VariableManager, Boolean> {
}
