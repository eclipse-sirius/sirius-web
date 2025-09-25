/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo and others.
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
import React, { FC, ForwardRefExoticComponent, MemoExoticComponent, PropsWithoutRef, RefAttributes } from 'react';
import { Selection } from '../selection/SelectionContext.types';
import { WorkbenchPanelHandle } from './Panels.types';

export type WorkbenchProps = {
  editingContextId: string;
  initialRepresentationSelected: RepresentationMetadata | null;
  onRepresentationSelected: (representation: RepresentationMetadata | null) => void;
  readOnly: boolean;
  initialWorkbenchConfiguration: WorkbenchConfiguration | null;
};

export type WorkbenchState = {
  id: string;
  displayedRepresentationMetadata: RepresentationMetadata | null;
  representationsMetadata: RepresentationMetadata[];
};

export type RepresentationMetadata = {
  id: string;
  label: string;
  kind: string;
  iconURLs: string[];
  description: RepresentationDescription;
};

export type RepresentationDescription = {
  id: string;
};

export type WorkbenchViewSide = 'left' | 'right';

export interface MainAreaComponentProps {
  editingContextId: string;
  readOnly: boolean;
}

export type RepresentationComponentProps = {
  editingContextId: string;
  representationId: string;
  readOnly: boolean;
};

export type RepresentationComponent =
  | FC<RepresentationComponentProps>
  | MemoExoticComponent<FC<RepresentationComponentProps>>
  | ForwardRefExoticComponent<
      PropsWithoutRef<RepresentationComponentProps> & RefAttributes<WorkbenchMainRepresentationHandle | null>
    >
  | MemoExoticComponent<
      ForwardRefExoticComponent<
        PropsWithoutRef<RepresentationComponentProps> & RefAttributes<WorkbenchMainRepresentationHandle | null>
      >
    >;

export type RepresentationComponentFactory = {
  (representationMetadata: RepresentationMetadata): RepresentationComponent | null;
};

export interface WorkbenchHandle {
  getConfiguration(): WorkbenchConfiguration;
}

export interface WorkbenchPanelsHandle {
  getWorkbenchPanelConfigurations: () => WorkbenchSidePanelConfiguration[];
  getWorkbenchPanelHandles: () => WorkbenchPanelHandle[];
}

export interface RepresentationNavigationHandle {
  getMainPanelConfiguration: () => WorkbenchMainPanelConfiguration | null;
}

export interface WorkbenchViewContribution {
  id: string;
  side: WorkbenchViewSide;
  title: string;
  icon: React.ReactElement;
  component: ForwardRefExoticComponent<
    PropsWithoutRef<WorkbenchViewComponentProps> & RefAttributes<WorkbenchViewHandle>
  >;
}

export interface WorkbenchViewComponentProps {
  id: string;
  editingContextId: string;
  readOnly: boolean;
  initialConfiguration: WorkbenchViewConfiguration | null;
}

export interface WorkbenchViewHandle {
  id: string;
  getWorkbenchViewConfiguration: () => Record<string, unknown>;
  applySelection: ((selection: Selection) => void) | null;
}

export interface WorkbenchConfiguration {
  mainPanel: WorkbenchMainPanelConfiguration | null;
  workbenchPanels: WorkbenchSidePanelConfiguration[];
}

export interface WorkbenchSidePanelConfiguration {
  id: string;
  isOpen: boolean;
  views: WorkbenchViewConfiguration[];
}

export interface WorkbenchViewConfiguration {
  id: string;
  isActive: boolean;
}

export interface WorkbenchMainPanelConfiguration {
  id: string;
  representationEditors: WorkbenchRepresentationEditorConfiguration[];
}

export interface WorkbenchRepresentationEditorConfiguration {
  representationId: string;
  isActive: boolean;
}

export interface WorkbenchMainRepresentationHandle {
  id: string;
  applySelection: ((selection: Selection) => void) | null;
}
