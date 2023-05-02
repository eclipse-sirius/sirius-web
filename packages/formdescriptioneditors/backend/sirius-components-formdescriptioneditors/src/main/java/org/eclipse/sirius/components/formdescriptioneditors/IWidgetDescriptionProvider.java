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
package org.eclipse.sirius.components.formdescriptioneditors;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;

/**
 * Provides the EClass to use to represent a given kind of widget in a Form Description Editor.
 *
 * @author pcdavid
 */
public interface IWidgetDescriptionProvider {
    Optional<EClass> getWidgetDescriptionType(String widgetKind);
}
