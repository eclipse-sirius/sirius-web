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

export interface UseCurrentProjectValue {
  project: Project;
}

export interface Project {
  id: string;
  name: string;
  natures: Nature[];
  currentEditingContext: EditingContext;
}

export interface Nature {
  name: string;
}

export interface EditingContext {
  id: string;
}
