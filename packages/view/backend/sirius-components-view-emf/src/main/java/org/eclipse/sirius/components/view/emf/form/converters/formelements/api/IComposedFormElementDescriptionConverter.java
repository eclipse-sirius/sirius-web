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
package org.eclipse.sirius.components.view.emf.form.converters.formelements.api;

import java.util.Optional;

import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.form.FormElementDescription;

/**
 * Used to convert composed form element descriptions.
 *
 * @author sbegaudeau
 */
public interface IComposedFormElementDescriptionConverter {
    Optional<AbstractControlDescription> convert(FormElementDescription formElementDescription, AQLInterpreter interpreter);
}
