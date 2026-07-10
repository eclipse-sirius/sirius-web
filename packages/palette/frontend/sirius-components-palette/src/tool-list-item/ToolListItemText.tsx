/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import ListItemText from '@mui/material/ListItemText';
import { makeStyles } from 'tss-react/mui';
import { fuzzyMatch } from '../search/fuzzyMatch';
import { HighlightedLabel } from '../search/HighlightedLabel';
import { ToolListItemTextProps } from './ToolListItemText.types';

const useStyle = makeStyles()(() => ({
  listItemText: {
    '& .MuiListItemText-primary': {
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
}));

export const ToolListItemText = ({ label, searchedValue }: ToolListItemTextProps) => {
  const { classes } = useStyle();
  const matchResult = searchedValue ? fuzzyMatch(label, searchedValue) : null;

  if (!matchResult || !matchResult.matches) {
    return <ListItemText primary={label} className={classes.listItemText} />;
  } else {
    return <HighlightedLabel label={label} textIndicesToHighlight={matchResult.matchingIndices}></HighlightedLabel>;
  }
};
