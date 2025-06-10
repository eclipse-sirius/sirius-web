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
import { useContext } from 'react';
import { makeStyles } from 'tss-react/mui';
import { Action } from './Action';
import { ActionsContainerProps } from './ActionsContainer.types';
import { useActions } from './useActions';
import { GQLAction } from './useActions.types';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DiagramContext } from '../../contexts/DiagramContext';

const useStyles = makeStyles()((theme) => ({
  actionsContainer: {
    gridRow: 1,
    gridColumn: 1,
    zIndex: 10,
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-end',
    gap: theme.spacing(1),
  },
}));

export const ActionsContainer = ({ diagramElementId }: ActionsContainerProps) => {
  const { classes } = useStyles();

  const { actions } = useActions(diagramElementId);
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

  if (readOnly) {
    return null;
  }

  return (
    <div className={classes.actionsContainer}>
      {actions.map((action: GQLAction) => {
        return <Action key={`action_${action.tooltip}`} action={action} diagramElementId={diagramElementId} />;
      })}
    </div>
  );
};
