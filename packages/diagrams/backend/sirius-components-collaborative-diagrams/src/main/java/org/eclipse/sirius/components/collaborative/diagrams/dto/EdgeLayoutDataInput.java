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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.List;

import org.eclipse.sirius.components.diagrams.layoutdata.Position;

/**
 * Input used to receive edge layout data.
 *
 * @author frouene
 */
public record EdgeLayoutDataInput(String id, List<Position> bendingPoints) {

}
