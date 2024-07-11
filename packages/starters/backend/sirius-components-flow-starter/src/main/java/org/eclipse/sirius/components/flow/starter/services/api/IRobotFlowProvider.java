/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.flow.starter.services.api;

import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Used to provide the robot flow sample.
 *
 * @author sbegaudeau
 */
public interface IRobotFlowProvider {
    UUID addRobotFlow(ResourceSet resourceSet, String resourceName);
}
