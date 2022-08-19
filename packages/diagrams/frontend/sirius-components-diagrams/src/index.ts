/*******************************************************************************
 * Copyright (c) 2022 Obeo and others.
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
// Required because Sprotty uses Inversify and both frameworks are written in TypeScript with experimental features.
import 'reflect-metadata';
export * from './representation/DiagramRepresentation';
