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
  WorkbenchConfiguration,
  WorkbenchMainPanelConfiguration,
  WorkbenchRepresentationEditorConfiguration,
  WorkbenchSidePanelConfiguration,
  WorkbenchViewConfiguration,
} from '@eclipse-sirius/sirius-components-core';
import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import {
  UseInitialWorkbenchConfigurationState,
  UseInitialWorkbenchConfigurationValue,
  WorkbenchConfigurationPatch,
  WorkbenchMainPanelConfigurationPatch,
  WorkbenchRepresentationEditorConfigurationPatch,
  WorkbenchSidePanelConfigurationPatch,
  WorkbenchViewConfigurationPatch,
} from './useInitialWorkbenchConfiguration.types';
import { useWorkbenchConfiguration } from './useWorkbenchConfiguration';

const patchWorkbenchConfiguration = (
  original: WorkbenchConfiguration,
  patch: WorkbenchConfigurationPatch | null
): WorkbenchConfiguration => {
  return {
    mainPanel:
      !!patch && patch.mainPanel && !!original.mainPanel
        ? patchMainPanel(original.mainPanel, patch.mainPanel)
        : original.mainPanel,
    workbenchPanels:
      !!patch && !!patch.workbenchPanels && (patch.workbenchPanels.length ?? 0) > 0
        ? patchPanels(original.workbenchPanels, patch.workbenchPanels)
        : original.workbenchPanels,
  };
};

const isWorkbenchRepresentationEditorConfiguration = (
  representationEditorPatch: WorkbenchRepresentationEditorConfigurationPatch
): representationEditorPatch is WorkbenchRepresentationEditorConfiguration => {
  return (
    !!representationEditorPatch.representationId &&
    representationEditorPatch.isActive !== null &&
    representationEditorPatch.isActive !== undefined
  );
};

const patchMainPanel = (
  original: WorkbenchMainPanelConfiguration,
  patch: WorkbenchMainPanelConfigurationPatch | null
): WorkbenchMainPanelConfiguration => {
  return {
    id: original.id,
    representationEditors:
      !!patch && !!patch.representationEditors && patch.representationEditors.length > 0
        ? patch.representationEditors.filter(isWorkbenchRepresentationEditorConfiguration)
        : original.representationEditors,
  };
};

const patchPanels = (
  original: WorkbenchSidePanelConfiguration[],
  patch: WorkbenchSidePanelConfigurationPatch[]
): WorkbenchSidePanelConfiguration[] => {
  const patchedPanelsResult: WorkbenchSidePanelConfiguration[] = [];
  original.forEach((side) => {
    const patchSide = patch.find((patchSide) => patchSide.id === side.id);
    if (patchSide) {
      const patchedSide = patchPanel(side, patchSide);
      patchedPanelsResult.push(patchedSide);
    } else {
      patchedPanelsResult.push(side);
    }
  });
  return patchedPanelsResult;
};

const patchPanel = (
  original: WorkbenchSidePanelConfiguration,
  patch: WorkbenchSidePanelConfigurationPatch
): WorkbenchSidePanelConfiguration => {
  return {
    id: original.id,
    isOpen: patch.isOpen !== null && patch.isOpen !== undefined ? patch.isOpen : original.isOpen,
    views: !!patch.views && (patch.views.length ?? 0) > 0 ? patchViews(original.views, patch.views) : original.views,
  };
};

const patchViews = (
  original: WorkbenchViewConfiguration[],
  patch: (WorkbenchViewConfigurationPatch & Record<string, any>)[]
): WorkbenchViewConfiguration[] => {
  const patchedViews: WorkbenchViewConfiguration[] = [];
  original.forEach((view) => {
    const viewPatch = patch.find((viewPatch) => viewPatch.id === view.id);
    if (viewPatch) {
      const patchedView: WorkbenchViewConfiguration = {
        ...viewPatch,
        id: view.id,
        isActive: viewPatch.isActive !== null && viewPatch.isActive !== undefined ? viewPatch.isActive : view.isActive,
      };
      patchedViews.push(patchedView);
    } else {
      patchedViews.push(view);
    }
  });

  return patchedViews;
};

export const useInitialWorkbenchConfiguration = (
  editingContextId: string | null
): UseInitialWorkbenchConfigurationValue => {
  const [urlSearchParams, setSearchParams] = useSearchParams();
  const { workbenchConfiguration, loading } = useWorkbenchConfiguration(
    editingContextId ?? '',
    editingContextId === null
  );

  const [state, setState] = useState<UseInitialWorkbenchConfigurationState>({
    workbenchConfiguration: null,
  });

  useEffect(() => {
    if (workbenchConfiguration && !loading) {
      if (urlSearchParams && urlSearchParams.has('workbenchConfiguration')) {
        const workbenchConfigurationString: string | null = urlSearchParams.get('workbenchConfiguration');
        const urlWorkbenchConfigurationPatch: WorkbenchConfigurationPatch | null = workbenchConfigurationString
          ? JSON.parse(workbenchConfigurationString)
          : null;
        setState((prevState) => ({
          ...prevState,
          workbenchConfiguration: patchWorkbenchConfiguration(workbenchConfiguration, urlWorkbenchConfigurationPatch),
        }));

        urlSearchParams.delete('workbenchConfiguration');
        setSearchParams(urlSearchParams);
      } else {
        setState((prevState) => ({
          ...prevState,
          workbenchConfiguration: workbenchConfiguration,
        }));
      }
    }
  }, [workbenchConfiguration, loading]);

  return { workbenchConfiguration: state.workbenchConfiguration };
};
