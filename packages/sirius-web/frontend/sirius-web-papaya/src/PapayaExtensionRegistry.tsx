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

import {
  ComponentExtension,
  DataExtension,
  ExtensionRegistry,
  IconOverlay,
} from '@eclipse-sirius/sirius-components-core';
import {
  ActionProps,
  DiagramNodeActionOverrideContribution,
  EdgeData,
  NodeData,
  ReactFlowPropsCustomizer,
  diagramNodeActionOverrideContributionExtensionPoint,
  diagramRendererReactFlowPropsCustomizerExtensionPoint,
} from '@eclipse-sirius/sirius-components-diagrams';
import {
  GQLOmniboxCommand,
  OmniboxCommandComponentProps,
  OmniboxCommandOverrideContribution,
  omniboxCommandOverrideContributionExtensionPoint,
} from '@eclipse-sirius/sirius-components-omnibox';
import {
  PaletteQuickToolContributionProps,
  paletteQuickToolExtensionPoint,
} from '@eclipse-sirius/sirius-components-palette';
import {
  NavigationBarProps,
  navigationBarCenterContributionExtensionPoint,
  useCurrentProject,
} from '@eclipse-sirius/sirius-web-application';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Typography from '@mui/material/Typography';
import { Edge, Node, ReactFlowProps, useStoreApi } from '@xyflow/react';
import { PapayaDiagramInformationPanel } from './diagrams/PapayaDiagramInformationPanel';
import { PapayaComponentLabelDetailNodeActionContribution } from './nodeactions/PapayaComponentLabelDetailNodeActionContribution';
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
const diagramPaletteToolContributions: PaletteQuickToolContributionProps[] = [
  {
    canHandle: (diagramElementId: string) => {
      const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
      const diagramElement = store.getState().nodeLookup.get(diagramElementId);
      return diagramElement?.data
        ? diagramElement.data.targetObjectKind.startsWith('siriusComponents://semantic?domain=papaya&entity=Component')
        : false;
    },
    component: PapayaComponentLabelDetailToolContribution,
  },
  {
    canHandle: (diagramElementId: string) => {
      const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
      return !!store.getState().nodeLookup.get(diagramElementId);
    },
    component: PapayaComponentDiagramToolContribution,
  },
];
papayaExtensionRegistry.putData<PaletteQuickToolContributionProps[]>(paletteQuickToolExtensionPoint, {
  identifier: `papaya_${paletteQuickToolExtensionPoint.identifier}`,
  data: diagramPaletteToolContributions,
});

/*******************************************************************************
 *
 * Omnibox command overrides
 *
 * Used to override the default rendering of omnibox commands
 *
 *******************************************************************************/
const ShowDocumentationCommand = ({ command, onKeyDown, onClose }: OmniboxCommandComponentProps) => {
  const handleClick = () => {
    window.open('https://www.github.com/eclipse-sirius/sirius-web', '_blank')?.focus();
    onClose();
  };

  return (
    <ListItemButton key={command.id} data-testid={command.label} onClick={handleClick} onKeyDown={onKeyDown}>
      <ListItemIcon>
        <IconOverlay iconURL={command.iconURLs} alt={command.label} />
      </ListItemIcon>
      <ListItemText sx={{ whiteSpace: 'nowrap', textOverflow: 'ellipsis' }}>{command.label}</ListItemText>
    </ListItemButton>
  );
};

const omniboxCommandOverrides: OmniboxCommandOverrideContribution[] = [
  {
    canHandle: (command: GQLOmniboxCommand) => {
      return command.id === 'showDocumentation';
    },
    component: ShowDocumentationCommand,
  },
];

papayaExtensionRegistry.putData<OmniboxCommandOverrideContribution[]>(
  omniboxCommandOverrideContributionExtensionPoint,
  {
    identifier: `siriusweb_${omniboxCommandOverrideContributionExtensionPoint.identifier}`,
    data: omniboxCommandOverrides,
  }
);

/*******************************************************************************
 *
 * Diagram node action command overrides
 *
 * Used to override the default show label node action
 *
 *******************************************************************************/
const diagramNodeActionOverrides: DiagramNodeActionOverrideContribution[] = [
  {
    canHandle: ({ action }: ActionProps) => {
      return action.id === 'papaya_show_label';
    },
    component: PapayaComponentLabelDetailNodeActionContribution,
  },
];

papayaExtensionRegistry.putData<DiagramNodeActionOverrideContribution[]>(
  diagramNodeActionOverrideContributionExtensionPoint,
  {
    identifier: `siriusweb_${diagramNodeActionOverrideContributionExtensionPoint.identifier}`,
    data: diagramNodeActionOverrides,
  }
);

export { papayaExtensionRegistry };
