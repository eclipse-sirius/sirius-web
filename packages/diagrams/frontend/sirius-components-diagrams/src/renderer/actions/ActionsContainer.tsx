/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { useDebounce } from '@eclipse-sirius/sirius-components-core';
import Box from '@mui/material/Box';
import { useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { Action } from './Action';
import { ActionsContainerProps } from './ActionsContainer.types';
import { useActions } from './useActions';
import { GQLAction } from './useActions.types';

// How long the user has to hover on the element for the actions to be displayed (in ms).
const REVEAL_DELAY = 500;

export const ActionsContainer = ({ diagramElementId }: ActionsContainerProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const delayPassed = useDebounce(true, REVEAL_DELAY);
  const shouldDisplay = delayPassed && !readOnly;

  const { actions } = useActions(diagramElementId, !shouldDisplay);

  if (!shouldDisplay) {
    return null;
  }

  return (
    <Box
      sx={{
        position: 'absolute',
        top: 0,
        left: 0,
        width: '100%',
        height: '100%',
        display: 'grid',
        gridTemplateRows: 'auto',
        gridTemplateColumns: 'minmax(0, 1fr)',
        alignContent: 'start',
        pointerEvents: 'none',
        zIndex: 10,
      }}>
      <Box
        sx={(theme) => ({
          gridRow: 1,
          gridColumn: 1,
          width: 'fit-content',
          justifySelf: 'flex-end',
          display: 'flex',
          flexDirection: 'row',
          alignItems: 'center',
          justifyContent: 'flex-end',
          gap: theme.spacing(1),
          pointerEvents: 'auto',
        })}>
        {actions.map((action: GQLAction) => {
          return <Action key={`action_${action.tooltip}`} action={action} diagramElementId={diagramElementId} />;
        })}
      </Box>
    </Box>
  );
};
