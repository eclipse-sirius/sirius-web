/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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

import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.form.FormElementFor;

/**
 * Used to convert form element for.
 *
 * @author sbegaudeau
 */
public interface IFormElementForDescriptionConverter {

    Optional<ForDescription> convert(FormElementFor formElementFor, AQLInterpreter interpreter);
}
