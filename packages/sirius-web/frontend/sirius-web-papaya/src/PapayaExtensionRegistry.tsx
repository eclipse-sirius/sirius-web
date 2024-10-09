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

import { ComponentExtension, DataExtension, ExtensionRegistry } from '@eclipse-sirius/sirius-components-core';
import {
  EdgeData,
  NodeData,
  ReactFlowPropsCustomizer,
  diagramPaletteToolExtensionPoint,
  diagramRendererReactFlowPropsCustomizerExtensionPoint,
} from '@eclipse-sirius/sirius-components-diagrams';
import {
  EditProjectNavbarSubtitleProps,
  editProjectNavbarSubtitleExtensionPoint,
  useCurrentProject,
} from '@eclipse-sirius/sirius-web-application';
import Typography from '@mui/material/Typography';
import { Edge, Node, ReactFlowProps } from '@xyflow/react';
import { PapayaDiagramInformationPanel } from './diagrams/PapayaDiagramInformationPanel';
import { PapayaDiagramLegendPanel } from './diagrams/PapayaDiagramLegendPanel';
import { PapayaComponentLabelDetailToolContribution } from './tools/PapayaComponentLabelDetailToolContribution';

const papayaExtensionRegistry = new ExtensionRegistry();

const PapayaProjectNavbarSubtitle = ({}: EditProjectNavbarSubtitleProps) => {
  const { project } = useCurrentProject();

  if (project.natures.filter((nature) => nature.name === 'siriusComponents://nature?kind=papaya').length > 0) {
    return (
      <Typography variant="caption" style={{ whiteSpace: 'nowrap' }}>
        Sirius Web internal test project
      </Typography>
    );
  }
  return null;
};

const editProjectNavbarSubtitleExtension: ComponentExtension<EditProjectNavbarSubtitleProps> = {
  identifier: `papaya_${editProjectNavbarSubtitleExtensionPoint.identifier}`,
  Component: PapayaProjectNavbarSubtitle,
};
papayaExtensionRegistry.addComponent(editProjectNavbarSubtitleExtensionPoint, editProjectNavbarSubtitleExtension);

const reactFlowPropsCustomizer: ReactFlowPropsCustomizer = ({
  children,
  ...props
}: ReactFlowProps<Node<NodeData>, Edge<EdgeData>>) => {
  const newChildren = (
    <>
      {children}
      <PapayaDiagramLegendPanel />
      <PapayaDiagramInformationPanel />
    </>
  );
  return { children: newChildren, ...props };
};

const papayaDiagramPanelExtension: DataExtension<Array<ReactFlowPropsCustomizer>> = {
  identifier: `papaya_${diagramRendererReactFlowPropsCustomizerExtensionPoint.identifier}`,
  data: [reactFlowPropsCustomizer],
};
papayaExtensionRegistry.putData(diagramRendererReactFlowPropsCustomizerExtensionPoint, papayaDiagramPanelExtension);

papayaExtensionRegistry.addComponent(diagramPaletteToolExtensionPoint, {
  identifier: `papaya_${diagramPaletteToolExtensionPoint.identifier}`,
  Component: PapayaComponentLabelDetailToolContribution,
});

export { papayaExtensionRegistry };
