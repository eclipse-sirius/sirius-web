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
package org.eclipse.sirius.components.emf.configuration;

import java.util.function.Supplier;

import org.eclipse.emf.edit.provider.IChildCreationExtender;

/**
 * Used to extend EMF's global IChildCreationExtender.Descriptor.Registry with new IChildCreationExtender.
 * Corresponds to the {@code org.eclipse.emf.edit.childCreationExtenders} extension point configuration.
 *
 * @author pcdavid
 */
public record ChildExtenderProvider(String nsURI, Supplier<IChildCreationExtender> childExtenderProvider) {
}
