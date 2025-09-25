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
import WebAssetOutlinedIcon from '@mui/icons-material/WebAssetOutlined';
import { useData } from '../extension/useData';
import { IconOverlay } from '../icon/IconOverlay';
import { useWorkbench } from '../workbench/useWorkbench';
import { workbenchViewContributionExtensionPoint } from '../workbench/WorkbenchExtensionPoints';
import { SelectionTarget, UseSelectionTargetValue } from './useSelectionTargets.types';

export const useSelectionTargets = (): UseSelectionTargetValue => {
  const { data: workbenchViewContributions } = useData(workbenchViewContributionExtensionPoint);
  const workbench = useWorkbench();
  const result: SelectionTarget[] = [];

  workbench.getWorkbenchPanelHandles().map((panelHandle) => {
    panelHandle.getWorkbenchViewHandles().map((viewHandle) => {
      const viewId = viewHandle.id;
      const applySelection = viewHandle.applySelection;
      if (applySelection) {
        const contribution = workbenchViewContributions.find((contrib) => contrib.id === viewId);
        const target: SelectionTarget = {
          viewId,
          label: contribution?.title ?? viewId,
          icon: contribution?.icon ?? <WebAssetOutlinedIcon />,
          applySelection,
        };
        result.push(target);
      }
    });
  });

  const representation = workbench.displayedRepresentationMetadata;
  const representationHandle = workbench.getDisplayedRepresentationHandle();
  if (representation && representationHandle?.applySelection) {
    const target: SelectionTarget = {
      viewId: representation.id,
      label: representation.label,
      icon:
        representation.iconURLs.length > 0 ? (
          <IconOverlay iconURL={representation.iconURLs} alt="representation icon" />
        ) : (
          <WebAssetOutlinedIcon />
        ),
      applySelection: representationHandle.applySelection,
    };
    result.push(target);
  }

  return { selectionTargets: result };
};
