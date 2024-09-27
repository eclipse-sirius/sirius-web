/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { makeStyles } from 'tss-react/mui';
import { DeckTagProps } from './DeckTag.types';

const useStyles = makeStyles()(() => ({
  tag: {
    padding: '2px 3px',
    borderRadius: '3px',
    margin: '2px 5px',
    fontSize: '70%',
  },
}));

export const DeckTag = ({ title, color, bgcolor, tagStyle }: DeckTagProps) => {
  const { classes } = useStyles();
  const style = { color: color || 'white', backgroundColor: bgcolor || 'orange', ...tagStyle };
  return (
    <span className={classes.tag} style={style}>
      {title}
    </span>
  );
};
