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
import { GQLLibrary } from './useLibraries.types';

export type EditLibraryViewParams = 'namespace' | 'name' | 'version';

export type EditLibraryViewState = {
  data: GQLGetLibraryQueryData;
};

export interface GQLGetLibraryQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  library: GQLLibrary | null;
}

export interface GQLGetLibrariesQueryVariables {
  name: string;
  namespace: string;
  version: string;
}
