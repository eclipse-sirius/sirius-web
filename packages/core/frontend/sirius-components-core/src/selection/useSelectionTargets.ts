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
import { useWorkbench } from '../workbench/useWorkbench';
import { SelectionTarget, UseSelectionTargetValue } from './useSelectionTargets.types';

export const useSelectionTargets = (): UseSelectionTargetValue => {
  const workbench = useWorkbench();
  const result: SelectionTarget[] = [];
  workbench.getWorkbenchPanelHandles().map((panelHandle) => {
    panelHandle.getWorkbenchViewHandles().map((viewHandle) => {
      const viewId = viewHandle.id;
      const config = viewHandle.getWorkbenchViewConfiguration();
      if (config['selectionTarget']) {
        const target: SelectionTarget = {
          viewId,
          label: config['selectionTarget']['label'] as string,
          icon: config['selectionTarget']['icon'] as React.ReactElement,
          applySelection: config['selectionTarget']['applySelection'] as (selection: any) => void,
        } as SelectionTarget;
        result.push(target);
      }
    });
  });
  return { selectionTargets: result };
};
