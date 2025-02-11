/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
  DiagramPaletteToolContributionProps,
  EdgeData,
  NodeData,
  ReactFlowPropsCustomizer,
  diagramPaletteToolExtensionPoint,
  diagramRendererReactFlowPropsCustomizerExtensionPoint,
} from '@eclipse-sirius/sirius-components-diagrams';
import {
  NavigationBarProps,
  navigationBarCenterContributionExtensionPoint,
  useCurrentProject,
} from '@eclipse-sirius/sirius-web-application';
import Typography from '@mui/material/Typography';
import { Edge, Node, ReactFlowProps } from '@xyflow/react';
import { PapayaDiagramInformationPanel } from './diagrams/PapayaDiagramInformationPanel';
import { PapayaDiagramLegendPanel } from './diagrams/PapayaDiagramLegendPanel';
import { PapayaComponentDiagramToolContribution } from './tools/PapayaComponentDiagramToolContribution';
import { PapayaComponentLabelDetailToolContribution } from './tools/PapayaComponentLabelDetailToolContribution';

const papayaExtensionRegistry = new ExtensionRegistry();

const PapayaProjectNavbarSubtitle = ({ children }: NavigationBarProps) => {
  const { project } = useCurrentProject();

  if (project.natures.filter((nature) => nature.name === 'siriusComponents://nature?kind=papaya').length > 0) {
    return (
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }}>
        {children}
        <Typography variant="caption" style={{ whiteSpace: 'nowrap' }}>
          Sirius Web internal test project
        </Typography>
      </div>
    );
  } else if (project) {
    return <div>{children}</div>;
  } else {
    return null;
  }
};

const navigationBarCenterContributionExtension: ComponentExtension<NavigationBarProps> = {
  identifier: `papaya_${navigationBarCenterContributionExtensionPoint.identifier}`,
  Component: PapayaProjectNavbarSubtitle,
};
papayaExtensionRegistry.addComponent(
  navigationBarCenterContributionExtensionPoint,
  navigationBarCenterContributionExtension
);

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
const diagramPaletteToolContributions: DiagramPaletteToolContributionProps[] = [
  {
    canHandle: (diagramElement: Node<NodeData> | Edge<EdgeData> | null) => {
      return diagramElement?.data
        ? diagramElement.data.targetObjectKind.startsWith('siriusComponents://semantic?domain=papaya&entity=Component')
        : false;
    },
    component: PapayaComponentLabelDetailToolContribution,
  },
  {
    canHandle: (diagramElement: Node<NodeData> | Edge<EdgeData> | null) => diagramElement === null,
    component: PapayaComponentDiagramToolContribution,
  },
];
papayaExtensionRegistry.putData<DiagramPaletteToolContributionProps[]>(diagramPaletteToolExtensionPoint, {
  identifier: `papaya_${diagramPaletteToolExtensionPoint.identifier}`,
  data: diagramPaletteToolContributions,
});

export { papayaExtensionRegistry };
