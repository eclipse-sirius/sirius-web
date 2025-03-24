/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form;

import java.util.Optional;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;

/**
 * Provides a switch to convert View-based custom widget descriptions into their API equivalent given an execution context.
 *
 * @author pcdavid
 */
public interface IWidgetConverterProvider {

    Switch<Optional<AbstractWidgetDescription>> getWidgetConverter(AQLInterpreter interpreter);
}
