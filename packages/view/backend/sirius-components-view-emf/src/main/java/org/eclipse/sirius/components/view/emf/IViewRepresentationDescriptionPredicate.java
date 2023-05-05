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
package org.eclipse.sirius.components.view.emf;

import java.util.function.Predicate;

import org.eclipse.sirius.components.representations.IRepresentationDescription;

/**
 * Used to test if a representation description has been created by the view converter.
 *
 * @author sbegaudeau
 */
public interface IViewRepresentationDescriptionPredicate extends Predicate<IRepresentationDescription> {
}
