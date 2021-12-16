/*******************************************************************************
 * Copyright (c) 2021 Obeo and others.
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
import { ReactNode } from 'react';

export interface Selection {
  entries: SelectionEntry[];
}

export interface SelectionEntry {
  id: string;
  label: string;
  kind: string;
}

export interface GQLEditingContextEventPayload {
  __typename: string;
}

export interface GQLRepresentationRenamedEventPayload extends GQLEditingContextEventPayload {
  id: string;
  representationId: string;
  newLabel: string;
}

export type GQLEditingContextEventSubscription = {
  editingContextEvent: GQLEditingContextEventPayload;
};

export type Representation = {
  id: string;
  label: string;
  kind: string;
};

export type WorkbenchProps = {
  editingContextId: string;
  initialRepresentationSelected: Representation;
  onRepresentationSelected: (representation: Representation) => void;
  readOnly: boolean;
  children: ReactNode;
};

export type RepresentationComponentProps = {
  editingContextId: string;
  representationId: string;
  label: string;
  readOnly: boolean;
  selection: Selection;
  setSelection: (selection: Selection) => void;
};

export type RepresentationComponent = (props: RepresentationComponentProps) => JSX.Element;
