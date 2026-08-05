/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
  RepresentationLoadingIndicator,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { ForwardedRef, forwardRef, useEffect, useState } from 'react';
import { ExplorerSubscriptionProvider } from './ExplorerSubscriptionProvider';
import { ExplorerViewConfiguration, ExplorerViewState } from './ExplorerView.types';
import { useExplorerDescriptions } from './useExplorerDescriptions';

export const ExplorerView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  (
    { editingContextId, id, initialConfiguration, readOnly }: WorkbenchViewComponentProps,
    ref: ForwardedRef<WorkbenchViewHandle>
  ) => {
    const initialExplorerViewConfiguration: ExplorerViewConfiguration =
      initialConfiguration as unknown as ExplorerViewConfiguration;

    const [state, setState] = useState<ExplorerViewState>({
      initialActiveTreeDescriptionId: initialExplorerViewConfiguration?.activeTreeDescriptionId ?? null,
    });

    const { loading, explorerDescriptions } = useExplorerDescriptions(editingContextId);

    useEffect(() => {
      if (explorerDescriptions && explorerDescriptions.length > 0) {
        setState((prevState) => ({
          ...prevState,
          initialActiveTreeDescriptionId: state.initialActiveTreeDescriptionId ?? explorerDescriptions[0].id,
        }));
      }
    }, [explorerDescriptions]);

    const initialTreeFilters = initialExplorerViewConfiguration?.activeTreeFilters ?? [];

    return (
      <Box sx={{ display: 'flex', flexDirection: 'column' }} data-testid="view-Explorer">
        <Box
          sx={(theme) => ({
            display: 'flex',
            flexDirection: 'row',
            borderBottomWidth: '1px',
            borderBottomStyle: 'solid',
            borderBottomColor: theme.palette.divider,
          })}>
          <AccountTreeIcon sx={(theme) => ({ margin: theme.spacing(1) })} />
          <Typography
            sx={(theme) => ({
              marginTop: theme.spacing(1),
              marginRight: theme.spacing(1),
              marginBottom: theme.spacing(1),
            })}>
            Explorer
          </Typography>
        </Box>
        {loading || !state.initialActiveTreeDescriptionId ? (
          <RepresentationLoadingIndicator />
        ) : (
          <ExplorerSubscriptionProvider
            id={id}
            explorerRef={ref}
            editingContextId={editingContextId}
            readOnly={readOnly}
            explorerDescriptions={explorerDescriptions}
            initialActiveTreeDescriptionId={state.initialActiveTreeDescriptionId}
            initialFilters={initialTreeFilters}
          />
        )}
      </Box>
    );
  }
);
