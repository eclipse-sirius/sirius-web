/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
export type Status = 'DRAFT' | 'PUBLISHED';

export interface Modeler {
  id: string;
  name: string;
  status: Status;
}

export interface GQLGetModelersQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  project: GQLProject;
}

export interface GQLProject {
  modelers: GQLModeler[];
}

export interface GQLModeler {
  id: string;
  name: string;
  status: Status;
}

export interface GQLGetModelersQueryVariables {
  projectId: string;
}

export interface ModelersTableProps {
  projectId: string;
  modelers: Modeler[];
  onMore: (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, modeler: Modeler) => void;
}

export interface ModelerContextMenuProps {
  menuAnchor: HTMLElement;
  onClose: () => void;
  onRename: () => void;
  onPublish: () => void;
  modeler: Modeler;
}
