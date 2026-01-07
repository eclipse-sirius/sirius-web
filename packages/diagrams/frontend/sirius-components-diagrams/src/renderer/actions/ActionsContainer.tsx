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
import { useContext, useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { Action } from './Action';
import { ActionsContainerProps } from './ActionsContainer.types';
import { useActions } from './useActions';
import { GQLAction } from './useActions.types';

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

// How long the user has to hover on the element for the actions to be displayed (in ms).
const REVEAL_DELAY = 500;

const useDelayedToggle = (delay: number): boolean => {
  const [toggle, setToggle] = useState<boolean>(false);
  useEffect(() => {
    const timeoutId = setTimeout(() => {
      setToggle(true);
    }, delay);
    return () => clearTimeout(timeoutId);
  }, []);
  return toggle;
};

export const ActionsContainer = ({ diagramElementId }: ActionsContainerProps) => {
  const { classes } = useStyles();

  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const delayPassed = useDelayedToggle(REVEAL_DELAY);
  const shouldDisplay = delayPassed && !readOnly;

  const { actions } = useActions(diagramElementId, !shouldDisplay);

  if (!shouldDisplay) {
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
