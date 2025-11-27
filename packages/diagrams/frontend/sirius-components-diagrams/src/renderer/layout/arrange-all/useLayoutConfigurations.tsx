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
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import ViewComfyIcon from '@mui/icons-material/ViewComfy';
import { LayoutOptions } from 'elkjs/lib/elk-api';
import { useTranslation } from 'react-i18next';
import { useDiagramDescription } from '../../../contexts/useDiagramDescription';
import { LayoutConfiguration, UseLayoutConfigurationsValue } from './useLayoutConfigurations.types';

const elkLayeredOptions = (direction: string): LayoutOptions => ({
  'elk.algorithm': 'layered',
  'elk.layered.spacing.nodeNodeBetweenLayers': '80',
  'layering.strategy': 'NETWORK_SIMPLEX',
  'elk.spacing.componentComponent': '60',
  'elk.spacing.nodeNode': '80',
  'elk.direction': `${direction}`,
  'elk.layered.spacing.edgeNodeBetweenLayers': '80',
  'elk.layered.nodePlacement.strategy': 'NETWORK_SIMPLEX',
});

const elkRectPackingOptions: LayoutOptions = {
  'elk.algorithm': 'rectpacking',
  'elk.spacing.nodeNode': '50',
  'elk.rectpacking.trybox': 'true',
  'widthApproximation.targetWidth': '1',
  'elk.contentAlignment': 'V_TOP H_CENTER',
};

export const useLayoutConfigurations = (): UseLayoutConfigurationsValue => {
  const { diagramDescription } = useDiagramDescription();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'useLayoutConfigurations' });

  const layoutConfigurationWithLayeredAlgorithm: LayoutConfiguration = {
    id: 'elk-layered',
    label: t('arrangeAllLayered'),
    icon: <AccountTreeIcon fontSize="small" />,
    layoutOptions: elkLayeredOptions(diagramDescription.arrangeLayoutDirection),
  };
  const layoutConfigurationWithRectPackingAlgorithm: LayoutConfiguration = {
    id: 'elk-rect-packing',
    label: t('arrangeAllRectPacking'),
    icon: <ViewComfyIcon fontSize="small" />,
    layoutOptions: elkRectPackingOptions,
  };

  return {
    layoutConfigurations: [layoutConfigurationWithLayeredAlgorithm, layoutConfigurationWithRectPackingAlgorithm],
  };
};
