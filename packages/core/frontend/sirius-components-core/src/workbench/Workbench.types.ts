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
import React from 'react';

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

export interface WorkbenchViewContribution {
  id: string;
  side: WorkbenchViewSide;
  title: string;
  icon: React.ReactElement;
  component: (props: WorkbenchViewComponentProps) => JSX.Element | null;
}

export interface WorkbenchViewComponentProps {
  editingContextId: string;
  readOnly: boolean;
}

export interface MainAreaComponentProps {
  editingContextId: string;
  readOnly: boolean;
}

export type WorkbenchProps = {
  editingContextId: string;
  initialRepresentationSelected: RepresentationMetadata | null;
  onRepresentationSelected: (representation: RepresentationMetadata | null) => void;
  readOnly: boolean;
  initialWorkbenchConfiguration: WorkbenchConfiguration | null;
};

export type RepresentationComponentProps = {
  editingContextId: string;
  representationId: string;
  readOnly: boolean;
};

export type RepresentationComponent = React.ComponentType<RepresentationComponentProps>;

export type RepresentationComponentFactory = {
  (representationMetadata: RepresentationMetadata): RepresentationComponent | null;
};

export interface WorkbenchHandle {
  getConfiguration(): WorkbenchConfiguration;
}

export interface PanelsHandle {
  getSidePanelConfigurations: () => WorkbenchSidePanelConfiguration[];
}

export interface WorkbenchConfiguration {
  sidePanels: WorkbenchSidePanelConfiguration[];
}

export interface WorkbenchSidePanelConfiguration {
  id: string;
  views: WorkbenchViewConfiguration[];
}

export interface WorkbenchViewConfiguration {
  id: string;
  isActive: boolean;
}
