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
package org.eclipse.sirius.components.collaborative.forms.configuration;

import java.util.List;

import org.eclipse.sirius.components.collaborative.forms.api.FormCreationParameters;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;

/**
 * Bundles the common dependencies that {@link IFormEventProcessor} implementations need
 * into a single object for convenience.
 *
 * @author frouene
 */
public record FormEventProcessorConfiguration(IEditingContext editingContext, FormCreationParameters formCreationParameters, List<IWidgetDescriptor> widgetDescriptors,
                                              List<IFormEventHandler> formEventHandlers) {

}
