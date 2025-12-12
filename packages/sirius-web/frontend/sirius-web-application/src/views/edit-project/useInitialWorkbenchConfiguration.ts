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
  WorkbenchSidePanelConfiguration,
} from '@eclipse-sirius/sirius-components-core';
import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { UseInitialWorkbenchConfigurationValue } from './useInitialWorkbenchConfiguration.types';
import { useWorkbenchConfiguration } from './useWorkbenchConfiguration';

const patchWorkbenchConfiguration = (
  original: WorkbenchConfiguration,
  patch: WorkbenchConfiguration
): WorkbenchConfiguration => {
  return {
    mainPanel: patchMainPanel(original.mainPanel, patch.mainPanel),
    workbenchPanels: patchPanels(original.workbenchPanels, patch.workbenchPanels),
  };
};

const patchMainPanel = (
  original: WorkbenchMainPanelConfiguration,
  patch: WorkbenchMainPanelConfiguration | null
): WorkbenchMainPanelConfiguration => {
  return {
    id: original.id,
    representationEditors:
      !!patch && patch.representationEditors.length > 0 ? patch.representationEditors : original.representationEditors,
  };
};

const patchPanels = (
  _original: WorkbenchSidePanelConfiguration[],
  patch: WorkbenchSidePanelConfiguration[]
): WorkbenchSidePanelConfiguration[] => {
  return patch;
};

export const useInitialWorkbenchConfiguration = (
  editingContextId: string | null
): UseInitialWorkbenchConfigurationValue => {
  const [urlSearchParams, setSearchParams] = useSearchParams();
  const { workbenchConfiguration, loading } = useWorkbenchConfiguration(editingContextId);

  const [state, setState] = useState<{
    workbenchConfiguration: WorkbenchConfiguration | null;
  }>({
    workbenchConfiguration: null,
  });

  useEffect(() => {
    if (workbenchConfiguration && !loading) {
      if (urlSearchParams && urlSearchParams.has('workbenchConfiguration')) {
        const urlWorkbenchConfigurationPatch: WorkbenchConfiguration = JSON.parse(
          urlSearchParams.get('workbenchConfiguration')
        );

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
